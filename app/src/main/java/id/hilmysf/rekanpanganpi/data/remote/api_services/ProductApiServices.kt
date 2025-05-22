package id.hilmysf.rekanpanganpi.data.remote.api_services

import id.hilmysf.rekanpanganpi.data.remote.responses.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductApiServices {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}