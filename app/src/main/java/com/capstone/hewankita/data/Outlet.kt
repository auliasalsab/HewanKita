package com.capstone.hewankita.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Outlet (
    var nameOutlet: String = "",
    var address: String = ""
) : Parcelable