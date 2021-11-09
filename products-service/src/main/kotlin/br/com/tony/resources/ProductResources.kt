package br.com.tony.resources

import br.com.tony.ProductServiceRequest
import br.com.tony.ProductServiceResponse
import br.com.tony.ProductsServiceGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources : ProductsServiceGrpc.ProductsServiceImplBase() {
    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        super.create(request, responseObserver)
    }
}