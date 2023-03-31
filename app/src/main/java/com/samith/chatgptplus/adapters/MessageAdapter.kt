package com.samith.chatgptplus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.samith.chatgptplus.R
import com.samith.chatgptplus.models.Chat

/**
 * The adapter class for displaying chat messages in a RecyclerView.
 *
 * @property context The context in which the adapter is being used.
 * @property list The list of Chat objects to display.
 */
class MessageAdapter(private var context: Context, var list : ArrayList<Chat>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    /**
     * ViewHolder class representing a single chat message view in the RecyclerView.
     *
     * @param view The view representing a single chat message.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.tv_message)
    }

    /**
     * Inflates the layout for the message view based on its type.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the view to be displayed.
     * @return A new ViewHolder that holds the view for the message.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_replay, parent, false)
            return ViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_send, parent, false)
            return ViewHolder(view)
        }


    }

    /**
     * Binds the chat message to its corresponding view.
     *
     * @param holder The ViewHolder to bind the data to.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = list[position]
        holder.message.text = chat.message

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Returns the type of view for a given chat message.
     *
     * @param position The position of the item within the adapter's data set.
     * @return An integer representing the type of view.
     */
    override fun getItemViewType(position: Int): Int {
        if (list[position].type == 0){
            return 0
        }else {
            return 1
        }
    }

    /**
     * Updates the list of chat messages with a new list.
     *
     * @param array The new list of chat messages.
     */
    fun updateList(array: ArrayList<Chat>){
        list = array
    }
}