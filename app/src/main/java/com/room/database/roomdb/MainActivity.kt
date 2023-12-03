package com.room.database.roomdb

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.room.database.roomdb.adapter.UserAdapter
import com.room.database.roomdb.database.UserDatabase
import com.room.database.roomdb.databinding.ActivityMainBinding
import com.room.database.roomdb.databinding.DialogUserBinding
import com.room.database.roomdb.`interface`.ItemUser
import com.room.database.roomdb.model.UserModel

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var bindingDialog : DialogUserBinding
    lateinit var listUser : ArrayList<UserModel>
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }


    private fun initView() {
        listUser = UserDatabase(this@MainActivity).userDao().getAllUser() as ArrayList<UserModel>
        userAdapter = UserAdapter(listUser,object : ItemUser{
            override fun item(userModel: UserModel, status_Update: Boolean) {
                if (status_Update){
                    updateUser(userModel)
                }else{
                    deleteUser(userModel)
                }
            }

        })
        binding.apply {
            rcvUser.setHasFixedSize(true)
            rcvUser.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            rcvUser.adapter = userAdapter
            btnAdd.setOnClickListener {
                insertUser()
            }
        }
    }

    fun updateUser(userModel: UserModel){
        val dialog = Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert)
        bindingDialog = DialogUserBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.apply {

            edtName.setText(userModel.name)
            edtAge.setText(userModel.age)
            edtAddress.setText(userModel.address)

            btnAction.text = "Update"
            btnAction.setOnClickListener {
                userModel.name = edtName.text.toString()
                userModel.age = edtAge.text.toString().toInt()
                userModel.address = edtAddress.text.toString()

                UserDatabase(this@MainActivity).userDao().updateUser(userModel)
                Toast.makeText(this@MainActivity,"Sửa thành công",Toast.LENGTH_SHORT).show()
                userAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun deleteUser(userModel: UserModel){
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Bạn có muốn xóa không?")
            setMessage("Xóa ${userModel.name}")
            setPositiveButton("Có") { dialog, which ->
                UserDatabase(this@MainActivity).userDao().deleteUser(userModel)
                Toast.makeText(this@MainActivity, "Xóa thành công", Toast.LENGTH_SHORT).show()
                listUser.remove(userModel)
                userAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            setNegativeButton("Không"
            ) { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun insertUser(){
        val dialog = Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert)
        bindingDialog = DialogUserBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)

        bindingDialog.apply {
            btnAction.setOnClickListener {
                val name = edtName.text.toString()
                val age = edtAge.text.toString().toInt()
                val address = edtAddress.text.toString()

                val userModel = UserModel(name,age,address)
                UserDatabase(this@MainActivity).userDao().insertUser(userModel)
                Toast.makeText(this@MainActivity,"Thêm thành công",Toast.LENGTH_SHORT).show()
                listUser.add(userModel)
                userAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}