package es.unex.asee.mercapp.data.model

import androidx.room.*

@Entity(primaryKeys = ["categoryId", "subCategoryId"],
    foreignKeys = [
        ForeignKey(
            entity = GenericCategory::class,
            parentColumns = ["GenCatId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenericCategory::class,
            parentColumns = ["GenCatId"],
            childColumns = ["subCategoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CrossRefCategorySubCategory(
    val categoryId: Int,
    val subCategoryId: Int
)