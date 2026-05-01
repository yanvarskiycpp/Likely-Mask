package com.topjohnwu.magisk.core

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog

object FirstLaunchDialog {
    private const val FIRST_LAUNCH_PREF = "first_launch_likely_mask"
    private const val FIRST_LAUNCH_KEY = "has_launched_before"

    fun showFirstLaunchDialogIfNeeded(context: Context, callback: (Boolean) -> Unit = {}) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            FIRST_LAUNCH_PREF,
            Context.MODE_PRIVATE
        )

        val hasLaunchedBefore = sharedPreferences.getBoolean(FIRST_LAUNCH_KEY, false)

        if (!hasLaunchedBefore) {
            // First launch - show dialog
            showReinstallDialog(context, callback, sharedPreferences)
            
            // Mark that we've launched before
            sharedPreferences.edit().putBoolean(FIRST_LAUNCH_KEY, true).apply()
        }
    }

    private fun showReinstallDialog(
        context: Context,
        callback: (Boolean) -> Unit,
        sharedPreferences: SharedPreferences
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Likely Mask")
            .setMessage("Советуем переустановить Likely Mask")
            .setPositiveButton("Да") { _, _ ->
                callback(true)
            }
            .setNegativeButton("Нет") { _, _ ->
                callback(false)
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}
