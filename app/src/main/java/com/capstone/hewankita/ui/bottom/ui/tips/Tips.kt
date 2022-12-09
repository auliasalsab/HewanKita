package com.capstone.hewankita.ui.bottom.ui.tips

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tips(
    var judul: String = "",
    var deskripsi: String = "",
    var photo: Int = 0
) : Parcelable