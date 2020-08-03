package br.com.sga.web.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sga.web.constants.ControllerMapping;

@Controller
@RequestMapping(ControllerMapping.ERRORS_PATH_ROOT)
public class WeblogicErrorController {
	
	@GetMapping(ControllerMapping.ERRORS_PATH_404)
	public ModelAndView notFound(){
		return new ModelAndView(ControllerMapping.ERRORS_VIEW_PAGE_NOT_FOUND);
	}
	
	@GetMapping(ControllerMapping.ERRORS_PATH_405)
	public ModelAndView notSupported(){
		return new ModelAndView(ControllerMapping.ERRORS_VIEW_METHOD_NOT_SUPPORTED);
	}
	
	@GetMapping(ControllerMapping.ERRORS_PATH_500)
	public ModelAndView internalError() {
		return new ModelAndView(ControllerMapping.ERRORS_VIEW_INTERNAL_ERROR);
	}
}