package com.CBPrograms.myfitnesslogger2023.enumerations

enum class googleSheetType(val id: Int) {

    unknown(0),
    none(1),
    dataSheet(2),
    activitySheet(3),
    caloriesSheet(4);

    override fun toString() : String
        {
            return when(this)
            {
                dataSheet -> "Data"
                activitySheet -> "Aktivitaet"
                caloriesSheet -> "Kalorien"
                else -> "unbekannt"
            }
        }
    }


