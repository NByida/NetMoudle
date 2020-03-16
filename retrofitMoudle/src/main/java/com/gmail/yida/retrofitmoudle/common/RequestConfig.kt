package com.gmail.yida.retrofitmoudle.common

class RequestConfig(var showDialog: Boolean=true,var showToast: Boolean=true) {

    fun setShowDialog(showDialog:Boolean):RequestConfig{
        this.showDialog =showDialog
        return this
    }

    fun setshowToast(showToast:Boolean):RequestConfig{
        this.showToast =showToast
        return this
    }
}