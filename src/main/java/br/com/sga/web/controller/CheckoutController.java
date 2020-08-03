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
@RequestMapping("/checkout")
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

		ProductDTO products = new ProductDTO(1,"Tenis Nike", "/resources/core-images/checkout/tenisnike.png", 650.00, 1, 650.00);

		mv.addObject("checkoutDTO", new CheckoutDTO(650.00, Arrays.asList(products)));

		return mv;
	}

	@PostMapping("/product/add-value/{indice}")
	public ModelAndView addValue(@PathVariable Integer indice, CheckoutDTO checkoutDTO, BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("pages/checkout/checkout-form :: fragmento-tabela");

		this.checkoutService.addValue(checkoutDTO, indice);
		checkoutDTO.setPrecoTotal(checkoutService.calculaValorTotalProdutos(checkoutDTO.getProducts()));

		mv.addObject("checkoutDTO", checkoutDTO);

		return mv;
	}

	@PostMapping("/product/remove-value/{indice}")
	public ModelAndView removeValue(@PathVariable Integer indice, CheckoutDTO checkoutDTO, BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("pages/checkout/checkout-form :: fragmento-tabela");

		this.checkoutService.removeValue(checkoutDTO, indice);
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


	@GetMapping("/session")
	public @ResponseBody String sessionPagSeguro() {
		return checkoutService.getIdPagSeguro();
	}

}
