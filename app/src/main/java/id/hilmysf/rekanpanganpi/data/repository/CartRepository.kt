package id.hilmysf.rekanpanganpi.data.repository

import id.hilmysf.rekanpanganpi.data.local.dao.CartDao
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.data.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartDao: CartDao) {
    suspend fun addToCart(product: Product) {
        val cartItem =
            CartEntity(
                productId = product.id,
                title = product.title,
                price = product.price,
                brand = product.brand,
                stock = product.stock,
                rating = product.rating,
                category = product.category,
                quantity = 1,
                thumbnail = product.thumbnail,
                description = product.description
            )
        cartDao.insert(cartItem)
    }

    fun getCartItems(): Flow<List<CartEntity>> {
        return cartDao.getCarts()
    }

    suspend fun updateQuantity(cartId: Int, quantity: Int) {
        cartDao.updateQuantity(cartId, quantity)
    }

    suspend fun removeFromCart(cartId: Int) {
        cartDao.delete(cartId)
    }

}