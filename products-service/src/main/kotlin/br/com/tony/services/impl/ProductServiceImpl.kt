package br.com.tony.services.impl

import br.com.tony.dto.ProductReq
import br.com.tony.dto.ProductRes
import br.com.tony.repository.ProductRepository
import br.com.tony.services.ProductService
import br.com.tony.util.toDomain
import br.com.tony.util.toProductRes
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun create(req: ProductReq): ProductRes {
        val productSaved = productRepository.save(req.toDomain())
        return productSaved.toProductRes()
    }
}