package com.timtam.uikit.recyclerview.payloadcontrol

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Timur
 *
 * This class is for handle missing payload in recycler view. Recyclerview won't execute/notify
 * item change (especially with payload) when the target view holder is not bind yet. This class
 * will save all of the payload along with target position and execute it when [RecyclerView.ViewHolder]
 * already bind
 *
 * [Payload] is any data which devs want to pass
 * */
interface AdapterPayloadDelegate<Payload> {
    var recyclerHost: RecyclerView?

    fun enqueueAdapterPayload(
        targetPosition: Int,
        payload: Payload,
        useMainThread: Boolean = true,
        also: ((Payload) -> Unit)? = null
    )

    fun executePendingPayload(position: Int)

    /**
     * Register @Nullable [RecyclerView] to perform inner execution i.e [RecyclerView.Adapter.notifyItemChanged]
     *
     * Calls Recommendation: [RecyclerView.Adapter.onAttachedToRecyclerView] AFTER super
     * */
    fun registerHost(host: RecyclerView?) {
        recyclerHost = host
    }

    /**
     * Register @Nullable [RecyclerView] to perform inner execution i.e [RecyclerView.Adapter.notifyItemChanged]
     *
     * Calls Recommendation: [RecyclerView.Adapter.onDetachedFromRecyclerView] BEFORE super
     * */
    fun cleanUp() {
        recyclerHost = null
    }
}
