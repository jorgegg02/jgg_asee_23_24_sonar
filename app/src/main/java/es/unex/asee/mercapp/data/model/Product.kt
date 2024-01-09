package es.unex.asee.mercapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Product (

    @PrimaryKey val productId: Int,
    val productName: String,
    val productPackaging: String,
    val productUnitPrice: Float,
    val productBulkPrice: Float,
    val imagePath: String,
    @ColumnInfo(name = "is_in_cart") var isInCart: Boolean,


) : Serializable