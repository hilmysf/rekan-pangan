package id.hilmysf.rekanpanganpi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.hilmysf.rekanpanganpi.data.local.dao.CartDao
import id.hilmysf.rekanpanganpi.data.local.dao.OrderDao
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.data.local.entity.OrderEntity
import id.hilmysf.rekanpanganpi.helper.Converters

@Database(entities = [OrderEntity::class, CartEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun cartDao(): CartDao
}