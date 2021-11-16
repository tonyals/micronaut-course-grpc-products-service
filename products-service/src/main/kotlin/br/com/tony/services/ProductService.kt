package br.com.tony.services

import br.com.tony.dto.ProductReq
import br.com.tony.dto.ProductRes
import br.com.tony.dto.ProductUpdateReq

interface ProductService {
    fun create(req: ProductReq): ProductRes
    fun findById(id: Long): ProductRes
    fun update(req: ProductUpdateReq): ProductRes
    fun delete(id: Long)
}