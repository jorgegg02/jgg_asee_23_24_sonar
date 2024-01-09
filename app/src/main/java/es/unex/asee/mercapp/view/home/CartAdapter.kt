package es.unex.asee.mercapp.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.unex.asee.mercapp.R
import es.unex.asee.mercapp.data.model.Product
import es.unex.asee.mercapp.databinding.CartProductBinding
import es.unex.asee.mercapp.databinding.CatalogProductBinding

class CartAdapter( private var products: List<Product>,
private val onItemClick: (product: Product) -> Unit,
private val onLongClick: (product: Product) -> Unit,
private val context: Context?
) : RecyclerView.Adapter<CartAdapter.CartProductViewHolder>() {


    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    class CartProductViewHolder(
        private val binding: CartProductBinding,
        private val onClick: (product: Product) -> Unit,
        private val onLongClick: (name: Product) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, totalItems: Int) {
            with(binding) {
                unitPrice.text = product.productUnitPrice.toString() + "€"


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val binding =
            CartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartProductViewHolder(binding, onItemClick, onLongClick, parent.context)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(products[position], products.size)
    }

    fun updateData(shows: List<Product>) {
        this.products = shows
        notifyDataSetChanged()
    }

}
