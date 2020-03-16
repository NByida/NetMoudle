package com.gmail.yida.retrofitmoudle.interceptor

import android.content.Context
import com.gmail.yida.retrofitmoudle.utils.SharedPreferencesUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ReceivedCookiesInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            var cookieBuffer = StringBuffer()
            var headers = originalResponse.headers("Set-Cookie")
            for (str in headers) {
                var str2:String
                try {
                    str2 = str.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                    cookieBuffer.append(str2).append(";")
                } catch (e: Exception) {
                }

            }
            SharedPreferencesUtil.push(context, "cookie", cookieBuffer.toString())
        }
        return originalResponse
    }
}
