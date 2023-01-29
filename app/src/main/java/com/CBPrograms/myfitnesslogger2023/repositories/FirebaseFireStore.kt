package com.CBPrograms.myfitnesslogger2023.epositories
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

object firebaseFireStoreService {

    val fireStoreDatabase = FirebaseFirestore.getInstance()

    fun getIDs(
        dataBase : String,
        onSuccessCallback: (result : List<String>) -> Unit,
        onErrorCallback: (exception : Exception) -> Unit)
    {

        val future = com.CBPrograms.myfitnesslogger2023.epositories.firebaseFireStoreService.fireStoreDatabase.collection(dataBase)
            .get()
            .addOnSuccessListener {
                var ids = arrayListOf<String>();

                val documents = it.documents
                if (documents != null) {
                    for (document in documents) {
                        ids.add(document.id)
                    }
                }

                onSuccessCallback(ids)
            }
            .addOnFailureListener {
                onErrorCallback(it)
            }
    }

    fun modifyData(dataBase : String,
                   data : HashMap<String, Any>)
    {
        val ids = com.CBPrograms.myfitnesslogger2023.epositories.firebaseFireStoreService.getIDs(
            dataBase,
            {
                for (id in it) {
                    var ref =
                        com.CBPrograms.myfitnesslogger2023.epositories.firebaseFireStoreService.fireStoreDatabase.collection(
                            dataBase
                        ).document(id)
                    ref.update(data)
                }
            },
            {

            }
        )
    }

    fun getData(dataBase : String,
                fields  : ArrayList<String>,
                onSuccessCallback: (result: HashMap<String, Any>) -> Unit,
                onErrorCallback: (exception : Exception) -> Unit)
    {

        com.CBPrograms.myfitnesslogger2023.epositories.firebaseFireStoreService.fireStoreDatabase.collection(dataBase)
            .get()
            .addOnSuccessListener {
                val result = hashMapOf<String, Any>()

                if (!it.isEmpty()) {
                    for (item in it.documents) {
                        for (field in fields)
                        {
                            result.set(field, item.get(field) ?: 0)
                        }
                    }
                }

                onSuccessCallback(result);
            }
            .addOnFailureListener {
                onErrorCallback(it)
            }
    }

    fun addData(collection : String,  data : HashMap<String, Any>)
    {
        com.CBPrograms.myfitnesslogger2023.epositories.firebaseFireStoreService.fireStoreDatabase.collection(collection)
                .add(data)
                .addOnSuccessListener {
                    Log.d(TAG, "Added document with ID ${it.id}")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding document $exception")
                }
    }
}