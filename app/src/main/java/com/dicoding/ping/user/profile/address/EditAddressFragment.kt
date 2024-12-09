package com.dicoding.ping.user.profile.address

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.ping.R
import com.dicoding.ping.api.RetrofitClient.apiService
import com.dicoding.ping.user.profile.ProfileModel
import com.dicoding.ping.user.profile.ProfileModelFactory
import com.dicoding.ping.user.profile.ProfileRepository
import com.dicoding.ping.utils.SessionManager

class EditAddressFragment : Fragment() {
    private lateinit var sessionManager: SessionManager

    private val profileViewModel: ProfileModel by viewModels {
        ProfileModelFactory(ProfileRepository(apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_address, container, false)

        val streetNameEditText = view.findViewById<EditText>(R.id.edit_street_name)
        val cityEditText = view.findViewById<EditText>(R.id.edit_city)
        val postalCodeEditText = view.findViewById<EditText>(R.id.edit_postal_code)
        val saveButton = view.findViewById<Button>(R.id.save_address_button)

        sessionManager = SessionManager(requireContext())
        profileViewModel.setSessionManager(sessionManager)
        saveButton.setOnClickListener {
            val streetName = streetNameEditText.text.toString()
            val city = cityEditText.text.toString()
            val postalCode = postalCodeEditText.text.toString()
            val fullAddress = combineAddress(streetName, city, postalCode)

            profileViewModel.addAddressResponse.observe(viewLifecycleOwner) { address ->
                if (address.isNotEmpty()) {
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("sucess!")
                        setMessage("sucess to fill address")
                        setPositiveButton("Back to Home", null)
                        create()
                        show()
                    }
                    Log.d("EditAddressFragment", "onCreateView: $address")
                    address.first().address_name?.let { it1 -> sessionManager.saveAddressUser(it1) }
                } else {
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle("Oops!")
                        setMessage("failed to fill address")
                        setPositiveButton("Repeat", null)
                        create()
                        show()
                    }
                }
            }
        }

        return view
    }

    private fun combineAddress(streetName: String, city: String, postalCode: String): String {
        return "$streetName $city $postalCode"
    }
}
