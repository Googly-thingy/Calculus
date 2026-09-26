package com.example.parser

import com.example.model.DimensionVector
import com.example.model.PhysicalQuantity
import com.example.model.PhysicalQuantityRegistry
import com.example.model.UnitRegistry
import kotlin.math.pow

data class ParsedDimensionResult(
    val inputExpression: String,
    val dimensions: DimensionVector,
    val scaleFactorToBaseSi: Double,
    val formattedDimensions: String,
    val asciiFormula: String,
    val baseSiUnits: String,
    val matchedQuantities: List<PhysicalQuantity>,
    val isValid: Boolean = true,
    val errorMessage: String? = null
)

data class EquationHomogeneityResult(
    val equation: String,
    val lhsExpr: String,
    val rhsExpr: String,
    val lhsDimension: DimensionVector,
    val rhsDimension: DimensionVector,
    val isHomogeneous: Boolean,
    val lhsFormula: String,
    val rhsFormula: String,
    val details: String
)

object DimensionalParser {

    /**
     * Resolves a single unit token (e.g., "kg", "m", "kN", "MHz", "cm")
     * Returns the DimensionVector and scale factor to SI base units.
     */
    fun resolveAtomicUnit(token: String): Pair<DimensionVector, Double>? {
        val clean = token.trim()
        if (clean.isEmpty()) return null

        // Common physics variable symbols (e.g. a = L*T^-2, v = L*T^-1, etc.)
        when (clean) {
            "a" -> return Pair(DimensionVector.ACCELERATION, 1.0)
            "v", "u" -> return Pair(DimensionVector.VELOCITY, 1.0)
            "x", "d", "r", "h" -> return Pair(DimensionVector.LENGTH, 1.0)
            "t" -> return Pair(DimensionVector.TIME, 1.0)
            "c" -> return Pair(DimensionVector.VELOCITY, 1.0)
            "g" -> return Pair(DimensionVector.ACCELERATION, 1.0)
            "F" -> return Pair(DimensionVector.FORCE, 1.0)
            "E" -> return Pair(DimensionVector.ENERGY, 1.0)
            "P" -> return Pair(DimensionVector.PRESSURE, 1.0)
            "q", "Q" -> return Pair(DimensionVector.ELECTRIC_CHARGE, 1.0)
            "rho", "ρ" -> return Pair(DimensionVector.DENSITY, 1.0)
            "omega", "ω" -> return Pair(DimensionVector.FREQUENCY, 1.0)
        }

        // Direct lookup
        UnitRegistry.BASE_AND_NAMED_UNITS[clean]?.let {
            return Pair(it.dimensions, it.scaleToBase)
        }

        // Check if token represents pure base dimension symbol (M, L, T, I, Theta, N, J)
        when (clean.uppercase()) {
            "M" -> return Pair(DimensionVector.MASS, 1.0)
            "L" -> return Pair(DimensionVector.LENGTH, 1.0)
            "T" -> return Pair(DimensionVector.TIME, 1.0)
            "I" -> return Pair(DimensionVector.CURRENT, 1.0)
            "THETA", "Θ" -> return Pair(DimensionVector.TEMPERATURE, 1.0)
            "N" -> return Pair(DimensionVector.AMOUNT_OF_SUBSTANCE, 1.0)
            "J" -> return Pair(DimensionVector.LUMINOUS_INTENSITY, 1.0)
        }

        // Try prefix separation (e.g., "km" -> "k" + "m", "kN" -> "k" + "N", "uA" -> "u" + "A")
        val sortedPrefixes = UnitRegistry.PREFIXES.entries.sortedByDescending { it.key.length }
        for ((prefix, factor) in sortedPrefixes) {
            if (clean.startsWith(prefix) && clean.length > prefix.length) {
                val baseUnitStr = clean.substring(prefix.length)
                val baseDef = UnitRegistry.BASE_AND_NAMED_UNITS[baseUnitStr]
                if (baseDef != null) {
                    return Pair(baseDef.dimensions, baseDef.scaleToBase * factor)
                }
            }
        }

        // Case-insensitive lookup fallback (e.g., "v" -> "V", "pa" -> "Pa", "hz" -> "Hz")
        val caseInsensitive = UnitRegistry.BASE_AND_NAMED_UNITS.entries.firstOrNull {
            it.key.equals(clean, ignoreCase = true)
        }?.value
        if (caseInsensitive != null) {
            return Pair(caseInsensitive.dimensions, caseInsensitive.scaleToBase)
        }

        return null
    }

    /**
     * Parses a compound unit expression such as "kg*m/s^2", "N*m", "W/(m*K)", "m/s^2", "1/s".
     */
    fun parseUnitExpression(input: String): ParsedDimensionResult {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) {
            return ParsedDimensionResult(
                inputExpression = "",
                dimensions = DimensionVector.DIMENSIONLESS,
                scaleFactorToBaseSi = 1.0,
                formattedDimensions = "[1] (Dimensionless)",
                asciiFormula = "1",
                baseSiUnits = "dimensionless",
                matchedQuantities = emptyList(),
                isValid = false,
                errorMessage = "Expression is empty"
            )
        }

        // Handle assignment like "a = m/s^2" or "F = N"
        val exprToParse = if (trimmed.contains("=")) {
            trimmed.substringAfter("=").trim()
        } else {
            trimmed
        }

        try {
            val tokens = tokenize(exprToParse)
            val parser = UnitAstParser(tokens)
            val (dim, scale) = parser.parseExpression()

            val formatted = dim.toFormattedString(useSuperscript = true)
            val ascii = dim.toAsciiFormula()
            val siUnits = dim.toSiBaseUnits()
            val matches = PhysicalQuantityRegistry.findMatches(dim)

            return ParsedDimensionResult(
                inputExpression = trimmed,
                dimensions = dim,
                scaleFactorToBaseSi = scale,
                formattedDimensions = formatted,
                asciiFormula = ascii,
                baseSiUnits = siUnits,
                matchedQuantities = matches,
                isValid = true
            )
        } catch (e: Exception) {
            return ParsedDimensionResult(
                inputExpression = trimmed,
                dimensions = DimensionVector.DIMENSIONLESS,
                scaleFactorToBaseSi = 1.0,
                formattedDimensions = "Invalid",
                asciiFormula = "error",
                baseSiUnits = "unknown",
                matchedQuantities = emptyList(),
                isValid = false,
                errorMessage = e.message ?: "Failed to parse unit expression"
            )
        }
    }

    /**
     * Verifies dimensional homogeneity of an equation e.g. "v = u + a*t" or "s = u*t + a*t^2".
     */
    fun verifyHomogeneity(equation: String): EquationHomogeneityResult {
        val parts = equation.split("=")
        if (parts.size != 2) {
            return EquationHomogeneityResult(
                equation = equation,
                lhsExpr = equation,
                rhsExpr = "",
                lhsDimension = DimensionVector.DIMENSIONLESS,
                rhsDimension = DimensionVector.DIMENSIONLESS,
                isHomogeneous = false,
                lhsFormula = "N/A",
                rhsFormula = "N/A",
                details = "Equation must contain exactly one '=' separating LHS and RHS."
            )
        }

        val lhsStr = parts[0].trim()
        val rhsStr = parts[1].trim()

        val lhsParsed = parseUnitExpression(lhsStr)
        if (!lhsParsed.isValid) {
            return EquationHomogeneityResult(
                equation = equation,
                lhsExpr = lhsStr,
                rhsExpr = rhsStr,
                lhsDimension = DimensionVector.DIMENSIONLESS,
                rhsDimension = DimensionVector.DIMENSIONLESS,
                isHomogeneous = false,
                lhsFormula = "Error",
                rhsFormula = "N/A",
                details = "Error in LHS: ${lhsParsed.errorMessage}"
            )
        }

        val rhsTerms = splitAdditiveTerms(rhsStr)
        var rhsDim: DimensionVector? = null

        for (term in rhsTerms) {
            val parsedTerm = parseUnitExpression(term)
            if (!parsedTerm.isValid) {
                return EquationHomogeneityResult(
                    equation = equation,
                    lhsExpr = lhsStr,
                    rhsExpr = rhsStr,
                    lhsDimension = lhsParsed.dimensions,
                    rhsDimension = DimensionVector.DIMENSIONLESS,
                    isHomogeneous = false,
                    lhsFormula = lhsParsed.formattedDimensions,
                    rhsFormula = "Error",
                    details = "Error in term '$term': ${parsedTerm.errorMessage}"
                )
            }
            if (rhsDim == null) {
                rhsDim = parsedTerm.dimensions
            } else if (rhsDim != parsedTerm.dimensions) {
                return EquationHomogeneityResult(
                    equation = equation,
                    lhsExpr = lhsStr,
                    rhsExpr = rhsStr,
                    lhsDimension = lhsParsed.dimensions,
                    rhsDimension = parsedTerm.dimensions,
                    isHomogeneous = false,
                    lhsFormula = lhsParsed.formattedDimensions,
                    rhsFormula = "Inconsistent RHS",
                    details = "Terms in RHS have differing dimensions: [${rhsDim.toFormulaNotation()}] vs [${parsedTerm.dimensions.toFormulaNotation()}]. Cannot add physically distinct dimensions."
                )
            }
        }

        val finalRhsDim = rhsDim ?: DimensionVector.DIMENSIONLESS
        val isMatch = lhsParsed.dimensions == finalRhsDim

        val detailMsg = if (isMatch) {
            "Valid: Both LHS and RHS evaluate to the same physical dimension ${lhsParsed.formattedDimensions}."
        } else {
            "Mismatch: LHS dimension is ${lhsParsed.formattedDimensions}, but RHS dimension is ${finalRhsDim.toFormattedString()}."
        }

        return EquationHomogeneityResult(
            equation = equation,
            lhsExpr = lhsStr,
            rhsExpr = rhsStr,
            lhsDimension = lhsParsed.dimensions,
            rhsDimension = finalRhsDim,
            isHomogeneous = isMatch,
            lhsFormula = lhsParsed.formattedDimensions,
            rhsFormula = finalRhsDim.toFormattedString(),
            details = detailMsg
        )
    }

    private fun splitAdditiveTerms(expr: String): List<String> {
        val terms = mutableListOf<String>()
        var depth = 0
        val current = StringBuilder()

        for (i in expr.indices) {
            val c = expr[i]
            when (c) {
                '(' -> depth++
                ')' -> depth--
                '+', '-' -> {
                    if (depth == 0 && current.isNotBlank() && i > 0 && expr[i - 1] != '^' && expr[i - 1] != '*' && expr[i - 1] != '/') {
                        terms.add(current.toString().trim())
                        current.clear()
                        continue
                    }
                }
            }
            current.append(c)
        }
        if (current.isNotBlank()) {
            terms.add(current.toString().trim())
        }
        return terms.ifEmpty { listOf(expr) }
    }

    private sealed class Token {
        data class UnitToken(val name: String) : Token()
        data class NumberToken(val value: Double) : Token()
        object Star : Token()
        object Slash : Token()
        object Caret : Token()
        object OpenParen : Token()
        object CloseParen : Token()
    }

    private fun tokenize(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var idx = 0
        val s = input.replace("·", "*").replace("×", "*")

        while (idx < s.length) {
            val c = s[idx]
            when {
                c.isWhitespace() -> idx++
                c == '*' -> {
                    tokens.add(Token.Star)
                    idx++
                }
                c == '/' -> {
                    tokens.add(Token.Slash)
                    idx++
                }
                c == '^' -> {
                    tokens.add(Token.Caret)
                    idx++
                }
                c == '(' -> {
                    tokens.add(Token.OpenParen)
                    idx++
                }
                c == ')' -> {
                    tokens.add(Token.CloseParen)
                    idx++
                }
                c.isDigit() || c == '.' || (c == '-' && (tokens.isEmpty() || tokens.last() is Token.Caret || tokens.last() is Token.OpenParen)) -> {
                    val start = idx
                    if (c == '-') idx++
                    while (idx < s.length && (s[idx].isDigit() || s[idx] == '.')) {
                        idx++
                    }
                    val numStr = s.substring(start, idx)
                    val num = numStr.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number: $numStr")
                    tokens.add(Token.NumberToken(num))
                }
                c.isLetter() || c == 'Ω' || c == '°' || c == '%' || c == 'µ' || c == 'Å' -> {
                    val start = idx
                    while (idx < s.length && (s[idx].isLetter() || s[idx] == '_' || s[idx] == 'Ω' || s[idx] == '°' || s[idx] == '%' || s[idx] == 'µ' || s[idx] == 'Å')) {
                        idx++
                    }
                    val unitName = s.substring(start, idx)
                    tokens.add(Token.UnitToken(unitName))
                }
                else -> {
                    idx++
                }
            }
        }
        return tokens
    }

    private class UnitAstParser(private val tokens: List<Token>) {
        private var pos = 0

        fun parseExpression(): Pair<DimensionVector, Double> {
            return parseMulDiv()
        }

        private fun parseFactor(): Pair<DimensionVector, Double> {
            if (pos >= tokens.size) return Pair(DimensionVector.DIMENSIONLESS, 1.0)
            val tok = tokens[pos]

            var basePair: Pair<DimensionVector, Double>
            when (tok) {
                is Token.OpenParen -> {
                    pos++
                    basePair = parseMulDiv()
                    if (pos < tokens.size && tokens[pos] is Token.CloseParen) {
                        pos++
                    } else {
                        throw IllegalArgumentException("Missing closing parenthesis ')'")
                    }
                }
                is Token.UnitToken -> {
                    pos++
                    val resolved = resolveAtomicUnit(tok.name)
                        ?: throw IllegalArgumentException("Unknown unit: '${tok.name}'")
                    basePair = resolved
                }
                is Token.NumberToken -> {
                    pos++
                    basePair = Pair(DimensionVector.DIMENSIONLESS, tok.value)
                }
                else -> throw IllegalArgumentException("Unexpected token in unit expression: $tok")
            }

            // Check for exponent: ^ number or ^ ( -number )
            if (pos < tokens.size && tokens[pos] is Token.Caret) {
                pos++
                if (pos >= tokens.size) throw IllegalArgumentException("Missing exponent after '^'")
                var expVal: Double
                if (tokens[pos] is Token.OpenParen) {
                    pos++
                    if (pos < tokens.size && tokens[pos] is Token.NumberToken) {
                        expVal = (tokens[pos] as Token.NumberToken).value
                        pos++
                        if (pos < tokens.size && tokens[pos] is Token.CloseParen) {
                            pos++
                        }
                    } else {
                        throw IllegalArgumentException("Expected numeric exponent in parentheses")
                    }
                } else if (tokens[pos] is Token.NumberToken) {
                    expVal = (tokens[pos] as Token.NumberToken).value
                    pos++
                } else {
                    throw IllegalArgumentException("Expected numeric exponent after '^'")
                }

                val intExp = expVal.toInt()
                basePair = Pair(
                    basePair.first * intExp,
                    basePair.second.pow(expVal)
                )
            }

            return basePair
        }

        private fun parseMulDiv(): Pair<DimensionVector, Double> {
            var (dim, scale) = parseFactor()

            while (pos < tokens.size) {
                val tok = tokens[pos]
                when (tok) {
                    is Token.Star -> {
                        pos++
                        val (nextDim, nextScale) = parseFactor()
                        dim = dim + nextDim
                        scale *= nextScale
                    }
                    is Token.Slash -> {
                        pos++
                        val (nextDim, nextScale) = parseFactor()
                        dim = dim - nextDim
                        scale /= if (nextScale != 0.0) nextScale else 1.0
                    }
                    is Token.UnitToken, is Token.OpenParen -> {
                        // Implicit multiplication e.g. "kg m / s^2"
                        val (nextDim, nextScale) = parseFactor()
                        dim = dim + nextDim
                        scale *= nextScale
                    }
                    else -> break
                }
            }

            return Pair(dim, scale)
        }
    }
}
