package co.com.tul.ecommerce.demo.web.rest.exeption

import co.com.tul.ecommerce.demo.exception.CartException
import co.com.tul.ecommerce.demo.exception.CartNotFoundException
import co.com.tul.ecommerce.demo.exception.ProductException
import co.com.tul.ecommerce.demo.exception.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ProductException::class)
    fun productExceptionHandler(exception: ProductException): ResponseEntity<ApiError>{
        val error = ApiError(exception.message)
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun productNotFoundExceptionHandler(exception: ProductNotFoundException): ResponseEntity<ApiError>{
        val error = ApiError(exception.message, HttpStatus.NOT_FOUND)
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(CartException::class)
    fun cartExceptionHandler(exception: CartException): ResponseEntity<ApiError>{
        val error = ApiError(exception.message)
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(CartNotFoundException::class)
    fun cartNotFoundExceptionHandler(exception: CartNotFoundException): ResponseEntity<ApiError>{
        val error = ApiError(exception.message, HttpStatus.NOT_FOUND)
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onMethodArgumentNotValidException(e: MethodArgumentNotValidException): ValidationErrorResponse? {
        val error = ValidationErrorResponse(mutableListOf())
        for (fieldError in e.bindingResult.fieldErrors) {
            error.violations.add(Violation(fieldError.field, fieldError.defaultMessage!!))
        }
        return error
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onConstraintValidationException(e: ConstraintViolationException): ValidationErrorResponse? {
        val error = ValidationErrorResponse(mutableListOf())
        for (violation in e.constraintViolations) {
            error.violations.add(Violation(violation.propertyPath.toString(), violation.message))
        }
        return error
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleUnprosseasableMsgException(exception: HttpMessageNotReadableException): ResponseEntity<ApiError> {
        val error = ApiError(exception.message)
        return ResponseEntity(error, error.status)
    }

    @ExceptionHandler(Exception::class)
    fun generalExceptionHandler(exception: Exception): ResponseEntity<ApiError>{
        val error = ApiError(exception.message, HttpStatus.INTERNAL_SERVER_ERROR)
        return ResponseEntity(error, error.status)
    }

}