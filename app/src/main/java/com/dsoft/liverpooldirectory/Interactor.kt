package com.dsoft.liverpooldirectory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Interactor @Inject constructor() {

    var vkSuccessConnectionListener: VkSuccessConnectionListener? = null

    var isSuccess: Boolean = false
        set(value) {
            field = value
            vkSuccessConnectionListener?.onCatch(value)
        }

    interface VkSuccessConnectionListener {
        fun onCatch(isSuccess: Boolean)
    }
}