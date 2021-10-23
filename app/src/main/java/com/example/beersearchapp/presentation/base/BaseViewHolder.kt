package com.example.beersearchapp.presentation.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer