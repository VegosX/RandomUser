package com.magnise.random_users.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magnise.random_users.R
import com.magnise.random_users.model.api.UserModel
import kotlinx.android.synthetic.main.item_user.view.*
import java.time.LocalDate

class UserAdapter : RecyclerView.Adapter<InformationViewHolder>() {
    private var list: List<UserModel> = listOf()
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return InformationViewHolder(
            inflater,
            parent)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        val view: UserModel = list[position]
        Log.d("Responce is ", view.results.name.first)
        holder.bind(view)
    }

    fun refreshData(list: List<UserModel>) {
        this.list = list
    }
}

class InformationViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_user, parent, false)) {

    private var tvUserName: TextView = itemView.findViewById(R.id.tvUsername)


    fun bind(instrument: UserModel) {
        tvUserName.text = instrument.results.name.first
        Glide.with(itemView.context).load(instrument.results.picture.medium).into(itemView.ivIcon)
    }
}
