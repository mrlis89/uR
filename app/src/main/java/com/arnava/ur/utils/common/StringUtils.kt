package com.arnava.ur.utils.common

object StringUtils {
    fun String.isImage(): Boolean {
        val subStr = this.substring(this.length-3, this.length)
        return (subStr == "jpg" || subStr == "png" || subStr == "gif")
    }
}