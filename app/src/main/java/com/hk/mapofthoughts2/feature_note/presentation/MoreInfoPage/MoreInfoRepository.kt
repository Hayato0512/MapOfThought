package com.hk.mapofthoughts2.feature_note.presentation.MoreInfoPage

import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.entities.Recording
import com.hk.mapofthoughts2.feature_note.presentation.AudioPage.entities.convertFilesToRecordings
import java.io.File


class MoreInfoRepository {

    fun getRecordings(directory: String): List<Recording> {
        val file = File(directory)
        //get all the files in the directory, and then pass it to convertFilesTorecordings => if empty, just pass empty list
        //we want to convert those files to more readable format, therefore we want to convert list of files into list of Recordings.
        if(file.exists()){
            println("debug: getRecordings(), file exists")
        }else{
            println("debug: getRecordings(), file NOT exist")
        }
        var listToReturn = file.listFiles()?.toList() ?: listOf()
        var newListToReturn  = listToReturn.filter{

           it.name.takeLast(3)=="m4a"

        }
       println("debug: after fileterling , ")
    newListToReturn.forEach {
        println("debug: ${it.name}")
    }




//        return convertFilesToRecordings(file.listFiles()?.toList() ?: listOf())
        return convertFilesToRecordings(newListToReturn)
    }
}
