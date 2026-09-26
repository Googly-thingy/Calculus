package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CalculationEntity
import com.example.data.CalculationRepository
import com.example.math.AngleMode
import com.example.math.ScientificEvaluator
import com.example.model.DerivationCatalog
import com.example.model.QuantityCategory
import com.example.model.QuantityDerivation
import com.example.model.UnitDefinition
import com.example.model.UnitRegistry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CalculatorUiState(
    val expression: String = "",
    val liveResult: String = "",
    val finalResult: String? = null,
    val angleMode: AngleMode = AngleMode.DEGREE,
    val isSecondMode: Boolean = false,
    val memory: Double = 0.0,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class DimensionalUiState(
    val selectedQuantity: QuantityDerivation = DerivationCatalog.ALL_DERIVATIONS.first { it.symbol == "a" },
    val selectedCategory: QuantityCategory = QuantityCategory.MECHANICS
)

data class UnitConverterUiState(
    val inputValue: String = "100",
    val availableUnits: List<UnitDefinition> = emptyList(),
    val sourceUnit: UnitDefinition? = null,
    val targetUnit: UnitDefinition? = null,
    val convertedValue: String = "",
    val conversionRatioText: String = "",
    val selectedCategory: QuantityCategory = QuantityCategory.MECHANICS
)

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CalculationRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = CalculationRepository(db.calculationDao())
    }

    val historyList: StateFlow<List<CalculationEntity>> = repository.allCalculations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Calculator State ---
    private val _calcState = MutableStateFlow(CalculatorUiState())
    val calcState: StateFlow<CalculatorUiState> = _calcState.asStateFlow()

    // --- Dimensional Analysis State ---
    private val _dimState = MutableStateFlow(DimensionalUiState())
    val dimState: StateFlow<DimensionalUiState> = _dimState.asStateFlow()

    // --- Unit Converter State ---
    private val _convState = MutableStateFlow(UnitConverterUiState())
    val convState: StateFlow<UnitConverterUiState> = _convState.asStateFlow()

    init {
        // Initialize Unit Converter with Length
        setConverterCategory(QuantityCategory.MECHANICS)
    }

    // ==========================================
    // Calculator Actions
    // ==========================================

    fun onKeyInput(key: String) {
        val currentExpr = _calcState.value.expression
        val newExpr = when (key) {
            "sin", "cos", "tan", "asin", "acos", "atan", "sinh", "cosh", "tanh", "ln", "log", "sqrt", "abs" -> {
                "$currentExpr$key("
            }
            "x²" -> "$currentExpr^2"
            "x^y" -> "$currentExpr^"
            "1/x" -> "$currentExpr^(-1)"
            "e^x" -> "$currentExpr*e^"
            "10^x" -> "$currentExpr*10^"
            else -> "$currentExpr$key"
        }

        updateExpressionAndPreview(newExpr)
    }

    fun onBackspace() {
        val currentExpr = _calcState.value.expression
        if (currentExpr.isNotEmpty()) {
            val newExpr = currentExpr.dropLast(1)
            updateExpressionAndPreview(newExpr)
        }
    }

    fun onClear() {
        _calcState.value = _calcState.value.copy(
            expression = "",
            liveResult = "",
            finalResult = null,
            isError = false,
            errorMessage = null
        )
    }

    fun onCalculate() {
        val expr = _calcState.value.expression.trim()
        if (expr.isEmpty()) return

        val evalResult = ScientificEvaluator.evaluate(expr, _calcState.value.angleMode)
        if (evalResult.isSuccess) {
            _calcState.value = _calcState.value.copy(
                finalResult = evalResult.formatted,
                liveResult = evalResult.formatted,
                isError = false,
                errorMessage = null
            )
            // Persist to Room
            viewModelScope.launch {
                repository.saveCalculation(
                    expression = expr,
                    result = evalResult.formatted,
                    isDimensionQuery = false
                )
            }
        } else {
            _calcState.value = _calcState.value.copy(
                isError = true,
                errorMessage = evalResult.errorMessage ?: "Calculation Error"
            )
        }
    }

    private fun updateExpressionAndPreview(expr: String) {
        if (expr.isEmpty()) {
            _calcState.value = _calcState.value.copy(
                expression = "",
                liveResult = "",
                finalResult = null,
                isError = false,
                errorMessage = null
            )
            return
        }

        val eval = ScientificEvaluator.evaluate(expr, _calcState.value.angleMode)
        _calcState.value = _calcState.value.copy(
            expression = expr,
            liveResult = if (eval.isSuccess) eval.formatted else "",
            finalResult = null,
            isError = false,
            errorMessage = null
        )
    }

    fun toggleAngleMode() {
        val newMode = if (_calcState.value.angleMode == AngleMode.DEGREE) AngleMode.RADIAN else AngleMode.DEGREE
        _calcState.value = _calcState.value.copy(angleMode = newMode)
        if (_calcState.value.expression.isNotEmpty()) {
            updateExpressionAndPreview(_calcState.value.expression)
        }
    }

    fun toggleSecondMode() {
        _calcState.value = _calcState.value.copy(isSecondMode = !_calcState.value.isSecondMode)
    }

    fun insertConstant(symbol: String, value: Double) {
        val currentExpr = _calcState.value.expression
        val newExpr = "$currentExpr$symbol"
        updateExpressionAndPreview(newExpr)
    }

    fun memoryAdd() {
        val currentNum = _calcState.value.finalResult?.toDoubleOrNull()
            ?: _calcState.value.liveResult.toDoubleOrNull()
            ?: return
        _calcState.value = _calcState.value.copy(memory = _calcState.value.memory + currentNum)
    }

    fun memorySub() {
        val currentNum = _calcState.value.finalResult?.toDoubleOrNull()
            ?: _calcState.value.liveResult.toDoubleOrNull()
            ?: return
        _calcState.value = _calcState.value.copy(memory = _calcState.value.memory - currentNum)
    }

    fun memoryRecall() {
        val mem = _calcState.value.memory
        val str = ScientificEvaluator.formatNumber(mem)
        updateExpressionAndPreview("${_calcState.value.expression}$str")
    }

    fun memoryClear() {
        _calcState.value = _calcState.value.copy(memory = 0.0)
    }

    fun reuseHistoryItem(item: CalculationEntity) {
        if (item.isDimensionQuery) {
            // Find derivation for item symbol
            val match = DerivationCatalog.ALL_DERIVATIONS.firstOrNull { it.symbol == item.expression || it.name.equals(item.expression, ignoreCase = true) }
            if (match != null) {
                selectDerivationQuantity(match)
            }
        } else {
            updateExpressionAndPreview(item.expression)
        }
    }

    fun deleteHistoryItem(id: Int) {
        viewModelScope.launch {
            repository.deleteCalculation(id)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    // ==========================================
    // Dimensional Calculator Actions
    // ==========================================

    fun selectDerivationQuantity(derivation: QuantityDerivation) {
        _dimState.value = _dimState.value.copy(
            selectedQuantity = derivation,
            selectedCategory = derivation.category
        )

        // Save to Room history
        viewModelScope.launch {
            repository.saveCalculation(
                expression = "${derivation.symbol} (${derivation.name})",
                result = derivation.finalDimension.toFormattedString(),
                dimensionFormula = derivation.finalDimension.toAsciiFormula(),
                matchedQuantity = derivation.name,
                isDimensionQuery = true
            )
        }
    }

    fun selectKeypadCategory(category: QuantityCategory) {
        val firstInCat = DerivationCatalog.ALL_DERIVATIONS.firstOrNull { it.category == category }
        _dimState.value = _dimState.value.copy(
            selectedCategory = category,
            selectedQuantity = firstInCat ?: _dimState.value.selectedQuantity
        )
    }

    // ==========================================
    // Unit Converter Actions
    // ==========================================

    fun setConverterCategory(category: QuantityCategory) {
        val units = UnitRegistry.BASE_AND_NAMED_UNITS.values
            .filter { it.category == category }
            .distinctBy { it.symbol }

        val first = units.firstOrNull()
        val second = if (units.size > 1) units[1] else first

        _convState.value = _convState.value.copy(
            selectedCategory = category,
            availableUnits = units,
            sourceUnit = first,
            targetUnit = second
        )
        recomputeConversion()
    }

    fun setConverterSourceUnit(unit: UnitDefinition) {
        val compatibleUnits = UnitRegistry.BASE_AND_NAMED_UNITS.values
            .filter { it.dimensions == unit.dimensions }
            .distinctBy { it.symbol }

        val newTarget = if (compatibleUnits.contains(_convState.value.targetUnit)) {
            _convState.value.targetUnit
        } else {
            compatibleUnits.firstOrNull { it != unit } ?: unit
        }

        _convState.value = _convState.value.copy(
            availableUnits = compatibleUnits,
            sourceUnit = unit,
            targetUnit = newTarget
        )
        recomputeConversion()
    }

    fun setConverterTargetUnit(unit: UnitDefinition) {
        _convState.value = _convState.value.copy(targetUnit = unit)
        recomputeConversion()
    }

    fun onConverterValueChange(newVal: String) {
        _convState.value = _convState.value.copy(inputValue = newVal)
        recomputeConversion()
    }

    fun swapConverterUnits() {
        val curSrc = _convState.value.sourceUnit
        val curTgt = _convState.value.targetUnit
        _convState.value = _convState.value.copy(
            sourceUnit = curTgt,
            targetUnit = curSrc
        )
        recomputeConversion()
    }

    private fun recomputeConversion() {
        val inputVal = _convState.value.inputValue.toDoubleOrNull() ?: 0.0
        val src = _convState.value.sourceUnit
        val tgt = _convState.value.targetUnit

        if (src == null || tgt == null) {
            _convState.value = _convState.value.copy(convertedValue = "0", conversionRatioText = "")
            return
        }

        val baseValue = src.toBase(inputVal)
        val finalVal = tgt.fromBase(baseValue)

        val oneBase = src.toBase(1.0)
        val oneRatio = tgt.fromBase(oneBase)

        val ratioText = "1 ${src.symbol} = ${ScientificEvaluator.formatNumber(oneRatio)} ${tgt.symbol}"

        _convState.value = _convState.value.copy(
            convertedValue = ScientificEvaluator.formatNumber(finalVal),
            conversionRatioText = ratioText
        )
    }
}
