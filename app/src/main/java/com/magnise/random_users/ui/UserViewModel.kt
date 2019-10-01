package com.magnise.random_users.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magnise.random_users.model.api.datasource.UsersDataSource
import com.magnise.random_users.model.api.model.Model
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class UserViewModel : ViewModel() {
    val onErrorLoad = MutableLiveData<Boolean>()
    private val onErrorMessage = MutableLiveData<String>()
    val showLoading = MutableLiveData<Boolean>()
    val onProgress = MutableLiveData<Boolean>()
    var userList: MutableLiveData<MutableList<BaseModel>> = MutableLiveData()

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

    private fun progressBarActivate() {
        onProgress.value = true
    }

    private fun loadUsers() {
        addLoadingItem()
        val disposable = UsersDataSource.getUsers(page, pageSize, "seedValue")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)
        paginationDisposable = disposable
        myCompositeDisposable.add(disposable)
    }

    private fun addLoadingItem() {
        val models = userList.value ?: mutableListOf()
        if (models.isNotEmpty()) {
            models.add(LoadingItem())
            userList.value = models
        }
    }

    private fun removeLoadingItem(list: MutableList<BaseModel>?): MutableList<BaseModel> {
        val models = list ?: mutableListOf()
        if (models.isNotEmpty() && models[models.lastIndex] is LoadingItem)
            models.removeAt(models.lastIndex)
        return models
    }

    private fun handleResponse(userModel: Model) {
        onProgress.value = false

        var oldUsers =
            if (page == 1)
                mutableListOf()
            else
                userList.value ?: mutableListOf()

        oldUsers = removeLoadingItem(oldUsers)
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
        userList.value = removeLoadingItem(userList.value)

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

