package id.hilmysf.rekanpanganpi.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.data.model.Product
import id.hilmysf.rekanpanganpi.data.repository.CartRepository
import id.hilmysf.rekanpanganpi.data.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    val cartItems = cartRepository.getCartItems()

    suspend fun addToCart(product: Product) {
        cartRepository.addToCart(product)
    }

    suspend fun updateQuantity(cartId: Int, quantity: Int) {
        cartRepository.updateQuantity(cartId, quantity)
    }

    suspend fun removeFromCart(cartId: Int) {
        cartRepository.removeFromCart(cartId)
    }

    val order = orderRepository.getOrderItems()

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