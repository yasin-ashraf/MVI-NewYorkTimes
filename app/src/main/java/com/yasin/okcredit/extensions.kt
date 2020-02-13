package com.yasin.okcredit

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by Yasin on 10/2/20.
 */
fun ViewGroup.inflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = false
): View = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Context.openChrome(url: String, onError: (() -> Unit)? = null) {
    openBrowser("com.android.chrome", url) {
        openBrowser("com.android.beta", url) {
            openBrowser("com.android.dev", url) {
                openBrowser("com.android.canary", url) {
                    onError?.invoke() ?: openBrowser(null, url)
                }
            }
        }
    }
}

fun Context.openBrowser(packageName: String?, url: String, onError: (() -> Unit)? = null) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            setPackage(packageName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    } catch (e: ActivityNotFoundException) {
        onError?.invoke()
    }
}