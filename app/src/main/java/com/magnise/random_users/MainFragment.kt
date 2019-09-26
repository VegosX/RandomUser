package com.magnise.random_users

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.magnise.random_users.ui.UserAdapter
import com.magnise.random_users.ui.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user.*

class MainFragment : Fragment(){

    private val loadMoreListener = object : UserAdapter.LoadMoreListener{
        override fun loadMoreUsers() {
            viewModel.loadMoreUsers()
        }
    }
    private val userAdapter by lazy { UserAdapter(loadMoreListener) }
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
        rvList.apply {
            layoutManager = activity?.let { LinearLayoutManager(it, RecyclerView.VERTICAL, false) }
            adapter = this@MainFragment.userAdapter
        }
        swipe_container.setOnRefreshListener { viewModel.refreshData() }
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
            rvList.visibility = if (it){View.GONE} else View.VISIBLE
            ivErrorIcon.visibility = if (it){ImageView.VISIBLE} else ImageView.GONE
            tvIErrorMessage.visibility = if (it){TextView.VISIBLE} else TextView.GONE
        })

        viewModel.showLoading.observe(this, Observer {
            swipe_container.isRefreshing = it
        })

        viewModel.onProgress.observe(this, Observer {
            pbProgress.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}
