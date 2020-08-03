package br.com.sga.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/payment"})
public class PaymentController {

	@GetMapping
	public ModelAndView fecharPedido() {
		ModelAndView mv = new ModelAndView("pages/payment/payment-form");
		
		return mv;
	}
}
