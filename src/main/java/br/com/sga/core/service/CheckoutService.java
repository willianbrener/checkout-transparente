package br.com.sga.core.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sga.core.dto.AddressDTO;
import br.com.sga.core.dto.CheckoutDTO;
import br.com.sga.core.dto.PaymentDTO;
import br.com.sga.core.dto.ProductDTO;
import br.com.sga.core.dto.SenderDTO;
import br.com.sga.core.enumerable.EnumPaymentType;
import br.com.sga.core.exception.BusinessValidationException;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.Address;
import br.com.uol.pagseguro.api.common.domain.BankName;
import br.com.uol.pagseguro.api.common.domain.CreditCard;
import br.com.uol.pagseguro.api.common.domain.Holder;
import br.com.uol.pagseguro.api.common.domain.Installment;
import br.com.uol.pagseguro.api.common.domain.PaymentItem;
import br.com.uol.pagseguro.api.common.domain.Sender;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.BankBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.CreditCardBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.DocumentBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.HolderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.InstallmentBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.DocumentType;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.exception.PagSeguroBadRequestException;
import br.com.uol.pagseguro.api.exception.ServerError;
import br.com.uol.pagseguro.api.session.CreatedSession;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;

@Service
public class CheckoutService {

	@Value( "${email}" )
	private String emailPagSeguro;

	@Value( "${token}" )
	private String tokenPagSeguro;
	
	@Value( "${ambiente}" )
	private String ambientePagSeguro;
	
	@Value( "${url-checkout}" )
	private String urlCheckout;
	

	public Double calculaValorTotalProdutos(List<ProductDTO> produtos) {
		Double valorTotal = 0.0;

		for (ProductDTO productDTO : produtos) {
			valorTotal += productDTO.getPrecoTotalProduto();
		}

		return valorTotal;
	}

	public Integer calculaQuantidadeTotalProdutos(List<ProductDTO> produtos) {
		Integer qtdTotal = 0;

		for (ProductDTO productDTO : produtos) {
			qtdTotal += productDTO.getQuantidadeProduto();
		}

		return qtdTotal;
	}

	public TransactionDetail realizaCompra(PaymentDTO paymentDTO) throws BusinessValidationException {
		try {
			final PagSeguro pagSeguro = PagSeguro.instance(
					Credential.sellerCredential(emailPagSeguro, tokenPagSeguro),
					PagSeguroEnv.fromName(ambientePagSeguro));
			
			List<PaymentItem> items = new ArrayList<>();
			Sender sender = null;
			Address addressSender = null;
			Holder holder = null;
			CreditCard creditCard = null;
			TransactionDetail transaction = null;
			
			if(paymentDTO != null) {
				List<ProductDTO> produtos = paymentDTO.getCheckoutDTO().getProducts();
				SenderDTO senderDTO = paymentDTO.getSenderDTO();
				AddressDTO addressDTO = paymentDTO.getSenderDTO().getAddressDTO();
				
				if(produtos != null && !produtos.isEmpty()) {
					items = montaItems(items, produtos);
					sender = montaSender(senderDTO);
					addressSender = montaAddressSender(addressDTO);
					holder = montaHolder(senderDTO);
					creditCard = montaCreditCard(paymentDTO, addressSender, holder);
					
					EnumPaymentType tipoDePagamento = EnumPaymentType.getByValueField(paymentDTO.getPaymentType());
					BankName.Name bankName = BankName.Name.fromName(paymentDTO.getBankSelected());
					
					if(tipoDePagamento.equals(EnumPaymentType.CARTAO_CREDITO)) {
						transaction = transacaoPagamentoCredito(pagSeguro, items, sender, addressSender, creditCard);
					} else if(tipoDePagamento.equals(EnumPaymentType.DEBITO_ONLINE)) {
						transaction = transacaoPagamentoDebito(pagSeguro, items, sender, addressSender, bankName);
						
					} else if(tipoDePagamento.equals(EnumPaymentType.BOLETO)) {
						transaction = transacaoPagamentoBoleto(pagSeguro, items, sender, addressSender);
					}
					
					return transaction;
				}
			}
		} catch(PagSeguroBadRequestException e) {
			e.printStackTrace();
			Iterable<? extends ServerError> erros = e.getErrors().getErrors();
			StringBuilder errosConcat = new StringBuilder();
			for (ServerError serverError : erros) {
				System.out.println(serverError.getMessage());
				errosConcat.append(serverError.getMessage()+ " ");
			}
			
			throw new BusinessValidationException(errosConcat.toString());
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessValidationException("Erro ao processar os dados para pagamento.");
		}
		
		return null;
	}

	private TransactionDetail transacaoPagamentoCredito(final PagSeguro pagSeguro, List<PaymentItem> items, 
			Sender sender, Address addressSender, CreditCard creditCard) {
		TransactionDetail transaction;
		transaction = 
		pagSeguro
		.transactions()
		.register(
			new DirectPaymentRegistrationBuilder()
			.withPaymentMode("default")
			.withCurrency(Currency.BRL)
			.addItems(items)
			.withNotificationURL("www.lojateste.com.br/notification")
			.withReference("DIRECT_PAYMENT")
			.withSender(sender)
			.withShipping(
				new ShippingBuilder()
				.withAddress(addressSender)
				.withType(ShippingType.Type.USER_CHOISE)
				.withCost(new BigDecimal(0.00))
			)
		).withCreditCard(creditCard);
		return transaction;
	}

	private TransactionDetail transacaoPagamentoDebito(final PagSeguro pagSeguro, List<PaymentItem> items,
			Sender sender, Address addressSender, BankName.Name bankName) {
		TransactionDetail transaction;
		transaction = 
		pagSeguro
		.transactions()
		.register(
			new DirectPaymentRegistrationBuilder()
			.withPaymentMode("default")
			.withCurrency(Currency.BRL)
			.addItems(items)
			.withNotificationURL("www.lojateste.com.br/notification")
			.withReference("DIRECT_PAYMENT")
			.withSender(sender)
			.withShipping(
				new ShippingBuilder()
				.withAddress(addressSender)
				.withType(ShippingType.Type.USER_CHOISE)
				.withCost(new BigDecimal(0.00))
			)
		).withOnlineDebit(new BankBuilder().withName(bankName));
		return transaction;
	}

	private TransactionDetail transacaoPagamentoBoleto(final PagSeguro pagSeguro, List<PaymentItem> items,
			Sender sender, Address addressSender) {
		TransactionDetail transaction;
		transaction = 
		pagSeguro
		.transactions()
		.register(
			new DirectPaymentRegistrationBuilder()
			.withPaymentMode("default")
			.withCurrency(Currency.BRL)
			.addItems(items)
			.withNotificationURL(
					"www.lojateste.com.br/notification")
			.withReference("DIRECT_PAYMENT")
			.withSender(sender)
			.withShipping(
				new ShippingBuilder()
				.withAddress(addressSender)
				.withType(ShippingType.Type.USER_CHOISE)
				.withCost(new BigDecimal(0.00))
			)
		).withBankSlip();
		
		return transaction;
	}

	private CreditCard montaCreditCard(PaymentDTO paymentDTO, Address addressSender, Holder holder) {
		Installment installment;
		CreditCardBuilder creditCardBuilder;
		CreditCard creditCard;
		creditCardBuilder = new CreditCardBuilder()
				.withBillingAddress(addressSender)
				.withHolder(holder)
				.withToken(paymentDTO.getCreditCardToken());
		
		if(paymentDTO.getInstallmentSelected() != null && !paymentDTO.getInstallmentSelected().isEmpty()) {
			String[] installmentDetails = paymentDTO.getInstallmentSelected().split("x");
			installment = new InstallmentBuilder()
					.withQuantity(new Integer(installmentDetails[0]))
					.withValue(new BigDecimal(installmentDetails[1]))
					.withNoInterestInstallmentQuantity(12).build();
			
			creditCardBuilder.withInstallment(installment);
		}
		
		creditCard = creditCardBuilder.build();
		
		return creditCard;
	}

	private Holder montaHolder(SenderDTO senderDTO) {
		Holder holder = null;
		
		String phoneNumber = senderDTO.getPhoneDTO().getNumber() != null ? senderDTO.getPhoneDTO().getNumber() : "";
		phoneNumber = phoneNumber.replace("-", "");
		String cpf = senderDTO.getCpf() != null ? senderDTO.getCpf().replace(".", "").replace("-", "") : "";
		
		try {
			holder = new HolderBuilder()
				.addDocument(
					new DocumentBuilder()
					.withType(DocumentType.CPF)
					.withValue(cpf)
				)
				.withName(senderDTO.getName())
				.withBithDate(new SimpleDateFormat("dd/MM/yyyy").parse(senderDTO.getBirthDate()))
				.withPhone(
					new PhoneBuilder()
					.withAreaCode(senderDTO.getPhoneDTO().getAreaCode())
					.withNumber(phoneNumber)
				).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return holder;
	}

	private Address montaAddressSender(AddressDTO ddressDTO) {
		String cep = ddressDTO.getPostalCode() != null ? ddressDTO.getPostalCode() : "";
		cep = cep.replace(".", "").replace("-", "");
		
		return new AddressBuilder()
				.withStreet(ddressDTO.getStreet())
				.withNumber(ddressDTO.getNumber())
				.withComplement(ddressDTO.getComplement())
				.withDistrict(ddressDTO.getDistrict())
				.withPostalCode(cep)
				.withCity(ddressDTO.getCity())
				.withState(State.GO)
				.withCountry("BRA").build();
	}

	private Sender montaSender(SenderDTO senderDTO) {
		String cpf = senderDTO.getCpf() != null ? senderDTO.getCpf().replace(".", "").replace("-", "") : "";
		
		String phoneNumber = senderDTO.getPhoneDTO().getNumber() != null ? senderDTO.getPhoneDTO().getNumber() : "";
		phoneNumber = phoneNumber.replace("-", "");
		return new SenderBuilder()
				.withName(senderDTO.getName())
				.withCPF(cpf)
				.withPhone(
					new PhoneBuilder()
					.withAreaCode(senderDTO.getPhoneDTO().getAreaCode())
					.withNumber(phoneNumber)
				)
				.withEmail(senderDTO.getEmail())
				.withHash(senderDTO.getHash()).build();
	}

	private List<PaymentItem> montaItems(List<PaymentItem> items, List<ProductDTO> produtos) {
		Integer auxId = 1;
		
		for (ProductDTO productDTO : produtos) {
			items.add(
				new PaymentItemBuilder()
				.withId(auxId.toString())
				.withDescription(productDTO.getNomeProduto())
				.withQuantity(productDTO.getQuantidadeProduto())
				.withAmount(new BigDecimal(productDTO.getPrecoProduto()))
				.build()
			);
			
			auxId++;
		}
		
		return items;
	}

	public String getIdPagSeguro() {
		try {
			final PagSeguro pagSeguro = PagSeguro.instance(
					Credential.sellerCredential(emailPagSeguro, tokenPagSeguro),
					PagSeguroEnv.fromName(ambientePagSeguro));
			
			CreatedSession createdSession = pagSeguro.sessions().create();
			return createdSession.getId();
		} catch (Exception e) {
			return "";
		}
	}

	public void addValue(CheckoutDTO checkoutDTO, Integer indice) {
		if (indice != null && checkoutDTO != null && checkoutDTO.getProducts() != null 
				&& !checkoutDTO.getProducts().isEmpty()) {
			List<ProductDTO> productDTOs = checkoutDTO.getProducts();
			ProductDTO produto = productDTOs.get(indice);
			
			if (produto != null) {
				Integer novaQtdProduto = produto.getQuantidadeProduto() + 1;
				produto.setQuantidadeProduto(novaQtdProduto);
				produto.setPrecoTotalProduto(novaQtdProduto * produto.getPrecoProduto());
			}
		}
		
	}

	public void removeValue(CheckoutDTO checkoutDTO, Integer indice) {
		if (indice != null && checkoutDTO != null && checkoutDTO.getProducts() != null 
				&& !checkoutDTO.getProducts().isEmpty()) {
			List<ProductDTO> productDTOs = checkoutDTO.getProducts();
			ProductDTO produto = productDTOs.get(indice);
			
			if (produto != null && produto.getQuantidadeProduto() != 0) {
				Integer novaQtdProduto = produto.getQuantidadeProduto() - 1;
				produto.setQuantidadeProduto(novaQtdProduto);
				produto.setPrecoTotalProduto(novaQtdProduto * produto.getPrecoProduto());
			}
		}
		
	}

	public void changeValue(CheckoutDTO checkoutDTO, Integer indice, Integer novaQuantidade) {
		if (indice != null && checkoutDTO != null && checkoutDTO.getProducts() != null 
				&& !checkoutDTO.getProducts().isEmpty() && novaQuantidade != null) {
			List<ProductDTO> productDTOs = checkoutDTO.getProducts();
			ProductDTO produto = productDTOs.get(indice);
			
			if (produto != null) {
				produto.setQuantidadeProduto(novaQuantidade);
				produto.setPrecoTotalProduto(novaQuantidade * produto.getPrecoProduto());
			}
		}
	}
}
