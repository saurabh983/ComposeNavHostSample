package com.gl.hindustantimesrss.apicall

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class KSApiClient : CoroutineScope {

    private val clientJob = SupervisorJob()
    protected val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    protected val uiScope = CoroutineScope(Dispatchers.Main + clientJob)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + clientJob


    fun provideApi(): KSApiService {
        return KSRetrofitInstance.provideApi()
    }

    fun clearJob() {
        clientJob.cancel()
    }
}