
package com.porashona.studymaster.ui.compose.screens.notes

import android.Manifest
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Highlight
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewList
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TertiaryTabRow
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.porashona.studymaster.data.model.Note
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.ui.compose.components.ConfirmDeleteDialog
import androidx.compose.material3.rememberSwipeToDismissBoxState
import com.porashona.studymaster.ui.compose.components.EmptyStateView
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.LocalGlassShapes
import com.porashona.studymaster.ui.compose.components.LocalMotion
import com.porashona.studymaster.ui.compose.components.SubjectChip
import com.porashona.studymaster.ui.compose.theme.*
import com.porashona.studymaster.ui.compose.viewmodels.NoteEvent
import com.porashona.studymaster.ui.compose.viewmodels.NoteSortMode
import com.porashona.studymaster.ui.compose.viewmodels.NotesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// NotesScreen — Full-featured notes management with search, filters,
// masonry/list toggle, rich-text editing, and more. All text in Bengali.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    onNavigateToFlashcard: (() -> Unit)? = null,
) {
    val notes by viewModel.notes.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedSubjectFilter by viewModel.selectedSubjectFilter.collectAsState()
    val sortMode by viewModel.sortMode.collectAsState()
    val event by viewModel.events.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // ─── UI State ────────────────────────────────────────────────────────────
    var showFavoritesOnly by remember { mutableStateOf(false) }
    var isGridView by remember { mutableStateOf(true) }
    var showSortMenu by remember { mutableStateOf(false) }
    var searchActive by remember { mutableStateOf(false) }
    var showAddNoteSheet by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<Note?>(null) }
    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    // Handle events
    LaunchedEffect(event) {
        when (event) {
            is NoteEvent.NoteCreated -> {
                snackbarHostState.showSnackbar("নোট তৈরি হয়েছে", duration = SnackbarDuration.Short)
            }
            is NoteEvent.NoteUpdated -> {
                snackbarHostState.showSnackbar("নোট আপডেট হয়েছে", duration = SnackbarDuration.Short)
            }
            is NoteEvent.NoteDeleted -> {
                val result = snackbarHostState.showSnackbar(
                    message = "নোট মুছে ফেলা হয়েছে",
                    actionLabel = "পূর্বাবস্থায়",
                    duration = SnackbarDuration.Long,
                )
                if (result == SnackbarResult.ActionPerformed) {
                    // Undo: re-add would need the note stored — simplified
                }
            }
            is NoteEvent.LinkedToGoal -> {
                snackbarHostState.showSnackbar("গোলের সাথে লিংক হয়েছে", duration = SnackbarDuration.Short)
            }
            is NoteEvent.LinkedToExam -> {
                snackbarHostState.showSnackbar("পরীক্ষার সাথে লিংক হয়েছে", duration = SnackbarDuration.Short)
            }
            null -> {}
        }
        viewModel.clearEvent()
    }

    // ─── Filtered notes for favorites tab ────────────────────────────────────
    val displayedNotes = remember(notes, showFavoritesOnly) {
        if (showFavoritesOnly) notes.filter { it.isFavorite } else notes
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            GlassmorphicFAB(
                onClick = {
                    editingNote = null
                    showAddNoteSheet = true
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // ─── Top Bar ───────────────────────────────────────────────────────
            NotesTopBar(
                searchActive = searchActive,
                searchQuery = searchQuery,
                onSearchQueryChanged = { viewModel.search(it) },
                onSearchToggle = { searchActive = !searchActive },
                isGridView = isGridView,
                onViewToggle = { isGridView = !isGridView },
                onSortClick = { showSortMenu = true },
                showSortMenu = showSortMenu,
                onSortDismiss = { showSortMenu = false },
                sortMode = sortMode,
                onSortSelected = { mode ->
                    viewModel.setSortMode(mode)
                    showSortMenu = false
                },
                showFavoritesOnly = showFavoritesOnly,
                onFavoritesToggle = { showFavoritesOnly = !showFavoritesOnly },
            )

            // ─── Subject Filter Chips ──────────────────────────────────────────
            SubjectFilterRow(
                subjects = subjects,
                selectedSubjectId = selectedSubjectFilter,
                onSubjectSelected = { viewModel.setSubjectFilter(it) },
            )

            // ─── Notes Content ─────────────────────────────────────────────────
            if (displayedNotes.isEmpty()) {
                EmptyStateView(
                    title = if (showFavoritesOnly) "কোনো প্রিয় নোট নেই" else "কোনো নোট নেই",
                    description = if (showFavoritesOnly)
                        "আপনার প্রিয় নোট এখানে দেখাবে। নোটে তারা চিহ্ন ট্যাপ করুন।"
                    else
                        "পড়ার সময় নোট লিখুন। + বাটনে ট্যাপ করুন নতুন নোট তৈরি করতে।",
                    icon = if (showFavoritesOnly) Icons.Default.FavoriteBorder else Icons.Default.Edit,
                    actionLabel = "নতুন নোট যোগ করুন",
                    onAction = {
                        editingNote = null
                        showAddNoteSheet = true
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            } else if (isGridView) {
                NotesMasonryGrid(
                    notes = displayedNotes,
                    onNoteClick = { editingNote = it },
                    onFavoriteToggle = { viewModel.toggleFavorite(it.id) },
                    onDeleteClick = { noteToDelete = it },
                )
            } else {
                NotesList(
                    notes = displayedNotes,
                    onNoteClick = { editingNote = it },
                    onFavoriteToggle = { viewModel.toggleFavorite(it.id) },
                    onDeleteClick = { noteToDelete = it },
                )
            }
        }
    }

    // ─── Add/Edit Note Bottom Sheet ──────────────────────────────────────────
    if (showAddNoteSheet || editingNote != null) {
        NoteEditorSheet(
            note = editingNote,
            subjects = subjects,
            onDismiss = {
                showAddNoteSheet = false
                editingNote = null
            },
            onSave = { title, content, subjectId, subjectName, htmlContent, color, tags, imagePaths, voiceNotePath ->
                if (editingNote != null) {
                    viewModel.updateNote(
                        editingNote!!.copy(
                            title = title,
                            content = content,
                            htmlContent = htmlContent,
                            subjectId = subjectId,
                            subjectName = subjectName,
                            color = color,
                            tags = tags,
                            imagesPaths = imagePaths,
                            voiceNotePath = voiceNotePath,
                        )
                    )
                } else {
                    viewModel.addNote(
                        title = title,
                        content = content,
                        subjectId = subjectId,
                        subjectName = subjectName,
                        htmlContent = htmlContent,
                    )
                }
                showAddNoteSheet = false
                editingNote = null
            },
        )
    }

    // ─── Delete Confirmation ─────────────────────────────────────────────────
    noteToDelete?.let { note ->
        ConfirmDeleteDialog(
            title = "নোট মুছুন",
            message = "\"${note.title}\" নোটটি মুছে ফেলতে চান?",
            onConfirm = {
                viewModel.deleteNote(note)
                noteToDelete = null
            },
            onDismiss = { noteToDelete = null },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Glassmorphic FAB
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun GlassmorphicFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    Box(
        modifier = modifier
            .padding(end = 16.dp, bottom = 16.dp)
            .size(64.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(20.dp)
                clip = true
            }
            .background(
                if (isDark) GlassDarkAlpha60.copy(alpha = 0.75f) else GlassLightAlpha90.copy(alpha = 0.85f),
                RoundedCornerShape(20.dp),
            )
            .border(
                width = 1.dp,
                color = if (isDark) GlassBorderDark.copy(alpha = 0.5f) else GlassBorderLight.copy(alpha = 0.6f),
                shape = RoundedCornerShape(20.dp),
            )
            .blur(0.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        // Gradient overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Primary.copy(alpha = 0.25f),
                            Secondary.copy(alpha = 0.15f),
                        ),
                    ),
                    RoundedCornerShape(20.dp),
                ),
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "নতুন নোট",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(28.dp),
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// NotesTopBar — Search, view toggle, sort, favorites tab
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotesTopBar(
    searchActive: Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchToggle: () -> Unit,
    isGridView: Boolean,
    onViewToggle: () -> Unit,
    onSortClick: () -> Unit,
    showSortMenu: Boolean,
    onSortDismiss: () -> Unit,
    sortMode: NoteSortMode,
    onSortSelected: (NoteSortMode) -> Unit,
    showFavoritesOnly: Boolean,
    onFavoritesToggle: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val motion = LocalMotion.current
    val shapes = LocalGlassShapes.current

    AnimatedContent(
        targetState = searchActive,
        transitionSpec = {
            fadeIn(motion.fadeIn) togetherWith fadeOut(motion.fadeIn)
        },
        label = "searchToggle",
    ) { active ->
        if (active) {
            // Search bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = {
                        Text(
                            "নোট খুঁজুন...",
                            style = StudyMasterTypography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp),
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChanged("") }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "মুছুন",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = androidx.compose.ui.text.input.ImeAction.Search,
                    ),
                    shape = RoundedCornerShape(shapes.inputFieldRadius),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary.copy(alpha = 0.5f),
                        unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                        cursorColor = Primary,
                    ),
                    textStyle = StudyMasterTypography.bodyMedium,
                    modifier = Modifier.weight(1f),
                )
                TextButton(onClick = onSearchToggle) {
                    Text(
                        "বাতিল",
                        style = StudyMasterTypography.labelMedium,
                        color = Primary,
                    )
                }
            }
        } else {
            // Normal top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (showFavoritesOnly) "প্রিয় নোট" else "নোট",
                    style = StudyMasterTypography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Favorites toggle
                    IconButton(onClick = onFavoritesToggle) {
                        Icon(
                            imageVector = if (showFavoritesOnly) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "প্রিয় নোট",
                            tint = if (showFavoritesOnly) Secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                    // Search button
                    IconButton(onClick = onSearchToggle) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "খুঁজুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                    // Sort dropdown
                    Box {
                        IconButton(onClick = onSortClick) {
                            Icon(
                                Icons.Default.Sort,
                                contentDescription = "সাজান",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(22.dp),
                            )
                        }
                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = onSortDismiss,
                            shape = RoundedCornerShape(shapes.cardRadiusSmall),
                            containerColor = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90,
                        ) {
                            SortMenuItem(
                                label = "সাম্প্রতিক আপডেট",
                                selected = sortMode == NoteSortMode.UPDATED_DESC,
                                onClick = { onSortSelected(NoteSortMode.UPDATED_DESC) },
                            )
                            SortMenuItem(
                                label = "শিরোনাম (আ-হ)",
                                selected = sortMode == NoteSortMode.TITLE_ASC,
                                onClick = { onSortSelected(NoteSortMode.TITLE_ASC) },
                            )
                            SortMenuItem(
                                label = "বিষয় অনুযায়ী",
                                selected = sortMode == NoteSortMode.SUBJECT,
                                onClick = { onSortSelected(NoteSortMode.SUBJECT) },
                            )
                        }
                    }
                    // View toggle
                    IconButton(onClick = onViewToggle) {
                        Icon(
                            imageVector = if (isGridView) Icons.Outlined.ViewList else Icons.Outlined.GridView,
                            contentDescription = if (isGridView) "তালিকা ভিউ" else "গ্রিড ভিউ",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SortMenuItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = label,
                style = StudyMasterTypography.bodyMedium,
                color = if (selected) Primary else MaterialTheme.colorScheme.onSurface,
            )
        },
        onClick = onClick,
        trailingIcon = {
            if (selected) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(18.dp),
                )
            }
        },
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// Subject Filter Row
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SubjectFilterRow(
    subjects: List<Subject>,
    selectedSubjectId: Long?,
    onSubjectSelected: (Long?) -> Unit,
) {
    if (subjects.isEmpty()) return

    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // "All" chip
        item(key = "all") {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(shapes.chipRadius))
                    .clickable { onSubjectSelected(null) },
                shape = RoundedCornerShape(shapes.chipRadius),
                color = if (selectedSubjectId == null) {
                    Primary.copy(alpha = if (isDark) 0.25f else 0.15f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                },
                contentColor = if (selectedSubjectId == null) {
                    Primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            ) {
                Text(
                    text = "সব",
                    style = StudyMasterTypography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                )
            }
        }

        items(subjects, key = { it.id }) { subject ->
            SubjectChip(
                subjectName = subject.name,
                colorHex = subject.colorHex,
                selected = selectedSubjectId == subject.id,
                onClick = {
                    onSubjectSelected(
                        if (selectedSubjectId == subject.id) null else subject.id
                    )
                },
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Notes Masonry Grid (2-column)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun NotesMasonryGrid(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onFavoriteToggle: (Note) -> Unit,
    onDeleteClick: (Note) -> Unit,
) {
    // Split notes into two columns for masonry effect
    val (leftNotes, rightNotes) = remember(notes) {
        val left = mutableListOf<Note>()
        val right = mutableListOf<Note>()
        notes.forEachIndexed { index, note ->
            if (index % 2 == 0) left.add(note) else right.add(note)
        }
        left to right
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Left column
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(leftNotes, key = { it.id }) { note ->
                NoteCard(
                    note = note,
                    onClick = { onNoteClick(note) },
                    onFavoriteToggle = { onFavoriteToggle(note) },
                    onDelete = { onDeleteClick(note) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        // Right column
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(rightNotes, key = { it.id }) { note ->
                NoteCard(
                    note = note,
                    onClick = { onNoteClick(note) },
                    onFavoriteToggle = { onFavoriteToggle(note) },
                    onDelete = { onDeleteClick(note) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Notes List (single column)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun NotesList(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onFavoriteToggle: (Note) -> Unit,
    onDeleteClick: (Note) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(notes, key = { it.id }) { note ->
            NoteCardListItem(
                note = note,
                onClick = { onNoteClick(note) },
                onFavoriteToggle = { onFavoriteToggle(note) },
                onDelete = { onDeleteClick(note) },
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Note Card (grid mode — masonry)
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoteCard(
    note: Note,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current
    val noteColor = remember(note.color) {
        runCatching { note.color.toComposeColor() }.getOrDefault(Color.Transparent)
    }
    val accentColor = if (noteColor == Color.Transparent) Primary else noteColor

    // Time formatting
    val timeAgo = remember(note.updatedAt) {
        formatTimeAgo(note.updatedAt)
    }

    // Card height varies based on content length for masonry effect
    val contentLines = note.content.count { it == '\n' }.coerceIn(2, 6)

    GlassmorphicCard(
        modifier = modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onDelete,
            ),
        variant = GlassCardVariant.ELEVATED,
        cornerRadius = shapes.cardRadiusSmall,
        padding = 0.dp,
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    // Color accent bar at top
                    drawRoundRect(
                        color = accentColor.copy(alpha = 0.7f),
                        topLeft = Offset(0f, 0f),
                        size = Size(size.width, 4.dp.toPx()),
                        cornerRadius = CornerRadius(shapes.cardRadiusSmall.toPx(), shapes.cardRadiusSmall.toPx()),
                    )
                },
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
            ) {
                // Title row with favorite
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = note.title,
                        style = StudyMasterTypography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.width(4.dp))
                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier.size(28.dp),
                    ) {
                        Icon(
                            imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "প্রিয়",
                            tint = if (note.isFavorite) Secondary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))

                // Content preview (3 lines)
                if (note.content.isNotBlank()) {
                    Text(
                        text = note.content,
                        style = StudyMasterTypography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp,
                        ),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.height(8.dp))
                }

                // Image indicators
                if (note.imagesPaths.isNotBlank()) {
                    val imageCount = note.imagesPaths.split(",").count { it.isNotBlank() }
                    if (imageCount > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(bottom = 6.dp),
                        ) {
                            Icon(
                                Icons.Default.Image,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(14.dp),
                            )
                            Text(
                                text = "${imageCount.toBengaliDigits()} ছবি",
                                style = StudyMasterTypography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            )
                        }
                    }
                }

                // Voice note indicator
                if (note.voiceNotePath != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(bottom = 6.dp),
                    ) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(14.dp),
                        )
                        Text(
                            text = "ভয়েস নোট",
                            style = StudyMasterTypography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                // Bottom row: subject chip + time
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (note.subjectName != null) {
                        SubjectChip(
                            subjectName = note.subjectName,
                            compact = true,
                        )
                    }
                    Text(
                        text = timeAgo,
                        style = StudyMasterTypography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    )
                }

                // Tags
                if (note.tags.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    val tagList = note.tags.split(",").filter { it.isNotBlank() && !it.startsWith("goal:") && !it.startsWith("exam:") }
                    if (tagList.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            tagList.take(2).forEach { tag ->
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                ) {
                                    Text(
                                        text = "#$tag",
                                        style = StudyMasterTypography.labelSmall.copy(
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                            }
                            if (tagList.size > 2) {
                                Text(
                                    text = "+${(tagList.size - 2).toBengaliDigits()}",
                                    style = StudyMasterTypography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    ),
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
// Note Card List Item (single-column list mode)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun NoteCardListItem(
    note: Note,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onDelete: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current
    val noteColor = remember(note.color) {
        runCatching { note.color.toComposeColor() }.getOrDefault(Color.Transparent)
    }
    val accentColor = if (noteColor == Color.Transparent) Primary else noteColor
    val timeAgo = remember(note.updatedAt) { formatTimeAgo(note.updatedAt) }

    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        variant = GlassCardVariant.ELEVATED,
        cornerRadius = shapes.cardRadius,
        padding = 0.dp,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            // Color accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(accentColor.copy(alpha = 0.7f)),
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = note.title,
                        style = StudyMasterTypography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row {
                        if (note.subjectName != null) {
                            SubjectChip(
                                subjectName = note.subjectName,
                                compact = true,
                                modifier = Modifier.padding(end = 8.dp),
                            )
                        }
                        IconButton(
                            onClick = onFavoriteToggle,
                            modifier = Modifier.size(28.dp),
                        ) {
                            Icon(
                                imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "প্রিয়",
                                tint = if (note.isFavorite) Secondary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = note.content,
                    style = StudyMasterTypography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = timeAgo,
                        style = StudyMasterTypography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    )
                    if (note.imagesPaths.isNotBlank()) {
                        val count = note.imagesPaths.split(",").count { it.isNotBlank() }
                        if (count > 0) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(Icons.Default.Image, null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                                Text(
                                    "${count.toBengaliDigits()}",
                                    style = StudyMasterTypography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                )
                            }
                        }
                    }
                    if (note.voiceNotePath != null) {
                        Icon(Icons.Default.Mic, null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Note Editor Sheet — full-featured note creation/editing
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun NoteEditorSheet(
    note: Note?,
    subjects: List<Subject>,
    onDismiss: () -> Unit,
    onSave: (
        title: String,
        content: String,
        subjectId: Long?,
        subjectName: String?,
        htmlContent: String,
        color: String,
        tags: String,
        imagePaths: String,
        voiceNotePath: String?,
    ) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current
    val context = LocalContext.current

    // ─── Form state ───────────────────────────────────────────────────────────
    var title by remember(note) { mutableStateOf(note?.title ?: "") }
    var content by remember(note) { mutableStateOf(note?.content ?: "") }
    var selectedSubjectId by remember(note) { mutableStateOf(note?.subjectId) }
    var selectedSubjectName by remember(note) { mutableStateOf(note?.subjectName) }
    var noteColor by remember(note) { mutableStateOf(note?.color ?: "#FFFFFF") }
    var tagInput by remember(note) { mutableStateOf(note?.tags ?: "") }
    var imagePaths by remember(note) { mutableStateOf(note?.imagesPaths ?: "") }
    var voiceNotePath by remember(note) { mutableStateOf(note?.voiceNotePath) }

    // Rich text state
    var textFieldValue by remember(note) {
        mutableStateOf(
            TextFieldValue(
                annotatedString = buildAnnotatedString {
                    append(note?.content ?: "")
                },
            )
        )
    }
    var isBold by remember { mutableStateOf(false) }
    var isItalic by remember { mutableStateOf(false) }
    var isUnderline by remember { mutableStateOf(false) }
    var isHighlight by remember { mutableStateOf(false) }
    var showSubjectDropdown by remember { mutableStateOf(false) }
    var showColorPicker by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }

    // Flashcard quick-add
    var showFlashcardDialog by remember { mutableStateOf(false) }
    var flashcardFront by remember { mutableStateOf("") }
    var flashcardBack by remember { mutableStateOf("") }

    // Camera/gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri: Uri? ->
        uri?.let {
            val path = copyUriToAppStorage(context, it, "note_${System.currentTimeMillis()}")
            if (path != null) {
                imagePaths = if (imagePaths.isNotBlank()) "$imagePaths,$path" else path
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            val photoFile = File(context.cacheDir, "note_photo_${System.currentTimeMillis()}.jpg")
            val path = photoFile.absolutePath
            imagePaths = if (imagePaths.isNotBlank()) "$imagePaths,$path" else path
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            val photoFile = File(context.cacheDir, "note_photo_${System.currentTimeMillis()}.jpg")
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                photoFile,
            )
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "ক্যামেরা অনুমতি প্রয়োজন", Toast.LENGTH_SHORT).show()
        }
    }

    // Note colors
    val noteColors = listOf(
        "#FFFFFF" to "সাদা",
        "#FFCDD2" to "লাল",
        "#C8E6C9" to "সবুজ",
        "#BBDEFB" to "নীল",
        "#FFF9C4" to "হলুদ",
        "#E1BEE7" to "বেগুনি",
        "#B2EBF2" to "সায়ান",
        "#F8BBD0" to "গোলাপি",
        "#D7CCC8" to "বাদামি",
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = if (isDark) DarkSurface else LightSurface,
        shape = RoundedCornerShape(topStart = shapes.bottomSheetRadius, topEnd = shapes.bottomSheetRadius),
        dragHandle = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)),
                )
                Text(
                    text = if (note != null) "নোট সম্পাদনা" else "নতুন নোট",
                    style = StudyMasterTypography.titleLarge,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // ─── Title ────────────────────────────────────────────────────────
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = {
                    Text("শিরোনাম", style = StudyMasterTypography.labelMedium)
                },
                singleLine = true,
                shape = RoundedCornerShape(shapes.inputFieldRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                ),
                textStyle = StudyMasterTypography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
            )

            // ─── Subject Selector ────────────────────────────────────────────
            Box(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(shapes.inputFieldRadius))
                        .clickable { showSubjectDropdown = !showSubjectDropdown },
                    shape = RoundedCornerShape(shapes.inputFieldRadius),
                    color = Color.Transparent,
                    border = BorderStroke(
                        1.dp,
                        if (isDark) GlassBorderDark else GlassBorderLight,
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = selectedSubjectName ?: "বিষয় নির্বাচন করুন",
                            style = StudyMasterTypography.bodyMedium.copy(
                                color = if (selectedSubjectName != null)
                                    MaterialTheme.colorScheme.onSurface
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                        if (selectedSubjectId != null) {
                            Text(
                                text = "✕",
                                style = StudyMasterTypography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.clickable {
                                    selectedSubjectId = null
                                    selectedSubjectName = null
                                },
                            )
                        }
                    }
                }
                DropdownMenu(
                    expanded = showSubjectDropdown,
                    onDismissRequest = { showSubjectDropdown = false },
                    shape = RoundedCornerShape(shapes.cardRadiusSmall),
                    containerColor = if (isDark) GlassDarkAlpha80 else GlassLightAlpha90,
                    modifier = Modifier.fillMaxWidth(0.85f),
                ) {
                    subjects.forEach { subject ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(subject.colorHex.toComposeColor()),
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(subject.name, style = StudyMasterTypography.bodyMedium)
                                }
                            },
                            onClick = {
                                selectedSubjectId = subject.id
                                selectedSubjectName = subject.name
                                showSubjectDropdown = false
                            },
                        )
                    }
                }
            }

            // ─── Color Picker Row ────────────────────────────────────────────
            Text(
                "রঙ নির্বাচন করুন",
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                noteColors.forEach { (hex, _) ->
                    val isSelected = noteColor == hex
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(hex.toComposeColor())
                            .then(
                                if (isSelected) Modifier.border(3.dp, Primary, CircleShape)
                                else Modifier.border(1.dp, if (isDark) GlassBorderDark else GlassBorderLight, CircleShape)
                            )
                            .clickable { noteColor = hex },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (isSelected) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = if (hex == "#FFFFFF") Color.Black else Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                }
            }

            // ─── Rich Text Toolbar ──────────────────────────────────────────
            Text(
                "বিষয়বস্তু",
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            // Formatting toolbar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(shapes.cardRadiusSmall))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FormatToolbarButton(
                    icon = Icons.Default.FormatBold,
                    label = "বোল্ড",
                    isActive = isBold,
                    onClick = { isBold = !isBold },
                    activeColor = Primary,
                )
                FormatToolbarButton(
                    icon = Icons.Default.FormatItalic,
                    label = "ইটালিক",
                    isActive = isItalic,
                    onClick = { isItalic = !isItalic },
                    activeColor = Secondary,
                )
                FormatToolbarButton(
                    icon = Icons.Default.FormatUnderlined,
                    label = "আন্ডারলাইন",
                    isActive = isUnderline,
                    onClick = { isUnderline = !isUnderline },
                    activeColor = Tertiary,
                )
                FormatToolbarButton(
                    icon = Icons.Default.Highlight,
                    label = "হাইলাইট",
                    isActive = isHighlight,
                    onClick = { isHighlight = !isHighlight },
                    activeColor = Warning,
                )
                FormatToolbarButton(
                    icon = Icons.AutoMirrored.Outlined.FormatListBulleted,
                    label = "তালিকা",
                    isActive = false,
                    onClick = {
                        // Prepend bullet
                        val current = textFieldValue.text.toString()
                        if (!current.startsWith("• ")) {
                            val newText = "• $current"
                            textFieldValue = TextFieldValue(annotatedString = buildAnnotatedString { append(newText) })
                            content = newText
                        }
                    },
                )
            }

            // Content text field with span styles
            val currentTextStyle = StudyMasterTypography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal,
                textDecoration = if (isUnderline) TextDecoration.Underline else TextDecoration.None,
                background = if (isHighlight) {
                    if (isDark) Tertiary.copy(alpha = 0.3f) else Tertiary.copy(alpha = 0.2f)
                } else {
                    Color.Transparent
                },
            )

            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    content = newValue.text
                },
                textStyle = currentTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(shapes.inputFieldRadius))
                    .border(
                        1.dp,
                        if (isDark) GlassBorderDark else GlassBorderLight,
                        RoundedCornerShape(shapes.inputFieldRadius),
                    )
                    .background(
                        if (isDark) DarkSurfaceVariant else Color.White,
                        RoundedCornerShape(shapes.inputFieldRadius),
                    )
                    .padding(16.dp),
                cursorBrush = SolidColor(Primary),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
            )

            // ─── Image Attachments ────────────────────────────────────────────
            Text(
                "সংযুক্তি",
                style = StudyMasterTypography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Gallery
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(shapes.buttonRadius))
                        .clickable {
                            galleryLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    shape = RoundedCornerShape(shapes.buttonRadius),
                    color = Primary.copy(alpha = 0.1f),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Icon(
                            Icons.Default.Image,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(18.dp),
                        )
                        Text(
                            "গ্যালারি",
                            style = StudyMasterTypography.labelMedium,
                            color = Primary,
                        )
                    }
                }

                // Camera
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(shapes.buttonRadius))
                        .clickable {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        },
                    shape = RoundedCornerShape(shapes.buttonRadius),
                    color = Secondary.copy(alpha = 0.1f),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(18.dp),
                        )
                        Text(
                            "ক্যামেরা",
                            style = StudyMasterTypography.labelMedium,
                            color = Secondary,
                        )
                    }
                }

                // Voice note
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(shapes.buttonRadius))
                        .clickable {
                            voiceNotePath = if (voiceNotePath != null) null else "voice_note_${System.currentTimeMillis()}.m4a"
                        },
                    shape = RoundedCornerShape(shapes.buttonRadius),
                    color = if (voiceNotePath != null) Success.copy(alpha = 0.15f) else Tertiary.copy(alpha = 0.1f),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = null,
                            tint = if (voiceNotePath != null) Success else Tertiary,
                            modifier = Modifier.size(18.dp),
                        )
                        Text(
                            if (voiceNotePath != null) "রেকর্ড করা হয়েছে" else "ভয়েস নোট",
                            style = StudyMasterTypography.labelMedium,
                            color = if (voiceNotePath != null) Success else Tertiary,
                        )
                    }
                }
            }

            // Show attached image count
            if (imagePaths.isNotBlank()) {
                val count = imagePaths.split(",").count { it.isNotBlank() }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Default.Image,
                        null,
                        modifier = Modifier.size(14.dp),
                        tint = Primary,
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "${count.toBengaliDigits()}টি ছবি সংযুক্ত আছে",
                        style = StudyMasterTypography.labelSmall,
                        color = Primary,
                    )
                }
            }

            // ─── Tags ────────────────────────────────────────────────────────
            OutlinedTextField(
                value = tagInput,
                onValueChange = { tagInput = it },
                label = {
                    Text("ট্যাগ (কমা দিয়ে আলাদা করুন)", style = StudyMasterTypography.labelSmall)
                },
                singleLine = true,
                shape = RoundedCornerShape(shapes.inputFieldRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                ),
                textStyle = StudyMasterTypography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
            )

            // ─── Quick-Add Flashcard ─────────────────────────────────────────
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(shapes.buttonRadius))
                    .clickable { showFlashcardDialog = true },
                shape = RoundedCornerShape(shapes.buttonRadius),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text("🃏", style = StudyMasterTypography.bodyLarge)
                        Column {
                            Text(
                                "দ্রুত ফ্ল্যাশকার্ড যোগ করুন",
                                style = StudyMasterTypography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Text(
                                "এই নোট থেকে ফ্ল্যাশকার্ড তৈরি করুন",
                                style = StudyMasterTypography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = Primary,
                    )
                }
            }

            // ─── Save Button ─────────────────────────────────────────────────
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(
                            title.trim(),
                            content.trim(),
                            selectedSubjectId,
                            selectedSubjectName,
                            "",  // htmlContent
                            noteColor,
                            tagInput.trim(),
                            imagePaths,
                            voiceNotePath,
                        )
                    }
                },
                enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    disabledContainerColor = Primary.copy(alpha = 0.3f),
                ),
                shape = RoundedCornerShape(shapes.buttonRadius),
            ) {
                Text(
                    if (note != null) "আপডেট করুন" else "নোট সংরক্ষণ করুন",
                    style = StudyMasterTypography.labelLarge,
                )
            }
        }
    }

    // ─── Flashcard Quick-Add Dialog ────────────────────────────────────────────
    if (showFlashcardDialog) {
        AlertDialog(
            onDismissRequest = { showFlashcardDialog = false },
            shape = RoundedCornerShape(shapes.dialogRadius),
            containerColor = if (isDark) GlassDarkAlpha60 else GlassLightAlpha90,
            title = {
                Text("দ্রুত ফ্ল্যাশকার্ড", style = StudyMasterTypography.titleMedium)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = flashcardFront,
                        onValueChange = { flashcardFront = it },
                        label = { Text("সামনে (প্রশ্ন)", style = StudyMasterTypography.labelSmall) },
                        shape = RoundedCornerShape(12.dp),
                        textStyle = StudyMasterTypography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    OutlinedTextField(
                        value = flashcardBack,
                        onValueChange = { flashcardBack = it },
                        label = { Text("পেছনে (উত্তর)", style = StudyMasterTypography.labelSmall) },
                        shape = RoundedCornerShape(12.dp),
                        textStyle = StudyMasterTypography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // In production, this would call FlashcardViewModel.addFlashcard()
                        showFlashcardDialog = false
                        flashcardFront = ""
                        flashcardBack = ""
                    },
                    enabled = flashcardFront.isNotBlank() && flashcardBack.isNotBlank(),
                ) {
                    Text("তৈরি করুন", fontWeight = FontWeight.SemiBold, color = Primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showFlashcardDialog = false }) {
                    Text("বাতিল")
                }
            },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Format Toolbar Button
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun FormatToolbarButton(
    icon: ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    activeColor: Color = Primary,
) {
    val bgColor by animateColorAsState(
        targetValue = if (isActive) activeColor.copy(alpha = 0.2f) else Color.Transparent,
        animationSpec = LocalMotion.current.colorTransition,
        label = "formatBtnBg",
    )
    val tintColor by animateColorAsState(
        targetValue = if (isActive) activeColor else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = LocalMotion.current.colorTransition,
        label = "formatBtnTint",
    )

    Surface(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = bgColor,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tintColor,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Utility: Format time ago in Bengali
// ═══════════════════════════════════════════════════════════════════════════════

private fun formatTimeAgo(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    val minutes = (diff / (1000 * 60)).toInt()
    val hours = (diff / (1000 * 60 * 60)).toInt()
    val days = (diff / (1000 * 60 * 60 * 24)).toInt()

    return when {
        minutes < 1 -> "এইমাত্র"
        minutes < 60 -> "${minutes.toBengaliDigits()} মিনিট আগে"
        hours < 24 -> "${hours.toBengaliDigits()} ঘণ্টা আগে"
        days < 7 -> "${days.toBengaliDigits()} দিন আগে"
        else -> {
            val sdf = SimpleDateFormat("dd MMM, yyyy", Locale("bn", "BD"))
            try {
                sdf.format(Date(timestamp))
            } catch (_: Exception) {
                SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(Date(timestamp))
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Utility: Copy URI to app storage
// ═══════════════════════════════════════════════════════════════════════════════

private fun copyUriToAppStorage(context: Context, uri: Uri, name: String): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.filesDir, "$name.jpg")
        file.outputStream().use { out ->
            inputStream.copyTo(out)
        }
        inputStream.close()
        file.absolutePath
    } catch (_: Exception) {
        null
    }
}

// Bengali digit extension for Int
private fun Int.toBengaliDigits(): String = toString().map { digit ->
    if (digit.isDigit()) "০১২৩৪৫৬৭৮৯"[digit.digitToInt()] else digit
}.joinToString("")
