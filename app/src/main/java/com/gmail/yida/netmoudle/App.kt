package com.gmail.yida.netmoudle

import android.app.Application
import com.gmail.yida.retrofitmoudle.net.RetrofitInstance

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        var map:HashMap<String,String> = HashMap()
        map.put("yida","aa")

        RetrofitInstance.init(this, "https://xuyida.club")
                .setLogLevel(okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS)
                .addCommonParams(map)
    }
}