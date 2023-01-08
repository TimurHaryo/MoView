package com.timtam.uikit.extension

import androidx.recyclerview.widget.RecyclerView

typealias RecyclerViewAdapter = RecyclerView.Adapter<RecyclerView.ViewHolder>

val RecyclerView.hasActiveAdapter get() = adapter != null

fun RecyclerView.preAttach(attachBlock: RecyclerView.() -> Unit) {
    if (adapter == null) attachBlock()
}

fun RecyclerView.detachFromAdapter() {
    adapter = null
}

inline fun <reified Payload> payloadByType(
    payloads: MutableList<Any>,
    crossinline onPayload: (Payload) -> Unit
) {
    payloads.filterIsInstance<Payload>().forEach { onPayload(it) }
}
