package com.fmap.common.exception;

import com.fmap.common.ResponseData;
import com.fmap.common.ResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Valid Exception 발생 시 응답 형식 정의 ( ResponseEntity<ResponseData> )
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseData> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ResponseData responseData = new ResponseData();
        responseData.setResultCode(ResultEnum.FAIL.getResultCode());
        responseData.setResultMessage("VALIDATION_ERROR");
        responseData.setData(errors);

        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
}
