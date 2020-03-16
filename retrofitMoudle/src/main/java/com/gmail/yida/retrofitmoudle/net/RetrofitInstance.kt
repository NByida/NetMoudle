package com.gmail.yida.retrofitmoudle.net

import android.annotation.SuppressLint
import android.content.Context
import com.gmail.yida.retrofitmoudle.interceptor.AddCookiesInterceptor
import com.gmail.yida.retrofitmoudle.interceptor.ReceivedCookiesInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@SuppressLint("StaticFieldLeak")
object RetrofitInstance {

    private var BASE_URL = ""
    var httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    object holder {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(ReceivedCookiesInterceptor(context))
                .addInterceptor(httpLoggingInterceptor)
         var retrofit:Retrofit.Builder?=null

        fun getRetrofitBuilder():Retrofit.Builder{
            if(retrofit== null){
                synchronized(RetrofitInstance::class.java){
                if(retrofit== null){
                    retrofit=Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClientBuilder.build())
                        //直接获取字符串
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                }
            }
        }
        return retrofit!!
        }
    }


    lateinit var context: Context

    /**
     * 初始化网络框架，必须先在application中调用
     */
    fun init(context: Context, url: String): RetrofitInstance {
        if (url.isEmpty()) throw IllegalArgumentException("baseurl == null")
        BASE_URL = url
        this.context = context
        return this
    }

    /**
     * 添加拦截器
     */
    fun addInterceptor(interceptor: Interceptor): RetrofitInstance{
        holder.okHttpClientBuilder.addInterceptor(interceptor)
        return this
    }

    /**
     * 添加公有参数
     */
    fun addCommonParams(map: HashMap<String, String>): RetrofitInstance {
        holder.okHttpClientBuilder.addInterceptor(AddCookiesInterceptor(context, map))
        return this
    }

    /**
     * 设置网络日志级别
     */
    fun setLogLevel(level: HttpLoggingInterceptor.Level): RetrofitInstance {
        httpLoggingInterceptor.level = level
        return this
    }


    /**
     * 创建service实列
     */
    fun <T> create(serviceClass: Class<T>): T {
        if (BASE_URL.isEmpty()) throw IllegalArgumentException("baseurl == null")
        return holder.getRetrofitBuilder().build().create(serviceClass)
    }
}