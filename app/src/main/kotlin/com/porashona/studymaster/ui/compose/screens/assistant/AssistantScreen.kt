/*
package com.porashona.studymaster.ui.compose.screens.assistant

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Weakness
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.porashona.studymaster.data.model.Formula
import com.porashona.studymaster.data.model.Subject
import com.porashona.studymaster.ui.compose.components.GlassCardVariant
import com.porashona.studymaster.ui.compose.components.GlassmorphicCard
import com.porashona.studymaster.ui.compose.components.SubjectChip
import com.porashona.studymaster.ui.compose.theme.BengaliFontFamily
import com.porashona.studymaster.ui.compose.theme.DarkSurfaceVariant
import com.porashona.studymaster.ui.compose.theme.EnglishFontFamily
import com.porashona.studymaster.ui.compose.theme.Error
import com.porashona.studymaster.ui.compose.theme.GlassBorderDark
import com.porashona.studymaster.ui.compose.theme.GlassBorderLight
import com.porashona.studymaster.ui.compose.theme.GlassDarkAlpha60
import com.porashona.studymaster.ui.compose.theme.GlassDarkAlpha80
import com.porashona.studymaster.ui.compose.theme.GlassLightAlpha90
import com.porashona.studymaster.ui.compose.theme.LocalGlassShapes
import com.porashona.studymaster.ui.compose.theme.LocalMotion
import com.porashona.studymaster.ui.compose.theme.Primary
import com.porashona.studymaster.ui.compose.theme.Secondary
import com.porashona.studymaster.ui.compose.theme.StudyMasterTypography
import com.porashona.studymaster.ui.compose.theme.Success
import com.porashona.studymaster.ui.compose.theme.Warning
import com.porashona.studymaster.ui.compose.theme.isDark
import com.porashona.studymaster.ui.compose.theme.toBengaliDigits
import com.porashona.studymaster.ui.compose.viewmodels.AssistantEvent
import com.porashona.studymaster.ui.compose.viewmodels.AssistantViewModel
import com.porashona.studymaster.ui.compose.viewmodels.ChatMessage
import com.porashona.studymaster.ui.compose.viewmodels.WeakTopic
import kotlinx.coroutines.launch

// ═══════════════════════════════════════════════════════════════════════════════
// AI Assistant Screen — full on-device assistant with chat, formula
// explanation, MCQ generation, note summarisation, weak topic detection,
// doubt solving, answer-structure feedback, and voice input.
// All text in Bengali.
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun AssistantScreen(
    onBack: () -> Unit = {},
    viewModel: AssistantViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val motion = LocalMotion.current
    val glassShapes = LocalGlassShapes.current
    val isDark = MaterialTheme.colorScheme.isDark

    val chatMessages by viewModel.chatMessages.collectAsState()
    val isTyping by viewModel.isTyping.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val weakTopics by viewModel.weakTopics.collectAsState()

    var inputText by remember { mutableStateOf("") }
    var showFormulaSelector by remember { mutableStateOf(false) }
    var showNoteSelector by remember { mutableStateOf(false) }
    var showMcqSubjectSelector by remember { mutableStateOf(false) }
    var showWeakTopicsSheet by remember { mutableStateOf(false) }
    var showAnswerFeedbackSheet by remember { mutableStateOf(false) }
    var showClearChatDialog by remember { mutableStateOf(false) }
    var isListening by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(chatMessages.size, isTyping) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size)
        }
    }

    // Handle events
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is AssistantEvent.WeakTopicsDetected -> {
                    showWeakTopicsSheet = true
                }
            }
        }
    }

    // Speech Recognizer
    val micPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val speechRecognizer = remember {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            SpeechRecognizer.createSpeechRecognizer(context)
        } else null
    }

    DisposableEffect(Unit) {
        onDispose {
            try { speechRecognizer?.destroy() } catch (_: Exception) {}
        }
    }

    fun startVoiceInput() {
        if (micPermissionState.status.isGranted) {
            val sr = speechRecognizer ?: return
            isListening = true

            sr.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {
                    isListening = false
                }
                override fun onError(error: Int) {
                    isListening = false
                    scope.launch {
                        snackbarHostState.showSnackbar("ভয়েস ইনপুট ব্যর্থ হয়েছে। আবার চেষ্টা করুন।")
                    }
                }
                override fun onResults(results: Bundle?) {
                    isListening = false
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        val transcript = matches.first()
                        inputText = transcript
                        viewModel.voiceInput(transcript)
                    }
                }
                override fun onPartialResults(partialResults: Bundle?) {
                    val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        inputText = matches.first()
                    }
                }
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })

            val intent = Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, "bn-BD")
                putExtra(android.speech.RecognizerIntent.EXTRA_PROMPT, "আপনার প্রশ্ন বলুন...")
            }
            try {
                sr.startListening(intent)
            } catch (e: Exception) {
                isListening = false
                scope.launch {
                    snackbarHostState.showSnackbar("ভয়েস শুরু করা যায়নি।")
                }
            }
        } else {
            micPermissionState.launchPermissionRequest()
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Main Scaffold
    // ═══════════════════════════════════════════════════════════════════════

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AI সহকারী",
                        style = StudyMasterTypography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরুন",
                        )
                    }
                },
                actions = {
                    if (chatMessages.isNotEmpty()) {
                        IconButton(onClick = { showClearChatDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "চ্যাট মুছুন",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            // ── Suggestion Chips ────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isDark) GlassDarkAlpha80 else GlassLightAlpha90
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    SuggestionChip(
                        label = "সূত্র ব্যাখ্যা",
                        icon = Icons.Default.Science,
                        onClick = { showFormulaSelector = true },
                    )
                    SuggestionChip(
                        label = "MCQ তৈরি",
                        icon = Icons.Default.QuestionMark,
                        onClick = { showMcqSubjectSelector = true },
                    )
                    SuggestionChip(
                        label = "নোট সারাংশ",
                        icon = Icons.Default.Summarize,
                        onClick = { showNoteSelector = true },
                    )
                    SuggestionChip(
                        label = "ডাউট সলভ",
                        icon = Icons.Default.Visibility,
                        onClick = { /* free text - just type */ },
                    )
                    SuggestionChip(
                        label = "দুর্বল টপিক",
                        icon = Icons.Default.Weakness,
                        onClick = {
                            viewModel.detectWeakTopics()
                        },
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 6.dp),
                    color = if (isDark) GlassBorderDark else GlassBorderLight,
                )

                // ── Input Row ────────────────────────────────────────────────
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Voice button
                    IconButton(
                        onClick = { startVoiceInput() },
                        modifier = Modifier.size(44.dp),
                    ) {
                        val micColor = if (isListening) Error else MaterialTheme.colorScheme.onSurfaceVariant
                        Icon(
                            imageVector = if (isListening) Icons.Default.Mic else Icons.Default.MicNone,
                            contentDescription = "ভয়েস ইনপুট",
                            tint = micColor,
                            modifier = if (isListening) Modifier.size(28.dp) else Modifier.size(24.dp),
                        )
                    }

                    // Text field
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = {
                            Text(
                                text = "আপনার প্রশ্ন লিখুন...",
                                style = StudyMasterTypography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        textStyle = StudyMasterTypography.bodyMedium,
                        singleLine = true,
                        shape = RoundedCornerShape(glassShapes.inputFieldRadius),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (isDark) GlassBorderDark else GlassBorderLight,
                            focusedBorderColor = Primary,
                            cursorColor = Primary,
                        ),
                    )

                    Spacer(Modifier.width(6.dp))

                    // Send button
                    IconButton(
                        onClick = {
                            val text = inputText.trim()
                            if (text.isNotBlank()) {
                                viewModel.solveDoubt(text)
                                inputText = ""
                            }
                        },
                        enabled = inputText.isNotBlank(),
                        modifier = Modifier.size(44.dp),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "পাঠান",
                            tint = if (inputText.isNotBlank()) Primary
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        // ── Chat Messages List ─────────────────────────────────────────────
        if (chatMessages.isEmpty() && !isTyping) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "🤖",
                    fontSize = 64.sp,
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "AI সহকারীতে স্বাগতম!",
                    style = StudyMasterTypography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "আমি আপনার সিলেবাস অনুযায়ী সূত্র ব্যাখ্যা, ডাউট সল্ভ, MCQ তৈরি, নোট সারাংশ এবং দুর্বল টপিক শনাক্ত করতে সাহায্য করতে পারি।",
                    style = StudyMasterTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(24.dp))

                // Quick-start options
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    QuickStartCard(
                        emoji = "📐",
                        title = "সূত্র ব্যাখ্যা",
                        subtitle = "যেকোনো সূত্রের বাংলায় ব্যাখ্যা পান",
                        onClick = { showFormulaSelector = true },
                    )
                    QuickStartCard(
                        emoji = "📝",
                        title = "MCQ তৈরি করুন",
                        subtitle = "আপনার নোট থেকে অটো MCQ",
                        onClick = { showMcqSubjectSelector = true },
                    )
                    QuickStartCard(
                        emoji = "📋",
                        title = "নোট সারাংশ",
                        subtitle = "দীর্ঘ নোটের সংক্ষিপ্ত সারাংশ",
                        onClick = { showNoteSelector = true },
                    )
                    QuickStartCard(
                        emoji = "🔍",
                        title = "দুর্বল টপিক খুঁজুন",
                        subtitle = "আপনার পড়াশোনার বিশ্লেষণ",
                        onClick = { viewModel.detectWeakTopics() },
                    )
                }
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(
                    items = chatMessages,
                    key = { it.id },
                ) { message ->
                    ChatBubble(message = message)
                }

                // Typing indicator
                if (isTyping) {
                    item(key = "typing") {
                        TypingIndicator()
                    }
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Bottom Sheets & Dialogs
    // ═══════════════════════════════════════════════════════════════════════

    // ── Formula Selector ──────────────────────────────────────────────────
    if (showFormulaSelector) {
        FormulaSelectorSheet(
            subjects = subjects,
            onFormulaSelected = { formulaId ->
                showFormulaSelector = false
                viewModel.explainFormula(formulaId)
            },
            onDismiss = { showFormulaSelector = false },
        )
    }

    // ── Note Selector ─────────────────────────────────────────────────────
    if (showNoteSelector) {
        NoteSelectorSheet(
            onNoteSelected = { noteId ->
                showNoteSelector = false
                viewModel.summarizeNote(noteId)
            },
            onDismiss = { showNoteSelector = false },
        )
    }

    // ── MCQ Subject Selector ──────────────────────────────────────────────
    if (showMcqSubjectSelector) {
        McqSubjectSelectorSheet(
            subjects = subjects,
            onSubjectSelected = { subjectId, _ ->
                showMcqSubjectSelector = false
                viewModel.generateMCQs(subjectId = subjectId, chapterName = null, count = 5)
            },
            onDismiss = { showMcqSubjectSelector = false },
        )
    }

    // ── Weak Topics Sheet ─────────────────────────────────────────────────
    if (showWeakTopicsSheet) {
        WeakTopicsSheet(
            weakTopics = weakTopics,
            onDismiss = { showWeakTopicsSheet = false },
        )
    }

    // ── Answer Feedback Sheet ─────────────────────────────────────────────
    if (showAnswerFeedbackSheet) {
        AnswerFeedbackSheet(
            onAnalyze = { questionText, answerText ->
                showAnswerFeedbackSheet = false
                // Analyze answer structure
                val feedback = analyzeAnswerStructure(answerText)
                viewModel.solveDoubt("আমার উত্তর:\n$answerText\n\nপ্রশ্ন: $questionText")
            },
            onDismiss = { showAnswerFeedbackSheet = false },
        )
    }

    // ── Clear Chat Dialog ─────────────────────────────────────────────────
    if (showClearChatDialog) {
        AlertDialog(
            onDismissRequest = { showClearChatDialog = false },
            title = {
                Text(
                    text = "চ্যাট মুছুন",
                    style = StudyMasterTypography.titleMedium,
                )
            },
            text = {
                Text(
                    text = "আপনি কি সব চ্যাট মেসেজ মুছে ফেলতে চান? এটি পূর্বাবস্থায় ফেরানো যাবে না।",
                    style = StudyMasterTypography.bodyMedium,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearChat()
                        showClearChatDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Error),
                ) {
                    Text("মুছুন", style = StudyMasterTypography.labelLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearChatDialog = false }) {
                    Text("বাতিল", style = StudyMasterTypography.labelLarge)
                }
            },
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Chat Bubble — user (right, accent) / assistant (left, darker glass)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ChatBubble(
    message: ChatMessage,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    val shapes = LocalGlassShapes.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start,
    ) {
        if (!message.isFromUser) {
            // Assistant avatar
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom),
                shape = CircleShape,
                color = Primary.copy(alpha = 0.15f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🤖",
                        fontSize = 16.sp,
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .then(
                    if (message.isFromUser) {
                        Modifier.align(Alignment.End)
                    } else {
                        Modifier.align(Alignment.Start)
                    }
                ),
            horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start,
        ) {
            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (message.isFromUser) 16.dp else 4.dp,
                    bottomEnd = if (message.isFromUser) 4.dp else 16.dp,
                ),
                color = if (message.isFromUser) {
                    Primary.copy(alpha = if (isDark) 0.25f else 0.15f)
                } else {
                    if (isDark) DarkSurfaceVariant else GlassLightAlpha90
                },
                border = if (message.isFromUser) {
                    BorderStroke(1.dp, Primary.copy(alpha = 0.3f))
                } else {
                    BorderStroke(1.dp, if (isDark) GlassBorderDark else GlassBorderLight)
                },
                contentColor = if (message.isFromUser) {
                    Primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // Process message text for simple markdown-like formatting
                    val textParts = message.text.split("\n")
                    textParts.forEachIndexed { index, line ->
                        val trimmedLine = line.trim()
                        when {
                            trimmedLine.startsWith("**") && trimmedLine.endsWith("**") -> {
                                Text(
                                    text = trimmedLine.removeSurrounding("**"),
                                    style = StudyMasterTypography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                    ),
                                    modifier = Modifier.padding(
                                        top = if (index > 0) 4.dp else 0.dp,
                                        bottom = 2.dp,
                                    ),
                                )
                            }
                            trimmedLine.startsWith("• ") || trimmedLine.startsWith("- ") -> {
                                Text(
                                    text = "  $trimmedLine",
                                    style = StudyMasterTypography.bodyMedium,
                                    modifier = Modifier.padding(top = 1.dp),
                                )
                            }
                            trimmedLine.startsWith("_") && trimmedLine.endsWith("_") -> {
                                Text(
                                    text = trimmedLine.removeSurrounding("_"),
                                    style = StudyMasterTypography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    ),
                                    modifier = Modifier.padding(top = 4.dp),
                                )
                            }
                            trimmedLine.isNotBlank() -> {
                                Text(
                                    text = trimmedLine,
                                    style = StudyMasterTypography.bodyMedium,
                                    modifier = Modifier.padding(
                                        top = if (index > 0) 2.dp else 0.dp,
                                    ),
                                )
                            }
                            else -> {
                                Spacer(Modifier.height(4.dp))
                            }
                        }
                    }

                    // Show related questions reveal button
                    if (message.relatedQuestions != null && message.relatedQuestions.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Success.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, Success.copy(alpha = 0.3f)),
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "✅ সঠিক উত্তরসমূহ:",
                                    style = StudyMasterTypography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Success,
                                    ),
                                )
                                Spacer(Modifier.height(4.dp))
                                message.relatedQuestions.forEachIndexed { idx, q ->
                                    val optionLabels = mapOf(1 to "ক", 2 to "খ", 3 to "গ", 4 to "ঘ")
                                    Text(
                                        text = "${idx + 1}. ${optionLabels[q.correctOption]}) ${getCorrectOptionText(q)}",
                                        style = StudyMasterTypography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurface,
                                        ),
                                        modifier = Modifier.padding(vertical = 1.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Timestamp
            Text(
                text = formatTimestamp(message.timestamp),
                style = StudyMasterTypography.labelSmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    fontFamily = EnglishFontFamily,
                ),
                modifier = Modifier.padding(
                    start = if (message.isFromUser) 0.dp else 4.dp,
                    end = if (message.isFromUser) 4.dp else 0.dp,
                    top = 2.dp,
                    bottom = 2.dp,
                ),
            )
        }

        if (message.isFromUser) {
            Spacer(Modifier.width(8.dp))
            // User avatar
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Bottom),
                shape = CircleShape,
                color = Secondary.copy(alpha = 0.15f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "👤",
                        fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Typing Indicator — three animated dots
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TypingIndicator() {
    val isDark = MaterialTheme.colorScheme.isDark
    val infiniteTransition = rememberInfiniteTransition(label = "typingDots")

    Row(
        modifier = Modifier.padding(start = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp,
            ),
            color = if (isDark) DarkSurfaceVariant else GlassLightAlpha90,
            border = BorderStroke(1.dp, if (isDark) GlassBorderDark else GlassBorderLight),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                repeat(3) { index ->
                    val animatedY by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = -6f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 400,
                                delayMillis = index * 150,
                                easing = FastOutSlowInEasing,
                            ),
                            repeatMode = RepeatMode.Reverse,
                        ),
                        label = "dot$index",
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .graphicsLayer { translationY = animatedY }
                            .background(
                                color = Primary.copy(alpha = 0.7f),
                                shape = CircleShape,
                            ),
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Suggestion Chip
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SuggestionChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark
    Surface(
        shape = RoundedCornerShape(LocalGlassShapes.current.chipRadius),
        color = if (isDark) Primary.copy(alpha = 0.08f) else Primary.copy(alpha = 0.06f),
        border = BorderStroke(1.dp, Primary.copy(alpha = 0.2f)),
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = Primary,
            )
            Text(
                text = label,
                style = StudyMasterTypography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = Primary,
                ),
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Quick-Start Card (empty state)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun QuickStartCard(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.isDark

    GlassmorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        variant = GlassCardVariant.OUTLINED,
        cornerRadius = LocalGlassShapes.current.cardRadiusSmall,
        padding = 14.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Text(text = emoji, fontSize = 28.sp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = StudyMasterTypography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = subtitle,
                    style = StudyMasterTypography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Formula Selector Bottom Sheet
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormulaSelectorSheet(
    subjects: List<Subject>,
    onFormulaSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedSubjectId by remember { mutableStateOf<Long?>(null) }
    var formulas by remember { mutableStateOf<List<Formula>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    // We can't directly access DAO from here, so we use the view model approach
    // via callback. For a simpler approach, we load formulas by subject.

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
        ) {
            Text(
                text = "📐 সূত্র নির্বাচন করুন",
                style = StudyMasterTypography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp),
            )

            // Subject filter
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(subjects) { subject ->
                    SubjectChip(
                        subjectName = subject.name,
                        colorHex = subject.colorHex,
                        selected = selectedSubjectId == subject.id,
                        onClick = {
                            selectedSubjectId = subject.id
                            // Formulas will be loaded via a callback approach
                        },
                    )
                }
            }

            if (selectedSubjectId != null) {
                Spacer(Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "এই বিষয়ের সূত্র দেখতে নির্বাচন করুন।",
                    style = StudyMasterTypography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                // The actual formula list loading would happen via the ViewModel
                // For now we provide the selected subject ID for the parent to handle
                Button(
                    onClick = {
                        // Use a generic formula ID — in production this would show a
                        // list of formulas loaded by subject. Here we signal selection.
                        scope.launch { sheetState.hide() }.invokeOnCompletion { onDismiss() }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "প্রথম সূত্রটি ব্যাখ্যা করুন",
                        style = StudyMasterTypography.labelLarge,
                    )
                }
            } else {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "উপরে থেকে একটি বিষয় নির্বাচন করুন",
                    style = StudyMasterTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Note Selector Bottom Sheet
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteSelectorSheet(
    onNoteSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // We accept a list of notes from parent. Since the screen calls this,
    // we'll pass a callback to load notes from the ViewModel in a real
    // production setup. Here we provide the UI shell.
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
        ) {
            Text(
                text = "📋 নোট নির্বাচন করুন",
                style = StudyMasterTypography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            Text(
                text = "যে নোটটির সারাংশ চান সেটি নির্বাচন করুন।",
                style = StudyMasterTypography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            // In production this would be populated with actual notes from ViewModel
            // via a StateFlow. The selection triggers onNoteSelected(noteId).
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "নোট লোড হচ্ছে...",
                    style = StudyMasterTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// MCQ Subject Selector Bottom Sheet
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun McqSubjectSelectorSheet(
    subjects: List<Subject>,
    onSubjectSelected: (Long, String) -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
        ) {
            Text(
                text = "📝 বিষয় নির্বাচন করুন",
                style = StudyMasterTypography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = "কোন বিষয় থেকে MCQ তৈরি করতে চান?",
                style = StudyMasterTypography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            if (subjects.isEmpty()) {
                Text(
                    text = "কোনো বিষয় পাওয়া যায়নি। আগে বিষয় যোগ করুন।",
                    style = StudyMasterTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                )
            } else {
                subjects.forEach { subject ->
                    Surface(
                        shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                scope.launch { sheetState.hide() }
                                    .invokeOnCompletion {
                                        onSubjectSelected(subject.id, subject.name)
                                    }
                            },
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Text(
                                text = subject.icon,
                                fontSize = 24.sp,
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = subject.name,
                                    style = StudyMasterTypography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                    ),
                                )
                                Text(
                                    text = "${subject.chaptersCompleted}/${subject.chaptersTotal} অধ্যায় সম্পন্ন",
                                    style = StudyMasterTypography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            Text(
                                text = "৫টি MCQ",
                                style = StudyMasterTypography.labelSmall.copy(
                                    color = Primary,
                                    fontWeight = FontWeight.SemiBold,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Weak Topics Sheet — ranked list of weak topics
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeakTopicsSheet(
    weakTopics: List<WeakTopic>,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
        ) {
            Text(
                text = "📊 দুর্বল টপিক বিশ্লেষণ",
                style = StudyMasterTypography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            Text(
                text = "আপনার সিলেবাস সম্পূর্ণতা ও পরীক্ষার ফলাফলের ভিত্তিতে",
                style = StudyMasterTypography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            if (weakTopics.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "✅ আপনার সব টপিক ভালো অবস্থায় আছে! দুর্বল কোনো টপিক পাওয়া যায়নি।",
                        style = StudyMasterTypography.bodyMedium,
                        color = Success,
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                weakTopics.forEachIndexed { index, topic ->
                    val progressColor = when {
                        topic.averageScore < 30 -> Error
                        topic.averageScore < 50 -> Warning
                        topic.averageScore < 70 -> Secondary
                        else -> Success
                    }

                    Surface(
                        shape = RoundedCornerShape(LocalGlassShapes.current.cardRadiusSmall),
                        color = progressColor.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, progressColor.copy(alpha = 0.2f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // Rank
                            Surface(
                                shape = CircleShape,
                                color = progressColor.copy(alpha = 0.2f),
                            ) {
                                Text(
                                    text = (index + 1).toBengaliDigits(),
                                    style = StudyMasterTypography.labelSmall.copy(
                                        fontFamily = EnglishFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = progressColor,
                                    ),
                                    modifier = Modifier.padding(6.dp),
                                )
                            }

                            Spacer(Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = topic.chapterName,
                                    style = StudyMasterTypography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                    ),
                                )
                                Text(
                                    text = topic.subjectName,
                                    style = StudyMasterTypography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }

                            // Progress bar
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(6.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(3.dp),
                                    ),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(topic.averageScore / 100f)
                                            .height(6.dp)
                                            .background(
                                                color = progressColor,
                                                shape = RoundedCornerShape(3.dp),
                                            ),
                                    )
                                }
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "${topic.averageScore.toBengaliDigits(0)}%",
                                style = StudyMasterTypography.labelSmall.copy(
                                    fontFamily = EnglishFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = progressColor,
                                ),
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))
                Text(
                    text = "💡 পরামর্শ: দুর্বল টপিকগুলোতে বেশি সময় দিন এবং মডেল টেস্ট দিন।",
                    style = StudyMasterTypography.bodySmall.copy(
                        color = Primary,
                        fontWeight = FontWeight.Medium,
                    ),
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Answer Structure Feedback Sheet
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnswerFeedbackSheet(
    onAnalyze: (String, String) -> Unit,
    onDismiss: () -> Unit,
) {
    var questionText by remember { mutableStateOf("") }
    var answerText by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
        ) {
            Text(
                text = "📝 উত্তর কাঠামো বিশ্লেষণ",
                style = StudyMasterTypography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp),
            )

            OutlinedTextField(
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text("প্রশ্ন", style = StudyMasterTypography.bodySmall) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                textStyle = StudyMasterTypography.bodyMedium,
                shape = RoundedCornerShape(LocalGlassShapes.current.inputFieldRadius),
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = answerText,
                onValueChange = { answerText = it },
                label = { Text("আপনার উত্তর", style = StudyMasterTypography.bodySmall) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                textStyle = StudyMasterTypography.bodyMedium,
                shape = RoundedCornerShape(LocalGlassShapes.current.inputFieldRadius),
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    if (questionText.isNotBlank() && answerText.isNotBlank()) {
                        onAnalyze(questionText, answerText)
                    }
                },
                enabled = questionText.isNotBlank() && answerText.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(LocalGlassShapes.current.buttonRadius),
            ) {
                Text(
                    text = "বিশ্লেষণ করুন",
                    style = StudyMasterTypography.labelLarge,
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// Utility Helpers
// ═══════════════════════════════════════════════════════════════════════════════

private fun getCorrectOptionText(q: com.porashona.studymaster.data.model.QuestionBank): String {
    return when (q.correctOption) {
        1 -> q.optionA
        2 -> q.optionB
        3 -> q.optionC
        4 -> q.optionD
        else -> ""
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.US)
    return sdf.format(java.util.Date(timestamp))
}

private fun analyzeAnswerStructure(answerText: String): String {
    val sentences = answerText.split(Regex("[।\\n]+")).filter { it.isNotBlank() }
    val wordCount = answerText.split(Regex("\\s+")).filter { it.isNotBlank() }.size

    return buildString {
        append("📋 **উত্তর কাঠামো বিশ্লেষণ**\n\n")
        append("• মোট বাক্য: ${sentences.size.toBengaliDigits()}\n")
        append("• মোট শব্দ: ${wordCount.toBengaliDigits()}\n")
        append("• গড় বাক্য দৈর্ঘ্য: ${(wordCount.toDouble() / sentences.size.coerceAtLeast(1)).toBengaliDigits(1)} শব্দ\n\n")

        if (sentences.size < 3) {
            append("⚠️ উত্তর খুব ছোট। আরও বিস্তারিত লিখুন।\n")
        } else if (sentences.size < 6) {
            append("✅ উত্তরের দৈর্ঘ্য পর্যাপ্ত।\n")
        } else {
            append("✅ উত্তর বিস্তারিত ও ভালো।\n")
        }

        if (!answerText.contains("কারণ") && !answerText.contains("যেহেতু") && !answerText.contains("কারণে")) {
            append("\n💡 পরামর্শ: কারণ বা যুক্তি যোগ করলে উত্তর আরও ভালো হবে।")
        }
    }
}
*/