package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculations")
data class CalculationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val expression: String,
    val result: String,
    val dimensionFormula: String? = null,
    val matchedQuantity: String? = null,
    val isDimensionQuery: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
