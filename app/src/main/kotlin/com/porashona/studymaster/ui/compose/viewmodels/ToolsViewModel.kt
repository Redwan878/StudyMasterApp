package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.BoardQuestionDao
import com.porashona.studymaster.data.dao.FormulaDao
import com.porashona.studymaster.data.dao.MediaResourceDao
import com.porashona.studymaster.data.dao.SubjectDao
import com.porashona.studymaster.data.dao.SyllabusChapterDao
import com.porashona.studymaster.data.model.BoardQuestion
import com.porashona.studymaster.data.model.Formula
import com.porashona.studymaster.data.model.SyllabusChapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

data class PeriodicTableElement(
    val symbol: String,
    val name: String,
    val nameBn: String,
    val atomicNumber: Int,
    val atomicMass: Double,
    val category: String
)

data class UnitConversionResult(
    val input: String,
    val output: String,
    val fromUnit: String,
    val toUnit: String
)

data class GPACalculationResult(
    val subjects: List<SubjectGradeInput>,
    val totalGPA: Double,
    val gradePoint: String,
    val letterGrade: String
)

data class SubjectGradeInput(
    val subjectName: String,
    val marks: Double,
    val gradePoint: Double,
    val letterGrade: String
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ToolsViewModel @Inject constructor(
    private val formulaDao: FormulaDao,
    private val boardQuestionDao: BoardQuestionDao,
    private val syllabusChapterDao: SyllabusChapterDao,
    private val mediaResourceDao: MediaResourceDao,
    private val subjectDao: SubjectDao
) : ViewModel() {

    // ─── Formulas ───────────────────────────────────────────────────────
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedSubject = MutableStateFlow<Long?>(null)
    val selectedSubject: StateFlow<Long?> = _selectedSubject.asStateFlow()

    private val _selectedChapter = MutableStateFlow<String?>(null)
    val selectedChapter: StateFlow<String?> = _selectedChapter.asStateFlow()

    val formulas: StateFlow<List<Formula>> = combine(
        _searchQuery.debounce(200),
        _selectedSubject,
        _selectedChapter
    ) { query, subjectId, chapter ->
        Triple(query.trim(), subjectId, chapter)
    }.flatMapLatest { (query, subjectId, chapter) ->
        val flow = when {
            query.isNotEmpty() -> formulaDao.search(query)
            subjectId != null && chapter != null -> formulaDao.getBySubjectAndChapter(subjectId, chapter)
            subjectId != null -> formulaDao.getBySubject(subjectId)
            chapter != null -> formulaDao.getByChapter(chapter)
            else -> formulaDao.getAllFormulas()
        }
        flow.map { list ->
            if (subjectId != null && query.isNotEmpty()) {
                list.filter { it.subjectId == subjectId }
            } else {
                list
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Board Questions ───────────────────────────────────────────────
    private val _boardSearchQuery = MutableStateFlow("")
    val boardSearchQuery: StateFlow<String> = _boardSearchQuery.asStateFlow()

    private val _selectedYear = MutableStateFlow<Int?>(null)
    val selectedYear: StateFlow<Int?> = _selectedYear.asStateFlow()

    private val _selectedBoard = MutableStateFlow<String?>(null)
    val selectedBoard: StateFlow<String?> = _selectedBoard.asStateFlow()

    val availableYears: StateFlow<List<Int>> = boardQuestionDao.getAvailableYears()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableBoards: StateFlow<List<String>> = boardQuestionDao.getAvailableBoards()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _boardQuestions = MutableStateFlow<List<BoardQuestion>>(emptyList())
    val boardQuestions: StateFlow<List<BoardQuestion>> = _boardQuestions.asStateFlow()

    // ─── Chapters ──────────────────────────────────────────────────────
    val chapters: StateFlow<List<SyllabusChapter>> = syllabusChapterDao.getAllChapters()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Periodic Table Result ──────────────────────────────────────────
    private val _periodicTableResult = MutableStateFlow<List<PeriodicTableElement>>(emptyList())
    val periodicTableResult: StateFlow<List<PeriodicTableElement>> = _periodicTableResult.asStateFlow()

    // ─── Unit Conversion Result ────────────────────────────────────────
    private val _unitConversionResult = MutableStateFlow<UnitConversionResult?>(null)
    val unitConversionResult: StateFlow<UnitConversionResult?> = _unitConversionResult.asStateFlow()

    // ─── GPA Result ────────────────────────────────────────────────────
    private val _gpaResult = MutableStateFlow<GPACalculationResult?>(null)
    val gpaResult: StateFlow<GPACalculationResult?> = _gpaResult.asStateFlow()

    // ─── Subjects for filtering ─────────────────────────────────────────
    val subjects: StateFlow<List<com.porashona.studymaster.data.model.Subject>> = subjectDao.getAllSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val formulaSubjects: StateFlow<List<String>> = formulas.map { list ->
        list.mapNotNull { it.subjectName }.distinct()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ═══════════════════════════════════════════════════════════════════════
    // Formula Functions
    // ═══════════════════════════════════════════════════════════════════════

    fun searchFormulas(query: String) {
        _searchQuery.value = query
    }

    fun setFormulaSubjectFilter(subjectId: Long?) {
        _selectedSubject.value = subjectId
    }

    fun setFormulaChapterFilter(chapter: String?) {
        _selectedChapter.value = chapter
    }

    fun toggleFormulaFavorite(formulaId: Long) {
        viewModelScope.launch {
            formulaDao.toggleFavorite(formulaId)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Board Question Functions
    // ═══════════════════════════════════════════════════════════════════════

    fun getBoardQuestions() {
        viewModelScope.launch {
            val query = _boardSearchQuery.value.trim()
            val year = _selectedYear.value
            val board = _selectedBoard.value

            val flow = when {
                query.isNotEmpty() -> boardQuestionDao.search(query)
                year != null -> boardQuestionDao.getByYear(year)
                board != null -> boardQuestionDao.getByBoard(board)
                else -> boardQuestionDao.getAllQuestions()
            }

            val questions = flow.first()
            _boardQuestions.value = when {
                year != null && query.isNotEmpty() -> questions.filter { it.year == year }
                board != null && year != null -> questions.filter { it.year == year && it.board == board }
                else -> questions
            }
        }
    }

    fun getPreviousYear(year: Int, board: String? = null) {
        _selectedYear.value = year
        _selectedBoard.value = board
        getBoardQuestions()
    }

    fun searchBoardQuestions(query: String) {
        _boardSearchQuery.value = query
        getBoardQuestions()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // GPA Calculator
    // ═══════════════════════════════════════════════════════════════════════

    fun calculateGPA(subjectMarks: List<SubjectGradeInput>) {
        val gradeInputs = subjectMarks.map { input ->
            val gp = marksToGradePoint(input.marks)
            val letter = marksToLetterGrade(input.marks)
            input.copy(gradePoint = gp, letterGrade = letter)
        }

        val totalGPA = if (gradeInputs.isNotEmpty()) {
            gradeInputs.map { it.gradePoint }.average()
        } else 0.0

        _gpaResult.value = GPACalculationResult(
            subjects = gradeInputs,
            totalGPA = totalGPA,
            gradePoint = String.format("%.2f", totalGPA),
            letterGrade = gpaToLetterGrade(totalGPA)
        )
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Periodic Table Search
    // ═══════════════════════════════════════════════════════════════════════

    fun searchPeriodicTable(query: String) {
        // Periodic table data is hardcoded (chemical elements)
        val allElements = getPeriodicTableData()
        val q = query.lowercase()
        _periodicTableResult.value = allElements.filter { element ->
            element.symbol.lowercase().contains(q) ||
                    element.name.lowercase().contains(q) ||
                    element.nameBn.contains(q) ||
                    element.atomicNumber.toString() == q
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Unit Converter
    // ═══════════════════════════════════════════════════════════════════════

    fun convertUnit(value: Double, fromUnit: String, toUnit: String) {
        val conversions = mapOf(
            // Length
            "m_to_cm" to 100.0, "cm_to_m" to 0.01,
            "km_to_m" to 1000.0, "m_to_km" to 0.001,
            "m_to_mm" to 1000.0, "mm_to_m" to 0.001,
            "inch_to_cm" to 2.54, "cm_to_inch" to 0.3937,
            "feet_to_m" to 0.3048, "m_to_feet" to 3.28084,
            // Mass
            "kg_to_g" to 1000.0, "g_to_kg" to 0.001,
            "kg_to_mg" to 1_000_000.0, "mg_to_kg" to 0.000001,
            "pound_to_kg" to 0.453592, "kg_to_pound" to 2.20462,
            // Temperature (special handling)
            "celsius_to_fahrenheit" to -1.0, // Special case
            "fahrenheit_to_celsius" to -1.0,
            // Time
            "hour_to_min" to 60.0, "min_to_hour" to 1.0 / 60,
            "hour_to_sec" to 3600.0, "sec_to_hour" to 1.0 / 3600,
            // Volume
            "liter_to_ml" to 1000.0, "ml_to_liter" to 0.001
        )

        val key = "${fromUnit.lowercase()}_to_${toUnit.lowercase()}"
        val factor = conversions[key]

        val resultValue = when {
            key == "celsius_to_fahrenheit" -> value * 9 / 5 + 32
            key == "fahrenheit_to_celsius" -> (value - 32) * 5 / 9
            factor != null -> value * factor
            else -> value // No conversion found
        }

        _unitConversionResult.value = UnitConversionResult(
            input = "$value $fromUnit",
            output = String.format("%.4f %s", resultValue, toUnit),
            fromUnit = fromUnit,
            toUnit = toUnit
        )
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Helpers
    // ═══════════════════════════════════════════════════════════════════════

    private fun marksToGradePoint(marks: Double): Double = when {
        marks >= 80 -> 5.0
        marks >= 70 -> 4.0
        marks >= 60 -> 3.5
        marks >= 50 -> 3.0
        marks >= 40 -> 2.0
        marks >= 33 -> 1.0
        else -> 0.0
    }

    private fun marksToLetterGrade(marks: Double): String = when {
        marks >= 80 -> "A+"
        marks >= 70 -> "A"
        marks >= 60 -> "A-"
        marks >= 50 -> "B"
        marks >= 40 -> "C"
        marks >= 33 -> "D"
        else -> "F"
    }

    private fun gpaToLetterGrade(gpa: Double): String = when {
        gpa >= 4.5 -> "A+"
        gpa >= 3.75 -> "A"
        gpa >= 3.25 -> "A-"
        gpa >= 2.75 -> "B"
        gpa >= 2.0 -> "C"
        gpa >= 1.0 -> "D"
        else -> "F"
    }

    private fun getPeriodicTableData(): List<PeriodicTableElement> = listOf(
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
        PeriodicTableElement("U", "Uranium", "ইউরেনিয়াম", 92, 238.029, "Actinide")
    )
}