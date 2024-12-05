package com.dicoding.ping.user.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ping.R

class ProfileFragment : Fragment() {

    // Views
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var saveButton: Button
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        editName = view.findViewById(R.id.edit_full_name)

       // editEmail = view.findViewById(R.id.edit_email)
        editPassword = view.findViewById(R.id.edit_password)
        saveButton = view.findViewById(R.id.save_button)
        toolbar = requireActivity().findViewById(R.id.toolbar)

        // Set toolbar title and color
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitle("Edit Profile")
        toolbar.setBackgroundColor(resources.getColor(R.color.blue))
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))

        // Handle Save Button click
        saveButton.setOnClickListener {
            // Example: Get text from inputs and perform validation
            val name = editName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            } else {
                // You can show a Toast or error message here
                editName.error = "Name is required"
                editEmail.error = "Email is required"
                editPassword.error = "Password is required"
            }
        }
    }
}
