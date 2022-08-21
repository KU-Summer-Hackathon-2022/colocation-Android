package org.aos.shareroof.rank

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.aos.shareroof.R
import org.aos.shareroof.databinding.ItemListBinding
import org.aos.shareroof.databinding.ItemLocalBinding

class RankRvAdapter : RecyclerView.Adapter<RankRvAdapter.HomeViewHolder>() {

    var homeList = arrayListOf<Rank>()

    inner class HomeViewHolder(private val binding: ItemLocalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Rank, position: Int) {
            binding.local = data
            binding.ivLocal.setImageResource(data.image)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemLocalBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: HomeViewHolder, position: Int) {
        viewHolder.onBind(homeList[position], position)

    }

    override fun getItemCount() = homeList.size
}