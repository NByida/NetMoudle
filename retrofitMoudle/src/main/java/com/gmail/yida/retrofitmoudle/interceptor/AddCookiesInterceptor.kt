package com.gmail.yida.retrofitmoudle.interceptor

import android.content.Context
import com.gmail.yida.retrofitmoudle.utils.SharedPreferencesUtil
import okhttp3.*
import okio.Buffer
import java.io.IOException

import kotlin.collections.HashMap


class AddCookiesInterceptor(private val context: Context, val gernalParamsMap: HashMap<String, String>) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val maxAge = 60 * 60 // 有网络时 设置缓存超时时间1个小时
        val maxStale = 60 * 60 * 24 * 28 // 无网络时，设置超时为4周
        var request = chain.request()
        val builder = request.newBuilder()

        val paramsMap =gernalParamsMap
        val cookie = SharedPreferencesUtil.pop(context, "cookie")
        if (null != cookie && "" != cookie) builder.addHeader("Cookie", cookie)
        if ("POST".equals(request.method(), ignoreCase = true)) {
            //post请求
            val formBodyBuilder = FormBody.Builder()
            for ((key, value) in paramsMap) {
                formBodyBuilder.add(key, value)
            }

            val formBody = formBodyBuilder.build()
            var postBodyString = bodyToString(request.body())
            postBodyString += (if (postBodyString.length > 0) "&" else "") + bodyToString(formBody)
            builder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString))
        } else if ("GET".equals(request.method(), ignoreCase = true)) {
            //get请求
            request = injectParamsIntoUrl(request.url().newBuilder(), builder, paramsMap)
            return chain.proceed(request)
        }

        return chain.proceed(builder.build())
    }

    private fun injectParamsIntoUrl(httpUrlBuilder: HttpUrl.Builder, requestBuilder: Request.Builder, paramsMap: Map<String, String>): Request {
        if (paramsMap.size > 0) {
            val iterator = paramsMap.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next() as Map.Entry<*, *>
                httpUrlBuilder.addQueryParameter(entry.key as String, entry.value as String)
            }
            requestBuilder.url(httpUrlBuilder.build())
            return requestBuilder.build()
        }
        return requestBuilder.build()
    }

    private fun bodyToString(request: RequestBody?): String {
        try {
            val buffer = Buffer()
            if (request != null)
                request.writeTo(buffer)
            else
                return ""
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }
}