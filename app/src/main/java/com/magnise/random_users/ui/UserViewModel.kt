package com.magnise.random_users.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magnise.random_users.model.api.datasource.UsersDataSource
import com.magnise.random_users.model.api.model.Model
import com.magnise.random_users.model.api.model.UserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class UserViewModel : ViewModel() {
    val onErrorLoad = MutableLiveData<Boolean>()
    val onErrorMessage = MutableLiveData<String>()
    val showLoading = MutableLiveData<Boolean>()
    val onProgress = MutableLiveData<Boolean>()
    var userList: MutableLiveData<MutableList<UserModel>> = MutableLiveData()

    private var myCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var paginationDisposable: Disposable? = null
    private var page = 1
    private var isAllUsersLoaded = false
    private val pageSize = 20

    init {
        progressBarActivate()
        loadUsers()
    }

    fun refreshData() {
        paginationDisposable?.dispose()
        page = 1
        isAllUsersLoaded = false
        loadUsers()
    }

    fun loadMoreUsers() {
        if (paginationDisposable != null || isAllUsersLoaded) return
        page++
        loadUsers()
    }

    fun progressBarActivate(){
        onProgress.value = true
    }

    private fun loadUsers() {
        val disposable = UsersDataSource.getUsers(page, pageSize, "seedValue")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)
        paginationDisposable = disposable
        myCompositeDisposable.add(disposable)
    }

    private fun handleResponse(userModel: Model) {
        onProgress.value = false
        for (o: UserModel in userModel.results) {
            Log.d("Model.result -> ", o.toString())
        }
        val oldUsers =
            if (page == 1)
                mutableListOf()
            else
                userList.value ?: mutableListOf()

        oldUsers.addAll(userModel.results)

        userList.value = oldUsers

        if (userModel.results.size < pageSize) {
            isAllUsersLoaded = true
        }
        paginationDisposable = null
        showLoading.value = false
        onErrorLoad.value = false
    }

    private fun handleError(error: Throwable) {
        onProgress.value = false
        onErrorLoad.value = true
        error.printStackTrace()
        onErrorMessage.value = when (error) {
            is IOException -> "Network error try again later"
            else -> "Try again later"
        }
        paginationDisposable = null
        showLoading.value = false
    }

    override fun onCleared() {
        myCompositeDisposable.clear()
        super.onCleared()
    }
}

