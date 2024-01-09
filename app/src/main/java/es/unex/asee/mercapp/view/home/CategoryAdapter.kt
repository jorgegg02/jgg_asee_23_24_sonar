package es.unex.asee.mercapp.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.unex.asee.mercapp.R
import es.unex.asee.mercapp.data.model.GenericCategory

class CategoryAdapter(
    private var categories: List<GenericCategory>,
    private val onItemClick: (GenericCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    fun setCategories(categories: List<GenericCategory>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Inicializa las vistas de cada elemento en el ViewHolder
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)

        init {
            // Maneja los clics en los elementos
            itemView.setOnClickListener {
                onItemClick(categories[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Infla el diseño de cada elemento y crea el ViewHolder
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.catalog_category, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // Vincula los datos del elemento en la posición actual al ViewHolder
        val category = categories[position]
        holder.categoryName.text = category.GenCatName
    }

    override fun getItemCount(): Int {
        // Devuelve el número de elementos en la lista
        return categories.size
    }
}
