package br.com.tony.services.impl

import br.com.tony.dto.ProductReq
import br.com.tony.dto.ProductRes
import br.com.tony.dto.ProductUpdateReq
import br.com.tony.exceptions.AlreadyExistsException
import br.com.tony.exceptions.ProductNotFoundException
import br.com.tony.repository.ProductRepository
import br.com.tony.services.ProductService
import br.com.tony.util.toDomain
import br.com.tony.util.toProductRes
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {
    override fun create(req: ProductReq): ProductRes {
        verifyName(req.name)
        val productSaved = productRepository.save(req.toDomain())
        return productSaved.toProductRes()
    }

    override fun findById(id: Long): ProductRes {
        val findById = productRepository.findById(id)
        findById.orElseThrow { ProductNotFoundException(id) }
        return findById.get().toProductRes()
    }

    override fun update(req: ProductUpdateReq): ProductRes {
        verifyName(req.name)
        val product = productRepository.findById(req.id)
            .orElseThrow { ProductNotFoundException(req.id) }
        val copy = product.copy(
            name = req.name,
            price = req.price,
            quantityInStock = req.quantityInStock
        )

        return productRepository.update(copy).toProductRes()
    }

    override fun delete(id: Long) {
        val product = productRepository.findById(id)
            .orElseThrow { ProductNotFoundException(id) }
        productRepository.delete(product)
    }

    private fun verifyName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}