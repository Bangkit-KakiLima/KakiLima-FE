package com.dicoding.ping.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.ping.R

class FragmentEditEmail : Fragment() {

    private lateinit var etNewEmail: EditText
    private lateinit var btnSubmitEmail: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_email, container, false)
        etNewEmail = view.findViewById(R.id.etNewEmail)
        btnSubmitEmail = view.findViewById(R.id.btnSubmitEmail)

        btnSubmitEmail.setOnClickListener {
            val email = etNewEmail.text.toString()
            if (email.isNotEmpty()) {
                sendOtpToEmail(email)
            } else {
                Toast.makeText(requireContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun sendOtpToEmail(email: String) {
        // Simulasikan pengiriman OTP
        Toast.makeText(requireContext(), "OTP telah dikirim ke $email", Toast.LENGTH_SHORT).show()

        // Navigasi ke FragmentVerifyOtp
        val bundle = Bundle()
        bundle.putString("email", email)
        findNavController().navigate(R.id.action_editEmail_to_verifyOtp, bundle)
    }
}
