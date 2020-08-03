package br.com.sga.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sga.web.constants.ControllerMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		return new ModelAndView("redirect:/login");
	}
	
	@GetMapping("/login/error")
	public ModelAndView loginError(@RequestParam("message") String message, 
			@RequestParam("username") String username, 
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("redirect:/login");
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("username", username);
		return mv;
	}

	@GetMapping("/logout/success")
	public ModelAndView logoutSuccess() {
		return new ModelAndView("core-auth/logout-success");
	}
	
	@GetMapping("/logout/concurrency")
	public ModelAndView logoutConcurrency() {
		return new ModelAndView("core-auth/logout-concurrency");
	}

	@GetMapping("/denied")
	public ModelAndView accessDenied() {
		return new ModelAndView("core-auth/access-denied");
	}
}
