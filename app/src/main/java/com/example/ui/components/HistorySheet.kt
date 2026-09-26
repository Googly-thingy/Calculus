package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CalculationEntity
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorySheet(
    historyList: List<CalculationEntity>,
    onSelectHistory: (CalculationEntity) -> Unit,
    onDeleteItem: (Int) -> Unit,
    onClearAll: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        scrimColor = DarkBackground.copy(alpha = 0.6f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Calculation History",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${historyList.size} stored entries",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }

                if (historyList.isNotEmpty()) {
                    TextButton(
                        onClick = onClearAll,
                        modifier = Modifier.testTag("clear_history_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Clear All",
                            tint = CoralRed,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Clear All", color = CoralRed, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (historyList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No calculations recorded yet.\nCompute expressions or inspect units to see them here.",
                        color = TextMuted,
                        fontSize = 14.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(historyList, key = { it.id }) { item ->
                        HistoryItemCard(
                            item = item,
                            onClick = {
                                onSelectHistory(item)
                                onDismiss()
                            },
                            onDelete = { onDeleteItem(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(
    item: CalculationEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dateStr = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(item.timestamp))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("history_item_${item.id}"),
        color = DarkSurfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = if (item.isDimensionQuery) ElectricCyanContainer else WarmAmberContainer,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (item.isDimensionQuery) "DIMENSION" else "MATH",
                            color = if (item.isDimensionQuery) ElectricCyan else WarmAmberLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = dateStr,
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.expression,
                    color = TextSecondary,
                    fontSize = 14.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "= ${item.result}",
                        color = if (item.isDimensionQuery) ElectricCyan else WarmAmber,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (!item.matchedQuantity.isNullOrEmpty()) {
                        Text(
                            text = "(${item.matchedQuantity})",
                            color = MintGreen,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(36.dp)
                    .testTag("delete_history_${item.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
