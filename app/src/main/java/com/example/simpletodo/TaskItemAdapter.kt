package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Bridge that tells the recyclerView how to display given data

class TaskItemAdapter(val listOfItems: List<String>, val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {
    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    // Inflate layout from XML and returns the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return new holder instance
        return ViewHolder(contactView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get data model based on position
        val item = listOfItems.get(position)

        holder.textView.text = item

    }
    override fun getItemCount(): Int {
        return listOfItems.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Store references to elements in our layout view
        val textView: TextView
        init {
            textView=itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                //Log.i("Bran","Long clicked on item: "+ adapterPosition)
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }

    }
}