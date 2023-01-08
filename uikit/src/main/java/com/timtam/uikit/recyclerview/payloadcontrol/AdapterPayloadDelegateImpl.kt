package com.timtam.uikit.recyclerview.payloadcontrol

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class AdapterPayloadDelegateImpl<Payload> : AdapterPayloadDelegate<Payload> {
    private val pendingPayload: MutableSet<Pair<Int, Payload>> = mutableSetOf()

    override var recyclerHost: RecyclerView? = null

    private val firstVisibleHolder: Int
        get() {
            if (recyclerHost?.layoutManager == null) return RecyclerView.NO_POSITION
            return when (recyclerHost?.layoutManager) {
                is LinearLayoutManager, is GridLayoutManager -> {
                    (recyclerHost?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                }
                is StaggeredGridLayoutManager -> {
                    (recyclerHost?.layoutManager as StaggeredGridLayoutManager)
                        .findFirstVisibleItemPositions(null)
                        .firstOrNull() ?: RecyclerView.NO_POSITION
                }
                else -> RecyclerView.NO_POSITION
            }
        }

    private val lastVisibleHolder: Int
        get() {
            return if (recyclerHost?.layoutManager != null &&
                recyclerHost?.layoutManager is LinearLayoutManager
            ) {
                (recyclerHost?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            } else RecyclerView.NO_POSITION
        }

    /**
     * This method will enqueue payload and execute it to targeted position.
     *
     * Calls Recommendation: [androidx.fragment.app.Fragment], [androidx.appcompat.app.AppCompatActivity], [RecyclerView.Adapter]
     * */
    override fun enqueueAdapterPayload(
        targetPosition: Int,
        payload: Payload,
        useMainThread: Boolean,
        also: ((Payload) -> Unit)?
    ) {
        also?.invoke(payload)
        if (targetPosition !in firstVisibleHolder..lastVisibleHolder) {
            pendingPayload.add(targetPosition to payload)
            return
        }

        if (useMainThread) {
            recyclerHost?.adapter?.notifyItemChanged(targetPosition, payload)
            return
        }

        recyclerHost?.post {
            recyclerHost?.adapter?.notifyItemChanged(targetPosition, payload)
        }
    }

    /**
     * This method has purpose to execute any pending binding in collection based on desired position.
     * Once executed, the pending payload will be deleted from the list to prevent multiple execution.
     *
     * Calls Recommendation: [RecyclerView.Adapter.onViewAttachedToWindow]
     * */
    override fun executePendingPayload(position: Int) {
        pendingPayload.filter { it.first == position }.forEach { (pos, payload) ->
            recyclerHost?.post {
                recyclerHost?.adapter?.notifyItemChanged(pos, payload)
            }
            pendingPayload.remove(pos to payload)
        }
    }
}
