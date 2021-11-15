package br.com.tony.services.impl

import br.com.tony.domain.Product
import br.com.tony.dto.ProductReq
import br.com.tony.exceptions.AlreadyExistsException
import br.com.tony.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

internal class ProductServiceImplTest {
    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private val productService = ProductServiceImpl(productRepository)

    @Test
    fun `when create method is call with valid data a ProductRes is returned`() {
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.save(productInput))
            .thenReturn(productOutput)

        val productReq = ProductReq(name = "product name", price = 10.00, quantityInStock = 5)

        val productRes = productService.create(productReq)

        assertEquals(productReq.name, productRes.name)
    }

    @Test
    fun `when create method is call with duplicated product-name, throws AlreadyExistsException`() {
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        `when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        val productReq = ProductReq(name = "product name", price = 10.00, quantityInStock = 5)

        assertThrowsExactly(AlreadyExistsException::class.java) { productService.create(productReq) }
    }
}