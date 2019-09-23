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
import com.magnise.random_users.model.api.Results
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : RecyclerView.Adapter<InformationViewHolder>() {
    private var list: List<Results> = listOf()
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return InformationViewHolder(
            inflater,
            parent)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        val view: Results = list[position]
        holder.bind(view)
        holder.itemView.setOnClickListener {
            holder.onClick(it, view)
        }
    }

    fun refreshData(list: List<Results>) {
        this.list = list
    }
}

class InformationViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_user, parent, false)) {

    private var tvName: TextView = itemView.findViewById(R.id.tvName)
    private var tvSurname: TextView = itemView.findViewById(R.id.tvSurname)

    fun bind(result: Results) {
        var nameUC: String = result.name.first
        var surnameUC: String = result.name.last
        nameUC = nameUC.substring(0,1).toUpperCase() + nameUC.substring(1).toLowerCase()
        surnameUC = surnameUC.substring(0,1).toUpperCase() + surnameUC.substring(1).toLowerCase()

        tvName.text = nameUC
        tvSurname.text = surnameUC
        Glide.with(itemView.context).load(result.picture.large).into(itemView.ivIcon)
    }

    fun onClick(view: View, result: Results){
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
