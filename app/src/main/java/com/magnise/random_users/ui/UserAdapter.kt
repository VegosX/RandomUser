package com.magnise.random_users.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magnise.random_users.InformationActivity
import com.magnise.random_users.R
import com.magnise.random_users.model.api.model.UserModel
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val listener: LoadMoreListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface LoadMoreListener {
        fun loadMoreUsers()
    }

    private var list: MutableList<BaseModel> = mutableListOf()

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Constants.ViewType.USER_TYPE -> {
                InformationViewHolder(inflater.inflate(R.layout.item_user, parent, false))
            }
            else -> {
                LoadingViewHolder(inflater.inflate(R.layout.item_loading, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if ((itemCount - position) <= 1) {
            listener.loadMoreUsers()
        }

        val model: BaseModel = list[position]
        when (holder) {
            is InformationViewHolder -> {
                holder.bind(model as UserModel)
                holder.itemView.setOnClickListener { holder.onClick(it, model) }
            }
            is LoadingViewHolder -> holder.bind(model as LoadingItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getViewType()
    }

    fun refreshData(list: MutableList<BaseModel>) {
        this.list = list
    }
}

class InformationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var tvName: TextView = itemView.findViewById(R.id.tvName)
    private var tvSurname: TextView = itemView.findViewById(R.id.tvSurname)

    fun bind(result: UserModel) {
        var nameUC: String = result.name.first
        var surnameUC: String = result.name.last
        nameUC = nameUC.substring(0, 1).toUpperCase() + nameUC.substring(1).toLowerCase()
        surnameUC = surnameUC.substring(0, 1).toUpperCase() + surnameUC.substring(1).toLowerCase()

        tvName.text = nameUC
        tvSurname.text = surnameUC
        Glide.with(itemView.context).load(result.picture.large).into(itemView.ivIcon)
    }

    fun onClick(view: View, result: UserModel) {
        val intent = Intent(view.context, InformationActivity::class.java)
        intent.putExtra(
            "username",
            result.name.first.toUpperCase() + "   " + result.name.last.toUpperCase()
        )
        intent.putExtra("lastName", result.name.last)
        intent.putExtra("phone", result.phone)
        intent.putExtra("email", result.email)
        intent.putExtra("gender", result.gender)
        intent.putExtra("age", result.dob.age.toString())
        intent.putExtra("image", result.picture.large)
        intent.putExtra("date", result.dob.date)

        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
            view.context as Activity?,
            android.util.Pair.create(itemView.ivIcon, "ivIcon")
        )
        view.context.startActivity(intent, activityOptions.toBundle())
    }
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(result: LoadingItem) {
    }
}
