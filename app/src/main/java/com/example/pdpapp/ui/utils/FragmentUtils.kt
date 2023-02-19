package com.example.pdpapp.ui.utils

import android.app.Service
import android.content.Intent
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun <T: Service> Fragment.startService(cls: Class<T>) =
    requireActivity().startService(Intent(requireActivity(), cls))

fun Fragment.withString(@StringRes id: Int, vararg params: Any) =
    requireContext().getString(id, params)