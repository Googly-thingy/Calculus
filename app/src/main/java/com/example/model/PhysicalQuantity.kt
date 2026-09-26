package com.example.model

/**
 * Recognized physical quantity metadata for dimension matching.
 */
data class PhysicalQuantity(
    val name: String,
    val symbol: String,
    val dimensions: DimensionVector,
    val siUnit: String,
    val category: QuantityCategory,
    val commonFormula: String,
    val description: String
)

enum class QuantityCategory(val displayName: String) {
    MECHANICS("Mechanics & Dynamics"),
    THERMODYNAMICS("Thermodynamics"),
    ELECTROMAGNETISM("Electromagnetism"),
    OPTICS_WAVES("Optics & Waves"),
    CHEMISTRY_NUCLEAR("Chemistry & Particles"),
    GENERAL("General / Dimensionless")
}

object PhysicalQuantityRegistry {
    val ALL_QUANTITIES = listOf(
        // Base quantities
        PhysicalQuantity(
            name = "Length / Displacement",
            symbol = "L, x, d, r",
            dimensions = DimensionVector(l = 1),
            siUnit = "m (meter)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "x = v · t",
            description = "Fundamental spatial extent or distance between points."
        ),
        PhysicalQuantity(
            name = "Mass",
            symbol = "m, M",
            dimensions = DimensionVector(m = 1),
            siUnit = "kg (kilogram)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "m = F / a",
            description = "Fundamental measure of inertia and gravitational attraction."
        ),
        PhysicalQuantity(
            name = "Time / Duration",
            symbol = "t, T, τ",
            dimensions = DimensionVector(t = 1),
            siUnit = "s (second)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "t = d / v",
            description = "Fundamental continuum of sequence and occurrence."
        ),
        PhysicalQuantity(
            name = "Electric Current",
            symbol = "I, i",
            dimensions = DimensionVector(i = 1),
            siUnit = "A (ampere)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "I = dq / dt",
            description = "Rate of flow of electric charge through a surface."
        ),
        PhysicalQuantity(
            name = "Thermodynamic Temperature",
            symbol = "T, θ",
            dimensions = DimensionVector(theta = 1),
            siUnit = "K (kelvin)",
            category = QuantityCategory.THERMODYNAMICS,
            commonFormula = "T = PV / (nR)",
            description = "Measure of average kinetic energy per microscopic particle."
        ),
        PhysicalQuantity(
            name = "Amount of Substance",
            symbol = "n",
            dimensions = DimensionVector(n = 1),
            siUnit = "mol (mole)",
            category = QuantityCategory.CHEMISTRY_NUCLEAR,
            commonFormula = "n = N / N_A",
            description = "Measure of the number of elementary chemical entities."
        ),
        PhysicalQuantity(
            name = "Luminous Intensity",
            symbol = "I_v",
            dimensions = DimensionVector(j = 1),
            siUnit = "cd (candela)",
            category = QuantityCategory.OPTICS_WAVES,
            commonFormula = "I_v = dΦ_v / dΩ",
            description = "Wavelength-weighted power emitted by a light source per unit solid angle."
        ),

        // Mechanics & Kinematics
        PhysicalQuantity(
            name = "Velocity / Speed",
            symbol = "v, u",
            dimensions = DimensionVector(l = 1, t = -1),
            siUnit = "m/s",
            category = QuantityCategory.MECHANICS,
            commonFormula = "v = dx / dt",
            description = "Rate of change of position with respect to time."
        ),
        PhysicalQuantity(
            name = "Acceleration",
            symbol = "a, g",
            dimensions = DimensionVector(l = 1, t = -2),
            siUnit = "m/s²",
            category = QuantityCategory.MECHANICS,
            commonFormula = "a = dv / dt",
            description = "Rate of change of velocity with respect to time."
        ),
        PhysicalQuantity(
            name = "Force / Weight / Tension",
            symbol = "F, W, T",
            dimensions = DimensionVector(m = 1, l = 1, t = -2),
            siUnit = "N (newton)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "F = m · a",
            description = "Push or pull acting upon an object resulting from interaction."
        ),
        PhysicalQuantity(
            name = "Linear Momentum / Impulse",
            symbol = "p, J",
            dimensions = DimensionVector(m = 1, l = 1, t = -1),
            siUnit = "kg·m/s (or N·s)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "p = m · v, J = F · Δt",
            description = "Product of mass and velocity; overall translational motion quantity."
        ),
        PhysicalQuantity(
            name = "Work / Energy / Heat",
            symbol = "W, E, U, Q",
            dimensions = DimensionVector(m = 1, l = 2, t = -2),
            siUnit = "J (joule)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "W = F · d, E = m·c²",
            description = "Capacity to do work; conserved scalar physical quantity."
        ),
        PhysicalQuantity(
            name = "Torque / Moment of Force",
            symbol = "τ, M",
            dimensions = DimensionVector(m = 1, l = 2, t = -2),
            siUnit = "N·m",
            category = QuantityCategory.MECHANICS,
            commonFormula = "τ = r × F",
            description = "Rotational equivalent of linear force."
        ),
        PhysicalQuantity(
            name = "Power / Radiant Flux",
            symbol = "P, Φ",
            dimensions = DimensionVector(m = 1, l = 2, t = -3),
            siUnit = "W (watt, J/s)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "P = dW / dt = F · v",
            description = "Rate at which work is performed or energy is transferred."
        ),
        PhysicalQuantity(
            name = "Pressure / Stress / Modulus",
            symbol = "P, p, σ, E",
            dimensions = DimensionVector(m = 1, l = -1, t = -2),
            siUnit = "Pa (pascal, N/m²)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "P = F / A",
            description = "Force applied perpendicular to the surface of an object per unit area."
        ),
        PhysicalQuantity(
            name = "Density / Volumetric Mass",
            symbol = "ρ",
            dimensions = DimensionVector(m = 1, l = -3),
            siUnit = "kg/m³",
            category = QuantityCategory.MECHANICS,
            commonFormula = "ρ = m / V",
            description = "Mass per unit volume of a material substance."
        ),
        PhysicalQuantity(
            name = "Surface Tension",
            symbol = "γ, σ",
            dimensions = DimensionVector(m = 1, t = -2),
            siUnit = "N/m (or J/m²)",
            category = QuantityCategory.MECHANICS,
            commonFormula = "γ = F / L",
            description = "Elastic-like force existing in the surface layer of a liquid."
        ),
        PhysicalQuantity(
            name = "Dynamic Viscosity",
            symbol = "μ, η",
            dimensions = DimensionVector(m = 1, l = -1, t = -1),
            siUnit = "Pa·s (kg/(m·s))",
            category = QuantityCategory.MECHANICS,
            commonFormula = "τ = μ · (dv/dy)",
            description = "Measure of a fluid's resistance to flow under shear stress."
        ),
        PhysicalQuantity(
            name = "Kinematic Viscosity",
            symbol = "ν",
            dimensions = DimensionVector(l = 2, t = -1),
            siUnit = "m²/s",
            category = QuantityCategory.MECHANICS,
            commonFormula = "ν = μ / ρ",
            description = "Ratio of dynamic viscosity to fluid density."
        ),
        PhysicalQuantity(
            name = "Area",
            symbol = "A, S",
            dimensions = DimensionVector(l = 2),
            siUnit = "m²",
            category = QuantityCategory.MECHANICS,
            commonFormula = "A = L · W",
            description = "Two-dimensional extent of a plane or surface."
        ),
        PhysicalQuantity(
            name = "Volume",
            symbol = "V",
            dimensions = DimensionVector(l = 3),
            siUnit = "m³",
            category = QuantityCategory.MECHANICS,
            commonFormula = "V = L · W · H",
            description = "Three-dimensional space enclosed within a boundary."
        ),
        PhysicalQuantity(
            name = "Frequency / Angular Frequency",
            symbol = "f, ν, ω",
            dimensions = DimensionVector(t = -1),
            siUnit = "Hz (hertz, 1/s)",
            category = QuantityCategory.OPTICS_WAVES,
            commonFormula = "f = 1 / T",
            description = "Number of occurrences of a repeating event per unit time."
        ),

        // Electromagnetism
        PhysicalQuantity(
            name = "Electric Charge",
            symbol = "q, Q",
            dimensions = DimensionVector(i = 1, t = 1),
            siUnit = "C (coulomb, A·s)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "Q = I · t",
            description = "Physical property of matter that causes it to experience electromagnetic force."
        ),
        PhysicalQuantity(
            name = "Electric Potential / Voltage / EMF",
            symbol = "V, U, ε",
            dimensions = DimensionVector(m = 1, l = 2, t = -3, i = -1),
            siUnit = "V (volt, J/C)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "V = W / Q = I · R",
            description = "Difference in electric potential energy per unit electric charge."
        ),
        PhysicalQuantity(
            name = "Electrical Resistance / Impedance",
            symbol = "R, Z",
            dimensions = DimensionVector(m = 1, l = 2, t = -3, i = -2),
            siUnit = "Ω (ohm, V/A)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "R = V / I",
            description = "Measure of opposition to current flow in an electrical circuit."
        ),
        PhysicalQuantity(
            name = "Electrical Conductance",
            symbol = "G",
            dimensions = DimensionVector(m = -1, l = -2, t = 3, i = 2),
            siUnit = "S (siemens, 1/Ω)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "G = 1 / R",
            description = "Ease with which electric current flows through a conductor."
        ),
        PhysicalQuantity(
            name = "Capacitance",
            symbol = "C",
            dimensions = DimensionVector(m = -1, l = -2, t = 4, i = 2),
            siUnit = "F (farad, C/V)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "C = Q / V",
            description = "Ratio of electric charge change to corresponding potential change."
        ),
        PhysicalQuantity(
            name = "Inductance",
            symbol = "L, M",
            dimensions = DimensionVector(m = 1, l = 2, t = -2, i = -2),
            siUnit = "H (henry, Wb/A)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "V = L · (dI/dt)",
            description = "Tendency of an electrical conductor to oppose changes in electric current."
        ),
        PhysicalQuantity(
            name = "Magnetic Flux Density / B-Field",
            symbol = "B",
            dimensions = DimensionVector(m = 1, t = -2, i = -1),
            siUnit = "T (tesla, Wb/m²)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "F = q · (v × B)",
            description = "Strength of magnetic field determining force on moving charges."
        ),
        PhysicalQuantity(
            name = "Magnetic Flux",
            symbol = "Φ_B",
            dimensions = DimensionVector(m = 1, l = 2, t = -2, i = -1),
            siUnit = "Wb (weber, V·s)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "Φ = B · A",
            description = "Surface integral of magnetic flux density over an area."
        ),
        PhysicalQuantity(
            name = "Electric Field Strength",
            symbol = "E",
            dimensions = DimensionVector(m = 1, l = 1, t = -3, i = -1),
            siUnit = "V/m (or N/C)",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "E = F / q",
            description = "Electric force exerted per unit positive charge."
        ),
        PhysicalQuantity(
            name = "Magnetic Field Strength / H-Field",
            symbol = "H",
            dimensions = DimensionVector(l = -1, i = 1),
            siUnit = "A/m",
            category = QuantityCategory.ELECTROMAGNETISM,
            commonFormula = "B = μ · H",
            description = "Magnetic field intensity produced by electric currents."
        ),

        // Thermodynamics
        PhysicalQuantity(
            name = "Entropy / Heat Capacity",
            symbol = "S, C",
            dimensions = DimensionVector(m = 1, l = 2, t = -2, theta = -1),
            siUnit = "J/K",
            category = QuantityCategory.THERMODYNAMICS,
            commonFormula = "dS = dQ / T",
            description = "Measure of thermal energy unavailable for work / microscopic disorder."
        ),
        PhysicalQuantity(
            name = "Specific Heat Capacity",
            symbol = "c, c_p, c_v",
            dimensions = DimensionVector(l = 2, t = -2, theta = -1),
            siUnit = "J/(kg·K)",
            category = QuantityCategory.THERMODYNAMICS,
            commonFormula = "Q = m · c · ΔT",
            description = "Heat required to raise the temperature of 1 kg by 1 Kelvin."
        ),
        PhysicalQuantity(
            name = "Thermal Conductivity",
            symbol = "k, λ",
            dimensions = DimensionVector(m = 1, l = 1, t = -3, theta = -1),
            siUnit = "W/(m·K)",
            category = QuantityCategory.THERMODYNAMICS,
            commonFormula = "q = -k · ∇T",
            description = "Property indicating ability to conduct heat across temperature gradients."
        ),
        PhysicalQuantity(
            name = "Molar Heat Capacity / Gas Constant",
            symbol = "C_m, R",
            dimensions = DimensionVector(m = 1, l = 2, t = -2, theta = -1, n = -1),
            siUnit = "J/(mol·K)",
            category = QuantityCategory.THERMODYNAMICS,
            commonFormula = "PV = n·R·T",
            description = "Heat capacity per mole of substance / universal gas constant."
        ),

        // Optics & Radiation
        PhysicalQuantity(
            name = "Luminous Flux",
            symbol = "Φ_v",
            dimensions = DimensionVector(j = 1),
            siUnit = "lm (lumen, cd·sr)",
            category = QuantityCategory.OPTICS_WAVES,
            commonFormula = "Φ_v = I_v · Ω",
            description = "Perceived power of light emitted or received."
        ),
        PhysicalQuantity(
            name = "Illuminance",
            symbol = "E_v",
            dimensions = DimensionVector(l = -2, j = 1),
            siUnit = "lx (lux, lm/m²)",
            category = QuantityCategory.OPTICS_WAVES,
            commonFormula = "E_v = Φ_v / A",
            description = "Total luminous flux incident on a surface per unit area."
        ),

        // Dimensionless
        PhysicalQuantity(
            name = "Angle / Dimensionless Ratio",
            symbol = "θ, α, φ",
            dimensions = DimensionVector.DIMENSIONLESS,
            siUnit = "rad, deg (dimensionless)",
            category = QuantityCategory.GENERAL,
            commonFormula = "θ = s / r",
            description = "Ratio of identical physical dimensions (e.g. angle, strain, Mach number)."
        )
    )

    fun findMatches(vector: DimensionVector): List<PhysicalQuantity> {
        return ALL_QUANTITIES.filter { it.dimensions == vector }
    }
}
