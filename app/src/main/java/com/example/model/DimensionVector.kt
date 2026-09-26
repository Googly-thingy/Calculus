package com.example.model

/**
 * Represents physical dimensions based on the 7 fundamental SI base dimensions:
 * - M: Mass (kg)
 * - L: Length (m)
 * - T: Time (s)
 * - I: Electric Current (A)
 * - Theta: Thermodynamic Temperature (K)
 * - N: Amount of Substance (mol)
 * - J: Luminous Intensity (cd)
 */
data class DimensionVector(
    val m: Int = 0,
    val l: Int = 0,
    val t: Int = 0,
    val i: Int = 0,
    val theta: Int = 0,
    val n: Int = 0,
    val j: Int = 0
) {
    fun isDimensionless(): Boolean {
        return m == 0 && l == 0 && t == 0 && i == 0 && theta == 0 && n == 0 && j == 0
    }

    operator fun plus(other: DimensionVector): DimensionVector {
        return DimensionVector(
            m = m + other.m,
            l = l + other.l,
            t = t + other.t,
            i = i + other.i,
            theta = theta + other.theta,
            n = n + other.n,
            j = j + other.j
        )
    }

    operator fun minus(other: DimensionVector): DimensionVector {
        return DimensionVector(
            m = m - other.m,
            l = l - other.l,
            t = t - other.t,
            i = i - other.i,
            theta = theta - other.theta,
            n = n - other.n,
            j = j - other.j
        )
    }

    operator fun times(power: Int): DimensionVector {
        return DimensionVector(
            m = m * power,
            l = l * power,
            t = t * power,
            i = i * power,
            theta = theta * power,
            n = n * power,
            j = j * power
        )
    }

    /**
     * Formatted string e.g. "[L · T⁻²]" or "L*T^-2"
     */
    fun toFormattedString(useSuperscript: Boolean = true): String {
        if (isDimensionless()) return "[1] (Dimensionless)"

        val parts = mutableListOf<String>()
        fun addDim(sym: String, exp: Int) {
            if (exp != 0) {
                if (useSuperscript) {
                    parts.add("$sym${toSuperscript(exp)}")
                } else {
                    val expStr = if (exp == 1) "" else "^$exp"
                    parts.add("$sym$expStr")
                }
            }
        }

        addDim("M", m)
        addDim("L", l)
        addDim("T", t)
        addDim("I", i)
        addDim("Θ", theta)
        addDim("N", n)
        addDim("J", j)

        val joined = parts.joinToString(if (useSuperscript) " · " else " * ")
        return "[$joined]"
    }

    /**
     * Formatted formula notation e.g. "M · L · T⁻²"
     */
    fun toFormulaNotation(): String {
        if (isDimensionless()) return "1"
        val parts = mutableListOf<String>()
        fun add(sym: String, exp: Int) {
            if (exp != 0) parts.add("$sym${toSuperscript(exp)}")
        }
        add("M", m)
        add("L", l)
        add("T", t)
        add("I", i)
        add("Θ", theta)
        add("N", n)
        add("J", j)
        return parts.joinToString(" · ")
    }

    /**
     * Standard ASCII notation e.g. "M*L*T^-2"
     */
    fun toAsciiFormula(): String {
        if (isDimensionless()) return "1"
        val parts = mutableListOf<String>()
        fun add(sym: String, exp: Int) {
            if (exp != 0) {
                val expStr = if (exp == 1) "" else "^$exp"
                parts.add("$sym$expStr")
            }
        }
        add("M", m)
        add("L", l)
        add("T", t)
        add("I", i)
        add("Theta", theta)
        add("N", n)
        add("J", j)
        return parts.joinToString("*")
    }

    /**
     * Decomposes into base SI unit expression, e.g. "kg · m · s⁻²"
     */
    fun toSiBaseUnits(): String {
        if (isDimensionless()) return "dimensionless (ratio)"
        val parts = mutableListOf<String>()
        fun add(unit: String, exp: Int) {
            if (exp != 0) parts.add("$unit${toSuperscript(exp)}")
        }
        add("kg", m)
        add("m", l)
        add("s", t)
        add("A", i)
        add("K", theta)
        add("mol", n)
        add("cd", j)
        return parts.joinToString(" · ")
    }

    companion object {
        fun toSuperscript(num: Int): String {
            if (num == 1) return ""
            return num.toString().map { char ->
                when (char) {
                    '-' -> '⁻'
                    '0' -> '⁰'
                    '1' -> '¹'
                    '2' -> '²'
                    '3' -> '³'
                    '4' -> '⁴'
                    '5' -> '⁵'
                    '6' -> '⁶'
                    '7' -> '⁷'
                    '8' -> '⁸'
                    '9' -> '⁹'
                    else -> char
                }
            }.joinToString("")
        }

        val DIMENSIONLESS = DimensionVector()
        val MASS = DimensionVector(m = 1)
        val LENGTH = DimensionVector(l = 1)
        val TIME = DimensionVector(t = 1)
        val CURRENT = DimensionVector(i = 1)
        val TEMPERATURE = DimensionVector(theta = 1)
        val AMOUNT_OF_SUBSTANCE = DimensionVector(n = 1)
        val LUMINOUS_INTENSITY = DimensionVector(j = 1)

        // Common physical quantities
        val VELOCITY = DimensionVector(l = 1, t = -1)
        val ACCELERATION = DimensionVector(l = 1, t = -2)
        val FORCE = DimensionVector(m = 1, l = 1, t = -2)
        val PRESSURE = DimensionVector(m = 1, l = -1, t = -2)
        val ENERGY = DimensionVector(m = 1, l = 2, t = -2)
        val POWER = DimensionVector(m = 1, l = 2, t = -3)
        val DENSITY = DimensionVector(m = 1, l = -3)
        val MOMENTUM = DimensionVector(m = 1, l = 1, t = -1)
        val FREQUENCY = DimensionVector(t = -1)
        val ELECTRIC_CHARGE = DimensionVector(i = 1, t = 1)
        val VOLTAGE = DimensionVector(m = 1, l = 2, t = -3, i = -1)
        val RESISTANCE = DimensionVector(m = 1, l = 2, t = -3, i = -2)
        val CAPACITANCE = DimensionVector(m = -1, l = -2, t = 4, i = 2)
        val INDUCTANCE = DimensionVector(m = 1, l = 2, t = -2, i = -2)
        val MAGNETIC_FLUX_DENSITY = DimensionVector(m = 1, t = -2, i = -1)
        val MAGNETIC_FLUX = DimensionVector(m = 1, l = 2, t = -2, i = -1)
        val DYNAMIC_VISCOSITY = DimensionVector(m = 1, l = -1, t = -1)
        val SPECIFIC_HEAT = DimensionVector(l = 2, t = -2, theta = -1)
        val THERMAL_CONDUCTIVITY = DimensionVector(m = 1, l = 1, t = -3, theta = -1)
    }
}
