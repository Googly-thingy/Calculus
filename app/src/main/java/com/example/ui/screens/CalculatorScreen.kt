package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CalculatorUiState
import com.example.ui.components.ScientificKeypad
import com.example.ui.theme.*

@Composable
fun CalculatorScreen(
    state: CalculatorUiState,
    onKeyTap: (String) -> Unit,
    onBackspace: () -> Unit,
    onClear: () -> Unit,
    onCalculate: () -> Unit,
    onToggleAngle: () -> Unit,
    onToggleSecond: () -> Unit,
    onMemoryAdd: () -> Unit,
    onMemorySub: () -> Unit,
    onMemoryRecall: () -> Unit,
    onMemoryClear: () -> Unit,
    onOpenConstants: () -> Unit,
    onOpenHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Upper Display & Utility Toolbar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Toolbar: Status Badges and Quick Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = DarkSurfaceElevated,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("angle_mode_badge")
                    ) {
                        Text(
                            text = state.angleMode.name,
                            color = ElectricCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    if (state.memory != 0.0) {
                        Surface(
                            color = WarmAmberContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("memory_active_badge")
                        ) {
                            Text(
                                text = "M",
                                color = WarmAmberLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledTonalButton(
                        onClick = onOpenConstants,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = DarkSurfaceElevated,
                            contentColor = ElectricCyan
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp).testTag("open_constants_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Science,
                            contentDescription = "Constants",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("CONST", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }

                    FilledTonalButton(
                        onClick = onOpenHistory,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = DarkSurfaceElevated,
                            contentColor = TextPrimary
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp).testTag("open_history_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("HIST", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Math Expression & Result Screen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                // Expression display
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState, reverseScrolling = true),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = if (state.expression.isEmpty()) "0" else state.expression,
                        color = if (state.expression.isEmpty()) TextMuted else TextSecondary,
                        fontSize = 28.sp,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        modifier = Modifier.testTag("calc_expression_display")
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Evaluated result or live preview
                if (state.isError) {
                    Text(
                        text = state.errorMessage ?: "Error",
                        color = CoralRed,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End,
                        modifier = Modifier.testTag("calc_error_display")
                    )
                } else {
                    val displayResult = state.finalResult ?: state.liveResult
                    if (displayResult.isNotEmpty()) {
                        Text(
                            text = "= $displayResult",
                            color = if (state.finalResult != null) WarmAmber else ElectricCyan.copy(alpha = 0.85f),
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            modifier = Modifier.testTag("calc_result_display")
                        )
                    } else {
                        Spacer(modifier = Modifier.height(46.dp))
                    }
                }
            }
        }

        // Keypad Container
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            color = DarkSurface,
            tonalElevation = 4.dp
        ) {
            ScientificKeypad(
                angleMode = state.angleMode,
                isSecondMode = state.isSecondMode,
                onKeyTap = onKeyTap,
                onBackspace = onBackspace,
                onClear = onClear,
                onCalculate = onCalculate,
                onToggleAngle = onToggleAngle,
                onToggleSecond = onToggleSecond,
                onMemoryAdd = onMemoryAdd,
                onMemorySub = onMemorySub,
                onMemoryRecall = onMemoryRecall,
                onMemoryClear = onMemoryClear
            )
        }
    }
}
