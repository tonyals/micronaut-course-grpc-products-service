package br.com.tony.repository

import br.com.tony.domain.Product
import io.micronaut.data.jpa.repository.JpaRepository
import jakarta.inject.Singleton

@Singleton
interface ProductRepository : JpaRepository<Product, Long>