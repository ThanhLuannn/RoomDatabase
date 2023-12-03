package com.room.database.roomdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.room.database.roomdb.databinding.ItemUserBinding
import com.room.database.roomdb.`interface`.ItemUser
import com.room.database.roomdb.model.UserModel

class UserAdapter(val listUser : ArrayList<UserModel>,val itemUser: ItemUser) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    inner class UserViewHolder(val binding : ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userModel: UserModel){
            binding.apply {
                tvName.text = userModel.name
                tvAge.text = "${userModel.age}"
                tvAddress.text = userModel.address

                tvUpdate.setOnClickListener {
                    itemUser.item(userModel,true)
                }

                tvDelete.setOnClickListener {
                    itemUser.item(userModel,false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(layoutInflater,parent,false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userModel = listUser[position]
        holder.bind(userModel)
    }
}