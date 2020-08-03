package br.com.sga.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sga.core.model.Country;
import br.com.sga.core.service.CountryService;
import br.com.sga.core.validator.input.CountryInputValidator;
import br.com.sga.web.constants.ControllerMapping;
import br.com.sga.web.support.Pagination;

@Controller
@RequestMapping("/country")
public class CountryController {
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private CountryInputValidator countryInputValidator;
	
	@GetMapping
	public ModelAndView countryList(@PageableDefault Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pages/country/country-list");

		Pagination<Country> page = new Pagination<>(countryService.findAll(pageable), request);
		
		mv.addObject("page", page);
		
		return mv;
	}

	@GetMapping("/form")
	public ModelAndView countryForm(Country country) {
		ModelAndView mv = new ModelAndView("pages/country/country-form");
		
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView save(Country country, BindingResult result, RedirectAttributes redirectAttributes) {
		
		this.countryInputValidator.validateSave(country, result);
		
		if (!result.hasErrors()) {
			
			this.countryService.save(country);
			
			ModelAndView mv = new ModelAndView("redirect:/country");
			redirectAttributes.addFlashAttribute(ControllerMapping.FORM_SUCCESS_MESSAGE_KEY, "Country salva com sucesso!");
			return mv;
		} else {
			return countryForm(country);
		}
	}
	
	@GetMapping("/form/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("pages/country/country-form");
		
		Country country = this.countryService.findById(id);
		
		mv.addObject("country", country);
		
		return mv;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		
		Country country = this.countryService.findById(id);
		
		this.countryService.delete(country);
		
		ModelAndView mv = new ModelAndView("redirect:/country");
		
		redirectAttributes.addFlashAttribute(ControllerMapping.FORM_SUCCESS_MESSAGE_KEY, "Country excluído com sucesso!");
		
		return mv;
	}
	
	@GetMapping("/modal-list")
	public String modalList(@PageableDefault Pageable pageable, Country countryFilter, Model model, BindingResult bindingResult, HttpServletRequest request) {

		Pagination<Country> page = new Pagination<>(countryService.findAll(countryFilter, pageable), request);
		
		model.addAttribute("pageCountry", page);

		return "pages/country/country-modal-list :: tabela-pesquisa";
	}
	
//	@PostMapping("/delete/{id}")
//	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//		
//		Country country = this.countryService.findById(id);
//		
//		this.countryService.delete(country);
//
//		return "country/country-list :: fragment-table-content";
//	}
}