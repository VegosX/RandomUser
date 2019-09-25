package com.magnise.random_users.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.magnise.random_users.model.api.datasource.UsersDataSource
import com.magnise.random_users.model.api.model.Model
import com.magnise.random_users.model.api.model.UserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull
import java.io.IOException

class UserViewModel : ViewModel() {
    val onErrorLoad = MutableLiveData<Boolean>()
    val onErrorMessage = MutableLiveData<String>()
    var userList: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    private var myCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var paginationDisposable: Disposable? = null
    private var page = 1
    private var isAllUsersLoaded = false
    private val pageSize = 10

    init {
        loadUsers()
    }

    fun loadMoreUsers() {
        if (paginationDisposable != null || isAllUsersLoaded) return
        page++
        loadUsers()
    }

    private fun loadUsers() {
        val disposable =  UsersDataSource.getUsers(page, pageSize, "seedValue")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)
        paginationDisposable = disposable
        myCompositeDisposable.add(disposable)
    }

    private fun handleResponse(userModel: Model) {
        for (o : UserModel in userModel.results){
            Log.d("Model.result -> ", o.toString())
        }
        val oldUsers = userList.value ?: mutableListOf()
        oldUsers.addAll(userModel.results)

        userList.value = oldUsers

        if (userModel.results.size < pageSize) {
            isAllUsersLoaded = true
        }
        paginationDisposable = null
    }

    private fun handleError(error: Throwable){
        error.printStackTrace()
        onErrorLoad.value = true
        onErrorMessage.value = when (error) {
            is IOException -> "Network error try again later"
            else -> "Try again later"
        }

        paginationDisposable = null
    }

    override fun onCleared() {
        myCompositeDisposable.clear()
        super.onCleared()
    }
}

