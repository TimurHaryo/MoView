package com.timtam.uikit.extension

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.preAttach(attachBlock: RecyclerView.() -> Unit) {
    if (adapter == null) attachBlock()
}
