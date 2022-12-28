package com.timtam.home.ui.header.payload

import com.timtam.home.ui.home.adapter.payload.HomePayload

sealed class HomeHeaderPayload : HomePayload {

    data class ShowMantra(val mantra: String) : HomeHeaderPayload()
}
