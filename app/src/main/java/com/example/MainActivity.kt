package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.CalculatorViewModel
import com.example.ui.components.ConstantsDialog
import com.example.ui.components.HistorySheet
import com.example.ui.screens.CalculatorScreen
import com.example.ui.screens.DimensionalAnalysisScreen
import com.example.ui.screens.UnitConverterScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

enum class NavTab(val title: String, val testTag: String) {
    CALCULATOR("Calculator", "tab_calculator"),
    DIMENSIONS("Dimensions", "tab_dimensions"),
    CONVERTER("Converter", "tab_converter")
}

class MainActivity : ComponentActivity() {

    private val viewModel: CalculatorViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                var selectedTab by remember { mutableStateOf(NavTab.CALCULATOR) }
                var showConstantsDialog by remember { mutableStateOf(false) }
                var showHistorySheet by remember { mutableStateOf(false) }

                val calcState by viewModel.calcState.collectAsStateWithLifecycle()
                val dimState by viewModel.dimState.collectAsStateWithLifecycle()
                val convState by viewModel.convState.collectAsStateWithLifecycle()
                val historyList by viewModel.historyList.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBackground),
                    topBar = {
                        TopAppBar(
                            title = {
                                Column {
                                    Text(
                                        text = "Dimensio",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "Scientific & SI Dimensional Engine",
                                        fontSize = 11.sp,
                                        color = ElectricCyan
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { showConstantsDialog = true },
                                    modifier = Modifier.testTag("appbar_constants_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Science,
                                        contentDescription = "Physical Constants",
                                        tint = ElectricCyan
                                    )
                                }
                                IconButton(
                                    onClick = { showHistorySheet = true },
                                    modifier = Modifier.testTag("appbar_history_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.History,
                                        contentDescription = "Calculation History",
                                        tint = TextPrimary
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = DarkBackground,
                                titleContentColor = TextPrimary
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = DarkSurface,
                            modifier = Modifier
                                .windowInsetsPadding(WindowInsets.navigationBars)
                                .testTag("main_bottom_nav")
                        ) {
                            NavigationBarItem(
                                selected = selectedTab == NavTab.CALCULATOR,
                                onClick = { selectedTab = NavTab.CALCULATOR },
                                icon = {
                                    Icon(
                                        imageVector = if (selectedTab == NavTab.CALCULATOR) Icons.Filled.Calculate else Icons.Outlined.Calculate,
                                        contentDescription = "Calculator"
                                    )
                                },
                                label = { Text("Calculator", fontSize = 12.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DarkBackground,
                                    indicatorColor = ElectricCyan,
                                    unselectedIconColor = TextSecondary,
                                    selectedTextColor = ElectricCyan,
                                    unselectedTextColor = TextSecondary
                                ),
                                modifier = Modifier.testTag(NavTab.CALCULATOR.testTag)
                            )

                            NavigationBarItem(
                                selected = selectedTab == NavTab.DIMENSIONS,
                                onClick = { selectedTab = NavTab.DIMENSIONS },
                                icon = {
                                    Icon(
                                        imageVector = if (selectedTab == NavTab.DIMENSIONS) Icons.Filled.Science else Icons.Outlined.Science,
                                        contentDescription = "Dimensions"
                                    )
                                },
                                label = { Text("Dimensions", fontSize = 12.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DarkBackground,
                                    indicatorColor = ElectricCyan,
                                    unselectedIconColor = TextSecondary,
                                    selectedTextColor = ElectricCyan,
                                    unselectedTextColor = TextSecondary
                                ),
                                modifier = Modifier.testTag(NavTab.DIMENSIONS.testTag)
                            )

                            NavigationBarItem(
                                selected = selectedTab == NavTab.CONVERTER,
                                onClick = { selectedTab = NavTab.CONVERTER },
                                icon = {
                                    Icon(
                                        imageVector = if (selectedTab == NavTab.CONVERTER) Icons.Filled.ChangeCircle else Icons.Outlined.ChangeCircle,
                                        contentDescription = "Converter"
                                    )
                                },
                                label = { Text("Converter", fontSize = 12.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DarkBackground,
                                    indicatorColor = ElectricCyan,
                                    unselectedIconColor = TextSecondary,
                                    selectedTextColor = ElectricCyan,
                                    unselectedTextColor = TextSecondary
                                ),
                                modifier = Modifier.testTag(NavTab.CONVERTER.testTag)
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (selectedTab) {
                            NavTab.CALCULATOR -> {
                                CalculatorScreen(
                                    state = calcState,
                                    onKeyTap = { viewModel.onKeyInput(it) },
                                    onBackspace = { viewModel.onBackspace() },
                                    onClear = { viewModel.onClear() },
                                    onCalculate = { viewModel.onCalculate() },
                                    onToggleAngle = { viewModel.toggleAngleMode() },
                                    onToggleSecond = { viewModel.toggleSecondMode() },
                                    onMemoryAdd = { viewModel.memoryAdd() },
                                    onMemorySub = { viewModel.memorySub() },
                                    onMemoryRecall = { viewModel.memoryRecall() },
                                    onMemoryClear = { viewModel.memoryClear() },
                                    onOpenConstants = { showConstantsDialog = true },
                                    onOpenHistory = { showHistorySheet = true }
                                )
                            }
                            NavTab.DIMENSIONS -> {
                                DimensionalAnalysisScreen(
                                    state = dimState,
                                    onCategorySelect = { viewModel.selectKeypadCategory(it) },
                                    onQuantitySelect = { viewModel.selectDerivationQuantity(it) }
                                )
                            }
                            NavTab.CONVERTER -> {
                                UnitConverterScreen(
                                    state = convState,
                                    onCategoryChange = { viewModel.setConverterCategory(it) },
                                    onSourceUnitChange = { viewModel.setConverterSourceUnit(it) },
                                    onTargetUnitChange = { viewModel.setConverterTargetUnit(it) },
                                    onValueChange = { viewModel.onConverterValueChange(it) },
                                    onSwapUnits = { viewModel.swapConverterUnits() }
                                )
                            }
                        }

                        // Constants Dialog
                        if (showConstantsDialog) {
                            ConstantsDialog(
                                onDismiss = { showConstantsDialog = false },
                                onSelectConstant = { constItem ->
                                    viewModel.insertConstant(constItem.symbol, constItem.value)
                                }
                            )
                        }

                        // History Bottom Sheet
                        if (showHistorySheet) {
                            HistorySheet(
                                historyList = historyList,
                                onSelectHistory = { item ->
                                    viewModel.reuseHistoryItem(item)
                                },
                                onDeleteItem = { id ->
                                    viewModel.deleteHistoryItem(id)
                                },
                                onClearAll = {
                                    viewModel.clearAllHistory()
                                },
                                onDismiss = { showHistorySheet = false }
                            )
                        }
                    }
                }
            }
        }
    }
}
