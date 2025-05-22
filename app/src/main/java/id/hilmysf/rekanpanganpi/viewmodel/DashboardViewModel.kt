package id.hilmysf.rekanpanganpi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.data.model.Product
import id.hilmysf.rekanpanganpi.data.repository.AuthRepository
import id.hilmysf.rekanpanganpi.data.repository.CartRepository
import id.hilmysf.rekanpanganpi.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val cartQuantities = mutableStateMapOf<Int, Int>()

    var productsState by mutableStateOf<UiState<List<Product>>>(UiState.Loading)
        private set

    fun fetchProducts() {
        viewModelScope.launch {
            productsState = UiState.Loading
            try {
                val products = productRepository.getProducts()
                productsState = UiState.Success(products)
            } catch (e: Exception) {
                productsState = UiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun getCartItems(): Flow<List<CartEntity>> {
        val carts = cartRepository.getCartItems().onEach { carts ->
            carts.forEach {
                if (it.productId != null) {
                    cartQuantities[it.productId] = it.quantity
                }
            }
        }
        return carts
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product)
        }
    }

    fun updateQuantity(cartId: Int, quantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(cartId, quantity)
        }
    }

    fun removeFromCart(cartId: Int) {
        viewModelScope.launch {
            cartRepository.removeFromCart(cartId)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}