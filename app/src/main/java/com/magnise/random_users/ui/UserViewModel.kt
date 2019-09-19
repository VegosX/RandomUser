package com.magnise.random_users.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magnise.random_users.model.api.UserModel
import com.magnise.random_users.model.api.datasource.UsersDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class UserViewModel : ViewModel() {
    val onErrorLoad = MutableLiveData<Boolean>()
    val onErrorMessage = MutableLiveData<String>()
    var user: MutableLiveData<List<UserModel>> = MutableLiveData()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        val requestUserCall = UsersDataSource.getUsers()
        requestUserCall.enqueue(object : Callback<List<UserModel>> {

        override fun onResponse(
            call: Call<List<UserModel>>,
            response: Response<List<UserModel>>
        ) {
            response.body()?.let { result ->
                user.value = result
                Log.d("Responce: ", result.toString())
            }
        }
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                t.printStackTrace()
                onErrorLoad.value = true

                onErrorMessage.value = when (t) {
                    is IOException -> "Network error try again later"
                    else -> "Try again later"
                }
            }
        })
    }
}

