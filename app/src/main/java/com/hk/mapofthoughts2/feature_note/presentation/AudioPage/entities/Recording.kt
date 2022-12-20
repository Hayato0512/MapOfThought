package com.hk.mapofthoughts2.feature_note.presentation.AudioPage.entities

import android.media.MediaMetadataRetriever
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

data class Recording(
    val duration: String,
    val readableDate: String,
    val readableDayTime: String,
    val date: Date,
    val path: String
)

fun generateRecordingName(path: String?): String {
    return "${path}/${
        SimpleDateFormat("ddMMyyyy-HHmmss", Locale.getDefault()).format(
            Calendar.getInstance().time
        )
    }.m4a"
}

fun convertFilesToRecordings(file:List<File>):List<Recording>{

    val mmr = MediaMetadataRetriever()
 val recordingList = file.map{

     println("debug: Recording.kt convertFilesToRecordings. it.path is ${it.path}")
     println("debug:  last 3 char is ${it.path.takeLast(3)}")
     var lastThreeChars = it.path.takeLast(3)
//     if(lastThreeChars =="jpg"){
//         //do nothing
//     }else {

         val calendar = Calendar.getInstance()
         calendar.time = Date(
             it.lastModified()
         )

         mmr.setDataSource(it.path)

         val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) ?: "0"

         val timeDuration = convertDurationToString(duration.toInt())

         return@map Recording(
             readableDate = "${calendar.get(Calendar.DAY_OF_MONTH)} ${
                 SimpleDateFormat(
                     "MMM",
                     Locale.getDefault()
                 ).format(calendar.time)
             }",
             readableDayTime = "${
                 SimpleDateFormat(
                     "EEEE",
                     Locale.getDefault()
                 ).format(calendar.time)
             } at " + String.format(
                 "%02d:%02d",
                 calendar.get(Calendar.HOUR_OF_DAY),
                 calendar.get(Calendar.MINUTE)
             ),
             duration = timeDuration,
             path = it.path,
             date = Date(it.lastModified())

         )
//     }
     }.toMutableList()

//if JPG, filter them out.
    mmr.release()
    return recordingList.toList()
}

fun convertDurationToString(duration:Int):String = String.format(
    "%02d:%02d",
    TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
    TimeUnit.MILLISECONDS.toSeconds(duration.toLong())
)

