package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.math.AngleMode
import com.example.ui.theme.*

@Composable
fun ScientificKeypad(
    angleMode: AngleMode,
    isSecondMode: Boolean,
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Memory & Quick Toggle Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyButton(
                text = "MC",
                color = FunctionKeyBg,
                textColor = TextSecondary,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f),
                onClick = onMemoryClear
            )
            KeyButton(
                text = "MR",
                color = FunctionKeyBg,
                textColor = TextSecondary,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f),
                onClick = onMemoryRecall
            )
            KeyButton(
                text = "M+",
                color = FunctionKeyBg,
                textColor = TextSecondary,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f),
                onClick = onMemoryAdd
            )
            KeyButton(
                text = "M-",
                color = FunctionKeyBg,
                textColor = TextSecondary,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f),
                onClick = onMemorySub
            )
            KeyButton(
                text = if (angleMode == AngleMode.DEGREE) "DEG" else "RAD",
                color = if (angleMode == AngleMode.DEGREE) WarmAmberContainer else ElectricCyanContainer,
                textColor = if (angleMode == AngleMode.DEGREE) WarmAmberLight else ElectricCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1.2f),
                onClick = onToggleAngle
            )
            KeyButton(
                text = "2nd",
                color = if (isSecondMode) ElectricCyan else FunctionKeyBg,
                textColor = if (isSecondMode) OnElectricCyan else ElectricCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1.2f),
                onClick = onToggleSecond
            )
        }

        // Scientific Row 1: Trig
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyButton(
                text = if (isSecondMode) "asin" else "sin",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(if (isSecondMode) "asin" else "sin") }
            )
            KeyButton(
                text = if (isSecondMode) "acos" else "cos",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(if (isSecondMode) "acos" else "cos") }
            )
            KeyButton(
                text = if (isSecondMode) "atan" else "tan",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(if (isSecondMode) "atan" else "tan") }
            )
            KeyButton(
                text = if (isSecondMode) "10^x" else "log",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(if (isSecondMode) "10^x" else "log") }
            )
            KeyButton(
                text = if (isSecondMode) "e^x" else "ln",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(if (isSecondMode) "e^x" else "ln") }
            )
        }

        // Scientific Row 2: Powers, Roots, Constants
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyButton(
                text = if (isSecondMode) "x^y" else "x²",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(if (isSecondMode) "x^y" else "x²") }
            )
            KeyButton(
                text = if (isSecondMode) "cbrt" else "√",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(if (isSecondMode) "cbrt" else "sqrt") }
            )
            KeyButton(
                text = "(",
                color = FunctionKeyBg,
                textColor = TextPrimary,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("(") }
            )
            KeyButton(
                text = ")",
                color = FunctionKeyBg,
                textColor = TextPrimary,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(")") }
            )
            KeyButton(
                text = "^",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("^") }
            )
        }

        // Keypad Grid: Row 3
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyButton(
                text = "π",
                color = FunctionKeyBg,
                textColor = WarmAmberLight,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("π") }
            )
            KeyButton(
                text = "e",
                color = FunctionKeyBg,
                textColor = WarmAmberLight,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("e") }
            )
            KeyButton(
                text = "!",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("!") }
            )
            KeyButton(
                text = "%",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("%") }
            )
            KeyButton(
                text = "AC",
                color = CoralRedContainer,
                textColor = CoralRed,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                onClick = onClear
            )
        }

        // Keypad Grid: Row 4 (7, 8, 9, ÷, DEL)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyButton(
                text = "7",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("7") }
            )
            KeyButton(
                text = "8",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("8") }
            )
            KeyButton(
                text = "9",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("9") }
            )
            KeyButton(
                text = "÷",
                color = OperatorKeyBg,
                textColor = WarmAmber,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("÷") }
            )
            KeyButton(
                text = "⌫",
                color = FunctionKeyBg,
                textColor = CoralRed,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f),
                onClick = onBackspace
            )
        }

        // Keypad Grid: Row 5 (4, 5, 6, ×, 1/x)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyButton(
                text = "4",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("4") }
            )
            KeyButton(
                text = "5",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("5") }
            )
            KeyButton(
                text = "6",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("6") }
            )
            KeyButton(
                text = "×",
                color = OperatorKeyBg,
                textColor = WarmAmber,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("×") }
            )
            KeyButton(
                text = "1/x",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("1/x") }
            )
        }

        // Keypad Grid: Row 6 (1, 2, 3, −, |x|)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyButton(
                text = "1",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("1") }
            )
            KeyButton(
                text = "2",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("2") }
            )
            KeyButton(
                text = "3",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("3") }
            )
            KeyButton(
                text = "−",
                color = OperatorKeyBg,
                textColor = WarmAmber,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("−") }
            )
            KeyButton(
                text = "|x|",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("abs") }
            )
        }

        // Keypad Grid: Row 7 (0, ., EXP, +, =)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeyButton(
                text = "0",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("0") }
            )
            KeyButton(
                text = ".",
                color = NumberKeyBg,
                textColor = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap(".") }
            )
            KeyButton(
                text = "EE",
                color = FunctionKeyBg,
                textColor = ElectricCyan,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("E") }
            )
            KeyButton(
                text = "+",
                color = OperatorKeyBg,
                textColor = WarmAmber,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                onClick = { onKeyTap("+") }
            )
            KeyButton(
                text = "=",
                color = ActionKeyBg,
                textColor = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                onClick = onCalculate
            )
        }
    }
}

@Composable
fun KeyButton(
    text: String,
    color: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: androidx.compose.ui.unit.TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Surface(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("key_$text"),
        color = color,
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        }
    }
}
