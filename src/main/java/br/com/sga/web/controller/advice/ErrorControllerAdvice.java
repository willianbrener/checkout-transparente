package br.com.sga.web.controller.advice;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import br.com.sga.core.exception.ExceptionSupport;
import br.com.sga.web.constants.ControllerMapping;

@ControllerAdvice
public class ErrorControllerAdvice {
	
    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Throwable ex) {
		ModelAndView mv = new ModelAndView();

		if (ex instanceof HttpRequestMethodNotSupportedException) {
			mv.setViewName(ControllerMapping.ERRORS_VIEW_METHOD_NOT_SUPPORTED);
			mv.addObject(ControllerMapping.MODEL_ERROR_DETAILS_NAME, ex.getMessage());
			return mv;
		} else if (ex instanceof AccessDeniedException) {
			mv.setViewName(ControllerMapping.AUTH_VIEW_DENIED);
			mv.addObject(ControllerMapping.MODEL_ERROR_DETAILS_NAME, ex.getMessage());
			return mv;
		} else {
			mv.setViewName(ControllerMapping.ERRORS_VIEW_INTERNAL_ERROR);
			mv.addObject(ControllerMapping.MODEL_ERROR_DETAILS_NAME, ExceptionSupport.getExceptionDetails(ex));
			mv.addObject("stackTraceMessage", ExceptionUtils.getStackTrace(ex));
			return mv;
		}
    }
}