package id.hilmysf.rekanpanganpi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val cartId: Int = 0,
    val productId: Int?,

    val title: String?,
    val description: String?,
    val category: String?,
    val price: Double?,
    val rating: Double?,
    val stock: Int?,
    val brand: String?,
    val thumbnail: String?,

    var quantity: Int = 1,
    val addedDate: Long = System.currentTimeMillis(),
)