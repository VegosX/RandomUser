package com.magnise.random_users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magnise.random_users.ui.UserAdapter
import com.magnise.random_users.ui.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.*

class MainFragment : Fragment() {

    private val loadMOreListener = object : UserAdapter.LoadMoreListener{
        override fun loadMoreUsers() {
            viewModel.loadMoreUsers()
        }
    }

    private val userAdapter by lazy { UserAdapter(loadMOreListener) }
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        rvEditorChoise.apply {
            layoutManager = activity?.let { LinearLayoutManager(it, RecyclerView.VERTICAL, false) }
            adapter = this@MainFragment.userAdapter
        }
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProviders.of(this)[UserViewModel::class.java]

        viewModel.userList.observe(this, Observer {
            it?.let {
                userAdapter.refreshData(it)
                userAdapter.notifyDataSetChanged()
            }
        })

        viewModel.onErrorLoad.observe(this, Observer {
            Toast.makeText(
                activity?.applicationContext,
                "Server response is incorrect. Try again",
                Toast.LENGTH_LONG
            ).show()
        })
    }
}
