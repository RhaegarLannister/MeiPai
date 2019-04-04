package com.rhaegar.meipai.util


import android.content.Context
import com.rhaegar.meipai.App
import java.util.*

/**
 * Author:    Li Hai Kun
 * Describe:  SP工具类
 * Date:      2016/7/20
 */
object SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    private val FILE_NAME = "share_data"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法

     * @param key
     * *
     * @param value
     */
    fun put(key: String, value: Any): Boolean {
        val sp = App.app.applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        if (value is String) {
            editor.putString(key, value)
        } else if (value is Int) {
            editor.putInt(key, value)
        } else if (value is Boolean) {
            editor.putBoolean(key, value)
        } else if (value is Float) {
            editor.putFloat(key, value)
        } else if (value is Long) {
            editor.putLong(key, value)
        } else if (value is Double) {
            editor.putString(key, value.toString())
        } else {
            editor.putString(key, value.toString())
        }
        return editor.commit()
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    fun put(map: Map<String, String>): Boolean {
        val sp = App.app.applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        val list = ArrayList(map.keys)
        for (key in list) {
            editor.putString(key, map[key])
        }
        return editor.commit()
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值

     * @param key
     * *
     * @param defaultObject
     * *
     * @return
     */
    operator fun get(key: String, defaultObject: Any): Any {
        val sp = App.app.applicationContext.getSharedPreferences(
            FILE_NAME,
                Context.MODE_PRIVATE)
        if (defaultObject is String) {
            return sp.getString(key, defaultObject)
        } else if (defaultObject is Int) {
            return sp.getInt(key, defaultObject)
        } else if (defaultObject is Boolean) {
            return sp.getBoolean(key, defaultObject)
        } else if (defaultObject is Float) {
            return sp.getFloat(key, defaultObject)
        } else if (defaultObject is Long) {
            return sp.getLong(key, defaultObject)
        }else if (defaultObject is Double){
            val string = sp.getString(key, "")
            if (string.isEmpty()){
                return defaultObject
            }
            return string.toDouble()
        }

        return ""
    }

    /**
     * 移除某个key值已经对应的值

     * @param key
     */
    fun remove(key: String): Boolean {
        val sp = App.app.applicationContext.getSharedPreferences(
            FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        return editor.commit()
    }

    /**
     * 清除所有数据
     */
    fun clear(): Boolean {
        val sp = App.app.applicationContext.getSharedPreferences(
            FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        return editor.commit()
    }

    /**
     * 查询某个key是否已经存在

     * @param key
     * *
     * @return
     */
    operator fun contains(key: String): Boolean {
        val sp = App.app.applicationContext.getSharedPreferences(
            FILE_NAME,
                Context.MODE_PRIVATE)
        return sp.contains(key)
    }

}
