package org.aos.shareroof.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.aos.shareroof.R
import org.aos.shareroof.databinding.ItemListBinding

class ListRvAdapter(name:String) : RecyclerView.Adapter<ListRvAdapter.HomeViewHolder>() {

    var homeList = arrayListOf<Home>()
    var pack = name

    private lateinit var itemEditClickListener: OnItemEditClickListener
    interface OnItemEditClickListener {
        fun onClick(v: View, data: Home, position: Int)
    }

    fun setItemEditClickListener(listener: OnItemEditClickListener) {
        this.itemEditClickListener = listener
    }

    inner class HomeViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Home, position: Int) {
            binding.home = data
            var index = if(data.image>9) data.image.toString()[1] else data.image
            var home = binding.ivHome.resources.getIdentifier("home${index}","drawable",pack)
            binding.ivHome.setImageResource(home)
            binding.cvHome.setOnClickListener {
                itemEditClickListener.onClick(it,data,position)
            }
            binding.ivLike.setOnClickListener {
                if(binding.ivLike.tag == "unstar") {
                    binding.ivLike.setImageResource(R.drawable.ic_star)
                    binding.ivLike.tag = "star"
                }else {
                    binding.ivLike.setImageResource(R.drawable.ic_un_star)
                    binding.ivLike.tag = "unstar"
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: HomeViewHolder, position: Int) {
        viewHolder.onBind(homeList[position], position)

    }

    override fun getItemCount() = homeList.size
}