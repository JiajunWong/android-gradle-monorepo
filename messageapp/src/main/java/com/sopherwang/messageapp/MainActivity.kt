package com.sopherwang.messageapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopherwang.message_demo.features.message_list.MessageViewModel
import com.sopherwang.messageapp.data.models.Message
import com.sopherwang.messageapp.ui.messagelist.MessageListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val repoViewModel: MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_message)
        val adapter = MessageListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        repoViewModel.getMessage()
            .observe(this, Observer<List<Message>>{ messages ->
                adapter.setMessageList(messages)
            })
    }
}
