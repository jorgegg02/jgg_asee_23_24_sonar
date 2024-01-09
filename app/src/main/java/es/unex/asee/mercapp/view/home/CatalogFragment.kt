package es.unex.asee.mercapp.view.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.asee.mercapp.R
import es.unex.asee.mercapp.data.model.GenericCategory
import es.unex.asee.mercapp.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment()  {

    companion object {
        fun newInstance() = CatalogFragment()
    }

    private val viewModel: CatalogViewModel by viewModels { CatalogViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentCatalogBinding? = null

    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setHomeViewModel(homeViewModel)
        setUpRecyclerView()
        subscribeUi(adapter)

        homeViewModel.previousCategories.observe(viewLifecycleOwner) { unit ->
            viewModel.handleBackAction()
        }

    }

    private fun subscribeUi(adapter: CategoryAdapter) {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.setCategories(categories)
        }
    }

    private fun setUpRecyclerView() {
        adapter = CategoryAdapter (
            categories = emptyList(),
            onItemClick = {
                viewModel.handleCategoryAction(it)
            }
        )
        viewModel.init()
        with(binding) {
            superCategoriesRecyclerView.layoutManager = LinearLayoutManager(context)
            superCategoriesRecyclerView.adapter = adapter
        }



    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }


}