package br.com.sga.web.controller.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.sga.core.exception.ExceptionSupport;
import br.com.sga.web.constants.ControllerMapping;

@Controller
public class SpringBootErrorController implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		
//		int status = response.getStatus();
		int status = (Integer) request.getAttribute("javax.servlet.error.status_code");
		
		if (request.getAttribute("javax.servlet.error.exception") != null) {
			Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}

		if(status == HttpStatus.NOT_FOUND.value()) {
			mv.setViewName(ControllerMapping.ERRORS_VIEW_PAGE_NOT_FOUND);
		}
		else if(status == HttpStatus.METHOD_NOT_ALLOWED.value()) {
			mv.setViewName(ControllerMapping.ERRORS_VIEW_METHOD_NOT_SUPPORTED);
		}
		else if(status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			mv.setViewName(ControllerMapping.ERRORS_VIEW_INTERNAL_ERROR);
		}
		else {
			mv.setViewName(ControllerMapping.ERRORS_VIEW_INTERNAL_ERROR);
		}

		return mv;

//		if (request.getAttribute("javax.servlet.error.exception") != null && request.getAttribute("javax.servlet.error.exception") instanceof Exception) {
//			Throwable ex = ((Throwable) request.getAttribute("javax.servlet.error.exception"));
//			
//	        if(ex instanceof HttpRequestMethodNotSupportedException) {
//	    		ModelAndView mv = new ModelAndView(ControllerMapping.ERRORS_VIEW_METHOD_NOT_SUPPORTED);
//	    		return mv;
//	    		
//	        } else if(ex instanceof AccessDeniedException) {
//	    		ModelAndView mv = new ModelAndView(ControllerMapping.AUTH_VIEW_DENIED);
//	    		mv.addObject(ControllerMapping.MODEL_ERROR_DETAILS_NAME, ex.getMessage());
//	    		return mv;
//	        } else {
//	    		ModelAndView mv = new ModelAndView(ControllerMapping.ERRORS_VIEW_INTERNAL_ERROR);
//	    		mv.addObject(ControllerMapping.MODEL_ERROR_DETAILS_NAME, ExceptionSupport.getExceptionDetails(ex));
//	    		mv.addObject("stackTraceMessage", ExceptionUtils.getStackTrace(ex));
//	        }
//		}
		
//		ModelAndView mv = new ModelAndView(ControllerMapping.ERRORS_VIEW_INTERNAL_ERROR);
//		return mv;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}