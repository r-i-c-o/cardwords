package com.ricode.kotlinwords.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:isGone")
fun isGone(v: View, isGone: Boolean) {
    v.visibility = if (isGone) View.GONE else View.VISIBLE
}
