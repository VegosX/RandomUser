package com.magnise.random_users.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magnise.random_users.InformationActivity
import com.magnise.random_users.MainActivity
import com.magnise.random_users.MainFragment
import com.magnise.random_users.R
import com.magnise.random_users.model.api.model.UserModel
import kotlinx.android.synthetic.main.item_user.view.*
import kotlin.time.toDuration

class UserAdapter(private val listener: LoadMoreListener) : RecyclerView.Adapter<InformationViewHolder>() {

    interface LoadMoreListener {
        fun loadMoreUsers()
    }

    private var list: List<UserModel> = listOf()
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return InformationViewHolder(
            inflater,
            parent)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        if ((itemCount - position) <= 5) {
            listener.loadMoreUsers()
        }
        val view: UserModel = list[position]
        holder.bind(view)
        holder.itemView.setOnClickListener {
            holder.onClick(it, view)
        }
    }

    fun refreshData(list: List<UserModel>) {
        this.list = list
    }
}

class InformationViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_user, parent, false)) {

    private var tvName: TextView = itemView.findViewById(R.id.tvName)
    private var tvSurname: TextView = itemView.findViewById(R.id.tvSurname)

    fun bind(result: UserModel) {
        var nameUC: String = result.name.first
        var surnameUC: String = result.name.last
        nameUC = nameUC.substring(0,1).toUpperCase() + nameUC.substring(1).toLowerCase()
        surnameUC = surnameUC.substring(0,1).toUpperCase() + surnameUC.substring(1).toLowerCase()

        tvName.text = nameUC
        tvSurname.text = surnameUC
//        itemView.animate().rotationYBy(10f)
        Glide.with(itemView.context).load(result.picture.large).into(itemView.ivIcon)
    }

    fun onClick(view: View, result: UserModel){
        val intent = Intent(view.context, InformationActivity::class.java)
        intent.putExtra("username", result.name.first.toUpperCase() + "   " + result.name.last.toUpperCase())
        intent.putExtra("lastName", result.name.last)
        intent.putExtra("phone", result.phone)
        intent.putExtra("email", result.email)
        intent.putExtra("gender", result.gender)
        intent.putExtra("age", result.dob.age.toString())
        intent.putExtra("image", result.picture.large)

        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
            view.context as Activity?,
            android.util.Pair.create(itemView.ivIcon, "ivIcon")
        )
        view.context.startActivity(intent, activityOptions.toBundle())
    }
}
