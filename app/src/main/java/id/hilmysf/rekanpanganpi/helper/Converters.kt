package id.hilmysf.rekanpanganpi.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.data.model.Product

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromCartEntityList(value: List<CartEntity>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCartEntityList(value: String?): List<CartEntity>? {
        if (value == null) return emptyList()
        val listType = object : TypeToken<List<CartEntity>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromProductList(value: List<Product>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProductList(value: String): List<Product> {
        val listType = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(value, listType)
    }
}