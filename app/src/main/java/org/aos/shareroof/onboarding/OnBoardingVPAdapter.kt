package org.aos.shareroof.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.aos.shareroof.onboarding.OnBoardingFirstFragment
import org.aos.shareroof.onboarding.OnBoardingSecondFragment
import org.aos.shareroof.onboarding.OnBoardingThirdFragment

class OnBoardingVPAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var fragments: ArrayList<Fragment> = arrayListOf(OnBoardingFirstFragment(), OnBoardingSecondFragment(), OnBoardingThirdFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}