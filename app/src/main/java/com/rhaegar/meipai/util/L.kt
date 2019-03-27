package com.rhaegar.meipai.util

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2017/7/4
 */
object L {
    private val VERBOSE = 1          //显示Verbose及以上的Log
    private val DEBUG = 2            //显示Debug及以上的Log
    private val INFO = 3            //显示Info及以上的Log
    private val WARN = 4            //显示Warn及以上的Log
    private val ERROR = 5            //显示Error及以上的Log
    private val NOTHING = 6        //全部不显示

    private val LEVEL = VERBOSE    //控制显示的级别

    var LogEnable = true

    private var className: String = ""            //所在的类名
    private var methodName: String = ""            //所在的方法名
    private var lineNumber: Int = 0                //所在行号

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    private fun createLog(log: String): String {

        val buffer = StringBuffer()
        buffer.append("[")
        buffer.append(methodName)
        buffer.append(":")
        buffer.append(lineNumber)
        buffer.append("]")
        buffer.append(log)

        return buffer.toString()
    }

    fun v(message: String) {
        if (LEVEL <= VERBOSE&&LogEnable) {
            getMethodNames(Throwable().stackTrace)
            android.util.Log.v(className, createLog(message))
        }
    }

    fun d(message: String) {
        if (LEVEL <= DEBUG&&LogEnable) {
            getMethodNames(Throwable().stackTrace)
            android.util.Log.d(className, createLog(message))
        }
    }

    fun i(message: String) {
        if (LEVEL <= INFO&&LogEnable) {
            getMethodNames(Throwable().stackTrace)
            android.util.Log.i(className, createLog(message))
        }
    }

    fun w(message: String) {
        if (LEVEL <= WARN&&LogEnable) {
            getMethodNames(Throwable().stackTrace)
            android.util.Log.w(className, createLog(message))
        }
    }

    fun e(message: String) {
        if (LEVEL <= ERROR&&LogEnable) {
            getMethodNames(Throwable().stackTrace)
            android.util.Log.e(className, createLog(message))
        }
    }
}