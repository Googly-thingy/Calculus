package com.example.model

data class DerivationStep(
    val stepNumber: Int,
    val title: String,
    val formula: String,
    val explanation: String
)

data class QuantityDerivation(
    val symbol: String,
    val name: String,
    val category: QuantityCategory,
    val siUnit: String,
    val finalDimension: DimensionVector,
    val baseFormula: String,
    val steps: List<DerivationStep>
)

object DerivationCatalog {

    val ALL_DERIVATIONS: List<QuantityDerivation> = listOf(
        // ------------------ MECHANICS ------------------
        QuantityDerivation(
            symbol = "v",
            name = "Velocity / Speed",
            category = QuantityCategory.MECHANICS,
            siUnit = "m/s",
            finalDimension = DimensionVector.VELOCITY,
            baseFormula = "v = d / t",
            steps = listOf(
                DerivationStep(1, "Physical Definition", "v = d / t", "Velocity is the displacement (d) divided by elapsed time (t)."),
                DerivationStep(2, "Substitute Dimensions", "[v] = [d] / [t] = L / T", "Length has dimension L; time has dimension T."),
                DerivationStep(3, "Algebraic Exponent Form", "= L · T⁻¹", "Applying reciprocal exponent rules yields LT⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "u",
            name = "Initial Velocity",
            category = QuantityCategory.MECHANICS,
            siUnit = "m/s",
            finalDimension = DimensionVector.VELOCITY,
            baseFormula = "u = d / t",
            steps = listOf(
                DerivationStep(1, "Physical Definition", "u = v₀ = d / t", "Initial velocity represents velocity at t = 0."),
                DerivationStep(2, "Substitute Dimensions", "[u] = [d] / [t] = L / T", "Displacement over time."),
                DerivationStep(3, "Final Canonical Form", "= L · T⁻¹", "Yields velocity dimensions LT⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "a",
            name = "Acceleration",
            category = QuantityCategory.MECHANICS,
            siUnit = "m/s²",
            finalDimension = DimensionVector.ACCELERATION,
            baseFormula = "a = Δv / Δt",
            steps = listOf(
                DerivationStep(1, "Physical Definition", "a = Δv / Δt", "Acceleration is the rate of change of velocity over time."),
                DerivationStep(2, "Decompose Velocity", "v = d / t  ⇒  a = (d / t) / t", "Substitute the definition of velocity (displacement over time)."),
                DerivationStep(3, "Dimensional Substitution", "[a] = [v] / [t] = (L · T⁻¹) / T", "Replace velocity with L·T⁻¹ and time with T."),
                DerivationStep(4, "Algebraic Simplification", "= L / (T · T) = L / T²", "Combine denominators into T²."),
                DerivationStep(5, "Final Canonical Dimension", "= L · T⁻²", "Canonical acceleration dimension: LT⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "g",
            name = "Gravitational Acceleration",
            category = QuantityCategory.MECHANICS,
            siUnit = "m/s²",
            finalDimension = DimensionVector.ACCELERATION,
            baseFormula = "g = F_g / m = G · M / r²",
            steps = listOf(
                DerivationStep(1, "Physical Definition", "g = F_g / m = Δv / Δt", "Free-fall acceleration produced by gravity on mass m."),
                DerivationStep(2, "Decompose Acceleration Steps", "g = (d / t) / t", "Rate of change of velocity per second."),
                DerivationStep(3, "Dimensional Substitution", "[g] = (L · T⁻¹) / T", "Velocity [L·T⁻¹] divided by time [T]."),
                DerivationStep(4, "Final Canonical Dimension", "= L · T⁻²", "Identical to linear acceleration dimension: LT⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "F",
            name = "Force / Weight",
            category = QuantityCategory.MECHANICS,
            siUnit = "N (kg·m/s²)",
            finalDimension = DimensionVector.FORCE,
            baseFormula = "F = m · a",
            steps = listOf(
                DerivationStep(1, "Newton's Second Law", "F = m · a", "Force equals mass multiplied by acceleration."),
                DerivationStep(2, "Decompose Acceleration", "a = Δv / Δt = (d / t) / t", "Acceleration is displacement per time squared."),
                DerivationStep(3, "Substitute Dimensions", "[F] = [m] · [a] = M · (L · T⁻²)", "Mass has dimension M; acceleration has dimension L·T⁻²."),
                DerivationStep(4, "Final Canonical Dimension", "= M · L · T⁻²", "Base SI unit: 1 Newton = 1 kg·m·s⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "p",
            name = "Linear Momentum / Impulse",
            category = QuantityCategory.MECHANICS,
            siUnit = "kg·m/s (N·s)",
            finalDimension = DimensionVector.MOMENTUM,
            baseFormula = "p = m · v",
            steps = listOf(
                DerivationStep(1, "Physical Definition", "p = m · v", "Momentum is the product of mass and velocity."),
                DerivationStep(2, "Substitute Dimensions", "[p] = [m] · [v] = M · (L · T⁻¹)", "Mass has dimension M; velocity is L·T⁻¹."),
                DerivationStep(3, "Final Canonical Dimension", "= M · L · T⁻¹", "Canonical momentum / impulse dimension.")
            )
        ),
        QuantityDerivation(
            symbol = "W",
            name = "Work / Energy / Heat",
            category = QuantityCategory.MECHANICS,
            siUnit = "J (N·m = kg·m²/s²)",
            finalDimension = DimensionVector.ENERGY,
            baseFormula = "W = F · d",
            steps = listOf(
                DerivationStep(1, "Work Definition", "W = F · d", "Work is force applied along a displacement distance d."),
                DerivationStep(2, "Substitute Force Definition", "F = m · a  ⇒  W = (m · a) · d", "Expand force into mass times acceleration."),
                DerivationStep(3, "Dimensional Substitution", "[W] = (M · L · T⁻²) · L", "Force [M·L·T⁻²] multiplied by length [L]."),
                DerivationStep(4, "Algebraic Simplification", "= M · (L · L) · T⁻²", "Combine length exponents: L × L = L²."),
                DerivationStep(5, "Final Canonical Dimension", "= M · L² · T⁻²", "1 Joule = 1 kg·m²·s⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "P_p",
            name = "Power / Radiant Flux",
            category = QuantityCategory.MECHANICS,
            siUnit = "W (J/s = kg·m²/s³)",
            finalDimension = DimensionVector.POWER,
            baseFormula = "P = W / t",
            steps = listOf(
                DerivationStep(1, "Physical Definition", "P = dW / dt = W / t", "Power is the rate at which work is performed over time."),
                DerivationStep(2, "Substitute Work Dimension", "W = F · d  ⇒  [W] = M · L² · T⁻²", "Work has dimension M·L²·T⁻²."),
                DerivationStep(3, "Divide by Time", "[P] = [W] / [t] = (M · L² · T⁻²) / T", "Divide energy by time [T]."),
                DerivationStep(4, "Final Canonical Dimension", "= M · L² · T⁻³", "1 Watt = 1 Joule/second = 1 kg·m²·s⁻³.")
            )
        ),
        QuantityDerivation(
            symbol = "P",
            name = "Pressure / Stress",
            category = QuantityCategory.MECHANICS,
            siUnit = "Pa (N/m²)",
            finalDimension = DimensionVector.PRESSURE,
            baseFormula = "P = F / A",
            steps = listOf(
                DerivationStep(1, "Pressure Definition", "P = F / A", "Pressure is normal force exerted per unit surface area A."),
                DerivationStep(2, "Decompose Components", "F = m · a,  A = L²", "Area has dimension L²; force has dimension M·L·T⁻²."),
                DerivationStep(3, "Dimensional Substitution", "[P] = (M · L · T⁻²) / L²", "Divide force by area."),
                DerivationStep(4, "Simplify Exponents", "= M · L¹⁻² · T⁻² = M · L⁻¹ · T⁻²", "L / L² reduces to L⁻¹."),
                DerivationStep(5, "Final Canonical Dimension", "= M · L⁻¹ · T⁻²", "1 Pascal = 1 kg·m⁻¹·s⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "ρ",
            name = "Density (Volumetric)",
            category = QuantityCategory.MECHANICS,
            siUnit = "kg/m³",
            finalDimension = DimensionVector.DENSITY,
            baseFormula = "ρ = m / V",
            steps = listOf(
                DerivationStep(1, "Physical Definition", "ρ = m / V", "Density is mass divided by enclosed volume V."),
                DerivationStep(2, "Substitute Volume", "V = L³  ⇒  [V] = L³", "Three-dimensional space has dimension L³."),
                DerivationStep(3, "Final Canonical Dimension", "[ρ] = M / L³ = M · L⁻³", "1 kg/m³ = M·L⁻³.")
            )
        ),
        QuantityDerivation(
            symbol = "τ",
            name = "Torque / Moment",
            category = QuantityCategory.MECHANICS,
            siUnit = "N·m",
            finalDimension = DimensionVector.ENERGY,
            baseFormula = "τ = r × F",
            steps = listOf(
                DerivationStep(1, "Torque Definition", "τ = r × F", "Rotational moment: lever arm radius r cross force F."),
                DerivationStep(2, "Substitute Dimensions", "[τ] = [r] · [F] = L · (M · L · T⁻²)", "Radius is length [L]; force is [M·L·T⁻²]."),
                DerivationStep(3, "Final Canonical Dimension", "= M · L² · T⁻²", "Dimensionally equivalent to energy: M·L²·T⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "μ",
            name = "Dynamic Viscosity",
            category = QuantityCategory.MECHANICS,
            siUnit = "Pa·s (kg/(m·s))",
            finalDimension = DimensionVector.DYNAMIC_VISCOSITY,
            baseFormula = "τ = μ · (dv / dy)",
            steps = listOf(
                DerivationStep(1, "Newton's Law of Viscosity", "τ = μ · (dv / dy)  ⇒  μ = τ / (dv / dy)", "Shear stress τ over velocity gradient (dv/dy)."),
                DerivationStep(2, "Decompose Quantities", "[τ] = M · L⁻¹ · T⁻²,  [dv/dy] = (L · T⁻¹) / L = T⁻¹", "Shear stress is pressure; velocity gradient is frequency."),
                DerivationStep(3, "Divide Dimensions", "[μ] = (M · L⁻¹ · T⁻²) / T⁻¹", "Subtract time exponent: T⁻² / T⁻¹ = T⁻¹."),
                DerivationStep(4, "Final Canonical Dimension", "= M · L⁻¹ · T⁻¹", "1 Pa·s = 1 kg·m⁻¹·s⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "A",
            name = "Area",
            category = QuantityCategory.MECHANICS,
            siUnit = "m²",
            finalDimension = DimensionVector(l = 2),
            baseFormula = "A = L · W",
            steps = listOf(
                DerivationStep(1, "Definition", "A = length · width", "Two-dimensional surface extent."),
                DerivationStep(2, "Dimensions", "[A] = L · L = L²", "Product of two lengths."),
                DerivationStep(3, "Final Dimension", "= L²", "Canonical area dimension.")
            )
        ),
        QuantityDerivation(
            symbol = "Vol",
            name = "Volume",
            category = QuantityCategory.MECHANICS,
            siUnit = "m³",
            finalDimension = DimensionVector(l = 3),
            baseFormula = "V = L · W · H",
            steps = listOf(
                DerivationStep(1, "Definition", "V = length · width · height", "Three-dimensional spatial capacity."),
                DerivationStep(2, "Dimensions", "[V] = L · L · L = L³", "Product of three lengths."),
                DerivationStep(3, "Final Dimension", "= L³", "Canonical volume dimension.")
            )
        ),

        // ------------------ ELECTRICITY & MAGNETISM ------------------
        QuantityDerivation(
            symbol = "I",
            name = "Electric Current",
            category = QuantityCategory.ELECTROMAGNETISM,
            siUnit = "A (ampere)",
            finalDimension = DimensionVector.CURRENT,
            baseFormula = "I = dq / dt",
            steps = listOf(
                DerivationStep(1, "SI Base Definition", "I = dq / dt", "Electric current is an independent SI base dimension."),
                DerivationStep(2, "Canonical Symbol", "[I] = I", "Fundamental base dimension.")
            )
        ),
        QuantityDerivation(
            symbol = "q",
            name = "Electric Charge",
            category = QuantityCategory.ELECTROMAGNETISM,
            siUnit = "C (A·s)",
            finalDimension = DimensionVector.ELECTRIC_CHARGE,
            baseFormula = "q = I · t",
            steps = listOf(
                DerivationStep(1, "Charge Definition", "q = I · t", "Charge is electric current integrated over time."),
                DerivationStep(2, "Substitute Dimensions", "[q] = [I] · [t] = I · T", "Current [I] times time [T]."),
                DerivationStep(3, "Final Canonical Dimension", "= I · T", "1 Coulomb = 1 Ampere · second.")
            )
        ),
        QuantityDerivation(
            symbol = "V",
            name = "Electric Potential / Voltage",
            category = QuantityCategory.ELECTROMAGNETISM,
            siUnit = "V (volt, J/C)",
            finalDimension = DimensionVector.VOLTAGE,
            baseFormula = "V = W / q = (F · d) / (I · t)",
            steps = listOf(
                DerivationStep(1, "Voltage Definition", "V = W / q", "Electric potential is work (energy) done per unit charge."),
                DerivationStep(2, "Decompose Components", "W = F · d = (m · a) · d,  q = I · t", "Substitute mechanical work and electrical charge."),
                DerivationStep(3, "Dimensional Substitution", "[V] = (M · L² · T⁻²) / (I · T)", "Work [M·L²·T⁻²] divided by charge [I·T]."),
                DerivationStep(4, "Algebraic Simplification", "= M · L² · T⁻²⁻¹ · I⁻¹", "Combine time exponents: T⁻² / T = T⁻³."),
                DerivationStep(5, "Final Canonical Dimension", "= M · L² · T⁻³ · I⁻¹", "1 Volt = 1 kg·m²·s⁻³·A⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "R",
            name = "Electrical Resistance",
            category = QuantityCategory.ELECTROMAGNETISM,
            siUnit = "Ω (ohm, V/A)",
            finalDimension = DimensionVector.RESISTANCE,
            baseFormula = "R = V / I",
            steps = listOf(
                DerivationStep(1, "Ohm's Law", "R = V / I", "Resistance equals potential difference divided by current."),
                DerivationStep(2, "Substitute Voltage Dimension", "[V] = M · L² · T⁻³ · I⁻¹", "Voltage dimension previously derived from W/q."),
                DerivationStep(3, "Divide by Current", "[R] = (M · L² · T⁻³ · I⁻¹) / I", "Divide voltage by current dimension [I]."),
                DerivationStep(4, "Final Canonical Dimension", "= M · L² · T⁻³ · I⁻²", "1 Ohm = 1 kg·m²·s⁻³·A⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "C",
            name = "Capacitance",
            category = QuantityCategory.ELECTROMAGNETISM,
            siUnit = "F (farad, C/V)",
            finalDimension = DimensionVector.CAPACITANCE,
            baseFormula = "C = q / V",
            steps = listOf(
                DerivationStep(1, "Capacitance Definition", "C = q / V", "Ratio of stored electric charge to applied voltage."),
                DerivationStep(2, "Substitute Charge & Voltage", "[q] = I · T,  [V] = M · L² · T⁻³ · I⁻¹", "Charge [I·T] divided by voltage [M·L²·T⁻³·I⁻¹]."),
                DerivationStep(3, "Invert & Multiply", "[C] = (I · T) / (M · L² · T⁻³ · I⁻¹)", "Move denominator dimensions to numerator."),
                DerivationStep(4, "Final Canonical Dimension", "= M⁻¹ · L⁻² · T⁴ · I²", "1 Farad = 1 kg⁻¹·m⁻²·s⁴·A².")
            )
        ),
        QuantityDerivation(
            symbol = "L_ind",
            name = "Inductance",
            category = QuantityCategory.ELECTROMAGNETISM,
            siUnit = "H (henry, Wb/A)",
            finalDimension = DimensionVector.INDUCTANCE,
            baseFormula = "V = L · (dI / dt)  ⇒  L = V · dt / dI",
            steps = listOf(
                DerivationStep(1, "Faraday-Lenz Law", "L = V · dt / dI", "Induced EMF per unit time rate of change of current."),
                DerivationStep(2, "Substitute Dimensions", "[L] = [V] · T / I = (M · L² · T⁻³ · I⁻¹) · T / I", "Multiply voltage by time and divide by current."),
                DerivationStep(3, "Final Canonical Dimension", "= M · L² · T⁻² · I⁻²", "1 Henry = 1 kg·m²·s⁻²·A⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "B",
            name = "Magnetic Flux Density (B-Field)",
            category = QuantityCategory.ELECTROMAGNETISM,
            siUnit = "T (tesla, N/(A·m))",
            finalDimension = DimensionVector.MAGNETIC_FLUX_DENSITY,
            baseFormula = "F = q · v · B  ⇒  B = F / (q · v)",
            steps = listOf(
                DerivationStep(1, "Lorentz Force Law", "B = F / (q · v)", "Magnetic force acting on a moving charged particle."),
                DerivationStep(2, "Decompose Components", "[F] = M · L · T⁻²,  [q] = I · T,  [v] = L · T⁻¹", "Force, charge, and velocity dimensions."),
                DerivationStep(3, "Dimensional Substitution", "[B] = (M · L · T⁻²) / (I · T · L · T⁻¹)", "Length and time terms simplify."),
                DerivationStep(4, "Final Canonical Dimension", "= M · T⁻² · I⁻¹", "1 Tesla = 1 kg·s⁻²·A⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "Φ_B",
            name = "Magnetic Flux",
            category = QuantityCategory.ELECTROMAGNETISM,
            siUnit = "Wb (weber, T·m²)",
            finalDimension = DimensionVector.MAGNETIC_FLUX,
            baseFormula = "Φ = B · A",
            steps = listOf(
                DerivationStep(1, "Flux Definition", "Φ = B · A", "Surface integral of magnetic flux density over area A."),
                DerivationStep(2, "Multiply B by Area", "[Φ] = (M · T⁻² · I⁻¹) · L²", "Multiply B-field by L²."),
                DerivationStep(3, "Final Canonical Dimension", "= M · L² · T⁻² · I⁻¹", "1 Weber = 1 kg·m²·s⁻²·A⁻¹.")
            )
        ),

        // ------------------ THERMODYNAMICS & HEAT ------------------
        QuantityDerivation(
            symbol = "T_temp",
            name = "Temperature",
            category = QuantityCategory.THERMODYNAMICS,
            siUnit = "K (kelvin)",
            finalDimension = DimensionVector.TEMPERATURE,
            baseFormula = "T = PV / (nR)",
            steps = listOf(
                DerivationStep(1, "SI Base Definition", "T = θ", "Thermodynamic temperature is an SI base dimension."),
                DerivationStep(2, "Canonical Symbol", "[T] = Θ", "Fundamental thermodynamic dimension Θ.")
            )
        ),
        QuantityDerivation(
            symbol = "Q_heat",
            name = "Thermal Heat Energy",
            category = QuantityCategory.THERMODYNAMICS,
            siUnit = "J (joule)",
            finalDimension = DimensionVector.ENERGY,
            baseFormula = "Q = m · c · ΔT",
            steps = listOf(
                DerivationStep(1, "Thermodynamic Equivalence", "Q = W = ΔU", "Heat is thermal energy in transit."),
                DerivationStep(2, "Energy Dimensions", "[Q] = [W] = M · L² · T⁻²", "Equivalent to mechanical work."),
                DerivationStep(3, "Final Dimension", "= M · L² · T⁻²", "1 Joule = 1 kg·m²·s⁻².")
            )
        ),
        QuantityDerivation(
            symbol = "S",
            name = "Entropy / Heat Capacity",
            category = QuantityCategory.THERMODYNAMICS,
            siUnit = "J/K",
            finalDimension = DimensionVector(m = 1, l = 2, t = -2, theta = -1),
            baseFormula = "dS = dQ / T",
            steps = listOf(
                DerivationStep(1, "Clausius Entropy Definition", "S = Q / T", "Heat energy transferred reversibly per kelvin."),
                DerivationStep(2, "Substitute Dimensions", "[S] = [Q] / [T] = (M · L² · T⁻²) / Θ", "Energy divided by temperature [Θ]."),
                DerivationStep(3, "Final Canonical Dimension", "= M · L² · T⁻² · Θ⁻¹", "1 J/K = 1 kg·m²·s⁻²·K⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "c_sp",
            name = "Specific Heat Capacity",
            category = QuantityCategory.THERMODYNAMICS,
            siUnit = "J/(kg·K)",
            finalDimension = DimensionVector(l = 2, t = -2, theta = -1),
            baseFormula = "c = Q / (m · ΔT)",
            steps = listOf(
                DerivationStep(1, "Specific Heat Definition", "c = Q / (m · ΔT)", "Heat required to raise 1 kg of mass by 1 Kelvin."),
                DerivationStep(2, "Substitute Dimensions", "[c] = (M · L² · T⁻²) / (M · Θ)", "Divide energy by mass [M] and temperature [Θ]."),
                DerivationStep(3, "Mass Cancels", "= (M / M) · L² · T⁻² · Θ⁻¹", "Mass term M cancels completely."),
                DerivationStep(4, "Final Canonical Dimension", "= L² · T⁻² · Θ⁻¹", "1 J/(kg·K) = 1 m²·s⁻²·K⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "k_th",
            name = "Thermal Conductivity",
            category = QuantityCategory.THERMODYNAMICS,
            siUnit = "W/(m·K)",
            finalDimension = DimensionVector(m = 1, l = 1, t = -3, theta = -1),
            baseFormula = "q = -k · A · (dT / dx)",
            steps = listOf(
                DerivationStep(1, "Fourier's Heat Conduction Law", "k = (q · dx) / (A · dT)", "Rate of heat flow per unit area per temperature gradient."),
                DerivationStep(2, "Substitute Power & Geometry", "[q] = M · L² · T⁻³,  [dx/A] = L / L² = L⁻¹", "Heat rate is power; area gradient is 1/length."),
                DerivationStep(3, "Divide by Temperature", "[k] = (M · L² · T⁻³ · L⁻¹) / Θ", "Multiply exponents and divide by Θ."),
                DerivationStep(4, "Final Canonical Dimension", "= M · L · T⁻³ · Θ⁻¹", "1 W/(m·K) = 1 kg·m·s⁻³·K⁻¹.")
            )
        ),

        // ------------------ WAVES & OPTICS ------------------
        QuantityDerivation(
            symbol = "f",
            name = "Frequency",
            category = QuantityCategory.OPTICS_WAVES,
            siUnit = "Hz (1/s)",
            finalDimension = DimensionVector.FREQUENCY,
            baseFormula = "f = 1 / T",
            steps = listOf(
                DerivationStep(1, "Definition", "f = 1 / T", "Number of periodic cycles per second."),
                DerivationStep(2, "Substitute Dimensions", "[f] = 1 / [t] = 1 / T", "Reciprocal of time period."),
                DerivationStep(3, "Final Canonical Dimension", "= T⁻¹", "1 Hertz = 1 s⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "ω",
            name = "Angular Frequency / Speed",
            category = QuantityCategory.OPTICS_WAVES,
            siUnit = "rad/s (1/s)",
            finalDimension = DimensionVector.FREQUENCY,
            baseFormula = "ω = 2π · f = θ / t",
            steps = listOf(
                DerivationStep(1, "Definition", "ω = θ / t", "Angle traversed per unit time. Angle is dimensionless (1)."),
                DerivationStep(2, "Substitute Dimensions", "[ω] = 1 / T", "Dimensionless angle divided by time."),
                DerivationStep(3, "Final Canonical Dimension", "= T⁻¹", "1 rad/s = 1 s⁻¹.")
            )
        ),
        QuantityDerivation(
            symbol = "λ",
            name = "Wavelength",
            category = QuantityCategory.OPTICS_WAVES,
            siUnit = "m",
            finalDimension = DimensionVector(l = 1),
            baseFormula = "λ = v / f",
            steps = listOf(
                DerivationStep(1, "Wave Equation", "λ = v / f", "Wave propagation speed divided by frequency."),
                DerivationStep(2, "Substitute Dimensions", "[λ] = (L · T⁻¹) / T⁻¹", "Divide velocity by frequency."),
                DerivationStep(3, "Time Cancels", "= L · (T⁻¹ / T⁻¹) = L", "Time dimensions cancel."),
                DerivationStep(4, "Final Dimension", "= L", "Spatial period in meters.")
            )
        ),
        QuantityDerivation(
            symbol = "Φ_v",
            name = "Luminous Flux",
            category = QuantityCategory.OPTICS_WAVES,
            siUnit = "lm (lumen, cd·sr)",
            finalDimension = DimensionVector.LUMINOUS_INTENSITY,
            baseFormula = "Φ_v = I_v · Ω",
            steps = listOf(
                DerivationStep(1, "Definition", "Φ_v = I_v · Ω", "Luminous intensity per solid angle (steradian is dimensionless)."),
                DerivationStep(2, "Substitute Dimensions", "[Φ_v] = J · 1 = J", "Candela times dimensionless solid angle."),
                DerivationStep(3, "Final Dimension", "= J", "1 Lumen = 1 Candela.")
            )
        ),
        QuantityDerivation(
            symbol = "E_v",
            name = "Illuminance",
            category = QuantityCategory.OPTICS_WAVES,
            siUnit = "lx (lux, lm/m²)",
            finalDimension = DimensionVector(l = -2, j = 1),
            baseFormula = "E_v = Φ_v / A",
            steps = listOf(
                DerivationStep(1, "Definition", "E_v = Φ_v / A", "Luminous flux incident per unit surface area."),
                DerivationStep(2, "Substitute Dimensions", "[E_v] = [Φ_v] / [A] = J / L²", "Luminous flux [J] divided by area [L²]."),
                DerivationStep(3, "Final Dimension", "= L⁻² · J", "1 Lux = 1 cd/m².")
            )
        ),

        // ------------------ SI BASE DIMENSIONS ------------------
        QuantityDerivation(
            symbol = "M",
            name = "Mass (Base)",
            category = QuantityCategory.GENERAL,
            siUnit = "kg (kilogram)",
            finalDimension = DimensionVector.MASS,
            baseFormula = "[M] = M¹",
            steps = listOf(
                DerivationStep(1, "Fundamental Definition", "M", "Fundamental SI base dimension of mass."),
                DerivationStep(2, "Canonical Representation", "= M", "Mass base power M¹.")
            )
        ),
        QuantityDerivation(
            symbol = "L",
            name = "Length / Distance (Base)",
            category = QuantityCategory.GENERAL,
            siUnit = "m (meter)",
            finalDimension = DimensionVector.LENGTH,
            baseFormula = "[L] = L¹",
            steps = listOf(
                DerivationStep(1, "Fundamental Definition", "L", "Fundamental SI base dimension of spatial length."),
                DerivationStep(2, "Canonical Representation", "= L", "Length base power L¹.")
            )
        ),
        QuantityDerivation(
            symbol = "T",
            name = "Time (Base)",
            category = QuantityCategory.GENERAL,
            siUnit = "s (second)",
            finalDimension = DimensionVector.TIME,
            baseFormula = "[T] = T¹",
            steps = listOf(
                DerivationStep(1, "Fundamental Definition", "T", "Fundamental SI base dimension of temporal duration."),
                DerivationStep(2, "Canonical Representation", "= T", "Time base power T¹.")
            )
        ),
        QuantityDerivation(
            symbol = "I_base",
            name = "Electric Current (Base)",
            category = QuantityCategory.GENERAL,
            siUnit = "A (ampere)",
            finalDimension = DimensionVector.CURRENT,
            baseFormula = "[I] = I¹",
            steps = listOf(
                DerivationStep(1, "Fundamental Definition", "I", "Fundamental SI base dimension of electric current."),
                DerivationStep(2, "Canonical Representation", "= I", "Current base power I¹.")
            )
        ),
        QuantityDerivation(
            symbol = "Θ",
            name = "Temperature (Base)",
            category = QuantityCategory.GENERAL,
            siUnit = "K (kelvin)",
            finalDimension = DimensionVector.TEMPERATURE,
            baseFormula = "[Θ] = Θ¹",
            steps = listOf(
                DerivationStep(1, "Fundamental Definition", "Θ", "Fundamental SI base dimension of thermodynamic temperature."),
                DerivationStep(2, "Canonical Representation", "= Θ", "Temperature base power Θ¹.")
            )
        ),
        QuantityDerivation(
            symbol = "N",
            name = "Amount of Substance (Base)",
            category = QuantityCategory.GENERAL,
            siUnit = "mol (mole)",
            finalDimension = DimensionVector.AMOUNT_OF_SUBSTANCE,
            baseFormula = "[N] = N¹",
            steps = listOf(
                DerivationStep(1, "Fundamental Definition", "N", "Fundamental SI base dimension of elementary entity count."),
                DerivationStep(2, "Canonical Representation", "= N", "Substance base power N¹.")
            )
        ),
        QuantityDerivation(
            symbol = "J",
            name = "Luminous Intensity (Base)",
            category = QuantityCategory.GENERAL,
            siUnit = "cd (candela)",
            finalDimension = DimensionVector.LUMINOUS_INTENSITY,
            baseFormula = "[J] = J¹",
            steps = listOf(
                DerivationStep(1, "Fundamental Definition", "J", "Fundamental SI base dimension of human-weighted light intensity."),
                DerivationStep(2, "Canonical Representation", "= J", "Luminous intensity base power J¹.")
            )
        )
    )

    fun findBySymbol(symbol: String): QuantityDerivation? {
        return ALL_DERIVATIONS.firstOrNull { it.symbol == symbol }
    }
}
