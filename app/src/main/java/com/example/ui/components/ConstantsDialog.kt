package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.math.PhysicalConstant
import com.example.math.PhysicalConstants
import com.example.ui.theme.*

@Composable
fun ConstantsDialog(
    onDismiss: () -> Unit,
    onSelectConstant: (PhysicalConstant) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .clip(RoundedCornerShape(20.dp))
                .testTag("constants_dialog"),
            color = DarkSurface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Physical Constants",
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Standard SI Values & Dimensions",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_constants_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(PhysicalConstants.CONSTANTS) { constItem ->
                        ConstantItemCard(
                            constant = constItem,
                            onClick = {
                                onSelectConstant(constItem)
                                onDismiss()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConstantItemCard(
    constant: PhysicalConstant,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("constant_${constant.symbol}"),
        color = DarkSurfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ElectricCyanContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = constant.symbol,
                        color = ElectricCyan,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column {
                    Text(
                        text = constant.name,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${constant.value} ${constant.unit}".trim(),
                        color = WarmAmberLight,
                        fontSize = 12.sp
                    )
                }
            }

            if (constant.dimensionalFormula.isNotEmpty()) {
                Surface(
                    color = DarkSurfaceElevated,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "[${constant.dimensionalFormula}]",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
