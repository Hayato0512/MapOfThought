package com.hk.mapofthoughts2.feature_note.presentation.AudioPage.entities

import java.text.SimpleDateFormat
import java.util.*

data class Recording(
    val duration: String,
    val readableDate: String,
    val readableDayTime: String,
    val date: Date,
    val path: String
)

fun generateRecordingName(path: String?): String {
    return "${path}/Macaw-${
        SimpleDateFormat("ddMMyyyy-HHmmss", Locale.getDefault()).format(
            Calendar.getInstance().time
        )
    }.m4a"
}