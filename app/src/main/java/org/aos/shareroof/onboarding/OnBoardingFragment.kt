package org.aos.shareroof.onboarding

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.aos.shareroof.R
import org.aos.shareroof.base.BaseFragment
import org.aos.shareroof.databinding.FragmentOnBoardingBinding

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnBoardingVpAdapter()
        setListeners()
    }

    private fun initOnBoardingVpAdapter() {
        binding.vpOnboarding.adapter = OnBoardingVPAdapter(requireActivity())
        binding.dotsIndicator.setViewPager2(binding.vpOnboarding)
    }

    private fun setListeners() {
        binding.btnOnboardingNext.setOnClickListener {
            if (binding.vpOnboarding.currentItem == 2) {
                findNavController().navigate(R.id.action_onBoardingFragment_to_signInFragment)
            } else {
                binding.vpOnboarding.currentItem += 1
            }
        }

    }
}