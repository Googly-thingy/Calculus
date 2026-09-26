package com.example.model

/**
 * Represents a single unit of measurement with its fundamental dimension vector
 * and conversion factor to the base SI unit.
 */
data class UnitDefinition(
    val symbol: String,
    val name: String,
    val dimensions: DimensionVector,
    val scaleToBase: Double,
    val offset: Double = 0.0,
    val category: QuantityCategory
) {
    fun toBase(value: Double): Double = (value + offset) * scaleToBase
    fun fromBase(baseValue: Double): Double = (baseValue / scaleToBase) - offset
}

object UnitRegistry {
    // Standard SI prefixes
    val PREFIXES = mapOf(
        "Y" to 1e24,
        "Z" to 1e21,
        "E" to 1e18,
        "P" to 1e15,
        "T" to 1e12,
        "G" to 1e9,
        "M" to 1e6,
        "k" to 1e3,
        "h" to 1e2,
        "da" to 1e1,
        "d" to 1e-1,
        "c" to 1e-2,
        "m" to 1e-3,
        "u" to 1e-6,
        "µ" to 1e-6,
        "n" to 1e-9,
        "p" to 1e-12,
        "f" to 1e-15,
        "a" to 1e-18
    )

    val BASE_AND_NAMED_UNITS: Map<String, UnitDefinition> = listOf(
        // Length (L)
        UnitDefinition("m", "meter", DimensionVector(l = 1), 1.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("km", "kilometer", DimensionVector(l = 1), 1e3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("cm", "centimeter", DimensionVector(l = 1), 1e-2, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("mm", "millimeter", DimensionVector(l = 1), 1e-3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("um", "micrometer", DimensionVector(l = 1), 1e-6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("µm", "micrometer", DimensionVector(l = 1), 1e-6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("nm", "nanometer", DimensionVector(l = 1), 1e-9, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("pm", "picometer", DimensionVector(l = 1), 1e-12, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("in", "inch", DimensionVector(l = 1), 0.0254, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("ft", "foot", DimensionVector(l = 1), 0.3048, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("yd", "yard", DimensionVector(l = 1), 0.9144, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("mi", "mile", DimensionVector(l = 1), 1609.344, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("nmi", "nautical mile", DimensionVector(l = 1), 1852.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("angstrom", "angstrom", DimensionVector(l = 1), 1e-10, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("Å", "angstrom", DimensionVector(l = 1), 1e-10, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("ly", "light year", DimensionVector(l = 1), 9.4607e15, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("au", "astronomical unit", DimensionVector(l = 1), 1.495978707e11, 0.0, QuantityCategory.MECHANICS),

        // Mass (M)
        UnitDefinition("kg", "kilogram", DimensionVector(m = 1), 1.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("g", "gram", DimensionVector(m = 1), 1e-3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("mg", "milligram", DimensionVector(m = 1), 1e-6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("ug", "microgram", DimensionVector(m = 1), 1e-9, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("µg", "microgram", DimensionVector(m = 1), 1e-9, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("t", "metric ton", DimensionVector(m = 1), 1000.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("ton", "metric ton", DimensionVector(m = 1), 1000.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("lb", "pound", DimensionVector(m = 1), 0.45359237, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("oz", "ounce", DimensionVector(m = 1), 0.028349523, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("u", "unified atomic mass unit", DimensionVector(m = 1), 1.660539066e-27, 0.0, QuantityCategory.CHEMISTRY_NUCLEAR),
        UnitDefinition("Da", "dalton", DimensionVector(m = 1), 1.660539066e-27, 0.0, QuantityCategory.CHEMISTRY_NUCLEAR),

        // Time (T)
        UnitDefinition("s", "second", DimensionVector(t = 1), 1.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("sec", "second", DimensionVector(t = 1), 1.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("ms", "millisecond", DimensionVector(t = 1), 1e-3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("us", "microsecond", DimensionVector(t = 1), 1e-6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("µs", "microsecond", DimensionVector(t = 1), 1e-6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("ns", "nanosecond", DimensionVector(t = 1), 1e-9, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("ps", "picosecond", DimensionVector(t = 1), 1e-12, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("min", "minute", DimensionVector(t = 1), 60.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("h", "hour", DimensionVector(t = 1), 3600.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("hr", "hour", DimensionVector(t = 1), 3600.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("d", "day", DimensionVector(t = 1), 86400.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("day", "day", DimensionVector(t = 1), 86400.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("yr", "year", DimensionVector(t = 1), 31557600.0, 0.0, QuantityCategory.MECHANICS),

        // Current (I)
        UnitDefinition("A", "ampere", DimensionVector(i = 1), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("mA", "milliampere", DimensionVector(i = 1), 1e-3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("uA", "microampere", DimensionVector(i = 1), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("µA", "microampere", DimensionVector(i = 1), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),

        // Temperature (Theta)
        UnitDefinition("K", "kelvin", DimensionVector(theta = 1), 1.0, 0.0, QuantityCategory.THERMODYNAMICS),

        // Amount of Substance (N)
        UnitDefinition("mol", "mole", DimensionVector(n = 1), 1.0, 0.0, QuantityCategory.CHEMISTRY_NUCLEAR),
        UnitDefinition("mmol", "millimole", DimensionVector(n = 1), 1e-3, 0.0, QuantityCategory.CHEMISTRY_NUCLEAR),

        // Luminous Intensity (J)
        UnitDefinition("cd", "candela", DimensionVector(j = 1), 1.0, 0.0, QuantityCategory.OPTICS_WAVES),

        // Named Derived SI Units
        UnitDefinition("Hz", "hertz", DimensionVector(t = -1), 1.0, 0.0, QuantityCategory.OPTICS_WAVES),
        UnitDefinition("kHz", "kilohertz", DimensionVector(t = -1), 1e3, 0.0, QuantityCategory.OPTICS_WAVES),
        UnitDefinition("MHz", "megahertz", DimensionVector(t = -1), 1e6, 0.0, QuantityCategory.OPTICS_WAVES),
        UnitDefinition("GHz", "gigahertz", DimensionVector(t = -1), 1e9, 0.0, QuantityCategory.OPTICS_WAVES),

        UnitDefinition("N", "newton", DimensionVector(m = 1, l = 1, t = -2), 1.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("kN", "kilonewton", DimensionVector(m = 1, l = 1, t = -2), 1e3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("MN", "meganewton", DimensionVector(m = 1, l = 1, t = -2), 1e6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("dyn", "dyne", DimensionVector(m = 1, l = 1, t = -2), 1e-5, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("lbf", "pound-force", DimensionVector(m = 1, l = 1, t = -2), 4.448222, 0.0, QuantityCategory.MECHANICS),

        UnitDefinition("Pa", "pascal", DimensionVector(m = 1, l = -1, t = -2), 1.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("kPa", "kilopascal", DimensionVector(m = 1, l = -1, t = -2), 1e3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("MPa", "megapascal", DimensionVector(m = 1, l = -1, t = -2), 1e6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("GPa", "gigapascal", DimensionVector(m = 1, l = -1, t = -2), 1e9, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("bar", "bar", DimensionVector(m = 1, l = -1, t = -2), 1e5, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("mbar", "millibar", DimensionVector(m = 1, l = -1, t = -2), 1e2, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("atm", "standard atmosphere", DimensionVector(m = 1, l = -1, t = -2), 101325.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("psi", "pound per square inch", DimensionVector(m = 1, l = -1, t = -2), 6894.757, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("torr", "torr", DimensionVector(m = 1, l = -1, t = -2), 133.3224, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("mmHg", "millimeter of mercury", DimensionVector(m = 1, l = -1, t = -2), 133.3224, 0.0, QuantityCategory.MECHANICS),

        UnitDefinition("J", "joule", DimensionVector(m = 1, l = 2, t = -2), 1.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("kJ", "kilojoule", DimensionVector(m = 1, l = 2, t = -2), 1e3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("MJ", "megajoule", DimensionVector(m = 1, l = 2, t = -2), 1e6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("GJ", "gigajoule", DimensionVector(m = 1, l = 2, t = -2), 1e9, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("cal", "calorie", DimensionVector(m = 1, l = 2, t = -2), 4.184, 0.0, QuantityCategory.THERMODYNAMICS),
        UnitDefinition("kcal", "kilocalorie", DimensionVector(m = 1, l = 2, t = -2), 4184.0, 0.0, QuantityCategory.THERMODYNAMICS),
        UnitDefinition("Wh", "watt-hour", DimensionVector(m = 1, l = 2, t = -2), 3600.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("kWh", "kilowatt-hour", DimensionVector(m = 1, l = 2, t = -2), 3.6e6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("MWh", "megawatt-hour", DimensionVector(m = 1, l = 2, t = -2), 3.6e9, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("eV", "electronvolt", DimensionVector(m = 1, l = 2, t = -2), 1.602176634e-19, 0.0, QuantityCategory.CHEMISTRY_NUCLEAR),
        UnitDefinition("keV", "kiloelectronvolt", DimensionVector(m = 1, l = 2, t = -2), 1.602176634e-16, 0.0, QuantityCategory.CHEMISTRY_NUCLEAR),
        UnitDefinition("MeV", "megaelectronvolt", DimensionVector(m = 1, l = 2, t = -2), 1.602176634e-13, 0.0, QuantityCategory.CHEMISTRY_NUCLEAR),
        UnitDefinition("GeV", "gigaelectronvolt", DimensionVector(m = 1, l = 2, t = -2), 1.602176634e-10, 0.0, QuantityCategory.CHEMISTRY_NUCLEAR),
        UnitDefinition("BTU", "British thermal unit", DimensionVector(m = 1, l = 2, t = -2), 1055.06, 0.0, QuantityCategory.THERMODYNAMICS),
        UnitDefinition("erg", "erg", DimensionVector(m = 1, l = 2, t = -2), 1e-7, 0.0, QuantityCategory.MECHANICS),

        UnitDefinition("W", "watt", DimensionVector(m = 1, l = 2, t = -3), 1.0, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("kW", "kilowatt", DimensionVector(m = 1, l = 2, t = -3), 1e3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("MW", "megawatt", DimensionVector(m = 1, l = 2, t = -3), 1e6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("GW", "gigawatt", DimensionVector(m = 1, l = 2, t = -3), 1e9, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("hp", "horsepower", DimensionVector(m = 1, l = 2, t = -3), 745.69987, 0.0, QuantityCategory.MECHANICS),

        UnitDefinition("C", "coulomb", DimensionVector(i = 1, t = 1), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("mC", "millicoulomb", DimensionVector(i = 1, t = 1), 1e-3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("uC", "microcoulomb", DimensionVector(i = 1, t = 1), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("µC", "microcoulomb", DimensionVector(i = 1, t = 1), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("nC", "nanocoulomb", DimensionVector(i = 1, t = 1), 1e-9, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("Ah", "ampere-hour", DimensionVector(i = 1, t = 1), 3600.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("mAh", "milliampere-hour", DimensionVector(i = 1, t = 1), 3.6, 0.0, QuantityCategory.ELECTROMAGNETISM),

        UnitDefinition("V", "volt", DimensionVector(m = 1, l = 2, t = -3, i = -1), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("kV", "kilovolt", DimensionVector(m = 1, l = 2, t = -3, i = -1), 1e3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("mV", "millivolt", DimensionVector(m = 1, l = 2, t = -3, i = -1), 1e-3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("uV", "microvolt", DimensionVector(m = 1, l = 2, t = -3, i = -1), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("µV", "microvolt", DimensionVector(m = 1, l = 2, t = -3, i = -1), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),

        UnitDefinition("ohm", "ohm", DimensionVector(m = 1, l = 2, t = -3, i = -2), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("Ω", "ohm", DimensionVector(m = 1, l = 2, t = -3, i = -2), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("kohm", "kiloohm", DimensionVector(m = 1, l = 2, t = -3, i = -2), 1e3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("kΩ", "kiloohm", DimensionVector(m = 1, l = 2, t = -3, i = -2), 1e3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("Mohm", "megaohm", DimensionVector(m = 1, l = 2, t = -3, i = -2), 1e6, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("MΩ", "megaohm", DimensionVector(m = 1, l = 2, t = -3, i = -2), 1e6, 0.0, QuantityCategory.ELECTROMAGNETISM),

        UnitDefinition("S", "siemens", DimensionVector(m = -1, l = -2, t = 3, i = 2), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("mho", "mho", DimensionVector(m = -1, l = -2, t = 3, i = 2), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),

        UnitDefinition("F", "farad", DimensionVector(m = -1, l = -2, t = 4, i = 2), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("mF", "millifarad", DimensionVector(m = -1, l = -2, t = 4, i = 2), 1e-3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("uF", "microfarad", DimensionVector(m = -1, l = -2, t = 4, i = 2), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("µF", "microfarad", DimensionVector(m = -1, l = -2, t = 4, i = 2), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("nF", "nanofarad", DimensionVector(m = -1, l = -2, t = 4, i = 2), 1e-9, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("pF", "picofarad", DimensionVector(m = -1, l = -2, t = 4, i = 2), 1e-12, 0.0, QuantityCategory.ELECTROMAGNETISM),

        UnitDefinition("H", "henry", DimensionVector(m = 1, l = 2, t = -2, i = -2), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("mH", "millihenry", DimensionVector(m = 1, l = 2, t = -2, i = -2), 1e-3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("uH", "microhenry", DimensionVector(m = 1, l = 2, t = -2, i = -2), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("µH", "microhenry", DimensionVector(m = 1, l = 2, t = -2, i = -2), 1e-6, 0.0, QuantityCategory.ELECTROMAGNETISM),

        UnitDefinition("T", "tesla", DimensionVector(m = 1, t = -2, i = -1), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("mT", "millitesla", DimensionVector(m = 1, t = -2, i = -1), 1e-3, 0.0, QuantityCategory.ELECTROMAGNETISM),
        UnitDefinition("G", "gauss", DimensionVector(m = 1, t = -2, i = -1), 1e-4, 0.0, QuantityCategory.ELECTROMAGNETISM),

        UnitDefinition("Wb", "weber", DimensionVector(m = 1, l = 2, t = -2, i = -1), 1.0, 0.0, QuantityCategory.ELECTROMAGNETISM),

        UnitDefinition("lm", "lumen", DimensionVector(j = 1), 1.0, 0.0, QuantityCategory.OPTICS_WAVES),
        UnitDefinition("lx", "lux", DimensionVector(l = -2, j = 1), 1.0, 0.0, QuantityCategory.OPTICS_WAVES),

        // Volume units
        UnitDefinition("L", "liter", DimensionVector(l = 3), 1e-3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("l", "liter", DimensionVector(l = 3), 1e-3, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("mL", "milliliter", DimensionVector(l = 3), 1e-6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("ml", "milliliter", DimensionVector(l = 3), 1e-6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("cc", "cubic centimeter", DimensionVector(l = 3), 1e-6, 0.0, QuantityCategory.MECHANICS),
        UnitDefinition("gal", "US gallon", DimensionVector(l = 3), 0.003785411784, 0.0, QuantityCategory.MECHANICS),

        // Dimensionless units
        UnitDefinition("rad", "radian", DimensionVector.DIMENSIONLESS, 1.0, 0.0, QuantityCategory.GENERAL),
        UnitDefinition("deg", "degree", DimensionVector.DIMENSIONLESS, Math.PI / 180.0, 0.0, QuantityCategory.GENERAL),
        UnitDefinition("°", "degree", DimensionVector.DIMENSIONLESS, Math.PI / 180.0, 0.0, QuantityCategory.GENERAL),
        UnitDefinition("%", "percent", DimensionVector.DIMENSIONLESS, 0.01, 0.0, QuantityCategory.GENERAL),
        UnitDefinition("ppm", "parts per million", DimensionVector.DIMENSIONLESS, 1e-6, 0.0, QuantityCategory.GENERAL)
    ).associateBy { it.symbol }
}
