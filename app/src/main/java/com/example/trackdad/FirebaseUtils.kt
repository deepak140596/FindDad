package com.example.trackdad

import com.google.firebase.database.FirebaseDatabase

class FirebaseUtils {
    val database = FirebaseDatabase.getInstance().reference

    fun writeDataToFirebase(dataModel: DataModel){

        database.child("location")
            .child(dataModel.timestamp.toString())
            .setValue(dataModel)
    }
}