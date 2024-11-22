package com.example.parkirkuy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parkirkuy.R

class NotificationAdapter(
    private val notificationList: List<NotificationModel>,
    private val onClose: (NotificationModel) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_notifikasi, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.bind(notification)
        holder.closeButton.setOnClickListener { onClose(notification) }
    }

    override fun getItemCount(): Int = notificationList.size

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.tanggal)
        val detailTextView: TextView = itemView.findViewById(R.id.detail)
        val closeButton: ImageButton = itemView.findViewById(R.id.close)

        fun bind(notification: NotificationModel) {
            dateTextView.text = notification.title
            detailTextView.text = notification.message
        }
    }
}
