package org.aos.shareroof.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.aos.shareroof.R

object BindingAdapter {


    @JvmStatic
    @BindingAdapter("android:profileUrl")
    fun setProfileUrl(imageView: ImageView,url:String?) {
        Glide.with(imageView.context).load(url).placeholder(R.drawable.bg_oval).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("android:HomeImg")
    fun setHomeImage(imageView: ImageView,index:Int){
        var home = imageView.resources.getIdentifier("home${index}","drawable","asdfsdf")
        Log.d("TAG", "setHomeImage: ${home}")
        imageView.setImageResource(R.drawable.home0)
    }
   @JvmStatic
    @BindingAdapter("android:textColor")
    fun setTextColor(textView: TextView, color: String?) {
        textView.setTextColor(Color.parseColor(color))
    }

    @JvmStatic
    @BindingAdapter("android:iconColor")
    fun setIconColor(imageView: ImageView, color: String?) {
        imageView.setColorFilter(Color.parseColor(color))
    }

    @JvmStatic
    @BindingAdapter("android:missionComplete")
    fun setTextLine(textView: TextView,complete:Boolean){
        if (complete) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    @JvmStatic
    @BindingAdapter("android:ticketColor")
    fun setTicketColor(imageView: ImageView, color: String) {
        imageView.setColorFilter(Color.parseColor(color))
    }

    @JvmStatic
    @BindingAdapter("android:ticketBackground")
    fun setTicketBackground(view: View, color: String?) {
        view.setBackgroundColor(Color.parseColor(color))
    }

    @JvmStatic
    @BindingAdapter("android:userLevel")
    fun setLevel(textView: TextView, level: Int) {
        textView.text = "LV.$level"
    }



    @JvmStatic
    @BindingAdapter("android:checkColor", "android:isSuccess")
    fun setCheckColor(imageView: ImageView, color: String, isSuccess: Boolean) {
        if (isSuccess) imageView.setColorFilter(Color.parseColor("#ffffff"))
        else imageView.setColorFilter(Color.parseColor(color))
    }

    @JvmStatic
    @BindingAdapter("android:checkBackgroundColor", "android:isSuccess")
    fun setCheckBackgroundColor(imageView: ImageView, color: String, isSuccess: Boolean) {
        if (isSuccess) imageView.setColorFilter(Color.parseColor(color))
        else imageView.setColorFilter(Color.parseColor("#ffffff"))
    }
}