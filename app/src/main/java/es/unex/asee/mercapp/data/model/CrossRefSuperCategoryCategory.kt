package es.unex.asee.mercapp.data.model

import androidx.room.*

@Entity(primaryKeys = ["superCategoryId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = GenericCategory::class,
            parentColumns = ["GenCatId"],
            childColumns = ["superCategoryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenericCategory::class,
            parentColumns = ["GenCatId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CrossRefSuperCategoryCategory(
    val superCategoryId: Int,
    val categoryId: Int
)