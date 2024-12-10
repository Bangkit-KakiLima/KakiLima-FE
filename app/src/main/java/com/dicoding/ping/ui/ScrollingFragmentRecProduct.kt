package com.dicoding.ping.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dicoding.ping.api.RetrofitClient.apiService
import com.dicoding.ping.databinding.FragmentScrollingRecProductBinding
import com.dicoding.ping.user.home.product.DetailProductActivity
import com.dicoding.ping.user.home.product.ProductModel
import com.dicoding.ping.user.home.product.ProductRecomendationWeatherAdapter
import com.dicoding.ping.user.home.product.ProductRepository
import com.dicoding.ping.user.home.product.ProductViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScrollingFragmentRecProduct : BottomSheetDialogFragment() {

    private var _binding: FragmentScrollingRecProductBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScrollingRecProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        productViewModel.fetchAllProductsRecommendations()

        val productAdapter = ProductRecomendationWeatherAdapter(
            events = listOf(),
            onItemClick = { dataItem ->
                val intent = Intent(requireContext(), DetailProductActivity::class.java)
                intent.putExtra("id", dataItem.id)
                startActivity(intent)
            }
        )

        val layoutManager = StaggeredGridLayoutManager(
            1, // Number of columns
            StaggeredGridLayoutManager.VERTICAL // Vertical orientation
        )

        binding.rvRecProduct.apply {
            this.layoutManager = layoutManager
            adapter = productAdapter
            setHasFixedSize(true)
        }

        productViewModel.productsRecommendations.observe(viewLifecycleOwner) { productList ->
            productList?.let {
                productAdapter.updateData(it)
            } ?: run {
                Log.d("MainActivity", "Product list is null or empty")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun removePath(imagePath: String): String {
        // Implement the logic to remove the path from the image URL
        return imagePath
    }
}