package com.sopherwang.messageapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopherwang.dagger_integration.HasComponent
import com.sopherwang.message_demo.features.message_list.MessageViewModel
import com.sopherwang.messageapp.data.models.Message
import com.sopherwang.messageapp.ui.messagelist.MessageListAdapter
import dagger.Component
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val repoViewModel: MessageViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerMainActivity_MainActivityComponent.builder()
            .parentComponent((application as HasComponent<ParentComponent>).component())
            .build()
            .inject(this)

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

    interface ParentComponent {
        fun viewModelFactory(): ViewModelProvider.Factory
    }

    @Component(dependencies = [ParentComponent::class])
    interface MainActivityComponent {
        fun inject(activity: MainActivity)
    }
}
