package com.magnise.random_users.ui

class LoadingItem: BaseModel {
    override fun getViewType(): Int {
        return Constants.ViewType.LOADING_TYPE
    }
}
