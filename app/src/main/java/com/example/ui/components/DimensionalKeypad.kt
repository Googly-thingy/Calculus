package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DerivationCatalog
import com.example.model.QuantityCategory
import com.example.model.QuantityDerivation
import com.example.ui.theme.*

@Composable
fun DimensionalKeypad(
    selectedCategory: QuantityCategory,
    selectedQuantity: QuantityDerivation,
    onCategorySelect: (QuantityCategory) -> Unit,
    onQuantitySelect: (QuantityDerivation) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        QuantityCategory.MECHANICS to "Mechanics",
        QuantityCategory.ELECTROMAGNETISM to "Electricity",
        QuantityCategory.THERMODYNAMICS to "Heat",
        QuantityCategory.OPTICS_WAVES to "Waves",
        QuantityCategory.GENERAL to "SI Base"
    )

    val currentItems = DerivationCatalog.ALL_DERIVATIONS.filter { it.category == selectedCategory }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkSurface)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Paged Category Tab Bar
        ScrollableTabRow(
            selectedTabIndex = categories.indexOfFirst { it.first == selectedCategory }.coerceAtLeast(0),
            containerColor = DarkSurfaceElevated,
            contentColor = ElectricCyan,
            edgePadding = 4.dp,
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        ) {
            categories.forEach { (cat, title) ->
                Tab(
                    selected = selectedCategory == cat,
                    onClick = { onCategorySelect(cat) },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedCategory == cat) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedCategory == cat) ElectricCyan else TextSecondary
                        )
                    },
                    modifier = Modifier.testTag("dim_cat_${cat.name}")
                )
            }
        }

        // Calculator Buttons Grid (Chunks of 4 per row)
        val rows = currentItems.chunked(4)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            rows.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    rowItems.forEach { item ->
                        val isSelected = selectedQuantity.symbol == item.symbol
                        DimensionalKeyButton(
                            item = item,
                            isSelected = isSelected,
                            onClick = { onQuantitySelect(item) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Pad empty spots in incomplete rows so buttons stay aligned
                    val remaining = 4 - rowItems.size
                    for (i in 0 until remaining) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun DimensionalKeyButton(
    item: QuantityDerivation,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(54.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("dim_key_${item.symbol}"),
        color = if (isSelected) ElectricCyanContainer else NumberKeyBg,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = if (isSelected) 6.dp else 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp, horizontal = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main quantity symbol (clean and prominent)
            val displaySymbol = when (item.symbol) {
                "P_p" -> "P"
                "L_ind" -> "L"
                "T_temp" -> "T"
                "Q_heat" -> "Q"
                "c_sp" -> "c"
                "k_th" -> "k"
                "I_base" -> "I"
                else -> item.symbol
            }
            Text(
                text = displaySymbol,
                color = if (isSelected) ElectricCyan else TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                maxLines = 1
            )

            // Short descriptor (e.g. accel, volt, work, etc.)
            val shortLabel = when (item.symbol) {
                "v" -> "vel"
                "u" -> "v₀"
                "a" -> "accel"
                "g" -> "gravity"
                "F" -> "force"
                "p" -> "moment"
                "W" -> "work/E"
                "P_p" -> "power"
                "P" -> "press"
                "ρ" -> "density"
                "τ" -> "torque"
                "μ" -> "visc"
                "A" -> "area"
                "Vol" -> "volume"
                "I" -> "current"
                "q" -> "charge"
                "V" -> "volt"
                "R" -> "resist"
                "C" -> "capac"
                "L_ind" -> "induct"
                "B" -> "B-field"
                "Φ_B" -> "flux"
                "T_temp" -> "temp"
                "Q_heat" -> "heat"
                "S" -> "entropy"
                "c_sp" -> "spec-ht"
                "k_th" -> "conduct"
                "f" -> "freq"
                "ω" -> "ang-vel"
                "λ" -> "lambda"
                "Φ_v" -> "lum-flux"
                "E_v" -> "lux"
                "M" -> "mass"
                "L" -> "length"
                "T" -> "time"
                "I_base" -> "current"
                "Θ" -> "theta"
                "N" -> "mol"
                "J" -> "candela"
                else -> item.name.take(6)
            }
            Text(
                text = shortLabel,
                color = if (isSelected) WarmAmberLight else TextMuted,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}
