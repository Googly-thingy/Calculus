package com.example.data

import kotlinx.coroutines.flow.Flow

class CalculationRepository(private val dao: CalculationDao) {
    val allCalculations: Flow<List<CalculationEntity>> = dao.getAllCalculations()

    suspend fun saveCalculation(
        expression: String,
        result: String,
        dimensionFormula: String? = null,
        matchedQuantity: String? = null,
        isDimensionQuery: Boolean = false
    ) {
        val entity = CalculationEntity(
            expression = expression,
            result = result,
            dimensionFormula = dimensionFormula,
            matchedQuantity = matchedQuantity,
            isDimensionQuery = isDimensionQuery
        )
        dao.insert(entity)
    }

    suspend fun deleteCalculation(id: Int) {
        dao.deleteById(id)
    }

    suspend fun clearHistory() {
        dao.clearAll()
    }
}
