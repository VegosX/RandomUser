package com.magnise.random_users.ui

//import UserModelCopy
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magnise.random_users.model.api.Results
import com.magnise.random_users.model.api.UserModel
import com.magnise.random_users.model.api.datasource.UsersDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class UserViewModel : ViewModel() {
    //Live data to send data to fragment -> MainFragment
    val onErrorLoad = MutableLiveData<Boolean>()
    val onErrorMessage = MutableLiveData<String>()
    var sendListToFragment: MutableLiveData<List<Results>> = MutableLiveData()

    init {
        loadUsers()
    }




    //Realize
    private fun loadUsers() {
        val requestUserCall = UsersDataSource.getUsers()
        requestUserCall.enqueue(object : Callback<UserModel> {



            //If good response ☻ ->
            //we give getModel to UserModelCopy
            override fun onResponse(
                call: Call<UserModel>,
                response: Response<UserModel>
            ) {
                response.body()?.let { userModel ->
                    sendListToFragment.value = userModel.results
                }
            }



            //If bad response ->
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
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

