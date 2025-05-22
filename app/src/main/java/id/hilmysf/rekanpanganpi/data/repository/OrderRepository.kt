package id.hilmysf.rekanpanganpi.data.repository

import id.hilmysf.rekanpanganpi.data.local.dao.OrderDao
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.data.local.entity.OrderEntity
import id.hilmysf.rekanpanganpi.data.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderDao: OrderDao) {
    suspend fun createOrder(
        products: List<CartEntity>,
        address: String,
        latitude: Double,
        longitude: Double,
        totalAmount: Double
    ) {
        val order =
            OrderEntity(
                products = products,
                address = address,
                latitude = latitude,
                longitude = longitude,
                totalAmount = totalAmount
            )
        orderDao.insert(order)
    }

    fun getOrderItems(): Flow<List<OrderEntity>> {
        return orderDao.getAllOrders()
    }

}