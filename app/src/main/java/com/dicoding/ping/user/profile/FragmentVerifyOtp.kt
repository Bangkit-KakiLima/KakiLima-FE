package com.dicoding.ping.user.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.dicoding.ping.R
import androidx.navigation.fragment.findNavController

class FragmentVerifyOtp : Fragment() {

    private lateinit var etOtpCode: EditText
    private lateinit var btnSubmitOtp: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_verify_otp, container, false)
        etOtpCode = view.findViewById(R.id.etOtpCode)
        btnSubmitOtp = view.findViewById(R.id.btnSubmitOtp)

        btnSubmitOtp.setOnClickListener {
            val otpCode = etOtpCode.text.toString()
            if (otpCode == "123456") { // Simulasi validasi OTP
                Toast.makeText(requireContext(), "Email berhasil diubah!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp() // Kembali ke halaman sebelumnya
            } else {
                Toast.makeText(requireContext(), "Kode OTP salah!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
