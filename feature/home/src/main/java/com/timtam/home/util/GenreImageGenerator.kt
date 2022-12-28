package com.timtam.home.util

import android.content.Context
import androidx.annotation.DrawableRes
import com.timtam.uikit.R as uikitR

/**
 * This class is actually a dummy class to get genre icon by local resource.
 * It would be better if the image is already available from API :)
 * */
object GenreImageGenerator {

    @DrawableRes
    fun Context?.findDrawableIdByTag(tag: String): Int? {
        if (this == null) return null

        return try {
            when {
                matchEntryName(uikitR.drawable.ic_genre_action, tag) -> {
                    uikitR.drawable.ic_genre_action
                }
                matchEntryName(uikitR.drawable.ic_genre_adventure, tag) -> {
                    uikitR.drawable.ic_genre_adventure
                }
                matchEntryName(uikitR.drawable.ic_genre_animation, tag) -> {
                    uikitR.drawable.ic_genre_animation
                }
                matchEntryName(uikitR.drawable.ic_genre_comedy, tag) -> {
                    uikitR.drawable.ic_genre_comedy
                }
                matchEntryName(uikitR.drawable.ic_genre_crime, tag) -> {
                    uikitR.drawable.ic_genre_crime
                }
                matchEntryName(uikitR.drawable.ic_genre_documentary, tag) -> {
                    uikitR.drawable.ic_genre_documentary
                }
                matchEntryName(uikitR.drawable.ic_genre_drama, tag) -> {
                    uikitR.drawable.ic_genre_drama
                }
                matchEntryName(uikitR.drawable.ic_genre_family, tag) -> {
                    uikitR.drawable.ic_genre_family
                }
                matchEntryName(uikitR.drawable.ic_genre_fantasy, tag) -> {
                    uikitR.drawable.ic_genre_fantasy
                }
                matchEntryName(uikitR.drawable.ic_genre_history, tag) -> {
                    uikitR.drawable.ic_genre_history
                }
                matchEntryName(uikitR.drawable.ic_genre_horror, tag) -> {
                    uikitR.drawable.ic_genre_horror
                }
                matchEntryName(uikitR.drawable.ic_genre_music, tag) -> {
                    uikitR.drawable.ic_genre_music
                }
                matchEntryName(uikitR.drawable.ic_genre_mystery, tag) -> {
                    uikitR.drawable.ic_genre_mystery
                }
                matchEntryName(uikitR.drawable.ic_genre_romance, tag) -> {
                    uikitR.drawable.ic_genre_romance
                }
                matchEntryName(uikitR.drawable.ic_genre_science_fiction, tag) -> {
                    uikitR.drawable.ic_genre_science_fiction
                }
                matchEntryName(uikitR.drawable.ic_genre_thriller, tag) -> {
                    uikitR.drawable.ic_genre_thriller
                }
                matchEntryName(uikitR.drawable.ic_genre_tv_movie, tag) -> {
                    uikitR.drawable.ic_genre_tv_movie
                }
                matchEntryName(uikitR.drawable.ic_genre_war, tag) -> {
                    uikitR.drawable.ic_genre_war
                }
                matchEntryName(uikitR.drawable.ic_genre_western, tag) -> {
                    uikitR.drawable.ic_genre_western
                }
                else -> null
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun Context.matchEntryName(@DrawableRes id: Int, tag: String) =
        resources.getResourceEntryName(id).contains(tag, true)
}
