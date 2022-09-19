package com.gl.hindustantimesrss.viewmodel

import androidx.lifecycle.*
import com.gl.hindustantimesrss.apicall.KSApiService
import com.gl.hindustantimesrss.apicall.KSRetrofitInstance
import com.gl.hindustantimesrss.models.MainResponse
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class NewViewModel: ViewModel(), DefaultLifecycleObserver, CoroutineScope {

    private val clientJob = SupervisorJob()
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val uiScope = CoroutineScope(Dispatchers.Main + clientJob)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + clientJob

    private val _newsResponse = MutableLiveData<MainResponse>()
    val newsResponse: LiveData<MainResponse> = _newsResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Pair<Boolean, String>>()
    val error: LiveData<Pair<Boolean, String>> = _error

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getNewsArticles()
    }


    fun getNewsArticles(){
        _loading.postValue(true)
        uiScope.launch {
            withContext(bgDispatcher){
                try {
                    val response = provideApi().getRssFeeds()
                    _newsResponse.postValue(response)
                    _loading.postValue(false)
                    _error.postValue(Pair(false, ""))
                }
                catch (ex: Exception){
                    _loading.postValue(false)
                    val string = getErrorResponse(ex)
                    _error.postValue(Pair(true, string))
                }
            }
        }
    }

    private fun provideApi(): KSApiService {
        return KSRetrofitInstance.provideApi()
    }

    override fun onCleared() {
        super.onCleared()
        clearJob()
    }

    fun clearJob() {
        clientJob.cancel()
    }

    /**
     * Handle and return error response
     */
    fun getErrorResponse(throwable : Throwable): String {

        return when (throwable) {
            is IOException -> {
                "Network Error"
            }
            is HttpException -> {
                convertErrorBody(throwable)
            }
            else -> {
                "Network Error"
            }
        }
    }

    /**
     * Convert throwable into error response
     */
    private fun convertErrorBody(throwable: HttpException): String {
        var error = "Some error"
        try {
            error = throwable.message()
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return error
    }

}