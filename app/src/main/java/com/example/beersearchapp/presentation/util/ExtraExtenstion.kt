package com.example.beersearchapp.presentation.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.internals.AnkoInternals

inline fun <reified T : Any> Activity.extraNotNull(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    requireNotNull(if (value is T) value else default) { key }
}

inline fun <reified T: Any> Context.intentFor(vararg params: Pair<String, Any?>): Intent =
    AnkoInternals.createIntent(this, T::class.java, params)