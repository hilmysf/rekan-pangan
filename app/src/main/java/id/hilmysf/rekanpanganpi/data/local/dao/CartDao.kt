package id.hilmysf.rekanpanganpi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: CartEntity)

    @Query("SELECT * FROM carts ORDER BY addedDate DESC")
    fun getCarts(): Flow<List<CartEntity>>

    @Query("DELETE FROM carts WHERE cartId = :cartId")
    suspend fun delete(cartId: Int)


    @Query("UPDATE carts SET quantity = :quantity WHERE cartId = :cartId")
    suspend fun updateQuantity(cartId: Int, quantity: Int)

    @Query("UPDATE carts SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantityByProductId(productId: Int, quantity: Int)

}