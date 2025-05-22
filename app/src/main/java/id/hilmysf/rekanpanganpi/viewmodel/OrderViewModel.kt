package id.hilmysf.rekanpanganpi.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.data.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {

    val orders = orderRepository.getOrderItems()

    suspend fun createOrder(
        products: List<CartEntity>,
        address: String,
        latitude: Double,
        longitude: Double,
        totalAmount: Double
    ) {
        orderRepository.createOrder(
            products = products,
            address = address,
            latitude = latitude,
            longitude = longitude,
            totalAmount = totalAmount
        )
    }
}