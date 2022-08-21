package org.aos.shareroof.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.aos.shareroof.MainViewModel
import org.aos.shareroof.R
import org.aos.shareroof.base.BaseFragment
import org.aos.shareroof.databinding.FragmentMyPageBinding

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = mainViewModel
        setlisteners()
    }

    private fun setlisteners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}