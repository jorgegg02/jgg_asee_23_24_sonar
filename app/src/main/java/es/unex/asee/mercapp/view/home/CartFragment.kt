package es.unex.asee.mercapp.view.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.asee.mercapp.R
import es.unex.asee.mercapp.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels { CartViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }

        setUpRecyclerView()
        subscribeUi(adapter)
    }

    fun subscribeUi(adapter: CartAdapter) {
        viewModel.productsInCart.observe(viewLifecycleOwner) { products ->
            adapter.updateData(products)
        }
    }

    fun setUpRecyclerView() {
        adapter = CartAdapter (
            products = viewModel.productsInCart.value ?: emptyList(),
            onItemClick = { product ->
                viewModel.UnSetToCart(product)
            },
            onLongClick = { product ->
                viewModel.UnSetToCart(product)
            },
            context = this.context
        )

        with(binding) {
             RecyclerView.layoutManager = GridLayoutManager(context,2)
            RecyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}