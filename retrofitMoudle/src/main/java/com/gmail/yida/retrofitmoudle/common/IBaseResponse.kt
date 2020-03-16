package com.gmail.yida.retrofitmoudle.common

interface IBaseResponse<T>  {
    fun code(): Int=2000
    fun msg(): String
    fun data(): T
    fun isSuccess(): Boolean{
        return code()==2000
    }
}