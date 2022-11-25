package com.capstone.hewankita.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleCare(
    var id: Int = 0,
    var outlet: String? = null,
    var check_in: String? = null,
    var check_out: String? = null,
    var time_of_arrival: String? = null
) : Parcelable