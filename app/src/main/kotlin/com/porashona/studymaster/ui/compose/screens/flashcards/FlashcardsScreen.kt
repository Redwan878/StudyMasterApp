
package com.porashona.studymaster.ui.compose.screens.flashcards

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.dao.FlashcardDao
import com.porashona.studymaster.data.model.Flashcard
import com.porashona.studymaster.data.model.FlashcardDeck
import com.porashona.studymaster.data.model.FlashcardDifficulty
import com.porashona.studymaster.ui.compose.components.GlassElevatedCard
import com.porashona.studymaster.ui.compose.components.GlassFilledCard
import com.porashona.studymaster.ui.compose.components.GlassOutlinedCard
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.DeckStats
import com.porashona.studymaster.ui.compose.viewmodels.FlashcardEvent
import com.porashona.studymaster.ui.compose.viewmodels.FlashcardViewModel
import com.porashona.studymaster.ui.compose.viewmodels.ReviewStats
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

// ═══════════════════════════════════════════════════════════════════════════════
// Occlusion Rectangle — for image occlusion cards
// ═══════════════════════════════════════════════════════════════════════════════

data class OcclusionRect(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
)

// ═══════════════════════════════════════════════════════════════════════════════
// Main FlashcardsScreen — Deck List
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardsScreen(
    viewModel: FlashcardViewModel = hiltViewModel(),
) {
    val decks by viewModel.decks.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val currentDeckId by viewModel.currentDeckId.collectAsState()
    val reviewActive by viewModel.reviewSessionActive.collectAsState()
    val currentCard by viewModel.currentCard.collectAsState()
    val currentCardIndex by viewModel.currentCardIndex.collectAsState()
    val dueCards by viewModel.dueCards.collectAsState()
    val reviewStats by viewModel.reviewStats.collectAsState()
    val deckStats by viewModel.deckStats.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scope = rememberCoroutineScope()

    var showAddDeckDialog by rememberSaveable { mutableStateOf(false) }
    var showAddCardDialog by rememberSaveable { mutableStateOf(false) }
    var showImportDialog by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showReviewComplete by rememberSaveable { mutableStateOf(false) }
    var showExportDialog by rememberSaveable { mutableStateOf(false) }
    var showQuickAddDialog by rememberSaveable { mutableStateOf(false) }

    // Event handling
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is FlashcardEvent.DeckCreated -> {
                    snackbarHostState.showSnackbar("ডেক তৈরি হয়েছে!", duration = SnackbarDuration.Short)
                }
                is FlashcardEvent.DeckDeleted -> {
                    snackbarHostState.showSnackbar("ডেক মুছে ফেলা হয়েছে", duration = SnackbarDuration.Short)
                }
                is FlashcardEvent.ReviewCompleted -> {
                    showReviewComplete = true
                }
                is FlashcardEvent.NoDueCards -> {
                    snackbarHostState.showSnackbar("এখন কোনো কার্ড রিভিউয়ের জন্য নেই!", duration = SnackbarDuration.Short)
                }
                is FlashcardEvent.CardsAddedFromNote -> {
                    snackbarHostState.showSnackbar(
                        "${event.count.toBengaliDigits()}টি কার্ড নোট থেকে যোগ হয়েছে!",
                        duration = SnackbarDuration.Short
                    )
                }
                is FlashcardEvent.QuickAddFailed -> {
                    snackbarHostState.showSnackbar(event.reason, duration = SnackbarDuration.Long)
                }
                is FlashcardEvent.ExportSuccess -> {
                    snackbarHostState.showSnackbar(
                        "${event.cardCount.toBengaliDigits()}টি কার্ড এক্সপোর্ট হয়েছে!",
                        duration = SnackbarDuration.Short
                    )
                }
                is FlashcardEvent.ExportFailed -> {
                    snackbarHostState.showSnackbar("এক্সপোর্ট ব্যর্থ: ${event.error}", duration = SnackbarDuration.Long)
                }
                is FlashcardEvent.ImportSuccess -> {
                    snackbarHostState.showSnackbar(
                        "${event.cardCount.toBengaliDigits()}টি কার্ড ইম্পোর্ট হয়েছে!",
                        duration = SnackbarDuration.Short
                    )
                }
                is FlashcardEvent.ImportFailed -> {
                    snackbarHostState.showSnackbar("ইম্পোর্ট ব্যর্থ: ${event.error}", duration = SnackbarDuration.Long)
                }
                is FlashcardEvent.ReviewStarted -> {
                    // Start review session UI
                }
            }
        }
    }

    // ── Review Session Active — Full Screen Review ─────────────────────────
    if (reviewActive) {
        ReviewSessionScreen(
            viewModel = viewModel,
            currentCard = currentCard,
            currentCardIndex = currentCardIndex,
            totalCards = dueCards.size,
            reviewStats = reviewStats,
            onEndReview = {
                viewModel.endReview()
            },
        )
        return
    }

    // ── Review Complete Dialog ──────────────────────────────────────────────
    if (showReviewComplete) {
        ReviewCompleteDialog(
            stats = reviewStats,
            onDismiss = { showReviewComplete = false },
        )
    }

    // ── Deck Detail View ───────────────────────────────────────────────────
    if (currentDeckId != null) {
        DeckDetailScreen(
            viewModel = viewModel,
            onBack = { /* Clear deck selection handled by parent navigation */ },
            onAddCard = { showAddCardDialog = true },
            onExport = { showExportDialog = true },
            onQuickAdd = { showQuickAddDialog = true },
        )
        return
    }

    // ── Main Deck List ─────────────────────────────────────────────────────
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(
                        text = "ফ্ল্যাশকার্ড",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                actions = {
                    IconButton(onClick = { showImportDialog = true }) {
                        Icon(
                            Icons.Default.FileUpload,
                            contentDescription = "ইম্পোর্ট",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                ),
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDeckDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = OnPrimary,
                shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
            ) {
                Icon(Icons.Default.Add, contentDescription = "ডেক তৈরি করুন")
            }
        },
    ) { innerPadding ->
        val filteredDecks = remember(decks, searchQuery) {
            if (searchQuery.isBlank()) decks
            else decks.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }

        if (filteredDecks.isEmpty() && decks.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                FlashcardEmptyState(onAdd = { showAddDeckDialog = true })
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                // Search bar
                item(key = "search") {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("ডেক খুঁজুন...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "মুছুন")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        ),
                        singleLine = true,
                    )
                }

                // Stats summary
                item(key = "stats_summary") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        StatChip(
                            label = "মোট ডেক",
                            value = decks.size.toBengaliDigits(),
                            color = Primary,
                        )
                        StatChip(
                            label = "মোট কার্ড",
                            value = decks.sumOf { it.cardCount }.toBengaliDigits(),
                            color = Secondary,
                        )
                        val totalDue = remember(decks) { 0 } // Would need per-deck due count
                        StatChip(
                            label = "রিভিউ বাকি",
                            value = totalDue.toBengaliDigits(),
                            color = Warning,
                        )
                    }
                }

                // Deck cards
                if (filteredDecks.isEmpty()) {
                    item(key = "search_empty") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "\"$searchQuery\" নামে কোনো ডেক পাওয়া যায়নি",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                } else {
                    items(filteredDecks, key = { it.id }) { deck ->
                        val dueCount = remember(deck) { 0 } // Would need async
                        DeckCard(
                            deck = deck,
                            dueCount = dueCount,
                            onClick = {
                                viewModel.selectDeck(deck.id)
                            },
                            onDelete = {
                                viewModel.deleteDeck(deck.id)
                            },
                            onReview = {
                                viewModel.selectDeck(deck.id)
                                viewModel.startReview(deck.id)
                            },
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    // ── Dialogs ─────────────────────────────────────────────────────────────
    if (showAddDeckDialog) {
        AddDeckDialog(
            subjects = subjects,
            onDismiss = { showAddDeckDialog = false },
            onAdd = { name, subjectId, subjectName, colorHex, description ->
                viewModel.createDeck(
                    name = name,
                    subjectId = subjectId,
                    subjectName = subjectName,
                    colorHex = colorHex,
                    description = description,
                )
                showAddDeckDialog = false
            }
        )
    }

    if (showAddCardDialog) {
        AddCardDialog(
            deckId = currentDeckId ?: return,
            onDismiss = { showAddCardDialog = false },
            onAdd = { front, back, imageUrl, audioPath ->
                viewModel.addCard(front, back, currentDeckId!!, imageUrl)
                showAddCardDialog = false
            }
        )
    }

    if (showImportDialog && currentDeckId != null) {
        ImportDeckDialog(
            onDismiss = { showImportDialog = false },
            onImport = { jsonString ->
                viewModel.importDeck(currentDeckId!!, jsonString)
                showImportDialog = false
            }
        )
    } else if (showImportDialog) {
        ImportDeckDialog(
            onDismiss = { showImportDialog = false },
            onImport = { /* no deck selected */ },
        )
    }

    if (showExportDialog && currentDeckId != null) {
        ExportDeckDialog(
            deckId = currentDeckId!!,
            viewModel = viewModel,
            onDismiss = { showExportDialog = false },
        )
    }

    if (showQuickAddDialog && currentDeckId != null) {
        QuickAddFromNoteDialog(
            onDismiss = { showQuickAddDialog = false },
            onAdd = { noteId ->
                viewModel.quickAddFromNote(noteId, currentDeckId!!)
                showQuickAddDialog = false
            }
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Stat Chip
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StatChip(
    label: String,
    value: String,
    color: Color,
) {
    GlassFilledCard(
        tint = color,
        padding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                ),
                color = OnPrimary,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = OnPrimary.copy(alpha = 0.85f),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Deck Card
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeckCard(
    deck: FlashcardDao.DeckWithCardCount,
    dueCount: Int,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onReview: () -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }
    val deckColor = deck.colorHex.toComposeColor()
    val isDark = MaterialTheme.colorScheme.isDark
    val scope = rememberCoroutineScope()

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Error
                    else -> Color.Transparent
                },
                label = "swipeBg",
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color, RoundedCornerShape(LocalGlassShapes.current.cardRadius))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "মুছুন",
                    tint = OnPrimary,
                )
            }
        },
        enableDismissFromStartToEnd = false,
    ) {
        GlassElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            padding = 16.dp,
            onClick = onClick,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                // Top row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(deckColor, CircleShape)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = deck.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            if (!deck.subjectName.isNullOrBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(100.dp),
                                    color = deckColor.copy(alpha = 0.12f),
                                ) {
                                    Text(
                                        text = deck.subjectName,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = deckColor,
                                    )
                                }
                            }
                        }
                    }
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "আরো",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text("রিভিউ শুরু") },
                                onClick = { onReview(); showMenu = false },
                                leadingIcon = { Icon(Icons.Default.PlayArrow, null) },
                            )
                            DropdownMenuItem(
                                text = { Text("মুছুন") },
                                onClick = { onDelete(); showMenu = false },
                                leadingIcon = {
                                    Icon(Icons.Default.Delete, null, tint = Error)
                                },
                            )
                        }
                    }
                }

                // Description
                if (deck.description.isNotBlank()) {
                    Text(
                        text = deck.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                // Stats row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Card count
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            Icons.Default.CardMembership,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp),
                        )
                        Text(
                            text = "${deck.cardCount.toBengaliDigits()} কার্ড",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    // Due badge
                    if (dueCount > 0) {
                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            color = Error.copy(alpha = 0.15f),
                        ) {
                            Text(
                                text = "${dueCount.toBengaliDigits()} রিভিউ বাকি",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Error,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Last studied
                    if (deck.lastStudiedAt != null) {
                        val lastStudied = SimpleDateFormat("dd MMM", Locale.getDefault())
                            .format(Date(deck.lastStudiedAt))
                        Text(
                            text = "সর্বশেষ: $lastStudied",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        )
                    }

                    // Review button
                    if (dueCount > 0) {
                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            color = deckColor.copy(alpha = 0.15f),
                            modifier = Modifier.clickable { onReview() },
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = deckColor,
                                    modifier = Modifier.size(14.dp),
                                )
                                Text(
                                    text = "রিভিউ",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = deckColor,
                                    fontWeight = FontWeight.Bold,
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
// Deck Detail Screen
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeckDetailScreen(
    viewModel: FlashcardViewModel,
    onBack: () -> Unit,
    onAddCard: () -> Unit,
    onExport: () -> Unit,
    onQuickAdd: () -> Unit,
) {
    val currentDeck by viewModel.currentDeck.collectAsState()
    val cards by viewModel.deckCards.collectAsState()
    val dueCards by viewModel.dueCards.collectAsState()
    val deckStats by viewModel.deckStats.collectAsState()
    val currentDeckId by viewModel.currentDeckId.collectAsState()

    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showOcclusionCreator by remember { mutableStateOf(false) }

    val filteredCards = remember(cards, searchQuery) {
        if (searchQuery.isBlank()) cards
        else cards.filter {
            it.front.contains(searchQuery, ignoreCase = true) ||
                it.back.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(
                        text = currentDeck?.name ?: "ডেক",
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Clear deck selection — go back to deck list
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ফিরে যান")
                    }
                },
                actions = {
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(Icons.Default.Search, contentDescription = "খুঁজুন")
                    }
                    IconButton(onClick = onExport) {
                        Icon(Icons.Default.FileDownload, contentDescription = "এক্সপোর্ট")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End,
            ) {
                // Occlusion FAB
                SmallFloatingActionButton(
                    onClick = { showOcclusionCreator = true },
                    containerColor = Secondary,
                    contentColor = OnPrimary,
                    icon = Icons.Default.Image,
                    label = "ইমেজ অক্লুজন",
                )
                // Main add FAB
                FloatingActionButton(
                    onClick = onAddCard,
                    containerColor = Primary,
                    contentColor = OnPrimary,
                    shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "কার্ড যোগ করুন")
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Stats row
            if (deckStats != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    StatChip("মোট", deckStats!!.totalCards.toBengaliDigits(), Primary)
                    StatChip("রিভিউ বাকি", deckStats!!.dueCards.toBengaliDigits(), Warning)
                    StatChip("মাস্টার্ড", deckStats!!.masteredCards.toBengaliDigits(), Success)
                }
            }

            // Review button
            if ((deckStats?.dueCards ?: 0) > 0) {
                Button(
                    onClick = {
                        currentDeckId?.let { viewModel.startReview(it) }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("রিভিউ শুরু করুন (${(deckStats?.dueCards ?: 0).toBengaliDigits()} কার্ড)")
                }
            }

            // Quick add from note button
            OutlinedButton(
                onClick = onQuickAdd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            ) {
                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("নোট থেকে দ্রুত যোগ")
            }

            // Search bar
            AnimatedVisibility(
                visible = showSearch,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("কার্ড খুঁজুন...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, null)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    ),
                    singleLine = true,
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
            )

            // Card grid
            if (filteredCards.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Icon(
                            Icons.Default.CardMembership,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            modifier = Modifier.size(64.dp),
                        )
                        Text(
                            text = "এই ডেকে কোনো কার্ড নেই",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        )
                        Text(
                            text = "+ বাটনে ক্লিক করে নতুন কার্ড যোগ করুন",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(filteredCards, key = { it.id }) { card ->
                        FlashcardGridItem(
                            card = card,
                            deckColor = currentDeck?.colorHex?.toComposeColor() ?: Primary,
                            onClick = { /* view card detail */ },
                            onDelete = { viewModel.deleteCard(card.id) },
                        )
                    }
                }
            }
        }
    }

    if (showOcclusionCreator) {
        ImageOcclusionCreator(
            onDismiss = { showOcclusionCreator = false },
            onSave = { /* create occlusion card */ showOcclusionCreator = false },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Flashcard Grid Item
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun FlashcardGridItem(
    card: Flashcard,
    deckColor: Color,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    var showMenu by remember { mutableStateOf(false) }

    GlassOutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f),
        padding = 10.dp,
        onClick = onClick,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = if (card.audioPath != null) 28.dp else 0.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                // Image thumbnail
                if (card.imageUrl != null) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.Image,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                }

                // Front text
                Text(
                    text = card.front,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp,
                )

                Spacer(modifier = Modifier.weight(1f))

                // Status indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = when {
                            card.reviewCount == 0 -> MaterialTheme.colorScheme.surfaceVariant
                            card.correctCount > 0 && card.reviewCount >= 3 -> Success.copy(alpha = 0.15f)
                            else -> Warning.copy(alpha = 0.15f)
                        },
                    ) {
                        Text(
                            text = when {
                                card.reviewCount == 0 -> "নতুন"
                                card.correctCount > 0 && card.reviewCount >= 3 -> "মাস্টার্ড"
                                else -> "শিখছি"
                            },
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = when {
                                card.reviewCount == 0 -> MaterialTheme.colorScheme.onSurfaceVariant
                                card.correctCount > 0 && card.reviewCount >= 3 -> Success
                                else -> Warning
                            },
                        )
                    }
                    Text(
                        text = "${card.reviewCount.toBengaliDigits()}x",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    )
                }
            }

            // Audio indicator
            if (card.audioPath != null) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(top = 4.dp),
                    shape = RoundedCornerShape(100.dp),
                    color = Primary.copy(alpha = 0.15f),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                    ) {
                        Icon(
                            Icons.Default.Audiotrack,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(12.dp),
                        )
                    }
                }
            }

            // Delete button
            IconButton(
                onClick = { showMenu = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp),
            ) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp),
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(
                    text = { Text("সম্পাদনা") },
                    onClick = { showMenu = false },
                    leadingIcon = { Icon(Icons.Default.Edit, null) },
                )
                DropdownMenuItem(
                    text = { Text("মুছুন") },
                    onClick = { onDelete(); showMenu = false },
                    leadingIcon = {
                        Icon(Icons.Default.Delete, null, tint = Error)
                    },
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Review Session Screen — Full screen SRS
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ReviewSessionScreen(
    viewModel: FlashcardViewModel,
    currentCard: Flashcard?,
    currentCardIndex: Int,
    totalCards: Int,
    reviewStats: ReviewStats,
    onEndReview: () -> Unit,
) {
    var isFlipped by remember { mutableStateOf(false) }
    var flipRotation by remember { mutableFloatStateOf(0f) }
    var sessionStartTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // Animate card flip
    val rotationAnim by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "cardFlip",
    )

    // Audio playback state
    var isPlayingAudio by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(
                        text = "রিভিউ সেশন",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onEndReview) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "শেষ করুন")
                    }
                },
                actions = {
                    Text(
                        text = "${(currentCardIndex + 1).toBengaliDigits()}/${totalCards.toBengaliDigits()}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = EnglishFontFamily,
                            fontWeight = FontWeight.Bold,
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
    ) { innerPadding ->
        if (currentCard == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "রিভিউ শেষ!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Progress bar
            LinearProgressIndicator(
                progress = { (currentCardIndex + 1).toFloat() / totalCards },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = Primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round,
            )

            // Card — with flip animation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer {
                        rotationY = rotationAnim
                        cameraDistance = 12f * density
                    }
                    .clickable { isFlipped = !isFlipped },
                contentAlignment = Alignment.Center,
            ) {
                // Front side
                if (rotationAnim < 90f) {
                    GlassElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        padding = 24.dp,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            // Image
                            if (currentCard.imageUrl != null) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .padding(bottom = 16.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            Icons.Default.Image,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                            modifier = Modifier.size(48.dp),
                                        )
                                    }
                                }
                            }

                            Text(
                                text = currentCard.front,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                lineHeight = 32.sp,
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Audio play button
                            if (currentCard.audioPath != null) {
                                Surface(
                                    shape = CircleShape,
                                    color = Primary.copy(alpha = 0.15f),
                                    modifier = Modifier.clickable { isPlayingAudio = !isPlayingAudio },
                                ) {
                                    Icon(
                                        imageVector = if (isPlayingAudio) Icons.Default.Audiotrack else Icons.Default.PlayArrow,
                                        contentDescription = "অডিও চালান",
                                        tint = Primary,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .padding(12.dp),
                                    )
                                }
                            }

                            Text(
                                text = "ট্যাপ করে উত্তর দেখুন",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.padding(top = 24.dp),
                            )
                        }
                    }
                }
                // Back side
                else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .graphicsLayer { rotationY = 180f },
                    ) {
                        GlassFilledCard(
                            modifier = Modifier.fillMaxSize(),
                            tint = Primary,
                            padding = 24.dp,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = "উত্তর",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = OnPrimary.copy(alpha = 0.7f),
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = currentCard.back,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = OnPrimary,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 32.sp,
                                )
                            }
                        }
                    }
                }
            }

            // Rating buttons — only visible when flipped
            AnimatedVisibility(
                visible = isFlipped,
                enter = expandVertically(expandFrom = Alignment.Bottom) + fadeIn(),
                exit = shrinkVertically(shrinkTowards = Alignment.Bottom) + fadeOut(),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "আপনার মূল্যায়ন দিন:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        RatingButton(
                            label = "আবার",
                            sublabel = "১ মিনিট",
                            color = Error,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.rateCard(FlashcardDifficulty.AGAIN)
                                isFlipped = false
                            },
                        )
                        RatingButton(
                            label = "কঠিন",
                            sublabel = "১০ মিনিট",
                            color = Warning,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.rateCard(FlashcardDifficulty.HARD)
                                isFlipped = false
                            },
                        )
                        RatingButton(
                            label = "ভালো",
                            sublabel = "১ দিন",
                            color = Success,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.rateCard(FlashcardDifficulty.GOOD)
                                isFlipped = false
                            },
                        )
                        RatingButton(
                            label = "সহজ",
                            sublabel = "৩ দিন",
                            color = Primary,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.rateCard(FlashcardDifficulty.EASY)
                                isFlipped = false
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingButton(
    label: String,
    sublabel: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = color.copy(alpha = 0.12f),
        contentColor = color,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = sublabel,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = EnglishFontFamily,
                    fontSize = 10.sp,
                ),
                color = color.copy(alpha = 0.7f),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Review Complete Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ReviewCompleteDialog(
    stats: ReviewStats,
    onDismiss: () -> Unit,
) {
    val sessionTimeSecs = remember { 0 } // Would be calculated from actual start time
    val minutes = (sessionTimeSecs / 60).coerceAtLeast(1)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = "🎉 রিভিউ সম্পন্ন!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Stats cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    ReviewStatBox(
                        label = "মোট রিভিউ",
                        value = stats.totalReviewed.toBengaliDigits(),
                        color = Primary,
                        modifier = Modifier.weight(1f),
                    )
                    ReviewStatBox(
                        label = "সঠিক",
                        value = stats.correctCount.toBengaliDigits(),
                        color = Success,
                        modifier = Modifier.weight(1f),
                    )
                    ReviewStatBox(
                        label = "ভুল",
                        value = stats.wrongCount.toBengaliDigits(),
                        color = Error,
                        modifier = Modifier.weight(1f),
                    )
                }

                // Accuracy
                GlassFilledCard(
                    tint = if (stats.accuracy >= 70) Success else Warning,
                    padding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "নির্ভুলতা",
                            style = MaterialTheme.typography.labelMedium,
                            color = OnPrimary.copy(alpha = 0.85f),
                        )
                        Text(
                            text = String.format("%.0f%%", stats.accuracy),
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontFamily = EnglishFontFamily,
                                fontWeight = FontWeight.Bold,
                            ),
                            color = OnPrimary,
                        )
                    }
                }

                // Time
                Text(
                    text = "সময়: ${minutes.toBengaliDigits()} মিনিট",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                // Encouragement message
                Text(
                    text = when {
                        stats.accuracy >= 90 -> "অসাধারণ! আপনি দারুণ প্রস্তুতি নিচ্ছেন! 🌟"
                        stats.accuracy >= 70 -> "ভালো! আরেকটু চেষ্টা করলেই পারবেন! 💪"
                        stats.accuracy >= 50 -> "চালিয়ে যান! অনুশীলনেই উন্নতি! 📚"
                        else -> "নিরাশ হবেন না! প্রতিদিন অনুশীলন করুন! 🔄"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("ঠিক আছে")
            }
        },
    )
}

@Composable
private fun ReviewStatBox(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    GlassOutlinedCard(
        modifier = modifier,
        padding = PaddingValues(vertical = 12.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = EnglishFontFamily,
                    fontWeight = FontWeight.Bold,
                ),
                color = color,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Add Deck Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddDeckDialog(
    subjects: List<com.porashona.studymaster.data.model.Subject>,
    onDismiss: () -> Unit,
    onAdd: (name: String, subjectId: Long?, subjectName: String?, colorHex: String, description: String) -> Unit,
) {
    var deckName by remember { mutableStateOf("") }
    var selectedSubjectId by remember { mutableStateOf<Long?>(null) }
    var selectedSubjectName by remember { mutableStateOf<String?>(null) }
    var selectedColor by remember { mutableStateOf(Primary.toHexString()) }
    var description by remember { mutableStateOf("") }

    val colorOptions = listOf(
        "#6C63FF", "#FF6584", "#4ECDC4", "#FFD93D", "#6BCF7F",
        "#AB47BC", "#FF7043", "#42A5F5", "#EC407A", "#26A69A",
    )

    val isValid by remember {
        derivedStateOf { deckName.isNotBlank() }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = "নতুন ডেক তৈরি করুন",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = deckName,
                    onValueChange = { deckName = it },
                    label = { Text("ডেকের নাম") },
                    placeholder = { Text("যেমন: বাংলা সাহিত্য") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                    ),
                    singleLine = true,
                )

                // Subject selection
                if (subjects.isNotEmpty()) {
                    Text(
                        text = "বিষয় নির্বাচন (ঐচ্ছিক)",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        subjects.forEach { subject ->
                            val isSelected = selectedSubjectId == subject.id
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(100.dp))
                                    .clickable {
                                        if (isSelected) {
                                            selectedSubjectId = null
                                            selectedSubjectName = null
                                        } else {
                                            selectedSubjectId = subject.id
                                            selectedSubjectName = subject.name
                                        }
                                    },
                                shape = RoundedCornerShape(100.dp),
                                color = if (isSelected) {
                                    Primary.copy(alpha = 0.2f)
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                },
                            ) {
                                Text(
                                    text = subject.name,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                }

                // Color selection
                Text(
                    text = "রঙ নির্বাচন করুন",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    colorOptions.forEach { hex ->
                        val isSelected = selectedColor == hex
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(hex.toComposeColor())
                                .then(
                                    if (isSelected) Modifier.border(2.dp, OnPrimary, CircleShape)
                                    else Modifier
                                )
                                .clickable { selectedColor = hex },
                        )
                    }
                }

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("বিবরণ (ঐচ্ছিক)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                    ),
                    maxLines = 3,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAdd(deckName, selectedSubjectId, selectedSubjectName, selectedColor, description)
                },
                enabled = isValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = selectedColor.toComposeColor(),
                    disabledContainerColor = selectedColor.toComposeColor().copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text("তৈরি করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Add Card Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun AddCardDialog(
    deckId: Long,
    onDismiss: () -> Unit,
    onAdd: (front: String, back: String, imageUrl: String?, audioPath: String?) -> Unit,
) {
    var frontText by remember { mutableStateOf("") }
    var backText by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var audioPath by remember { mutableStateOf<String?>(null) }
    var showImagePicker by remember { mutableStateOf(false) }
    var showAudioPicker by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { imageUrl = it.toString() }
        }
    )

    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { audioPath = it.toString() }
        }
    )

    val isValid by remember {
        derivedStateOf { frontText.isNotBlank() && backText.isNotBlank() }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = "নতুন কার্ড যোগ করুন",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = frontText,
                    onValueChange = { frontText = it },
                    label = { Text("সামনের দিক (প্রশ্ন)") },
                    placeholder = { Text("প্রশ্ন বা শব্দ লিখুন...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                    ),
                    maxLines = 4,
                )

                OutlinedTextField(
                    value = backText,
                    onValueChange = { backText = it },
                    label = { Text("পেছনের দিক (উত্তর)") },
                    placeholder = { Text("উত্তর বা সংজ্ঞা লিখুন...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                    ),
                    maxLines = 4,
                )

                // Media options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // Image picker
                    OutlinedButton(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, if (imageUrl != null) Success else MaterialTheme.colorScheme.outline),
                    ) {
                        Icon(
                            Icons.Default.Image,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (imageUrl != null) Success else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            if (imageUrl != null) "ইমেজ যোগ হয়েছে" else "ইমেজ যোগ করুন",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }

                    // Audio picker
                    OutlinedButton(
                        onClick = { audioPickerLauncher.launch("audio/*") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, if (audioPath != null) Success else MaterialTheme.colorScheme.outline),
                    ) {
                        Icon(
                            Icons.Default.Audiotrack,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (audioPath != null) Success else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            if (audioPath != null) "অডিও যোগ হয়েছে" else "অডিও যোগ করুন",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }

                // Preview
                if (frontText.isNotBlank() || backText.isNotBlank()) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        text = "প্রিভিউ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    GlassOutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        padding = 12.dp,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "সামনে:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                text = frontText.ifBlank { "..."},
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            Text(
                                text = "পেছনে:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                            Text(
                                text = backText.ifBlank { "..."},
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAdd(frontText, backText, imageUrl, audioPath)
                },
                enabled = isValid,
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text("যোগ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Image Occlusion Creator
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageOcclusionCreator(
    onDismiss: () -> Unit,
    onSave: (imageUri: String, occlusions: List<OcclusionRect>) -> Unit,
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<String?>(null) }
    var occlusions by remember { mutableStateOf(listOf<OcclusionRect>()) }
    var currentRectStart by remember { mutableStateOf<Offset?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri?.toString()
            occlusions = emptyList()
        }
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = "ইমেজ অক্লুজন কার্ড",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (imageUri == null) {
                    // Step 1: Select image
                    Text(
                        text = "ধাপ ১: একটি ছবি নির্বাচন করুন",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ছবি নির্বাচন করুন")
                    }
                } else {
                    // Step 2: Draw occlusion rectangles
                    Text(
                        text = "ধাপ ২: লুকাতে চাওয়া অংশে আয়তক্ষেত্র আঁকুন",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    // Canvas for occlusion drawing
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        currentRectStart = offset
                                    },
                                    onDragEnd = {
                                        // On real implementation, this would use start + current position
                                        currentRectStart = null
                                    },
                                    onDrag = { change, _ ->
                                        change.consume()
                                    }
                                )
                            },
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.Image,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                    modifier = Modifier.size(48.dp),
                                )
                                Text(
                                    text = "ছবি লোড হয়েছে — এখানে আয়তক্ষেত্র আঁকুন",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }

                        // Draw occlusion rectangles
                        occlusions.forEach { rect ->
                            Box(
                                modifier = Modifier
                                    .offset(x = rect.left.dp, y = rect.top.dp)
                                    .width((rect.right - rect.left).dp)
                                    .height((rect.bottom - rect.top).dp)
                                    .background(Error.copy(alpha = 0.6f))
                            )
                        }
                    }

                    // Add occlusion button
                    Text(
                        text = "অক্লুজন সংখ্যা: ${occlusions.size.toBengaliDigits()}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
        confirmButton = {
            if (imageUri != null) {
                Button(
                    onClick = { onSave(imageUri!!, occlusions) },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text("কার্ড তৈরি করুন")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Import Deck Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ImportDeckDialog(
    onDismiss: () -> Unit,
    onImport: (jsonString: String) -> Unit,
) {
    var jsonString by remember { mutableStateOf("") }
    var importError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                try {
                    val inputStream = context.contentResolver.openInputStream(it)
                    jsonString = inputStream?.bufferedReader()?.use { reader -> reader.readText() } ?: ""
                    importError = null
                } catch (e: Exception) {
                    importError = "ফাইল পড়তে সমস্যা: ${e.message}"
                }
            }
        }
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = "ডেক ইম্পোর্ট করুন",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = { filePickerLauncher.launch("application/json") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("JSON ফাইল নির্বাচন করুন")
                }

                if (importError != null) {
                    Text(
                        text = importError!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = Error,
                    )
                }

                OutlinedTextField(
                    value = jsonString,
                    onValueChange = { jsonString = it },
                    label = { Text("অথবা JSON পেস্ট করুন") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                    ),
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onImport(jsonString) },
                enabled = jsonString.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text("ইম্পোর্ট করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Export Deck Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ExportDeckDialog(
    deckId: Long,
    viewModel: FlashcardViewModel,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json"),
        onResult = { uri: Uri? ->
            uri?.let {
                scope.launch {
                    // Copy from content URI to file path for the ViewModel
                    val fileName = "deck_export_$deckId.json"
                    viewModel.exportDeck(deckId, fileName)
                }
            }
        }
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = "ডেক এক্সপোর্ট",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "এই ডেকের সব কার্ড JSON ফাইল হিসেবে সেভ করুন। পরে অন্য ডিভাইসে ইম্পোর্ট করতে পারবেন।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Button(
                    onClick = {
                        exportLauncher.launch("deck_$deckId.json")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Icon(Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("JSON ফাইল সেভ করুন")
                }

                // Copy to clipboard
                OutlinedButton(
                    onClick = {
                        // Copy JSON to clipboard (would need actual JSON string)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ক্লিপবোর্ডে কপি করুন")
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বন্ধ করুন")
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Quick Add from Note Dialog
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun QuickAddFromNoteDialog(
    onDismiss: () -> Unit,
    onAdd: (noteId: Long) -> Unit,
) {
    // In a real app, this would load notes from the database and allow selection
    // For now, we show a placeholder implementation
    var noteContent by remember { mutableStateOf("") }
    var noteId by remember { mutableStateOf(1L) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(28.dp),
        title = {
            Text(
                text = "নোট থেকে দ্রুত যোগ",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "নোটে Q/A ফরম্যাটে লিখুন:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Q: প্রশ্ন এখানে\nA: উত্তর এখানে",
                    style = MaterialTheme.typography.bodySmall,
                    color = Primary,
                    fontFamily = EnglishFontFamily,
                )
                OutlinedTextField(
                    value = noteContent,
                    onValueChange = { noteContent = it },
                    placeholder = {
                        Text(
                            "Q: ফটোসিন্থেসিস কী?\nA: সবুজ উদ্ভিদ সূর্যালোক শক্তি ব্যবহার করে খাদ্য তৈরি করে\n\nQ: ক্লোরোফিল কোথায় থাকে?\nA: ক্লোরোপ্লাস্টে",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                    ),
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onAdd(noteId) },
                enabled = noteContent.contains("Q:", ignoreCase = true) && noteContent.contains("A:", ignoreCase = true),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text("কার্ড তৈরি করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Small Floating Action Button
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SmallFloatingActionButton(
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    icon: ImageVector,
    label: String,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        contentColor = contentColor,
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Flashcard Empty State
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun FlashcardEmptyState(onAdd: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            Icons.Default.CardMembership,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
            modifier = Modifier.size(72.dp),
        )
        Text(
            text = "কোনো ফ্ল্যাশকার্ড ডেক নেই",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
        )
        Text(
            text = "ডেক তৈরি করুন এবং স্মৃতি শক্তি বাড়ান\nঅন্তর্নিহিত পুনরাবৃত্তি (SRS) পদ্ধতিতে",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = onAdd,
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("প্রথম ডেক তৈরি করুন")
        }
    }
}
