package com.example.math

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.*

enum class AngleMode {
    DEGREE,
    RADIAN
}

data class PhysicalConstant(
    val symbol: String,
    val name: String,
    val value: Double,
    val unit: String,
    val dimensionalFormula: String
)

object PhysicalConstants {
    val CONSTANTS = listOf(
        PhysicalConstant("π", "Pi (Archimedes)", Math.PI, "rad", "1 (Dimensionless)"),
        PhysicalConstant("e", "Euler's Number", Math.E, "", "1 (Dimensionless)"),
        PhysicalConstant("c", "Speed of Light in Vacuum", 299792458.0, "m/s", "L · T⁻¹"),
        PhysicalConstant("G", "Newtonian Gravitational Constant", 6.67430e-11, "m³/(kg·s²)", "M⁻¹ · L³ · T⁻²"),
        PhysicalConstant("h", "Planck Constant", 6.62607015e-34, "J·s", "M · L² · T⁻¹"),
        PhysicalConstant("ℏ", "Reduced Planck Constant", 1.054571817e-34, "J·s", "M · L² · T⁻¹"),
        PhysicalConstant("k_B", "Boltzmann Constant", 1.380649e-23, "J/K", "M · L² · T⁻² · Θ⁻¹"),
        PhysicalConstant("N_A", "Avogadro Constant", 6.02214076e23, "1/mol", "N⁻¹"),
        PhysicalConstant("R", "Universal Gas Constant", 8.314462618, "J/(mol·K)", "M · L² · T⁻² · Θ⁻¹ · N⁻¹"),
        PhysicalConstant("e_charge", "Elementary Charge", 1.602176634e-19, "C", "I · T"),
        PhysicalConstant("m_e", "Electron Rest Mass", 9.1093837e-31, "kg", "M"),
        PhysicalConstant("m_p", "Proton Rest Mass", 1.67262192e-27, "kg", "M"),
        PhysicalConstant("ε_0", "Vacuum Electric Permittivity", 8.8541878128e-12, "F/m", "M⁻¹ · L⁻³ · T⁴ · I²"),
        PhysicalConstant("μ_0", "Vacuum Magnetic Permeability", 1.25663706212e-6, "N/A²", "M · L · T⁻² · I⁻²"),
        PhysicalConstant("g_0", "Standard Earth Gravity", 9.80665, "m/s²", "L · T⁻²"),
        PhysicalConstant("σ_SB", "Stefan-Boltzmann Constant", 5.670374419e-8, "W/(m²·K⁴)", "M · T⁻³ · Θ⁻⁴")
    )
}

data class EvaluationResult(
    val value: Double,
    val formatted: String,
    val isSuccess: Boolean = true,
    val errorMessage: String? = null
)

object ScientificEvaluator {

    fun evaluate(expression: String, angleMode: AngleMode = AngleMode.DEGREE): EvaluationResult {
        val trimmed = expression.trim()
        if (trimmed.isEmpty()) {
            return EvaluationResult(0.0, "0", isSuccess = true)
        }

        return try {
            val tokens = tokenize(trimmed)
            val rpn = shuntingYard(tokens)
            val result = evaluateRpn(rpn, angleMode)
            if (result.isNaN() || result.isInfinite()) {
                EvaluationResult(result, "Undefined", isSuccess = false, errorMessage = "Result is undefined or overflow")
            } else {
                EvaluationResult(result, formatNumber(result), isSuccess = true)
            }
        } catch (e: Exception) {
            EvaluationResult(0.0, "Error", isSuccess = false, errorMessage = e.message ?: "Syntax Error")
        }
    }

    fun formatNumber(value: Double): String {
        if (value.isNaN()) return "NaN"
        if (value == Double.POSITIVE_INFINITY) return "∞"
        if (value == Double.NEGATIVE_INFINITY) return "-∞"

        val absVal = abs(value)
        val symbols = DecimalFormatSymbols(Locale.US)

        return if (absVal != 0.0 && (absVal >= 1e11 || absVal < 1e-6)) {
            val df = DecimalFormat("0.######E0", symbols)
            df.format(value)
        } else {
            val df = DecimalFormat("#,##0.########", symbols)
            df.format(value)
        }
    }

    private sealed class Token {
        data class Number(val value: Double) : Token()
        data class Operator(val op: Char, val precedence: Int, val isRightAssociative: Boolean = false) : Token()
        data class Function(val name: String) : Token()
        object OpenParen : Token()
        object CloseParen : Token()
        object Comma : Token()
        object Factorial : Token()
    }

    private fun tokenize(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0
        val s = input
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace("π", "pi")

        while (i < s.length) {
            val c = s[i]
            when {
                c.isWhitespace() -> i++
                c == '(' -> {
                    // Check implicit multiplication: 2( or )(
                    if (tokens.isNotEmpty() && (tokens.last() is Token.Number || tokens.last() is Token.CloseParen || tokens.last() is Token.Factorial)) {
                        tokens.add(Token.Operator('*', 2))
                    }
                    tokens.add(Token.OpenParen)
                    i++
                }
                c == ')' -> {
                    tokens.add(Token.CloseParen)
                    i++
                }
                c == ',' -> {
                    tokens.add(Token.Comma)
                    i++
                }
                c == '!' -> {
                    tokens.add(Token.Factorial)
                    i++
                }
                c == '+' -> {
                    tokens.add(Token.Operator('+', 1))
                    i++
                }
                c == '-' -> {
                    // Unary minus vs binary minus
                    val isUnary = tokens.isEmpty() ||
                            tokens.last() is Token.OpenParen ||
                            tokens.last() is Token.Operator ||
                            tokens.last() is Token.Comma
                    if (isUnary) {
                        tokens.add(Token.Operator('~', 4, isRightAssociative = true)) // '~' represents unary minus
                    } else {
                        tokens.add(Token.Operator('-', 1))
                    }
                    i++
                }
                c == '*' -> {
                    tokens.add(Token.Operator('*', 2))
                    i++
                }
                c == '/' -> {
                    tokens.add(Token.Operator('/', 2))
                    i++
                }
                c == '%' -> {
                    tokens.add(Token.Operator('%', 2))
                    i++
                }
                c == '^' -> {
                    tokens.add(Token.Operator('^', 3, isRightAssociative = true))
                    i++
                }
                c.isDigit() || c == '.' -> {
                    val start = i
                    var hasDot = false
                    while (i < s.length && (s[i].isDigit() || (!hasDot && s[i] == '.'))) {
                        if (s[i] == '.') hasDot = true
                        i++
                    }
                    // Check scientific notation in number: e.g. 1.23e-4 or 5E6
                    if (i < s.length && (s[i] == 'e' || s[i] == 'E')) {
                        val nextIdx = i + 1
                        if (nextIdx < s.length && (s[nextIdx].isDigit() || s[nextIdx] == '+' || s[nextIdx] == '-')) {
                            i++
                            if (i < s.length && (s[i] == '+' || s[i] == '-')) i++
                            while (i < s.length && s[i].isDigit()) i++
                        }
                    }
                    val numStr = s.substring(start, i)
                    val num = numStr.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number: $numStr")

                    // Implicit multiplication check e.g. )2
                    if (tokens.isNotEmpty() && (tokens.last() is Token.CloseParen || tokens.last() is Token.Factorial)) {
                        tokens.add(Token.Operator('*', 2))
                    }
                    tokens.add(Token.Number(num))
                }
                c.isLetter() || c == 'ℏ' || c == 'ε' || c == 'μ' || c == 'σ' || c == '_' -> {
                    val start = i
                    while (i < s.length && (s[i].isLetter() || s[i].isDigit() || s[i] == '_' || s[i] == 'ℏ' || s[i] == 'ε' || s[i] == 'μ' || s[i] == 'σ')) {
                        i++
                    }
                    val word = s.substring(start, i)

                    // Check if it's a known constant
                    val constVal = resolveConstant(word)
                    if (constVal != null) {
                        if (tokens.isNotEmpty() && (tokens.last() is Token.Number || tokens.last() is Token.CloseParen || tokens.last() is Token.Factorial)) {
                            tokens.add(Token.Operator('*', 2))
                        }
                        tokens.add(Token.Number(constVal))
                    } else {
                        // Function
                        if (tokens.isNotEmpty() && (tokens.last() is Token.Number || tokens.last() is Token.CloseParen || tokens.last() is Token.Factorial)) {
                            tokens.add(Token.Operator('*', 2))
                        }
                        tokens.add(Token.Function(word.lowercase()))
                    }
                }
                else -> i++
            }
        }
        return tokens
    }

    private fun resolveConstant(name: String): Double? {
        return when (name.lowercase()) {
            "pi", "π" -> Math.PI
            "e" -> Math.E
            "c" -> 299792458.0
            "g" -> 6.67430e-11
            "h" -> 6.62607015e-34
            "hbar", "ℏ" -> 1.054571817e-34
            "kb", "k_b", "k" -> 1.380649e-23
            "na", "n_a" -> 6.02214076e23
            "r_gas", "r" -> 8.314462618
            "q_e", "qe" -> 1.602176634e-19
            "m_e", "me" -> 9.1093837e-31
            "m_p", "mp" -> 1.67262192e-27
            "eps0", "ε_0" -> 8.8541878128e-12
            "mu0", "μ_0" -> 1.25663706212e-6
            "g0", "g_0" -> 9.80665
            else -> null
        }
    }

    private fun shuntingYard(tokens: List<Token>): List<Token> {
        val output = mutableListOf<Token>()
        val stack = ArrayDeque<Token>()

        for (token in tokens) {
            when (token) {
                is Token.Number -> output.add(token)
                is Token.Factorial -> output.add(token)
                is Token.Function -> stack.addLast(token)
                is Token.Comma -> {
                    while (stack.isNotEmpty() && stack.last() !is Token.OpenParen) {
                        output.add(stack.removeLast())
                    }
                    if (stack.isEmpty()) throw IllegalArgumentException("Misplaced comma or mismatched parentheses")
                }
                is Token.Operator -> {
                    while (stack.isNotEmpty()) {
                        val top = stack.last()
                        if (top is Token.Operator) {
                            val cond = if (token.isRightAssociative) {
                                token.precedence < top.precedence
                            } else {
                                token.precedence <= top.precedence
                            }
                            if (cond) {
                                output.add(stack.removeLast())
                                continue
                            }
                        } else if (top is Token.Function) {
                            output.add(stack.removeLast())
                            continue
                        }
                        break
                    }
                    stack.addLast(token)
                }
                is Token.OpenParen -> stack.addLast(token)
                is Token.CloseParen -> {
                    var foundOpen = false
                    while (stack.isNotEmpty()) {
                        val top = stack.removeLast()
                        if (top is Token.OpenParen) {
                            foundOpen = true
                            break
                        }
                        output.add(top)
                    }
                    if (!foundOpen) throw IllegalArgumentException("Mismatched parentheses: extra ')'")
                    if (stack.isNotEmpty() && stack.last() is Token.Function) {
                        output.add(stack.removeLast())
                    }
                }
            }
        }

        while (stack.isNotEmpty()) {
            val top = stack.removeLast()
            if (top is Token.OpenParen || top is Token.CloseParen) {
                throw IllegalArgumentException("Mismatched parentheses")
            }
            output.add(top)
        }

        return output
    }

    private fun evaluateRpn(rpn: List<Token>, angleMode: AngleMode): Double {
        val stack = ArrayDeque<Double>()

        for (token in rpn) {
            when (token) {
                is Token.Number -> stack.addLast(token.value)
                is Token.Factorial -> {
                    if (stack.isEmpty()) throw IllegalArgumentException("Invalid factorial operand")
                    val n = stack.removeLast()
                    stack.addLast(factorial(n))
                }
                is Token.Operator -> {
                    if (token.op == '~') { // Unary minus
                        if (stack.isEmpty()) throw IllegalArgumentException("Invalid unary minus")
                        val a = stack.removeLast()
                        stack.addLast(-a)
                    } else {
                        if (stack.size < 2) throw IllegalArgumentException("Missing operand for operator '${token.op}'")
                        val b = stack.removeLast()
                        val a = stack.removeLast()
                        val res = when (token.op) {
                            '+' -> a + b
                            '-' -> a - b
                            '*' -> a * b
                            '/' -> {
                                if (b == 0.0) throw ArithmeticException("Division by zero")
                                a / b
                            }
                            '%' -> a % b
                            '^' -> a.pow(b)
                            else -> throw IllegalArgumentException("Unknown operator '${token.op}'")
                        }
                        stack.addLast(res)
                    }
                }
                is Token.Function -> {
                    if (stack.isEmpty()) throw IllegalArgumentException("Missing operand for function '${token.name}'")
                    val arg = stack.removeLast()
                    val toRad = { x: Double -> if (angleMode == AngleMode.DEGREE) Math.toRadians(x) else x }
                    val fromRad = { x: Double -> if (angleMode == AngleMode.DEGREE) Math.toDegrees(x) else x }

                    val res = when (token.name) {
                        "sin" -> sin(toRad(arg))
                        "cos" -> cos(toRad(arg))
                        "tan" -> {
                            val r = toRad(arg)
                            if (abs(cos(r)) < 1e-15) throw ArithmeticException("tan is undefined at 90° + k*180°")
                            tan(r)
                        }
                        "asin", "arcsin" -> {
                            if (arg < -1.0 || arg > 1.0) throw IllegalArgumentException("asin domain error: must be in [-1, 1]")
                            fromRad(asin(arg))
                        }
                        "acos", "arccos" -> {
                            if (arg < -1.0 || arg > 1.0) throw IllegalArgumentException("acos domain error: must be in [-1, 1]")
                            fromRad(acos(arg))
                        }
                        "atan", "arctan" -> fromRad(atan(arg))
                        "sinh" -> sinh(arg)
                        "cosh" -> cosh(arg)
                        "tanh" -> tanh(arg)
                        "ln" -> {
                            if (arg <= 0.0) throw IllegalArgumentException("ln domain error: argument must be > 0")
                            ln(arg)
                        }
                        "log", "log10" -> {
                            if (arg <= 0.0) throw IllegalArgumentException("log domain error: argument must be > 0")
                            log10(arg)
                        }
                        "log2" -> {
                            if (arg <= 0.0) throw IllegalArgumentException("log2 domain error: argument must be > 0")
                            ln(arg) / ln(2.0)
                        }
                        "sqrt", "√" -> {
                            if (arg < 0.0) throw IllegalArgumentException("sqrt domain error: argument cannot be negative")
                            sqrt(arg)
                        }
                        "cbrt" -> cbrt(arg)
                        "abs" -> abs(arg)
                        "exp" -> exp(arg)
                        "inv" -> {
                            if (arg == 0.0) throw ArithmeticException("Division by zero in inv(1/x)")
                            1.0 / arg
                        }
                        "sqr" -> arg * arg
                        else -> throw IllegalArgumentException("Unknown function '${token.name}'")
                    }
                    stack.addLast(res)
                }
                else -> {}
            }
        }

        if (stack.size != 1) throw IllegalArgumentException("Malformed expression")
        return stack.removeLast()
    }

    private fun factorial(n: Double): Double {
        if (n < 0 || n != floor(n)) throw IllegalArgumentException("Factorial is only defined for non-negative integers")
        if (n > 170) return Double.POSITIVE_INFINITY // overflow for 64-bit IEEE-754
        var res = 1.0
        val intN = n.toInt()
        for (i in 2..intN) {
            res *= i
        }
        return res
    }
}
