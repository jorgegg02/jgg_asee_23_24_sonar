package es.unex.asee.mercapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class GenericCategory  (
    @PrimaryKey val GenCatId: Int,
    val GenCatName: String,
    val GenCatOrder: Int, // Orden de la categoría dentro de su nivel
    val GenCatLevel: Int, // Nivel de la categoría: 1 para supercategoría, 2 para categoría, 3 para subcategoría

    ) : Serializable