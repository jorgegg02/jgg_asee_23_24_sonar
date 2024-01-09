package es.unex.asee.mercapp.view.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import es.unex.asee.mercapp.R

class UserFragment : PreferenceFragmentCompat() {


    private lateinit var viewModel: UserViewModel


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val logoutPreference: Preference? = findPreference("logout")

        // Agrega un listener para manejar el clic en el botón de logout
        logoutPreference?.setOnPreferenceClickListener {
            // Aquí puedes realizar las acciones necesarias antes de cerrar la actividad
            // Por ejemplo, guardar el estado actual, cerrar sesiones, etc.

            // Cierra la actividad
            activity?.finish()

            // Indica que el evento fue manejado
            true
        }
    }

}

