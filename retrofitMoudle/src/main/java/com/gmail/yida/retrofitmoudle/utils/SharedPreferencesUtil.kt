package com.gmail.yida.retrofitmoudle.utils

import android.content.Context

object SharedPreferencesUtil {
    fun push(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun pop(context: Context?, key: String): String? {
        if (null == context) return null
        val sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        return sp.getString(key, null)
    }

    fun getBooleanConfig(context: Context?, key: String): Boolean {
        if (null == context) return false
        val sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        return sp.getBoolean(key, false)
    }


}
