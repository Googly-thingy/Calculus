# Dimensional Calculator Interface & Multi-Step Derivation Engine

A complete redesign of the dimensional analysis section from a static/presentation view into an authentic **Calculator Interface** where physical quantities and units exist as tactile keypad buttons that immediately compute their dimensional formulas with multi-line step-by-step physical derivations.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> Based on your answers in Phase 1, the new Dimensional Calculator interface will strictly follow:

- **Confirmed Interface Style**: A true calculator layout featuring a top derivation display area and a bottom interactive keypad.
- **Confirmed Keypad Architecture**: Categorized pages across 5 physical domains:
  1. **Mechanics**: $v, u, a, g, F, p, W/E, P, \rho, \tau, \sigma, \mu, \nu$ (Velocity, Acceleration, Gravity, Force, Momentum, Work, Power, Density, Torque, Pressure/Stress, Dynamic & Kinematic Viscosity)
  2. **Electricity & Magnetism**: $I, q, V, R, G, C, L, B, \Phi_B, E, H$ (Current, Charge, Voltage, Resistance, Conductance, Capacitance, Inductance, Magnetic B-Field, Flux, Electric Field, H-Field)
  3. **Thermodynamics & Heat**: $T, Q, S, c, C_m, k_{th}$ (Temperature, Heat, Entropy, Specific Heat, Molar Heat, Thermal Conductivity)
  4. **Waves & Optics**: $f, \omega, \lambda, \Phi_v, E_v, I_v$ (Frequency, Angular Speed, Wavelength, Luminous Flux, Illuminance, Intensity)
  5. **SI Base Dimensions**: $M, L, T, I, \Theta, N, J$ (Mass, Length, Time, Current, Temperature, Substance, Luminosity)
- **Confirmed Derivation Display**: Multi-line progression showing exactly how the physical definition breaks down into SI base dimensions:
  - Example for Velocity ($v$):
    - Formula: $v = d / t$
    - Dimensions: $[v] = [d] / [t] = L / T$
    - Result: $= L \cdot T^{-1}$ (or $LT^{-1}$)
  - Example for Acceleration ($a$) and Gravity ($g$):
    - Formula: $a = \Delta v / \Delta t = (d / t) / t$
    - Dimensions: $[a] = [v] / [t] = (L \cdot T^{-1}) / T$
    - Simplification: $= L / T^2$
    - Result: $= L \cdot T^{-2}$
  - Example for Force ($F$):
    - Formula: $F = m \cdot a$
    - Dimensions: $[F] = [m] \cdot [a] = M \cdot (L \cdot T^{-2})$
    - Result: $= M \cdot L \cdot T^{-2}$
  - Example for Pressure ($P$):
    - Formula: $P = F / A = (m \cdot a) / A$
    - Dimensions: $[P] = (M \cdot L \cdot T^{-2}) / L^2$
    - Result: $= M \cdot L^{-1} \cdot T^{-2}$
  - Example for Voltage ($V$):
    - Formula: $V = W / q = (F \cdot d) / (I \cdot t)$
    - Dimensions: $[V] = (M \cdot L^2 \cdot T^{-2}) / (I \cdot T)$
    - Result: $= M \cdot L^2 \cdot T^{-3} \cdot I^{-1}$
- **Confirmed Interaction Model**: Single-tap inspection—tapping any physical quantity button instantly computes and displays its full multi-step derivation in the upper calculator screen.

---

## 1. Overview & Core Concept

### What It Does
- Replaces the generic card/form presentation with a high-fidelity **Dimensional Keypad Calculator**.
- The user operates it just like a scientific calculator: select a domain (Mechanics, Electricity, Heat, Waves, Base), tap any quantity button (e.g. $V, a, g, F, P, W, C, B, \dots$), and the upper screen animates to show:
  1. Quantity Header: Symbol, full name, SI unit, and standard formula.
  2. Hero Final Dimension: Large, crisp canonical notation (e.g. `L · T⁻²` or `M · L · T⁻²`).
  3. Step-by-Step Derivation Progression: Numbered steps illustrating physical law definition, dimensional substitution, algebraic simplification, and base dimension resolution.
  4. SI Base Dimensions Matrix: Visual state of the 7 base dimensions $(M, L, T, I, \Theta, N, J)$.

---

## 2. User Experience & Visual Design

### Visual Layout & Architecture

```
┌──────────────────────────────────────────────────────────┐
│  DIMENSIONAL CALCULATOR DISPLAY                          │
│                                                          │
│  a  Acceleration                                  m/s²   │
│                                                          │
│  [a] = L · T⁻²                                           │
│                                                          │
│  ┌────────────────────────────────────────────────────┐  │
│  │ Derivation Progression:                            │  │
│  │ 1. Definition:   a = Δv / Δt                       │  │
│  │ 2. Sub-Formula:  v = d / t  =>  a = (d / t) / t    │  │
│  │ 3. Dimensions:   [a] = [v] / [t] = (L · T⁻¹) / T   │  │
│  │ 4. Simplification: = L / T²                        │  │
│  │ 5. Final:        = L · T⁻²                         │  │
│  └────────────────────────────────────────────────────┘  │
│                                                          │
│  SI Base: [ M⁰ | L¹ | T⁻² | I⁰ | Θ⁰ | N⁰ | J⁰ ]          │
└──────────────────────────────────────────────────────────┘
┌──────────────────────────────────────────────────────────┐
│  Domain Tabs: [Mechanics] [Electricity] [Heat] [Waves]   │
├──────────────────────────────────────────────────────────┤
│  ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐                 │
│  │  v  │ │  u  │ │  a  │ │  g  │ │  F  │                 │
│  │speed│ │speed│ │accel│ │grav │ │force│                 │
│  └─────┘ └─────┘ └─────┘ └─────┘ └─────┘                 │
│  ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐                 │
│  │  p  │ │  W  │ │  P  │ │  ρ  │ │  τ  │                 │
│  │momen│ │work │ │power│ │dens │ │torq │                 │
│  └─────┘ └─────┘ └─────┘ └─────┘ └─────┘                 │
│  ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐ ┌─────┐                 │
│  │  P  │ │  μ  │ │  ν  │ │  A  │ │  V  │                 │
│  │press│ │visc │ │k-vis│ │area │ │vol  │                 │
│  └─────┘ └─────┘ └─────┘ └─────┘ └─────┘                 │
└──────────────────────────────────────────────────────────┘
```

---

## 3. Step-by-Step Implementation Strategy

1. **Step-by-Step Derivation Catalog (`DerivationCatalog.kt`)**:
   - Model `DerivationStep(stepNumber: Int, title: String, mathExpression: String, explanation: String)`
   - Model `QuantityDerivation(symbol: String, name: String, category: QuantityCategory, siUnit: String, finalDimension: DimensionVector, steps: List<DerivationStep>)`
   - Complete derivations for all major physical quantities across Mechanics, Electromagnetism, Thermodynamics, Waves/Optics, and SI Base dimensions:
     - Acceleration $a$ & $g$: $a = v/t = (d/t)/t \to [a] = (L \cdot T^{-1})/T = L \cdot T^{-2}$
     - Velocity $v$ & $u$: $v = d/t \to [v] = L/T = L \cdot T^{-1}$
     - Force $F$: $F = m \cdot a \to [F] = M \cdot (L \cdot T^{-2}) = M \cdot L \cdot T^{-2}$
     - Momentum $p$: $p = m \cdot v \to [p] = M \cdot (L \cdot T^{-1}) = M \cdot L \cdot T^{-1}$
     - Work/Energy $W$: $W = F \cdot d \to [W] = (M \cdot L \cdot T^{-2}) \cdot L = M \cdot L^2 \cdot T^{-2}$
     - Power $P$: $P = W/t \to [P] = (M \cdot L^2 \cdot T^{-2})/T = M \cdot L^2 \cdot T^{-3}$
     - Pressure $P$: $P = F/A \to [P] = (M \cdot L \cdot T^{-2})/L^2 = M \cdot L^{-1} \cdot T^{-2}$
     - Density $\rho$: $\rho = m/V \to [\rho] = M/L^3 = M \cdot L^{-3}$
     - Torque $\tau$: $\tau = r \times F \to [\tau] = L \cdot (M \cdot L \cdot T^{-2}) = M \cdot L^2 \cdot T^{-2}$
     - Voltage $V$: $V = W/q = (F \cdot d)/(I \cdot t) \to [V] = (M \cdot L^2 \cdot T^{-2})/(I \cdot T) = M \cdot L^2 \cdot T^{-3} \cdot I^{-1}$
     - Resistance $R$: $R = V/I \to [R] = (M \cdot L^2 \cdot T^{-3} \cdot I^{-1})/I = M \cdot L^2 \cdot T^{-3} \cdot I^{-2}$
     - Capacitance $C$: $C = q/V \to [C] = (I \cdot T)/(M \cdot L^2 \cdot T^{-3} \cdot I^{-1}) = M^{-1} \cdot L^{-2} \cdot T^4 \cdot I^2$
     - Magnetic Field $B$: $B = F/(q \cdot v) \to [B] = (M \cdot L \cdot T^{-2})/(I \cdot T \cdot L \cdot T^{-1}) = M \cdot T^{-2} \cdot I^{-1}$
     - Inductance $L$: $L = \Phi_B/I \to [L] = (M \cdot L^2 \cdot T^{-2} \cdot I^{-1})/I = M \cdot L^2 \cdot T^{-2} \cdot I^{-2}$
     - Frequency $f$: $f = 1/T \to [f] = T^{-1}$
     - Heat Capacity $S$: $S = Q/T \to [S] = (M \cdot L^2 \cdot T^{-2})/\Theta = M \cdot L^2 \cdot T^{-2} \cdot \Theta^{-1}$
     - Specific Heat $c$: $c = Q/(m \cdot \Delta T) \to [c] = (M \cdot L^2 \cdot T^{-2})/(M \cdot \Theta) = L^2 \cdot T^{-2} \cdot \Theta^{-1}$
     - And base dimensions ($M, L, T, I, \Theta, N, J$).

2. **Calculator Keypad UI Component (`DimensionalKeypad.kt`)**:
   - Modern grid of physical quantity buttons with primary symbol in bold (e.g. `a`, `V`, `F`, `g`, `W`) and secondary descriptive label.
   - Domain tab switcher at top of keypad (Mechanics, Electricity, Heat, Waves, Base).
   - High-contrast button styles matching the Dimensio dark instrument theme.

3. **Rebuilt `DimensionalAnalysisScreen.kt`**:
   - Upper screen: Display area with:
     - Quantity banner with name & SI unit
     - Hero dimension output ($[a] = L \cdot T^{-2}$)
     - Multi-line derivation card with step numbers, equations, and substitution
     - SI Base Dimensions matrix badges
   - Lower screen: Interactive `DimensionalKeypad`.
   - Single tap on any button updates the display instantaneously and records the entry to history.

4. **Verification**:
   - Run `compile_applet` and test suite `gradle :app:testDebugUnitTest`.
