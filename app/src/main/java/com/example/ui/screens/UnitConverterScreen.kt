package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuantityCategory
import com.example.model.UnitDefinition
import com.example.ui.UnitConverterUiState
import com.example.ui.theme.*

@Composable
fun UnitConverterScreen(
    state: UnitConverterUiState,
    onCategoryChange: (QuantityCategory) -> Unit,
    onSourceUnitChange: (UnitDefinition) -> Unit,
    onTargetUnitChange: (UnitDefinition) -> Unit,
    onValueChange: (String) -> Unit,
    onSwapUnits: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Dimensionally Validated Unit Converter",
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "All conversions strictly preserve physical dimensions and SI invariants.",
                color = TextSecondary,
                fontSize = 13.sp
            )
        }

        // Category Chips
        item {
            ScrollableTabRow(
                selectedTabIndex = QuantityCategory.values().indexOf(state.selectedCategory),
                containerColor = DarkSurface,
                contentColor = ElectricCyan,
                edgePadding = 0.dp,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                QuantityCategory.values().forEach { cat ->
                    Tab(
                        selected = state.selectedCategory == cat,
                        onClick = { onCategoryChange(cat) },
                        text = { Text(cat.displayName, fontSize = 12.sp) }
                    )
                }
            }
        }

        // Conversion Card
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp)),
                color = DarkSurfaceVariant,
                tonalElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Dimension Pill
                    state.sourceUnit?.let { src ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Preserved Dimension",
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                            Surface(
                                color = ElectricCyanContainer,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = src.dimensions.toFormattedString(),
                                    color = ElectricCyan,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    // Source Input
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "From", color = TextSecondary, fontSize = 12.sp)
                        OutlinedTextField(
                            value = state.inputValue,
                            onValueChange = onValueChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("converter_value_input"),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricCyan,
                                unfocusedBorderColor = DarkSurfaceElevated,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedContainerColor = DarkSurface,
                                unfocusedContainerColor = DarkSurface
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Source Unit Chips
                        Text(text = "Select Source Unit:", color = TextMuted, fontSize = 11.sp)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(state.availableUnits) { unit ->
                                FilterChip(
                                    selected = state.sourceUnit == unit,
                                    onClick = { onSourceUnitChange(unit) },
                                    label = { Text("${unit.symbol} (${unit.name})", fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = DarkSurfaceElevated,
                                        labelColor = TextSecondary,
                                        selectedContainerColor = ElectricCyanContainer,
                                        selectedLabelColor = ElectricCyan
                                    )
                                )
                            }
                        }
                    }

                    // Swap Button
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        FilledTonalIconButton(
                            onClick = onSwapUnits,
                            colors = IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = DarkSurfaceElevated,
                                contentColor = WarmAmber
                            ),
                            modifier = Modifier.testTag("swap_units_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.SwapVert,
                                contentDescription = "Swap Units"
                            )
                        }
                    }

                    // Target Converted Result
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "To", color = TextSecondary, fontSize = 12.sp)
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = DarkSurface,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                            ) {
                                Text(
                                    text = state.convertedValue,
                                    color = WarmAmberLight,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.testTag("converter_converted_result")
                                )
                                state.targetUnit?.let { tgt ->
                                    Text(
                                        text = "${tgt.symbol} (${tgt.name})",
                                        color = TextSecondary,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }

                        // Target Unit Chips
                        Text(text = "Select Target Unit:", color = TextMuted, fontSize = 11.sp)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(state.availableUnits) { unit ->
                                FilterChip(
                                    selected = state.targetUnit == unit,
                                    onClick = { onTargetUnitChange(unit) },
                                    label = { Text("${unit.symbol} (${unit.name})", fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = DarkSurfaceElevated,
                                        labelColor = TextSecondary,
                                        selectedContainerColor = WarmAmberContainer,
                                        selectedLabelColor = WarmAmberLight
                                    )
                                )
                            }
                        }
                    }

                    // Ratio formula
                    if (state.conversionRatioText.isNotEmpty()) {
                        HorizontalDivider(color = DarkSurfaceElevated)
                        Text(
                            text = "Conversion Factor: ${state.conversionRatioText}",
                            color = TextMuted,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}
