package com.timtam.common.extension

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.preAttach(attachBlock: RecyclerView.() -> Unit) {
    if (adapter.isNull()) attachBlock()
}
