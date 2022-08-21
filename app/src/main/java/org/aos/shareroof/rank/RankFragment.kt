package org.aos.shareroof.rank

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.aos.shareroof.R
import org.aos.shareroof.base.BaseFragment
import org.aos.shareroof.databinding.FragmentRankBinding


@AndroidEntryPoint
class RankFragment : BaseFragment<FragmentRankBinding>(R.layout.fragment_rank) {

    private val rankRvAdapter:RankRvAdapter by lazy { RankRvAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setListeners()
    }

    private fun setListeners(){
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setAdapter() {
        rankRvAdapter.homeList = arrayListOf(Rank(R.drawable.local1,"본돈까스","경기도 안산시 광덕동로 99 현대타운 111호"),Rank(R.drawable.local2,"금암소바","전라북도 전주시 덕진구 금암1동 729-8")
        ,Rank(R.drawable.lock,"프리미엄","정기구독을 하시면 보실 수 있습니다."))
        binding.rvLocal.adapter = rankRvAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvLocal.layoutManager =layoutManager
        rankRvAdapter.notifyDataSetChanged()
    }


}