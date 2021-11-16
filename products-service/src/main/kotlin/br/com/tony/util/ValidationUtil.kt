package br.com.tony.util

import br.com.tony.ProductServiceRequest
import br.com.tony.exceptions.InvalidArgumentException

class ValidationUtil {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?): ProductServiceRequest {
            payload?.let {
                if (it.name.isNullOrBlank())
                    throw InvalidArgumentException("nome")

                if (it.price.isNaN() || it.price < 0)
                    throw InvalidArgumentException("preço")
                return it
            }
            throw InvalidArgumentException("payload")
        }
    }
}