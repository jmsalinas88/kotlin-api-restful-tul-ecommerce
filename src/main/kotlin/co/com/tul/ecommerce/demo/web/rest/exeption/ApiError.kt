package co.com.tul.ecommerce.demo.web.rest.exeption

import org.springframework.http.HttpStatus

data class ApiError(
        private var _message : String?,
        var status : HttpStatus = HttpStatus.BAD_REQUEST,
        var code : Int = status.value()
){
    val message : String
    get() = _message ?: "Something went wrong"
}
