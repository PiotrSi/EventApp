package com.example.pracav2.ui.home

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.pracav2.R
import com.example.pracav2.data.responses.EventResponse
import com.example.pracav2.data.responses.EventResponseItem
import com.example.pracav2.databinding.ListItemBinding
import com.example.pracav2.ui.convertImg
import okhttp3.internal.notify

class EventsViewAdapter(
//    private val events: EventResponse,
    private val listener: RecyclerViewClickListener
) :RecyclerView.Adapter<EventsViewAdapter.EventsViewHolder>(){

    private var events = emptyList<EventResponseItem>()

//    var item = listOf<EventResponse>()
//    set(value) {
//        field = value
//        notifyDataSetChanged()
//    }

    inner class EventsViewHolder (
        val binding: ListItemBinding
    ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder{
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return EventsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        with(holder){
            with(events[position]){
                binding.title.text = this.name
//                var imgString : String? = null

//                imgString = this.imageData
//                val imageBytes = Base64.decode(imgString, Base64.DEFAULT)
//                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.imageView.setImageBitmap(convertImg(this.imageData))
            }
        }



        holder.binding.imageView.setOnClickListener{
            listener.onItemClick(holder.binding.imageView, events[position])
        }


    }
    fun setData(newEvents : List<EventResponseItem>){
        events = newEvents
        notifyDataSetChanged()
    }

    override fun getItemCount() = events.size


}