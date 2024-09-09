package com.example.composemoviebox

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToLocal(inputDate: String): String =
    if (inputDate.isNotEmpty()) {
        try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            val date = LocalDate.parse(inputDate, inputFormatter)
            date.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            "No date because of parse exception"
        }
    } else {
        "No date"
    }
