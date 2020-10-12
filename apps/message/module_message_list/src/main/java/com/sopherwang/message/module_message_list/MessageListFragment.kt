package com.sopherwang.message.module_message_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.sopherwang.message.library_common_network.models.Message
import com.sopherwang.message.module_message_list_export.MessageListRouter
import dagger.hilt.android.AndroidEntryPoint

@Route(path = MessageListRouter.PATH_FRAGMENT_MESSAGE_LIST)
@AndroidEntryPoint
class MessageListFragment: Fragment() {
    private val repoViewModel: MessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_message)
            val adapter = MessageListAdapter(it)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(it)
            repoViewModel.getMessage()
                .observe(viewLifecycleOwner, Observer<List<Message>>{ messages ->
                    adapter.setMessageList(messages)
                })
        }
    }
}
