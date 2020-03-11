package com.nextus.baseapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nextus.baseapp.model.GistItem
import com.nextus.baseapp.model.mapToPresentation
import com.nextus.baseapp.ui.base.BaseViewModel
import com.nextus.domain.core.Result
import com.nextus.domain.usecase.GetGistListUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val getGistListUseCase: GetGistListUseCase
): BaseViewModel(application) {

    private val _gistItemList = MutableLiveData<List<GistItem>>().apply { value = emptyList() }
    val gistItemList : LiveData<List<GistItem>> = _gistItemList

    init {
        getGistList()
    }

    private fun getGistList() {
        viewModelScope.launch {
            showLoadingProgress()
            getGistListUseCase.invoke().let { result ->
                if(result is Result.Success) {
                    onGistListLoaded(result.data.map { it.mapToPresentation() })
                }
            }
            hideLoadingProgress()
        }
    }

    private fun onGistListLoaded(gistItemList: List<GistItem>) {
        _gistItemList.postValue(gistItemList)
    }
}