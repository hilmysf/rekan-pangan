package id.hilmysf.rekanpanganpi.data.remote.responses

import id.hilmysf.rekanpanganpi.data.model.Product

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)