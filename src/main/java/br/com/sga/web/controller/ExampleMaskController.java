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

import br.com.sga.core.model.ExampleMaskChildren;
import br.com.sga.core.model.ExampleMask;
import br.com.sga.core.service.ExampleMaskChildrenService;
import br.com.sga.core.service.ExampleMaskService;
import br.com.sga.core.validator.input.ExampleMaskInputValidator;
import br.com.sga.web.constants.ControllerMapping;
import br.com.sga.web.support.Pagination;

@Controller
@RequestMapping("/example-mask")
public class ExampleMaskController {
	
	@Autowired
	private ExampleMaskService exampleMaskService;
	
	@Autowired
	private ExampleMaskInputValidator exampleMaskInputValidator;
	
	@Autowired
	private ExampleMaskChildrenService eListMaskService;
	
	@GetMapping
	public ModelAndView exampleMaskList(ExampleMask exampleMask, @PageableDefault Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pages/example-mask/example-mask-list");

		Pagination<ExampleMask> page = new Pagination<>(exampleMaskService.findAll(pageable), request);
		
		mv.addObject("page", page);
		mv.addObject("exampleMask", exampleMask);
		
		return mv;
	}

	@GetMapping("/form")
	public ModelAndView exampleMaskForm(ExampleMask exampleMask) {
		ModelAndView mv = new ModelAndView("pages/example-mask/example-mask-form");
		
		mv.addObject("exampleMask", exampleMask);
		
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView save(ExampleMask exampleMask, BindingResult result, RedirectAttributes redirectAttributes) {
		
		this.exampleMaskInputValidator.validateSave(exampleMask, result);
		
		if (!result.hasErrors()) {
			this.exampleMaskService.save(exampleMask);
			
			ModelAndView mv = new ModelAndView("redirect:/example-mask/form/"+exampleMask.getId());
			redirectAttributes.addFlashAttribute(ControllerMapping.FORM_SUCCESS_MESSAGE_KEY, "Example Mask salva com sucesso!");
			return mv;
		} else {
			return exampleMaskForm(exampleMask);
		}
	}
	
	@GetMapping("/form/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("pages/example-mask/example-mask-form");
		
		ExampleMask exampleMask = this.exampleMaskService.findById(id);
		
		mv.addObject("exampleMask", exampleMask);
		
		return mv;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		
		ExampleMask exampleMask = this.exampleMaskService.findById(id);
		
		this.exampleMaskService.delete(exampleMask);
		
		ModelAndView mv = new ModelAndView("redirect:/example-mask");
		
		redirectAttributes.addFlashAttribute(ControllerMapping.FORM_SUCCESS_MESSAGE_KEY, "Example Mask excluída com sucesso!");
		
		return mv;
	}
	
	@PostMapping(value="/list-example-mask-children/adicionar")
	public String adicionarListMask(ExampleMask exampleMask, Model model, BindingResult result, HttpServletRequest request) {

		this.exampleMaskInputValidator.validateAddEListMask(exampleMask, result);
		
		if (!result.hasErrors()) {
			ExampleMask exampleMaskAdd = new ExampleMask();
			exampleMaskAdd.setId(exampleMask.getId());
			
			ExampleMaskChildren exampleMaskChildrenAdd = exampleMask.getExampleMaskChildrenInputDTO().getInputExampleMaskChildren();
			exampleMaskChildrenAdd.setExampleMask(exampleMaskAdd);
			
			eListMaskService.save(exampleMaskChildrenAdd);
			
			model.addAttribute("succesListExampleMaskChildren", "Example Mask Children '"+exampleMaskChildrenAdd.getDescricao()+"' adicionado com sucesso!");
			
			//Atualiza lista
			exampleMask.setListExampleMaskChildren(eListMaskService.findByExampleMask(exampleMask));

			//Limpa elementos da tela
			exampleMask.getExampleMaskChildrenInputDTO().setInputExampleMaskChildren(new ExampleMaskChildren());
			exampleMask.getExampleMaskChildrenInputDTO().setIndiceEdicaoExampleMaskChildren(null);
		}else{
			model.addAttribute("errosListExampleMaskChildren", result.getFieldErrors());
		}
		
		return "pages/example-mask/example-mask-form :: fragment-list-example-mask-children";
	}

	@PostMapping(value="/list-example-mask-children/cancelar")
	public String cancelarListMask(ExampleMask exampleMask, Model model, BindingResult result, HttpServletRequest request) {
		
		//Limpa elementos da tela
		exampleMask.getExampleMaskChildrenInputDTO().setInputExampleMaskChildren(new ExampleMaskChildren());
		exampleMask.getExampleMaskChildrenInputDTO().setIndiceEdicaoExampleMaskChildren(null);

		return "pages/example-mask/example-mask-form :: fragment-list-example-mask-children";
	}

	@PostMapping(value="/list-example-mask-children/editar/{indice}")
	public String editarListMask(@PathVariable Integer indice, ExampleMask exampleMask, Model model, BindingResult result, HttpServletRequest request) {
		ExampleMaskChildren listMask = exampleMask.getListExampleMaskChildren().get(indice);

		//Definindo elemtnos na tela
		exampleMask.getExampleMaskChildrenInputDTO().setInputExampleMaskChildren(listMask);
		exampleMask.getExampleMaskChildrenInputDTO().setIndiceEdicaoExampleMaskChildren(indice);

		return "pages/example-mask/example-mask-form :: fragment-list-example-mask-children";
	}

	@PostMapping(value="/list-example-mask-children/remover/{indice}")
	public String removerListMask(@PathVariable Integer indice, ExampleMask exampleMask, Model model, BindingResult result, HttpServletRequest request) {
		ExampleMaskChildren exampleMaskChildren = exampleMask.getListExampleMaskChildren().get(indice);

		this.exampleMaskInputValidator.validateRemoveEListMask(exampleMask, result);

		if (!result.hasErrors()) {
			
			eListMaskService.delete(exampleMaskChildren);
			
			//Atualiza lista
			exampleMask.setListExampleMaskChildren(eListMaskService.findByExampleMask(exampleMask));

			//Limpa os campos da sujeito
			exampleMask.getExampleMaskChildrenInputDTO().setInputExampleMaskChildren(new ExampleMaskChildren());
			exampleMask.getExampleMaskChildrenInputDTO().setIndiceEdicaoExampleMaskChildren(null);
		}else{
			model.addAttribute("errosListExampleMaskChildren", result.getFieldErrors());
		}
		
		return "pages/example-mask/example-mask-form :: fragment-list-example-mask-children";
	}
}