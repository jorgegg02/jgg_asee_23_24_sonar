package es.unex.asee.mercapp

import android.app.Application
import es.unex.asee.mercapp.util.AppContainer

class MercApplication: Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}