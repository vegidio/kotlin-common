package io.vinicius.androidcommon.util

interface Callback<T>
{
    fun response(response: T)
}