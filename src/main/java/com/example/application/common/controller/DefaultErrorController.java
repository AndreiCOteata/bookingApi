package com.example.application.common.controller;

import com.example.application.common.dtos.ErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static com.example.application.common.dtos.CommonErrorResponseType.NOT_FOUND;

@Controller
@ApiIgnore
public class DefaultErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ErrorResponse> error(HttpServletRequest request) throws Throwable {
        Throwable ex = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if(ex != null) {
            throw ex;
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(NOT_FOUND));
    }

}
