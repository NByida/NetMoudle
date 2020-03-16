package com.gmail.yida.netmoudle

import com.gmail.yida.retrofitmoudle.common.IBaseResponse

data class BaseResult<T>(
        val errorMsg: String,
        val errorCode: Int,
        val list: T
) : IBaseResponse<T> {

    override fun code() = 2000

    override fun msg() = errorMsg

    override fun data() = list

    override fun isSuccess() = errorCode == 0

}