package com.gmail.yida.retrofitmoudle.net

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.yida.retrofitmoudle.common.ExceptionHandle
import com.gmail.yida.retrofitmoudle.common.IBaseResponse
import com.gmail.yida.retrofitmoudle.common.RequestConfig
import com.gmail.yida.retrofitmoudle.common.ResponseThrowable
import com.gmail.yida.retrofitmoudle.liveData.SingleLiveEvent
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    val defUI: UIChange by lazy { UIChange() }

    /**
     * UI事件
     * 需要在view中注册使用
     * viewModel.defUI.showDialog.observe(this, Observer {
    showLoading()
    })
     */
    inner class UIChange {
        val showDialog by lazy { SingleLiveEvent<String>() }
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
        val toastEvent by lazy { SingleLiveEvent<String>() }
    }

    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

    /**
     * 发起网络请求，不过滤结果
     */
    fun launchGo(
            block: suspend CoroutineScope.() -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {},
            complete: suspend CoroutineScope.() -> Unit = {},
            requestConfig: RequestConfig = RequestConfig()
    ) {
        if (requestConfig.showDialog) defUI.showDialog.call()
        launchUI {
            handleException(
                    withContext(Dispatchers.IO) { block },
                    {
                        if (requestConfig.showToast) defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
                        error(it)
                    },
                    {
                        defUI.dismissDialog.call()
                        complete()
                    })
        }
    }

    /**
     * 发起网络请求，过滤结果
     */
    fun <T> launchOnlyResult(
            block: suspend CoroutineScope.() -> IBaseResponse<T>,
            success: (T) -> Unit,
            error: (ResponseThrowable) -> Unit = {},
            complete: () -> Unit = {},
            requestConfig: RequestConfig = RequestConfig()
    ) {
        if (requestConfig.showDialog) defUI.showDialog.call()
        launchUI {
            handleException({
                withContext(Dispatchers.IO) {
                    block()
                }
            },
                    { res ->
                        executeResponse(res) { success(it) }
                    },
                    {
                        if (requestConfig.showToast) defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
                        error(it)
                    },
                    {
                        defUI.dismissDialog.call()
                        complete()
                    })
        }
    }

    /**
     * 设置成功code码
     */
    var successCode: Int = 2000


    /**
     * 请求结果过滤
     * code==successCode为成功
     */
    private suspend fun <T> executeResponse(
            response: IBaseResponse<T>,
            success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            if (response.code() == successCode) success(response.data())
            else throw ResponseThrowable(response.code(), response.msg())
        }
    }


    private suspend fun <T> handleException(
            block: suspend CoroutineScope.() -> IBaseResponse<T>,
            success: suspend CoroutineScope.(IBaseResponse<T>) -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
            complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                Log.e("tag", e.message)
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }

    /**
     * 异常统一处理
     */
    private suspend fun handleException(
            block: suspend CoroutineScope.() -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
            complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
            } catch (e: Throwable) {
                Log.e("tag", e.message)
                error(ExceptionHandle.handleException(e))
            } finally {
                complete()
            }
        }
    }
}