package com.netcore.smarttechdemo

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class InboxAdapter(private val messages: List<InboxMessage>) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textview)
        val body: TextView = itemView.findViewById(R.id.textview2)
        val deeplinkButton: Button = itemView.findViewById(R.id.textview3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inbox_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.title.text = message.title
        holder.body.text = message.body
        holder.deeplinkButton.setOnClickListener {
            val context = holder.itemView.context
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(message.deeplink))
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Invalid Deeplink", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = messages.size
}
