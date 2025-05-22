package id.hilmysf.rekanpanganpi.data.repository

import id.hilmysf.rekanpanganpi.data.model.Product
import id.hilmysf.rekanpanganpi.data.remote.api_services.ProductApiServices
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApiServices: ProductApiServices) {
    suspend fun getProducts(): List<Product> = productApiServices.getProducts().products

}