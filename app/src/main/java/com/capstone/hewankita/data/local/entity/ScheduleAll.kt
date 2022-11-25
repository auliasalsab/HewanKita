package com.capstone.hewankita.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleAll(
    var id: Int = 0,
    var outlet: String? = null,
    var booking_date: String? = null,
    var booking_time: String? = null
) : Parcelable