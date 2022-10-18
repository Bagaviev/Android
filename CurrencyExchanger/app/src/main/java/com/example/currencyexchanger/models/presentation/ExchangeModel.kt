package com.example.currencyexchanger.models.presentation

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */

data class ExchangeModel(
    val base: String,
    val timeLoaded: String,
    val success: Boolean,
    val rates: List<NormalRate>
)

@Entity(tableName = "SavedRates")
data class NormalRate(
    @PrimaryKey
    val name: String,
    val value: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NormalRate

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
