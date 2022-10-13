package com.example.currencyexchanger.utils

import android.app.AlertDialog
import android.content.Context

/**
 * @author Bulat Bagaviev
 * @created 14.10.2022
 */
class Utility {

    companion object {
        fun provideAlertDialog(context: Context, message: String): AlertDialog {
            return AlertDialog.Builder(context)
                .setTitle("Какая-то ошибка")
                .setMessage(message)
                .setPositiveButton("Похуй", null)
                .setNegativeButton("Пойти нахуй", null)
                .create()
        }
    }
}