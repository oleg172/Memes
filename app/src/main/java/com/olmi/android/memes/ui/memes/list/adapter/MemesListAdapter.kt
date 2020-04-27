package com.olmi.android.memes.ui.memes.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.olmi.android.memes.R
import com.olmi.android.memes.data.models.Mem
import java.util.*

/**
 * Adapter for RecyclerView in MemesListFragment
 */
class MemesListAdapter(val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MemesListAdapter.Holder>() {

    private var data: List<Mem> = listOf()
    private val mRandom = Random()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list,
            parent,
            false
        )
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position], itemClickListener)
    }

    fun setData(data: List<Mem>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun getRandomIntInRange(max: Int, min: Int): Int {
        return mRandom.nextInt(max - min + min) + min
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

        val memImage: ImageView = view.findViewById(R.id.mem_image)
        val memText: TextView = view.findViewById(R.id.mem_text)
        val memLike: ImageView = view.findViewById(R.id.mem_like)
        val memShare: ImageView = view.findViewById(R.id.mem_share)

        fun bind(mem: Mem, clickListener: OnItemClickListener) {
            //need to reset the size of the view/target and behave as if the list item was just inflated
            memImage.layout(0, 0, 0, 0)
            memImage.layoutParams.height = getRandomIntInRange(450, 350)
            Glide.with(memImage.context)
                .load(mem.photoUrl)
                .transform(GranularRoundedCorners(24F, 24F, 0F, 0F))
                .into(memImage)
            memText.text = mem.title
            memLike.setOnClickListener { view: View ->
                view.isSelected = !view.isSelected
            }
            itemView.setOnClickListener {
                clickListener.onItemClicked(mem)
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(mem: Mem)
}
