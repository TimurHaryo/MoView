package com.timtam.initial.model.type

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import com.timtam.navigation.R

enum class MainTabType(
    @NavigationRes val navigationRes: Int
) {
    HOME(R.navigation.home_flow),
    REVIEW(R.navigation.review_flow),
    COLLECTION(R.navigation.collection_flow);

    companion object {
        fun getTypeById(@IdRes idRes: Int) = when (idRes) {
            R.id.home_flow -> HOME
            R.id.review_flow -> REVIEW
            R.id.collection_flow -> COLLECTION
            else -> null
        }
    }
}
