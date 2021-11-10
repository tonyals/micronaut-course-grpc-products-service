package br.com.tony.resources

import br.com.tony.ProductServiceRequest
import br.com.tony.ProductServiceResponse
import br.com.tony.ProductsServiceGrpc
import br.com.tony.dto.ProductReq
import br.com.tony.services.ProductService
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources(private val productService: ProductService) : ProductsServiceGrpc.ProductsServiceImplBase() {
    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        val productReq = ProductReq(name = request!!.name, price = request.price, quantityInStock = request.quantityInStock)
        val productRes = productService.create(productReq)
        val productResponse = ProductServiceResponse.newBuilder()
            .setId(productRes.id)
            .setName(productRes.name)
            .setPrice(productRes.price)
            .setQuantityInStock(productRes.quantityInStock)
            .build()

        responseObserver?.onNext(productResponse)
        responseObserver?.onCompleted()
    }
}