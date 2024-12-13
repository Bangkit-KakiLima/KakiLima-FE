package com.dicoding.ping.user.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.ping.databinding.FragmentShowProfileBinding
import com.dicoding.ping.utils.SessionManager

class ShowProfileFragment : Fragment() {
    private lateinit var binding: FragmentShowProfileBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        val fullName = sessionManager.getUsername() ?: "No Name"
        val email = sessionManager.getEmail() ?: "No Email"
        binding.showFullName.setText(fullName)
        binding.showEmail.setText(email)
    }
}
