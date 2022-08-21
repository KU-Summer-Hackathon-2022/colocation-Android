package org.aos.shareroof.intro

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.aos.shareroof.R
import org.aos.shareroof.base.BaseFragment
import org.aos.shareroof.databinding.FragmentIntroBinding

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding>(R.layout.fragment_intro) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_introFragment_to_onBoardingFragment)
        }, 2000)
    }

}