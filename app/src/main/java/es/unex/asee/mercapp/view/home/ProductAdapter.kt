package es.unex.asee.mercapp.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.asee.mercapp.R
import es.unex.asee.mercapp.data.model.GenericCategory
import es.unex.asee.mercapp.data.model.Product
import es.unex.asee.mercapp.databinding.CatalogProductBinding

class ProductAdapter(
    private var products: List<Product>,
    private val onItemClick: (product: Product) -> Unit,
    private val onLongClick: (product: Product) -> Unit,
    private val context: Context?
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        private val binding: CatalogProductBinding,
        private val onClick: (product: Product) -> Unit,
        private val onLongClick: (name: Product) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, totalItems: Int) {
            with(binding) {
                productName.text = product.productName.toString().replace("-", " ")
                unitPrice.text =  product.productUnitPrice.toString() + "€"
                bulkPrice.text = product.productBulkPrice.toString() + "€/kg"
                packaging.text = product.productPackaging

                context?.let {
                    Glide.with(context)
                        .load(product.imagePath)
                        .placeholder(R.drawable.mercapp_banner)
                        .into(itemImg)
                }
                clItem.setOnClickListener {
                    onClick(product)
                }
                clItem.setOnLongClickListener {
                    onLongClick(product)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            CatalogProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onItemClick, onLongClick, parent.context)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], products.size)
    }

    fun updateData(shows: List<Product>) {
        this.products = shows
        notifyDataSetChanged()
    }

}