package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DimensionVector
import com.example.model.QuantityCategory
import com.example.model.QuantityDerivation
import com.example.ui.DimensionalUiState
import com.example.ui.components.DimensionalKeypad
import com.example.ui.theme.*

@Composable
fun DimensionalAnalysisScreen(
    state: DimensionalUiState,
    onCategorySelect: (QuantityCategory) -> Unit,
    onQuantitySelect: (QuantityDerivation) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val item = state.selectedQuantity

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Upper Calculator Display Screen Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AnimatedContent(
                targetState = item,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "derivation_display_anim"
            ) { targetItem ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // 1. Quantity Identity Bar
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .testTag("dim_display_header"),
                        color = DarkSurfaceVariant
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    color = ElectricCyanContainer,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = targetItem.symbol,
                                        color = ElectricCyan,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        text = targetItem.name,
                                        color = TextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "SI Unit: ${targetItem.siUnit}",
                                        color = WarmAmberLight,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Surface(
                                color = DarkSurfaceElevated,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = targetItem.baseFormula,
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    // 2. Hero Dimension Result Display
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .testTag("dim_hero_result_screen"),
                        color = DarkSurface,
                        tonalElevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = "PHYSICAL DIMENSION",
                                color = TextMuted,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = "[${targetItem.symbol}] = ${targetItem.finalDimension.toFormulaNotation()}",
                                    color = ElectricCyan,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.testTag("dim_hero_formula_text")
                                )

                                Text(
                                    text = targetItem.finalDimension.toAsciiFormula(),
                                    color = WarmAmberLight,
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    // 3. Step-by-Step Derivation Progression Card
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .testTag("dim_derivation_card"),
                        color = DarkSurfaceVariant
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Derivation Progression (Physical Steps)",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )

                            targetItem.steps.forEach { step ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Surface(
                                        modifier = Modifier.size(20.dp),
                                        color = ElectricCyanContainer,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "${step.stepNumber}",
                                                color = ElectricCyan,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = step.formula,
                                            color = if (step.stepNumber == targetItem.steps.size) WarmAmberLight else TextPrimary,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            fontFamily = FontFamily.Monospace
                                        )
                                        Text(
                                            text = step.explanation,
                                            color = TextMuted,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 4. SI Base Dimensions Matrix Bar
                    SiBaseMatrixBar(vector = targetItem.finalDimension)
                }
            }
        }

        // Lower Calculator Keypad
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
            color = DarkSurface,
            tonalElevation = 8.dp
        ) {
            DimensionalKeypad(
                selectedCategory = state.selectedCategory,
                selectedQuantity = state.selectedQuantity,
                onCategorySelect = onCategorySelect,
                onQuantitySelect = onQuantitySelect
            )
        }
    }
}

@Composable
fun SiBaseMatrixBar(vector: DimensionVector) {
    val items = listOf(
        "M" to vector.m,
        "L" to vector.l,
        "T" to vector.t,
        "I" to vector.i,
        "Θ" to vector.theta,
        "N" to vector.n,
        "J" to vector.j
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        color = DarkSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SI Base:",
                color = TextMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )

            items.forEach { (sym, exp) ->
                val isNonZero = exp != 0
                Surface(
                    color = if (isNonZero) ElectricCyanContainer else DarkSurfaceElevated,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "$sym${if (isNonZero) DimensionVector.toSuperscript(exp) else "⁰"}",
                        color = if (isNonZero) ElectricCyan else TextMuted,
                        fontSize = 11.sp,
                        fontWeight = if (isNonZero) FontWeight.Bold else FontWeight.Normal,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
