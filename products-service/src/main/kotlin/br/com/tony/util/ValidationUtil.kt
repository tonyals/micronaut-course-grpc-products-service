package br.com.tony.util

import br.com.tony.ProductServiceRequest

class ValidationUtil {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?): ProductServiceRequest {
            payload?.let {
                if (it.name.isNullOrBlank())
                    throw IllegalArgumentException("Nome não pode ser nulo ou vazio")

                if (it.price.isNaN())
                    throw IllegalArgumentException("Preço precisa ser um valor válido")
                return it
            }
            throw IllegalArgumentException()
        }
    }
}