package com.example.myfitnesslogger2023.utils

class dataStoreAction(
    val url : String,
    val sheetName: String,
    val col: Int,
    val row: Int,
    val value: String,
    val key: String,
    val collection: String,
    val data : HashMap<String, Any>)