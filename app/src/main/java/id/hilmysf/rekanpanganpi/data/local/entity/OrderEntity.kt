package id.hilmysf.rekanpanganpi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.hilmysf.rekanpanganpi.data.model.Product

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val products: List<CartEntity>,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null

)