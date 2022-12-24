package com.timtam.usecase.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProvider @Inject constructor() {
    val io: CoroutineDispatcher = Dispatchers.IO
    val main: CoroutineDispatcher = Dispatchers.Main
    val default: CoroutineDispatcher = Dispatchers.Default
    val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
