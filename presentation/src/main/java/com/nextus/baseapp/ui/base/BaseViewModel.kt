package com.nextus.baseapp.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nextus.baseapp.MyApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel


abstract class BaseViewModel(
    application: Application
): AndroidViewModel(application) {

    private val _mIsLoading = MutableLiveData(false)
    val mIsLoading : LiveData<Boolean> = _mIsLoading

    private val _mLoadingMsg = MutableLiveData("")
    val mLoadingMsg : LiveData<String> = _mLoadingMsg

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        viewModelScope.cancel()
        hideLoadingProgress()
        super.onCleared()
    }

    fun showLoadingProgress() {
        _mIsLoading.postValue(true)
    }

    fun showLoadingProgress(msg: String) {
        setLoadingMsg(msg)
        _mIsLoading.postValue(true)
    }

    fun hideLoadingProgress() {
        _mIsLoading.postValue(false)
    }

    private fun setLoadingMsg(msg: String) {
        _mLoadingMsg.postValue(msg)
    }

}