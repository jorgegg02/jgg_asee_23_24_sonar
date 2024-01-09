package es.unex.asee.mercapp.util

import android.content.Context
import es.unex.asee.mercapp.api.getNetworkService
import es.unex.asee.mercapp.data.Repository
import es.unex.asee.mercapp.database.MercappDatabase

class AppContainer(context: Context?) {

        private val networkService = getNetworkService()
        private val db = MercappDatabase.getInstance(context!!)
        val repository = Repository(db!!.userDao(), db!!.categoryDao(),  getNetworkService() )
}