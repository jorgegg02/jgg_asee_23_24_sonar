package es.unex.asee.mercapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["subCategoryId", "productId"],
    foreignKeys = [
        ForeignKey(
            entity = GenericCategory::class,
            parentColumns = ["GenCatId"],
            childColumns = ["subCategoryId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class CrossRefSubCategoryProduct(
    val subCategoryId: Int,
    val productId: Int
)
