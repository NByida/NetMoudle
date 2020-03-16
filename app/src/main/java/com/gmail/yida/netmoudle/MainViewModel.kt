package com.gmail.yida.netmoudle

import androidx.lifecycle.MutableLiveData
import com.gmail.yida.retrofitmoudle.net.BaseViewModel
import com.gmail.yida.retrofitmoudle.net.RetrofitInstance

class MainViewModel : BaseViewModel() {
    var list = MutableLiveData<List<Poetry>>()


    fun getPoetryByTag() {
        launchOnlyResult({
            RetrofitInstance.create(GankApi::class.java).getPoetry(1)
        }, { list.value = it })
    }

}