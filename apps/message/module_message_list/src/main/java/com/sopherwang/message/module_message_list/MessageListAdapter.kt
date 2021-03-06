package com.sopherwang.message.module_message_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sopherwang.message.library_common_network.models.Message
import javax.inject.Inject

class MessageListAdapter @Inject constructor(private val context: Context, private val listItemClickListener: ListItemClickListener) :
    RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {
    private val messageList: MutableList<Message> = mutableListOf()

    fun setMessageList(messageList: List<Message>) {
        this.messageList.clear()
        this.messageList.addAll(messageList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        holder.titleTextView.text = message.author.name
        holder.timeTextView.text = message.updated
        holder.contentTextView.text = message.content
        Glide.with(context).load("https://message-list.appspot.com/" + message.author.photoUrl).into(holder.avatarImageView)

        holder.rootView.setOnClickListener {
            listItemClickListener.onItemClicked(message)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView: View = itemView.findViewById(R.id.list_item_message_root)
        var titleTextView: TextView = itemView.findViewById(R.id.list_item_message_title)
        var timeTextView: TextView = itemView.findViewById(R.id.list_item_message_time)
        var contentTextView: TextView = itemView.findViewById(R.id.list_item_message_content)
        var avatarImageView: ImageView = itemView.findViewById(R.id.list_item_message_avatar)
    }
}