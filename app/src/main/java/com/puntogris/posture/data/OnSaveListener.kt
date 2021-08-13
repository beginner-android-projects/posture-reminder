package com.puntogris.posture.data

interface OnSaveListener {
    fun onSuccess()
    fun onError(it: Throwable)
}