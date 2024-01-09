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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.asee.mercapp.R
import es.unex.asee.mercapp.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private val viewModel: ProductViewModel by viewModels { ProductViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter

    private val args: ProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val subCategory = args.subcategory
        viewModel.setSubcategory(subCategory)

        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
        }



        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        setUpRecyclerView()
        subscribeUi(adapter)
    }

    private fun setUpRecyclerView() {
        adapter = ProductAdapter (
            products = viewModel.products.value ?: emptyList(),
            onItemClick = { product ->
                viewModel.setToCart(product)
            },
            onLongClick = { product ->
                viewModel.setToCart(product)
            },
            context = this.context
        )

        with(binding) {
            productRecyclerView.layoutManager = LinearLayoutManager(context)
            productRecyclerView.adapter = adapter
        }

    }

    private fun subscribeUi(adapter: ProductAdapter) {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.setProducts(products)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}