package com.food.cpg.applicationhandlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestController
public class ApplicationUncaughtExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_VIEW = "/500-error";

    @ExceptionHandler(Exception.class)
    public ModelAndView handleServiceException(Exception ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(INTERNAL_SERVER_ERROR_VIEW);
        return mav;
    }
}
