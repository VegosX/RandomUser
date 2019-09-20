package com.magnise.random_users.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magnise.random_users.R
import com.magnise.random_users.model.api.Results
import kotlinx.android.synthetic.main.item_user.view.*

//Adapter
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
    }

    fun refreshData(list: List<Results>) {
        this.list = list
    }
}


//ViewHolder
class InformationViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_user, parent, false)) {

    private var tvName: TextView = itemView.findViewById(R.id.tvName)
    private var tvSurname: TextView = itemView.findViewById(R.id.tvSurname)


    fun bind(result: Results) {
        //Get name and surname and convert first letter to UpperCase
        var nameUC: String = result.name.first
        var surnameUC: String = result.name.last
        nameUC = nameUC.substring(0,1).toUpperCase() + nameUC.substring(1).toLowerCase()
        surnameUC = surnameUC.substring(0,1).toUpperCase() + surnameUC.substring(1).toLowerCase()

        //Set result to view
        tvName.text = nameUC
        tvSurname.text = surnameUC
        Glide.with(itemView.context).load(result.picture.large).into(itemView.ivIcon)
    }
}
