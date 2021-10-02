package co.com.tul.ecommerce.demo.web.rest.exeption

data class ValidationErrorResponse (
        var violations: MutableList<Violation>
)