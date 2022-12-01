package com.timtam.navigation.util

import android.os.Bundle
import android.view.View
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment

class SafeNavHostFragment : NavHostFragment() {
    private var listener: MalfunctionListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = parentFragment as? MalfunctionListener
    }

    override fun onDestroyView() {
        try {
            super.onDestroyView()
        } catch (any: Throwable) {
            listener?.onDestroyViewError(Throwable("$tag -> ${any.message}"))
        } finally {
            listener = null
        }
    }

    interface MalfunctionListener {
        fun onDestroyViewError(t: Throwable)
    }

    companion object {
        private const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        fun createHost(@NavigationRes graphRes: Int): SafeNavHostFragment {
            val host = SafeNavHostFragment()
            if (graphRes != 0) {
                host.arguments = Bundle().apply {
                    putInt(KEY_GRAPH_ID, graphRes)
                }
            }
            return host
        }
    }
}
