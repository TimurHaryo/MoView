package com.timtam.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvision {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
