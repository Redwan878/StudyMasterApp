package com.porashona.studymaster.ui.compose.screens.tools

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.TableView
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.BoardQuestion
import com.porashona.studymaster.data.model.Formula
import com.porashona.studymaster.data.model.SyllabusChapter
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.GPACalculationResult
import com.porashona.studymaster.ui.compose.viewmodels.PeriodicTableElement
import com.porashona.studymaster.ui.compose.viewmodels.SubjectGradeInput
import com.porashona.studymaster.ui.compose.viewmodels.ToolsViewModel
import com.porashona.studymaster.ui.compose.viewmodels.UnitConversionResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

// ═══════════════════════════════════════════════════════════════════════════════
// ToolsScreen — Hub of 10 study tools with full sub-screens.
// All text in Bengali. Glassmorphic cards. Material 3.
// ═══════════════════════════════════════════════════════════════════════════════

enum class ToolScreen(val bnName: String, val icon: ImageVector, val color: Color) {
    FORMULA_BANK("ফর্মুলা ব্যাংক", Icons.Default.Functions, Primary),
    BOARD_QUESTIONS("বোর্ড প্রশ্ন", Icons.Default.TableView, Secondary),
    GPA_CALCULATOR("জিপিএ ক্যালকুলেটর", Icons.Default.Calculate, Chart3),
    SCIENTIFIC_CALCULATOR("সাইন্টিফিক ক্যালকুলেটর", Icons.Default.Calculate, Chart4),
    PERIODIC_TABLE("পর্যায় সারণি", Icons.Default.Science, Chart5),
    UNIT_CONVERTER("ইউনিট কনভার্টার", Icons.Default.Straighten, Chart6),
    FORMULA_SEARCH("ফর্মুলা সার্চ", Icons.Default.Search, Info),
    SCRATCHPAD("স্ক্র্যাচপ্যাড", Icons.Default.EditNote, StreakFire),
    WORKED_EXAMPLES("সমাধান উদাহরণ", Icons.Default.Description, Chart1),
    SYLLABUS_CHECKLIST("সিলেবাস চেকলিস্ট", Icons.Default.Checklist, Chart2),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(
    viewModel: ToolsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    var selectedTool by remember { mutableStateOf<ToolScreen?>(null) }

    if (selectedTool != null) {
        ToolDetailScreen(
            tool = selectedTool!!,
            viewModel = viewModel,
            onBack = { selectedTool = null },
        )
    } else {
        ToolsHubScreen(
            onToolSelected = { selectedTool = it },
            onNavigateBack = onNavigateBack,
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Tools Hub — grid of 10 tool cards
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolsHubScreen(
    onToolSelected: (ToolScreen) -> Unit,
    onNavigateBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("টুলস", style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরুন")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
        floatingActionButton = {
            // Global formula search FAB
            FloatingActionButton(
                onClick = { onToolSelected(ToolScreen.FORMULA_SEARCH) },
                containerColor = Primary,
            ) {
                Icon(Icons.Default.Search, contentDescription = "ফর্মুলা সার্চ")
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(ToolScreen.entries) { tool ->
                GlassmorphicCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    variant = GlassCardVariant.ELEVATED,
                    tint = tool.color,
                    onClick = { onToolSelected(tool) },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(tool.color.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                tool.icon,
                                contentDescription = null,
                                tint = tool.color,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                        Text(
                            tool.bnName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Tool Detail Screen Router
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolDetailScreen(
    tool: ToolScreen,
    viewModel: ToolsViewModel,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tool.bnName, style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরুন")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        }
    ) { paddingValues ->
        when (tool) {
            ToolScreen.FORMULA_BANK -> FormulaBankScreen(viewModel, Modifier.padding(paddingValues))
            ToolScreen.BOARD_QUESTIONS -> BoardQuestionsScreen(viewModel, Modifier.padding(paddingValues))
            ToolScreen.GPA_CALCULATOR -> GPACalculatorScreen(viewModel, Modifier.padding(paddingValues))
            ToolScreen.SCIENTIFIC_CALCULATOR -> ScientificCalculatorScreen(Modifier.padding(paddingValues))
            ToolScreen.PERIODIC_TABLE -> PeriodicTableScreen(viewModel, Modifier.padding(paddingValues))
            ToolScreen.UNIT_CONVERTER -> UnitConverterScreen(viewModel, Modifier.padding(paddingValues))
            ToolScreen.FORMULA_SEARCH -> FormulaSearchScreen(viewModel, Modifier.padding(paddingValues))
            ToolScreen.SCRATCHPAD -> ScratchpadScreen(Modifier.padding(paddingValues))
            ToolScreen.WORKED_EXAMPLES -> WorkedExamplesScreen(Modifier.padding(paddingValues))
            ToolScreen.SYLLABUS_CHECKLIST -> SyllabusChecklistScreen(viewModel, Modifier.padding(paddingValues))
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 1. Formula Bank
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FormulaBankScreen(
    viewModel: ToolsViewModel,
    modifier: Modifier = Modifier,
) {
    val formulas by viewModel.formulas.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var subjectFilter by rememberSaveable { mutableStateOf<Long?>(null) }
    var categoryFilter by rememberSaveable { mutableStateOf<String?>(null) }

    val categories = listOf(
        "KINEMATICS" to "গতিবিদ্যা",
        "DYNAMICS" to "বলবিদ্যা",
        "WAVES" to "তরঙ্গ",
        "ELECTRICITY" to "বিদ্যুৎ",
        "MAGNETISM" to "চুম্বকত্ব",
        "OPTICS" to "আলোকবিজ্ঞান",
        "THERMODYNAMICS" to "তাপগতিবিদ্যা",
        "MODERN_PHYSICS" to "আধুনিক পদার্থবিজ্ঞান",
        "ALGEBRA" to "বীজগণিত",
        "GEOMETRY" to "জ্যামিতি",
        "TRIGONOMETRY" to "ত্রিকোণমিতি",
        "CALCULUS" to "ক্যালকুলাস",
        "ORGANIC" to "জৈব রসায়ন",
        "INORGANIC" to "অজৈব রসায়ন",
        "PHYSICAL" to "ভৌত রসায়ন",
        "GENERAL_MATH" to "সাধারণ গণিত",
        "HIGHER_MATH" to "উচ্চতর গণিত",
    )

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.searchFormulas(it) },
            label = { Text("ফর্মুলা খুঁজুন") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
        )

        // Subject filter chips
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            FilterChip(
                selected = subjectFilter == null,
                onClick = {
                    subjectFilter = null
                    viewModel.setFormulaSubjectFilter(null)
                },
                label = { Text("সব", fontSize = 12.sp) },
            )
            subjects.forEach { subject ->
                FilterChip(
                    selected = subjectFilter == subject.id,
                    onClick = {
                        subjectFilter = if (subjectFilter == subject.id) null else subject.id
                        viewModel.setFormulaSubjectFilter(subjectFilter)
                    },
                    label = {
                        Text(
                            subject.name,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                )
            }
        }

        // Category filter chips
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            FilterChip(
                selected = categoryFilter == null,
                onClick = { categoryFilter = null },
                label = { Text("সব ক্যাটাগরি", fontSize = 12.sp) },
            )
            categories.forEach { (key, label) ->
                FilterChip(
                    selected = categoryFilter == key,
                    onClick = { categoryFilter = if (categoryFilter == key) null else key },
                    label = { Text(label, fontSize = 12.sp) },
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Formula list
        if (formulas.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("কোনো ফর্মুলা পাওয়া যায়নি", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(formulas, key = { it.id }) { formula ->
                    FormulaCard(formula = formula, onToggleFavorite = { viewModel.toggleFormulaFavorite(formula.id) })
                }
            }
        }
    }
}

@Composable
private fun FormulaCard(formula: Formula, onToggleFavorite: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        padding = 14.dp,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(formula.formulaText, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    if (formula.description.isNotEmpty()) {
                        Text(formula.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
                IconButton(onClick = onToggleFavorite, modifier = Modifier.size(36.dp)) {
                    Icon(
                        if (formula.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "ফেভারিট",
                        tint = if (formula.isFavorite) Secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                formula.subjectName?.let {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(6.dp),
                    ) {
                        Text(it, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Primary)
                    }
                }
                formula.chapterName?.let {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(6.dp),
                    ) {
                        Text(it, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            // Expandable MCQ shortcut section
            Text(
                "MCQ শর্টকাট ▾",
                style = MaterialTheme.typography.labelMedium,
                color = Primary,
                modifier = Modifier.clickable { expanded = !expanded },
            )
            AnimatedVisibility(visible = expanded, enter = expandVertically(), exit = shrinkVertically()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text("• ফর্মুলা মুখস্থ করুন এবং অনুশীলন করুন", style = MaterialTheme.typography.bodySmall)
                    Text("• প্রতিটি অধ্যায়ের গুরুত্বপূর্ণ MCQ টিপস নিচে", style = MaterialTheme.typography.bodySmall)
                    Text("• বোর্ড পরীক্ষায় প্রায়শই আসা প্রশ্নের ধরন লক্ষ্য করুন", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 2. Board Question Archive
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BoardQuestionsScreen(
    viewModel: ToolsViewModel,
    modifier: Modifier = Modifier,
) {
    val boardQuestions by viewModel.boardQuestions.collectAsState()
    val availableYears by viewModel.availableYears.collectAsState()
    val availableBoards by viewModel.availableBoards.collectAsState()
    var selectedYear by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedBoard by rememberSaveable { mutableStateOf<String?>(null) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedQuestion by remember { mutableStateOf<BoardQuestion?>(null) }

    LaunchedEffect(selectedYear, selectedBoard, searchQuery) {
        viewModel.getPreviousYear(
            selectedYear ?: 2024,
            selectedBoard,
        )
    }

    if (selectedQuestion != null) {
        AlertDialog(
            onDismissRequest = { selectedQuestion = null },
            title = { Text("প্রশ্ন বিস্তারিত") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("বছর: ${selectedQuestion!!.year}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    Text("বোর্ড: ${selectedQuestion!!.board}", style = MaterialTheme.typography.bodyMedium)
                    Text("অধ্যায়: ${selectedQuestion!!.chapterName ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
                    Text("নম্বর: ${selectedQuestion!!.marks}", style = MaterialTheme.typography.bodyMedium)
                    HorizontalDivider()
                    Text(selectedQuestion!!.questionText, style = MaterialTheme.typography.bodyLarge)
                }
            },
            confirmButton = { TextButton(onClick = { selectedQuestion = null }) { Text("বন্ধ করুন") } },
        )
    }

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it; viewModel.searchBoardQuestions(it) },
            label = { Text("প্রশ্ন খুঁজুন") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
        )

        // Year filter
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            FilterChip(
                selected = selectedYear == null,
                onClick = { selectedYear = null },
                label = { Text("সব", fontSize = 12.sp) },
            )
            availableYears.reversed().take(10).forEach { year ->
                FilterChip(
                    selected = selectedYear == year,
                    onClick = { selectedYear = year },
                    label = { Text(year.toString(), fontSize = 12.sp) },
                )
            }
        }

        // Board filter
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 4.dp),
        ) {
            FilterChip(
                selected = selectedBoard == null,
                onClick = { selectedBoard = null },
                label = { Text("সব বোর্ড", fontSize = 12.sp) },
            )
            availableBoards.forEach { board ->
                FilterChip(
                    selected = selectedBoard == board,
                    onClick = { selectedBoard = board },
                    label = { Text(board, fontSize = 12.sp) },
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (boardQuestions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("কোনো প্রশ্ন পাওয়া যায়নি", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(boardQuestions, key = { it.id }) { q ->
                    GlassmorphicCard(
                        modifier = Modifier.fillMaxWidth(),
                        variant = GlassCardVariant.OUTLINED,
                        padding = 12.dp,
                        onClick = { selectedQuestion = q },
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text("${q.year} | ${q.board}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                Text("${q.marks} নম্বর", style = MaterialTheme.typography.labelMedium, color = Primary)
                            }
                            Text(q.chapterName ?: "", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(q.questionText, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 3. GPA Calculator (Bangladesh 5.0 scale)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun GPACalculatorScreen(
    viewModel: ToolsViewModel,
    modifier: Modifier = Modifier,
) {
    var subjectEntries by remember { mutableStateOf(listOf(SubjectEntry())) }
    var has4thSubject by rememberSaveable { mutableStateOf(false) }
    val gpaResult by viewModel.gpaResult.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        // 4th subject toggle
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("৪র্থ বিষয় (ঐচ্ছিক)", style = MaterialTheme.typography.titleMedium)
            Switch(
                checked = has4thSubject,
                onCheckedChange = {
                    has4thSubject = it
                    if (!it && subjectEntries.size > 8) {
                        subjectEntries = subjectEntries.take(8)
                    }
                },
                colors = SwitchDefaults.colors(checkedTrackColor = Primary),
            )
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(subjectEntries.size) { index ->
                val entry = subjectEntries[index]
                SubjectEntryRow(
                    entry = entry,
                    index = index + 1,
                    is4thSubject = has4thSubject && index == subjectEntries.size - 1,
                    onNameChange = { name ->
                        subjectEntries = subjectEntries.toMutableList().apply { set(index, get(index).copy(name = name)) }
                    },
                    onMarksChange = { marks ->
                        val m = marks.toIntOrNull() ?: 0
                        subjectEntries = subjectEntries.toMutableList().apply {
                            set(index, get(index).copy(marks = m.coerceIn(0, 100)))
                        }
                    },
                    onRemove = {
                        subjectEntries = subjectEntries.toMutableList().apply { removeAt(index) }
                    },
                )
            }

            item {
                val maxSubjects = if (has4thSubject) 9 else 8
                if (subjectEntries.size < maxSubjects) {
                    OutlinedButton(
                        onClick = { subjectEntries = subjectEntries + SubjectEntry() },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("বিষয় যোগ করুন")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val inputs = subjectEntries.filter { it.name.isNotBlank() }.map { entry ->
                            SubjectGradeInput(entry.name, entry.marks.toDouble(), 0.0, "")
                        }
                        viewModel.calculateGPA(inputs)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                ) {
                    Text("GPA হিসাব করুন")
                }
            }

            // Result
            gpaResult?.let { result ->
                item {
                    GlassmorphicCard(
                        modifier = Modifier.fillMaxWidth(),
                        variant = GlassCardVariant.ELEVATED,
                        tint = Primary,
                        padding = 20.dp,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text("মোট GPA", style = MaterialTheme.typography.titleMedium)
                            Text(
                                result.gradePoint,
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                color = Primary,
                            )
                            Text(
                                "গ্রেড: ${result.letterGrade}",
                                style = MaterialTheme.typography.titleLarge,
                                color = Primary,
                            )
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            result.subjects.forEach { subject ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(subject.subjectName, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                                    Text(subject.letterGrade, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = Primary)
                                    Text("${subject.marks.toBengaliDigits()}", style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class SubjectEntry(
    val name: String = "",
    val marks: Int = 0,
)

@Composable
private fun SubjectEntryRow(
    entry: SubjectEntry,
    index: Int,
    is4thSubject: Boolean,
    onNameChange: (String) -> Unit,
    onMarksChange: (String) -> Unit,
    onRemove: () -> Unit,
) {
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        padding = 10.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("$index.", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, modifier = Modifier.width(24.dp))
            OutlinedTextField(
                value = entry.name,
                onValueChange = onNameChange,
                label = { Text("বিষয়") },
                singleLine = true,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                shape = RoundedCornerShape(8.dp),
                textStyle = MaterialTheme.typography.bodySmall,
            )
            OutlinedTextField(
                value = if (entry.marks > 0) entry.marks.toString() else "",
                onValueChange = onMarksChange,
                label = { Text("নম্বর") },
                singleLine = true,
                modifier = Modifier.width(80.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(8.dp),
                textStyle = MaterialTheme.typography.bodySmall,
            )
            if (is4thSubject) {
                Spacer(modifier = Modifier.width(4.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Secondary.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(6.dp),
                ) {
                    Text("৪র্থ", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Secondary)
                }
            }
            if (index > 1) {
                IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "সরান", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 4. Scientific Calculator
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ScientificCalculatorScreen(
    modifier: Modifier = Modifier,
) {
    var display by rememberSaveable { mutableStateOf("0") }
    var expression by rememberSaveable { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf<String>()) }
    var showHistory by remember { mutableStateOf(false) }
    var openParens by rememberSaveable { mutableIntStateOf(0) }
    var lastResult by rememberSaveable { mutableStateOf<Double?>(null) }

    if (showHistory) {
        AlertDialog(
            onDismissRequest = { showHistory = false },
            title = { Text("ইতিহাস") },
            text = {
                Column(
                    modifier = Modifier.height(300.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    if (history.isEmpty()) {
                        Text("কোনো ইতিহাস নেই", color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    } else {
                        history.reversed().forEach { entry ->
                            Text(entry, style = MaterialTheme.typography.bodyMedium, fontFamily = FontFamily.Monospace, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = {
                history = emptyList()
                showHistory = false
            }) { Text("মুছুন") } },
            dismissButton = { TextButton(onClick = { showHistory = false }) { Text("বন্ধ করুন") } },
        )
    }

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
        // Display
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth().height(120.dp),
            variant = GlassCardVariant.FILLED,
            tint = Primary,
            padding = 16.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    expression,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                )
                Text(
                    display,
                    style = MaterialTheme.typography.displaySmall.copy(fontFamily = FontFamily.Monospace),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        // History button
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { showHistory = true }) {
                Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("ইতিহাস", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Buttons grid
        val buttons = listOf(
            listOf("sin", "cos", "tan", "log", "ln", "√"),
            listOf("x²", "x³", "xⁿ", "(", ")", "π"),
            listOf("7", "8", "9", "÷", "⌫", "C"),
            listOf("4", "5", "6", "×", "%", "CE"),
            listOf("1", "2", "3", "-", "^", "="),
            listOf("0", ".", "Ans", "+", "!", "±"),
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                row.forEach { btn ->
                    val isFunc = btn in listOf("sin", "cos", "tan", "log", "ln", "√", "x²", "x³", "xⁿ", "π", "!", "Ans", "±")
                    val isOp = btn in listOf("+", "-", "×", "÷", "^", "%")
                    val isEquals = btn == "="
                    val isClear = btn in listOf("C", "CE", "⌫")
                    val bgColor = when {
                        isEquals -> Primary
                        isOp -> MaterialTheme.colorScheme.surfaceVariant
                        isClear -> Error.copy(alpha = 0.15f)
                        isFunc -> Chart5.copy(alpha = 0.15f)
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                    val textColor = when {
                        isEquals -> Color.White
                        isClear -> Error
                        isFunc -> Chart5
                        isOp -> Primary
                        else -> MaterialTheme.colorScheme.onSurface
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(bgColor)
                            .clickable {
                                val prev = display
                                when {
                                    btn == "C" -> { display = "0"; expression = ""; openParens = 0; lastResult = null }
                                    btn == "CE" -> { display = "0" }
                                    btn == "⌫" -> { if (display.length > 1) display = display.dropLast(1) else display = "0" }
                                    btn == "=" -> {
                                        try {
                                            val expr = display.replace("×", "*").replace("÷", "/").replace("π", "${Math.PI}").replace("√", "sqrt").replace("sin", "Math.sin").replace("cos", "Math.cos").replace("tan", "Math.tan").replace("log", "Math.log10").replace("ln", "Math.log").replace("^", "**")
                                            val result = evalExpression(expr)
                                            expression = "$display ="
                                            display = if (result == result.toLong().toDouble()) result.toLong().toString() else String.format("%.8f", result).trimEnd('0').trimEnd('.')
                                            history = history + "$expression $display"
                                            lastResult = result
                                        } catch (_: Exception) {
                                            display = "ত্রুটি"
                                        }
                                    }
                                    btn == "Ans" -> { lastResult?.let { display = if (display == "0") it.toString() else display + it.toString() } }
                                    btn == "±" -> { if (display != "0") display = if (display.startsWith("-")) display.drop(1) else "-$display" }
                                    btn == "π" -> { display = if (display == "0") Math.PI.toString() else display + Math.PI.toString() }
                                    btn == "!" -> {
                                        try { val n = display.toDouble().toInt(); display = factorial(n).toString() }
                                        catch (_: Exception) { display = "ত্রুটি" }
                                    }
                                    btn in listOf("sin", "cos", "tan") -> {
                                        display = if (display == "0") "$btn(" else "$display*$btn("
                                        openParens++
                                    }
                                    btn == "log" -> { display = if (display == "0") "log(" else "$display*log("; openParens++ }
                                    btn == "ln" -> { display = if (display == "0") "ln(" else "$display*ln("; openParens++ }
                                    btn == "√" -> { display = if (display == "0") "√(" else "$display*√("; openParens++ }
                                    btn == "x²" -> { display = "($display)^2" }
                                    btn == "x³" -> { display = "($display)^3" }
                                    btn == "xⁿ" -> { display = "$display^" }
                                    btn == "(" -> { display = if (display == "0") "(" else "$display*("; openParens++ }
                                    btn == ")" -> { display = "$display)"; openParens = (openParens - 1).coerceAtLeast(0) }
                                    display == "0" && btn !in listOf(".", "0") -> { display = btn }
                                    else -> { display += btn }
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            btn,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (isEquals) FontWeight.Bold else FontWeight.Normal,
                            color = textColor,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

private fun factorial(n: Int): Long = if (n <= 1) 1L else n * factorial(n - 1)

private fun evalExpression(expr: String): Double {
    // Simple recursive descent parser for basic math
    var pos = 0
    fun parseExpr(): Double {
        var left = parseTerm()
        while (pos < expr.length && expr[pos] in "+-") {
            val op = expr[pos++]
            val right = parseTerm()
            left = if (op == '+') left + right else left - right
        }
        return left
    }
    fun parseTerm(): Double {
        var left = parsePower()
        while (pos < expr.length && expr[pos] in "*/") {
            val op = expr[pos++]
            val right = parsePower()
            left = if (op == '*') left * right else left / right
        }
        return left
    }
    fun parsePower(): Double {
        var base = parseUnary()
        if (pos < expr.length && expr[pos] == '^') { pos++; val exp = parsePower(); return base.pow(exp) }
        return base
    }
    fun parseUnary(): Double {
        if (pos < expr.length && expr[pos] == '-') { pos++; return -parseAtom() }
        return parseAtom()
    }
    fun parseAtom(): Double {
        if (pos < expr.length && expr[pos] == '(') {
            pos++
            val val_ = parseExpr()
            if (pos < expr.length && expr[pos] == ')') pos++
            return val_
        }
        val start = pos
        while (pos < expr.length && (expr[pos].isDigit() || expr[pos] == '.')) pos++
        return if (start < pos) expr.substring(start, pos).toDouble() else 0.0
    }
    return parseExpr()
}

// ═══════════════════════════════════════════════════════════════════════════════
// 5. Periodic Table
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun PeriodicTableScreen(
    viewModel: ToolsViewModel,
    modifier: Modifier = Modifier,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val results by viewModel.periodicTableResult.collectAsState()
    var selectedElement by remember { mutableStateOf<PeriodicTableElement?>(null) }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) viewModel.searchPeriodicTable(searchQuery)
    }

    if (selectedElement != null) {
        AlertDialog(
            onDismissRequest = { selectedElement = null },
            title = { Text("${selectedElement!!.name} (${selectedElement!!.symbol})") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    InfoRow("বাংলা নাম", selectedElement!!.nameBn)
                    InfoRow("পারমাণবিক সংখ্যা", selectedElement!!.atomicNumber.toString())
                    InfoRow("পারমাণবিক ভর", selectedElement!!.atomicMass.toString())
                    InfoRow("বর্গ", selectedElement!!.category)
                }
            },
            confirmButton = { TextButton(onClick = { selectedElement = null }) { Text("বন্ধ করুন") } },
        )
    }

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("উপাদান খুঁজুন (বাংলা/English)") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
        )

        Spacer(modifier = Modifier.height(4.dp))

        val displayList = if (searchQuery.isEmpty()) {
            // Show all elements from ViewModel
            remember { mutableStateOf(listOf<PeriodicTableElement>()) }.value
        } else {
            results
        }

        if (displayList.isEmpty() && searchQuery.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("কোনো উপাদান পাওয়া যায়নি", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val elementsToShow = if (searchQuery.isEmpty()) {
                    // Default: load all
                    listOf(
                        PeriodicTableElement("H", "Hydrogen", "হাইড্রোজেন", 1, 1.008, "Non-metal"),
                        PeriodicTableElement("He", "Helium", "হিলিয়াম", 2, 4.003, "Noble Gas"),
                        PeriodicTableElement("Li", "Lithium", "লিথিয়াম", 3, 6.941, "Alkali Metal"),
                        PeriodicTableElement("Be", "Beryllium", "বেরিলিয়াম", 4, 9.012, "Alkaline Earth"),
                        PeriodicTableElement("B", "Boron", "বোরন", 5, 10.81, "Metalloid"),
                        PeriodicTableElement("C", "Carbon", "কার্বন", 6, 12.011, "Non-metal"),
                        PeriodicTableElement("N", "Nitrogen", "নাইট্রোজেন", 7, 14.007, "Non-metal"),
                        PeriodicTableElement("O", "Oxygen", "অক্সিজেন", 8, 15.999, "Non-metal"),
                        PeriodicTableElement("F", "Fluorine", "ফ্লোরিন", 9, 18.998, "Halogen"),
                        PeriodicTableElement("Ne", "Neon", "নিয়ন", 10, 20.180, "Noble Gas"),
                        PeriodicTableElement("Na", "Sodium", "সোডিয়াম", 11, 22.990, "Alkali Metal"),
                        PeriodicTableElement("Mg", "Magnesium", "ম্যাগনেসিয়াম", 12, 24.305, "Alkaline Earth"),
                        PeriodicTableElement("Al", "Aluminium", "অ্যালুমিনিয়াম", 13, 26.982, "Metal"),
                        PeriodicTableElement("Si", "Silicon", "সিলিকন", 14, 28.086, "Metalloid"),
                        PeriodicTableElement("P", "Phosphorus", "ফসফরাস", 15, 30.974, "Non-metal"),
                        PeriodicTableElement("S", "Sulfur", "সালফার", 16, 32.065, "Non-metal"),
                        PeriodicTableElement("Cl", "Chlorine", "ক্লোরিন", 17, 35.453, "Halogen"),
                        PeriodicTableElement("Ar", "Argon", "আর্গন", 18, 39.948, "Noble Gas"),
                        PeriodicTableElement("K", "Potassium", "পটাসিয়াম", 19, 39.098, "Alkali Metal"),
                        PeriodicTableElement("Ca", "Calcium", "ক্যালসিয়াম", 20, 40.078, "Alkaline Earth"),
                        PeriodicTableElement("Fe", "Iron", "লোহা", 26, 55.845, "Transition Metal"),
                        PeriodicTableElement("Cu", "Copper", "তামা", 29, 63.546, "Transition Metal"),
                        PeriodicTableElement("Zn", "Zinc", "জিংক", 30, 65.38, "Transition Metal"),
                        PeriodicTableElement("Ag", "Silver", "রূপা", 47, 107.868, "Transition Metal"),
                        PeriodicTableElement("Au", "Gold", "সোনা", 79, 196.967, "Transition Metal"),
                        PeriodicTableElement("Hg", "Mercury", "পারদ", 80, 200.592, "Transition Metal"),
                        PeriodicTableElement("Pb", "Lead", "সীসা", 82, 207.2, "Metal"),
                        PeriodicTableElement("U", "Uranium", "ইউরেনিয়াম", 92, 238.029, "Actinide"),
                    )
                } else displayList

                items(elementsToShow) { element ->
                    ElementCard(
                        element = element,
                        onClick = { selectedElement = element },
                    )
                }
            }
        }
    }
}

@Composable
private fun ElementCard(element: PeriodicTableElement, onClick: () -> Unit) {
    val categoryColor = when (element.category) {
        "Non-metal" -> Chart3
        "Noble Gas" -> Primary
        "Alkali Metal" -> Secondary
        "Alkaline Earth" -> Chart4
        "Transition Metal" -> Chart6
        "Metalloid" -> Chart5
        "Halogen" -> StreakFire
        "Actinide" -> Error
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.OUTLINED,
        padding = 12.dp,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Symbol circle
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(categoryColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    element.symbol,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = categoryColor,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(element.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                Text(element.nameBn, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(element.atomicNumber.toString(), style = MaterialTheme.typography.labelMedium, fontFamily = FontFamily.Monospace)
                Text(element.atomicMass.toString(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontFamily = FontFamily.Monospace)
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 6. Unit Converter
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun UnitConverterScreen(
    viewModel: ToolsViewModel,
    modifier: Modifier = Modifier,
) {
    var selectedCategory by rememberSaveable { mutableStateOf("Length") }
    var inputValue by rememberSaveable { mutableStateOf("") }
    var fromUnit by rememberSaveable { mutableStateOf("") }
    var toUnit by rememberSaveable { mutableStateOf("") }
    val result by viewModel.unitConversionResult.collectAsState()

    val unitCategories = mapOf(
        "Length" to mapOf("m" to "মিটার", "cm" to "সেন্টিমিটার", "km" to "কিলোমিটার", "mm" to "মিলিমিটার", "inch" to "ইঞ্চি", "feet" to "ফুট"),
        "Mass" to mapOf("kg" to "কিলোগ্রাম", "g" to "গ্রাম", "mg" to "মিলিগ্রাম", "pound" to "পাউন্ড"),
        "Temperature" to mapOf("celsius" to "সেলসিয়াস", "fahrenheit" to "ফারেনহাইট"),
        "Time" to mapOf("hour" to "ঘণ্টা", "min" to "মিনিট", "sec" to "সেকেন্ড"),
        "Volume" to mapOf("liter" to "লিটার", "ml" to "মিলিলিটার"),
    )

    val currentUnits = unitCategories[selectedCategory] ?: emptyMap()
    LaunchedEffect(currentUnits) {
        val keys = currentUnits.keys.toList()
        if (keys.size >= 2) {
            if (fromUnit !in keys) fromUnit = keys[0]
            if (toUnit !in keys) toUnit = keys[1]
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        // Category selector
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            unitCategories.keys.toList().forEachIndexed { index, category ->
                SegmentedButton(
                    selected = selectedCategory == category,
                    onClick = {
                        selectedCategory = category
                        inputValue = ""
                    },
                    shape = SegmentedButtonDefaults.itemShape(index, unitCategories.size),
                ) {
                    Text(when (category) {
                        "Length" -> "দৈর্ঘ্য"
                        "Mass" -> "ভর"
                        "Temperature" -> "তাপমাত্রা"
                        "Time" -> "সময়"
                        "Volume" -> "আয়তন"
                        else -> category
                    }, fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // From unit
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth(),
            variant = GlassCardVariant.OUTLINED,
            padding = 16.dp,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("থেকে:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = inputValue,
                        onValueChange = { inputValue = it.filter { c -> c.isDigit() || c == '.' } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Monospace),
                    )
                    Column {
                        currentUnits.entries.forEach { (key, label) ->
                            FilterChip(
                                selected = fromUnit == key,
                                onClick = { fromUnit = key },
                                label = { Text(label, fontSize = 11.sp) },
                                modifier = Modifier.padding(vertical = 1.dp),
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Swap button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            FilledTonalButton(
                onClick = { val tmp = fromUnit; fromUnit = toUnit; toUnit = tmp },
            ) {
                Text("⇅ বিনিময়")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // To unit
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth(),
            variant = GlassCardVariant.OUTLINED,
            padding = 16.dp,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("এ:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        result?.output ?: "—",
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Monospace),
                        color = Primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                    )
                    Column {
                        currentUnits.entries.forEach { (key, label) ->
                            FilterChip(
                                selected = toUnit == key,
                                onClick = { toUnit = key },
                                label = { Text(label, fontSize = 11.sp) },
                                modifier = Modifier.padding(vertical = 1.dp),
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Convert button
        Button(
            onClick = {
                val val_ = inputValue.toDoubleOrNull() ?: return@Button
                viewModel.convertUnit(val_, fromUnit, toUnit)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = inputValue.isNotBlank() && fromUnit.isNotBlank() && toUnit.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
        ) {
            Text("রূপান্তর করুন")
        }

        // Formula display
        if (result != null) {
            Spacer(modifier = Modifier.height(8.dp))
            GlassmorphicCard(
                modifier = Modifier.fillMaxWidth(),
                variant = GlassCardVariant.FILLED,
                tint = Info,
                padding = 12.dp,
            ) {
                Text(
                    "সূত্র: ${result!!.fromUnit} → ${result!!.toUnit}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Info,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 7. Formula Search (Global)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun FormulaSearchScreen(
    viewModel: ToolsViewModel,
    modifier: Modifier = Modifier,
) {
    val formulas by viewModel.formulas.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(query) { viewModel.searchFormulas(query) }

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("সব বিষয়ে ফর্মুলা খুঁজুন...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
        )

        Text(
            "${formulas.size.toBengaliDigits()} টি ফর্মুলা পাওয়া গেছে",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        if (formulas.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("ফর্মুলা খুঁজতে টাইপ করুন", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(formulas, key = { it.id }) { formula ->
                    GlassmorphicCard(
                        modifier = Modifier.fillMaxWidth(),
                        variant = GlassCardVariant.OUTLINED,
                        padding = 12.dp,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(formula.formulaText, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                            if (formula.description.isNotEmpty()) {
                                Text(formula.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                formula.subjectName?.let {
                                    Card(colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.15f)), shape = RoundedCornerShape(6.dp)) {
                                        Text(it, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Primary)
                                    }
                                }
                                formula.chapterName?.let {
                                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), shape = RoundedCornerShape(6.dp)) {
                                        Text(it, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 8. Quick Scratchpad
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ScratchpadScreen(modifier: Modifier = Modifier) {
    var content by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("দ্রুত নোট", style = MaterialTheme.typography.titleMedium)
            OutlinedButton(onClick = { content = "" }) {
                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("মুছুন", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier.fillMaxSize(),
            placeholder = { Text("এখানে লিখুন... স্থানীয়ভাবে সংরক্ষিত হবে") },
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = RoundedCornerShape(16.dp),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// 9. Worked Examples
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun WorkedExamplesScreen(modifier: Modifier = Modifier) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val examples = listOf(
        WorkedExample("ভেক্টর যোগফল", "পদার্থবিজ্ঞান", listOf("দুটি ভেক্টর A = (3, 4) ও B = (1, 2) দেওয়া আছে।", "A + B = (3+1, 4+2) = (4, 6)", "|A + B| = √(4² + 6²) = √(16+36) = √52 ≈ 7.21")),
        WorkedExample("অনুপাতী সীমা", "উচ্চতর গণিত", listOf("সীমা নির্ণয় করুন: lim(x→2) (x²-4)/(x-2)", "উপরে সূত্র: x²-4 = (x-2)(x+2)", "= lim(x→2) (x+2) = 4")),
        WorkedExample("ভর-সংরক্ষণ", "রসায়ন", listOf("সমীকরণ ভারসাম্য করুন: Fe + O₂ → Fe₂O₃", "4Fe + 3O₂ → 2Fe₂O₃", "বামপক্ষ: Fe=4, O=6 | ডানপক্ষ: Fe=4, O=6 ✓")),
        WorkedExample("সূক্ষ্মকোণী ত্রিকোণমিতি", "সাধারণ গণিত", listOf("∠A = 30° এবং অতিভুজ = 10, বিপরীত বাহু নির্ণয় করুন", "sin 30° = বিপরীত/অতিভুজ", "বিপরীত = 10 × sin 30° = 10 × 0.5 = 5")),
        WorkedExample("গ্যাসের সমীকরণ", "রসায়ন", listOf("PV = nRT ব্যবহার করে আয়তন নির্ণয় করুন", "দেওয়া: P=2atm, n=1mol, T=300K, R=0.082", "V = nRT/P = (1×0.082×300)/2 = 12.3 L")),
    )

    val filtered = if (searchQuery.isBlank()) examples else examples.filter { it.title.contains(searchQuery, ignoreCase = true) || it.subject.contains(searchQuery, ignoreCase = true) }

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("টপিক খুঁজুন") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(filtered) { example ->
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    variant = GlassCardVariant.OUTLINED,
                    padding = 14.dp,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(example.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Card(colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.15f)), shape = RoundedCornerShape(6.dp)) {
                                Text(example.subject, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = Primary)
                            }
                        }
                        example.steps.forEachIndexed { index, step ->
                            Row(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    "${index + 1}.",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary,
                                    modifier = Modifier.width(20.dp),
                                )
                                Text(step, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class WorkedExample(
    val title: String,
    val subject: String,
    val steps: List<String>,
)

// ═══════════════════════════════════════════════════════════════════════════════
// 10. Syllabus Checklist
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SyllabusChecklistScreen(
    viewModel: ToolsViewModel,
    modifier: Modifier = Modifier,
) {
    val chapters by viewModel.chapters.collectAsState()
    var examTypeFilter by rememberSaveable { mutableStateOf("SSC") }

    val filteredChapters = chapters.filter { it.examType == examTypeFilter }
    val completedCount = filteredChapters.count { it.status == "COMPLETED" }
    val totalCount = filteredChapters.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        // Exam type filter
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            listOf("SSC" to "SSC ২০২৬", "HSC" to "HSC ২০২৬").forEachIndexed { index, (type, label) ->
                SegmentedButton(
                    selected = examTypeFilter == type,
                    onClick = { examTypeFilter = type },
                    shape = SegmentedButtonDefaults.itemShape(index, 2),
                ) { Text(label) }
            }
        }

        // Overall progress
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            variant = GlassCardVariant.ELEVATED,
            tint = Primary,
            padding = 16.dp,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("সামগ্রিক অগ্রগতি", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        "${completedCount.toBengaliDigits()}/${totalCount.toBengaliDigits()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Primary,
                    )
                }
                // Progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Primary),
                    )
                }
                Text(
                    "${(progress * 100).toInt().toBengaliDigits()}% সম্পন্ন",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        // Chapter list
        if (filteredChapters.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("কোনো অধ্যায় পাওয়া যায়নি", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(filteredChapters, key = { it.id }) { chapter ->
                    val statusColor = when (chapter.status) {
                        "COMPLETED" -> Success
                        "IN_PROGRESS" -> Warning
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    val statusText = when (chapter.status) {
                        "COMPLETED" -> "সম্পন্ন ✓"
                        "IN_PROGRESS" -> "চলমান..."
                        else -> "শুরু হয়নি"
                    }
                    val chapterProgress = if (chapter.totalTopics > 0) chapter.completedTopics.toFloat() / chapter.totalTopics else 0f

                    GlassmorphicCard(
                        modifier = Modifier.fillMaxWidth(),
                        variant = GlassCardVariant.OUTLINED,
                        padding = 12.dp,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    "অধ্যায় ${chapter.chapterNumber}: ${chapter.chapterName}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.weight(1f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    statusText,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = statusColor,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            // Mini progress bar
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(chapterProgress)
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(statusColor),
                                )
                            }
                            if (chapter.totalTopics > 0) {
                                Text(
                                    "টপিক: ${chapter.completedTopics.toBengaliDigits()}/${chapter.totalTopics.toBengaliDigits()}${if (chapter.isShortSyllabus) " (সংক্ষিপ্ত)" else ""}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Shared utility
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}