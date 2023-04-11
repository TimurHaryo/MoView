package com.timtam.navigation.navigator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.timtam.navigation.extension.safeNavigate

sealed class NavigationEvent {
    object Back : NavigationEvent()

    object FinishActivity : NavigationEvent()

    data class To(val direction: NavDirections) : NavigationEvent()

    data class StartActivity(
        val activity: Activity,
        val bundle: Bundle? = null,
        val flag: Int? = null
    ) : NavigationEvent()

    data class StartActivityForResult(
        val launcher: ActivityResultLauncher<Intent>,
        val activity: Activity,
        val bundle: Bundle? = null
    ) : NavigationEvent()

    data class FinishActivityWithResult(
        val resultCode: Int,
        val intent: Intent? = null
    ) : NavigationEvent()

    data class OpenDialog(
        val dialog: DialogFragment,
        val tag: String
    ) : NavigationEvent()

    fun navigate(
        sourceFragment: Fragment,
        onBack: () -> Unit
    ) {
        when (this) {
            Back -> onBack()
            FinishActivity -> sourceFragment.activity?.finish()
            is FinishActivityWithResult -> {
                sourceFragment.activity?.setResult(resultCode, intent)
                sourceFragment.activity?.finish()
            }
            is OpenDialog -> {
                val transaction = sourceFragment.hideDialog(tag)
                dialog.show(transaction, tag)
            }
            is StartActivity -> {
                val intent = Intent(sourceFragment.requireActivity(), activity::class.java)
                bundle?.let { intent.putExtras(it) }
                flag?.let { intent.flags = it }
                sourceFragment.startActivity(intent)
            }
            is StartActivityForResult -> {
                val intent = Intent(sourceFragment.requireActivity(), activity::class.java)
                bundle?.let { intent.putExtras(it) }
                launcher.launch(intent)
            }
            is To -> {
                sourceFragment.findNavController().safeNavigate(direction)
            }
        }
    }

    private fun Fragment.hideDialog(tag: String): FragmentTransaction {
        val transaction = childFragmentManager.beginTransaction()
        val prevFragment = childFragmentManager.findFragmentByTag(tag)

        if (prevFragment != null) {
            (prevFragment as DialogFragment).dismiss()
            transaction.remove(prevFragment)
        }

        return transaction
    }
}
