package br.com.sga.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/inicio", "/"})
public class InicioController {

	@GetMapping
	public ModelAndView inicio() {
		ModelAndView mv = new ModelAndView("pages/inicio/inicio");
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("core-auth/login");
		mv.addObject("ipAddress", request.getRemoteAddr());
//		mv.addObject("ipAddress", request.getHeader("X-FORWARDED-FOR"));
		return mv;
	}
}
