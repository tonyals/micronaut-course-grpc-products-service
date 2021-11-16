package br.com.tony.resources

import br.com.tony.*
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
internal class ProductResourcesTestIT(
    private val flyway: Flyway,
    private val productsServiceBlockingStub: ProductsServiceGrpc.ProductsServiceBlockingStub
) {

    @BeforeEach
    fun setUp() {
        flyway.clean()
        flyway.migrate()
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with valid data a success is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        assertEquals(3, response.id)
        assertEquals("product name", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc create method is call with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("Product A")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val description = "Produto ${request.name} já cadastrado no sistema."

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.create(request)
        }

        assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with valid id a success is returned`() {
        val request = RequestById.newBuilder()
            .setId(1)
            .build()

        val response = productsServiceBlockingStub.findById(request)

        assertEquals(1, response.id)
        assertEquals("Product A", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is call with invalid id a ProductNotFound is returned`() {
        val request = RequestById.newBuilder()
            .setId(10)
            .build()

        val description = "Produto com ID ${request.id} não encontrado."

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.findById(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with valid data a success is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(2L)
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.update(request)

        assertEquals(2, response.id)
        assertEquals("product name", response.name)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(2L)
            .setName("Product A")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val description = "Produto ${request.name} já cadastrado no sistema."

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(request)
        }

        assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is call with invalid id a ProductNotFound is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(10L)
            .setName("Product W")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val description = "Produto com ID ${request.id} não encontrado."

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc delete method is call with valid id a success is returned`() {
        val request = RequestById.newBuilder()
            .setId(1L)
            .build()

        assertDoesNotThrow {
            productsServiceBlockingStub.delete(request)
        }
    }

    @Test
    fun `when ProductsServiceGrpc delete method is call with invalid id a ProductNotFound is returned`() {
        val request = RequestById.newBuilder()
            .setId(10L)
            .build()

        val description = "Produto com ID ${request.id} não encontrado."

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.delete(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals(description, response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc findAll method is call a list of ProductServiceResponse is returned`() {
        val request = Empty.newBuilder().build()

        val response = productsServiceBlockingStub.findAll(request)

        assertEquals("Product A", response.getProducts(0).name)
        assertEquals("Product B", response.getProducts(1).name)
    }
}