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
