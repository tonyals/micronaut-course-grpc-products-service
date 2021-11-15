package br.com.tony.services

import br.com.tony.dto.ProductReq
import br.com.tony.dto.ProductRes

interface ProductService {
    fun create(req: ProductReq): ProductRes
    fun findById(id: Long): ProductRes
}