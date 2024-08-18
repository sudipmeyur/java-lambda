package org.example.exception.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.example.exception.WeatherException;
import org.example.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class RestResponseEntityExceptionHandler{

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleWeatherException(final WeatherException exception
            ,final HttpServletRequest request) {

        return ExceptionResponse.builder()
                .errorMessage(exception.getMessage())
                .rootErrorMessage(exception.getRootErrorMsg())
                .callerURL(request.getRequestURI())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleValidationExceptions(
            final MethodArgumentNotValidException ex,final HttpServletRequest request) {


        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        return ExceptionResponse.builder()
                .errorMessage(((FieldError) objectError).getField())
                .rootErrorMessage(objectError.getDefaultMessage())
                .callerURL(request.getRequestURI())
                .build();
    }
}
