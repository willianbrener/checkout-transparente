package br.com.sga.web.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sga.core.dto.CheckoutDTO;
import br.com.sga.core.dto.PaymentDTO;
import br.com.sga.core.dto.ProductDTO;
import br.com.sga.core.enumerable.EnumPaymentType;
import br.com.sga.core.exception.BusinessValidationException;
import br.com.sga.core.service.CheckoutService;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;

@Controller
@RequestMapping("/custom-checkout")
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;

	@GetMapping
	public ModelAndView index() {
		return this.form();
	}

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("pages/checkout/checkout-form");

		ProductDTO products = new ProductDTO(1,"Tenis Nike", "/resources/core-images/checkout/tenisnike.png", 1.00, 1, 1.00);

		mv.addObject("checkoutDTO", new CheckoutDTO(1.00, Arrays.asList(products)));

		return mv;
	}
	
	@PostMapping("/product/change-value/{indice}/{novaQuantidade}")
	public ModelAndView changeValue(@PathVariable Integer indice, @PathVariable Integer novaQuantidade, CheckoutDTO checkoutDTO, BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("pages/checkout/checkout-form :: fragmento-tabela");

		this.checkoutService.changeValue(checkoutDTO, indice, novaQuantidade);
		checkoutDTO.setPrecoTotal(checkoutService.calculaValorTotalProdutos(checkoutDTO.getProducts()));

		mv.addObject("checkoutDTO", checkoutDTO);

		return mv;
	}

	@PostMapping("/payment")
	public ModelAndView payment(CheckoutDTO checkoutDTO, BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("pages/checkout/checkout-payment-form");

		checkoutDTO = checkoutDTO != null ? checkoutDTO : new CheckoutDTO();

		Double valorTotalProdutos = checkoutService.calculaValorTotalProdutos(checkoutDTO.getProducts());
		Integer quantidadeTotalProdutos = checkoutService.calculaQuantidadeTotalProdutos(checkoutDTO.getProducts());

		mv.addObject("checkoutDTO", checkoutDTO);
		mv.addObject("valorTotalProdutos", valorTotalProdutos);
		mv.addObject("quantidadeProdutos", quantidadeTotalProdutos);
		mv.addObject("paymentDTO", new PaymentDTO(checkoutDTO, valorTotalProdutos, quantidadeTotalProdutos));

		return mv;
	}

	@PostMapping("/payment/finish")
	public String  paymentFinish(PaymentDTO paymentDTO, BindingResult result, RedirectAttributes redirectAttributes) throws BusinessValidationException {
		//ModelAndView mv = new ModelAndView("pages/checkout/checkout-payment-finish-form");

		TransactionDetail transaction = checkoutService.realizaCompra(paymentDTO);

		EnumPaymentType tipoDePagamento = EnumPaymentType.getByValueField(paymentDTO.getPaymentType());
		if(transaction != null && !tipoDePagamento.equals(EnumPaymentType.CARTAO_CREDITO)) {
			//return new ModelAndView("redirect:" + transaction.getPaymentLink());
			return "redirect:" + transaction.getPaymentLink();
		}

		return "pages/checkout/checkout-payment-finish-form";
		//return mv;
	}


	@GetMapping("/pagseguro/session")
	public @ResponseBody String sessionPagSeguro() {
		return checkoutService.getIdPagSeguro();
	}

}
