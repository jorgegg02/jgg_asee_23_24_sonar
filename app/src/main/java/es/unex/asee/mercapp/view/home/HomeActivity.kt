package es.unex.asee.mercapp.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import es.unex.asee.mercapp.R
import es.unex.asee.mercapp.data.model.CrossRefCategorySubCategory
import es.unex.asee.mercapp.data.model.GenericCategory
import es.unex.asee.mercapp.data.model.User
import es.unex.asee.mercapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels() {HomeViewModel.Factory}
    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }



    companion object {
        const val USER_INFO = "USER_INFO"
        fun start(
            context: Context,
            user: User,
        ) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra(USER_INFO, user)
            }
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userInSession = intent.getSerializableExtra(USER_INFO) as User

        viewModel.navigateToProducts.observe(this) { subcategory ->
            subcategory?.let {
                onSubCategoryClick(subcategory)
            }
        }

        setUpUI()
        setUpListeners()
    }

    fun setUpUI() {
        binding.bottomNavigation.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.cartFragment,
                R.id.userFragment,
                R.id.catalogFragment
            )
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            viewModel.updatePreviousCategories()
        }
        else{
            super.onBackPressed()
        }
    }

    fun onSubCategoryClick(subCategory: GenericCategory) {
        val action = CatalogFragmentDirections.actionCatalogFragmentToProductFragment(subCategory)
        navController.navigate(action)
    }

    fun setUpListeners() {
        //nothing to do
    }

}