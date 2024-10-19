package com.netcore.smarttechdemo

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class InboxAdapter(private val messages: List<InboxMessage>) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textview)
        val body: TextView = itemView.findViewById(R.id.textview2)
        val time: TextView = itemView.findViewById(R.id.textview3)
        val subtitle: TextView = itemView.findViewById(R.id.Divider)
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inbox_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]

        // Set the title, body, and time fields
        holder.title.text = message.title
        holder.body.text = message.body
        holder.time.text = message.time

        // Load image using Glide
        if (!message.mediaUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(message.mediaUrl) // Load the image from the URL
                .placeholder(R.drawable.ic_launcher_round) // Show placeholder while loading
                .into(holder.imageView)
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_round) // Show placeholder if no image URL
        }

        // Uncomment and modify if deeplink handling is needed
        /* holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(message.deeplink))
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Invalid Deeplink", Toast.LENGTH_SHORT).show()
            }
        } */
    }

    override fun getItemCount(): Int = messages.size
}


