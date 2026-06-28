package com.vtol.zaka.presentation.quiz

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ─── Loading Step ─────────────────────────────────────────────────────────────
data class LoadingStep(
    val icon: String,
    val titleAr: String,
    val descriptionAr: String,
    val durationMs: Long = 1500
)

// ─── Steps per source ─────────────────────────────────────────────────────────
val pdfSteps = listOf(
    LoadingStep("📄", "قراءة الملف",       "جاري فتح وتحليل ملف PDF",              1500),
    LoadingStep("🔍", "استخراج المحتوى",   "الذكاء الاصطناعي يقرأ المحتوى",        2000),
    LoadingStep("🧠", "فهم المادة",        "تحليل المفاهيم الرئيسية",              1500),
    LoadingStep("✍️", "إنشاء الأسئلة",    "توليد أسئلة مخصصة لك",               2000),
    LoadingStep("✅", "جاهز!",             "اختبارك جاهز للبدء",                  800)
)

val imageSteps = listOf(
    LoadingStep("🖼️", "تحليل الصورة",     "جاري فحص محتوى الصورة",               1500),
    LoadingStep("📝", "قراءة النص",        "استخراج النص من الصورة",               2000),
    LoadingStep("🧠", "فهم المحتوى",       "الذكاء الاصطناعي يحلل المادة",         1500),
    LoadingStep("✍️", "إنشاء الأسئلة",    "توليد أسئلة مخصصة لك",               2000),
    LoadingStep("✅", "جاهز!",             "اختبارك جاهز للبدء",                  800)
)

val topicSteps = listOf(
    LoadingStep("📚", "اختيار الموضوع",    "تحضير محتوى الموضوع",                 1000),
    LoadingStep("🧠", "توليد الأسئلة",     "الذكاء الاصطناعي يبدع أسئلة ذكية",    2000),
    LoadingStep("🎯", "ضبط الصعوبة",       "تخصيص مستوى الأسئلة لك",              1500),
    LoadingStep("✅", "جاهز!",             "اختبارك جاهز للبدء",                  800)
)

// ─── Fun facts per topic ──────────────────────────────────────────────────────
fun getFunFacts(topic: String): List<String> = when {
    topic.contains("علوم") || topic.contains("كيمياء") || topic.contains("فيزياء") -> listOf(
        "الأكسجين يشكّل نحو 46% من كتلة القشرة الأرضية",
        "الضوء يسافر بسرعة 300,000 كيلومتر في الثانية",
        "جسم الإنسان يحتوي على ما يكفي من الكربون لصنع 900 قلم رصاص"
    )
    topic.contains("رياضيات") -> listOf(
        "الرقم صفر اخترعه علماء العرب وأدخلوه إلى أوروبا",
        "π (باي) يحتوي على لا نهاية من الأرقام بعد الفاصلة",
        "مجموع زوايا أي مثلث يساوي 180 درجة دائماً"
    )
    topic.contains("تاريخ") -> listOf(
        "بغداد كانت أكبر مدينة في العالم في القرن العاشر الميلادي",
        "ابن بطوطة قطع مسافة أكبر من ماركو بولو بثلاثة أضعاف",
        "الحضارة الإسلامية أنقذت كثيراً من العلوم اليونانية من الضياع"
    )
    topic.contains("لغة") -> listOf(
        "اللغة العربية من أكثر اللغات ثراءً في العالم بعدد مفرداتها",
        "يوجد أكثر من 400 مليون متحدث باللغة العربية حول العالم",
        "القرآن الكريم أقوى عامل في الحفاظ على اللغة العربية عبر القرون"
    )
    else -> listOf(
        "المذاكرة المنتظمة أفضل بكثير من الحشو قبل الامتحان",
        "النوم الكافي يساعد الدماغ على تثبيت المعلومات",
        "تقسيم المادة إلى أجزاء صغيرة يجعل الحفظ أسهل",
        "الاختبار الذاتي من أفضل طرق المراجعة الفعالة"
    )
}

@Composable
fun QuizLoadingContent(
    source: LoadingSource = LoadingSource.PDF,
    topic: String = "",
    state: QuizUiState,
    onReadyToNavigate: () -> Unit
) {
    val context = LocalContext.current
    val steps = remember(source) {
        when (source) {
            LoadingSource.PDF   -> pdfSteps
            LoadingSource.IMAGE -> imageSteps
            LoadingSource.TOPIC -> topicSteps
        }
    }
    val funFacts = remember(topic) { getFunFacts(topic) }

    // ─── Local UI state ───────────────────────────────────────────────────
    var currentStep    by remember { mutableStateOf(0) }
    var currentFact    by remember { mutableStateOf(0) }
    var adShown        by remember { mutableStateOf(false) }

    // ─── Animate steps independently from AI ──────────────────────────────
    LaunchedEffect(Unit) {
        steps.forEachIndexed { index, step ->
            if (index > currentStep) currentStep = index
            delay(step.durationMs)
        }
    }

    // ─── Rotate fun facts every 3 seconds ────────────────────────────────
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentFact = (currentFact + 1) % funFacts.size
        }
    }

    // ─── THE KEY LOGIC ────────────────────────────────────────────────────
    // Safely observe when the questions slice populates without duplicate execution loops
    LaunchedEffect(state.questions) {
        if (state.questions.isNotEmpty() && !adShown) {
            adShown = true
            currentStep = steps.lastIndex // Force UI checkpoints to complete checkmark animations

            // Ad implementation logic wrapper fits directly here later
            delay(600) // Small programmatic padding so the final layout checkmark step is visible
            onReadyToNavigate()
        }
    }

    // ─── UI Layout Tree ───────────────────────────────────────────────────
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A2E)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {

            // ─── Spinner + app icon ───────────────────────────────────────
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(90.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(90.dp),
                    color = Color(0xFF7F77DD),
                    trackColor = Color(0xFF2A2A4A),
                    strokeWidth = 3.dp
                )
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF534AB7)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Z",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 26.sp
                        )
                        Text(
                            text = "ذكاء",
                            fontSize = 9.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ─── Animated current step ────────────────────────────────────
            AnimatedContent(
                targetState = steps.getOrNull(currentStep),
                transitionSpec = {
                    slideInVertically { it } + fadeIn() togetherWith
                            slideOutVertically { -it } + fadeOut()
                },
                label = "step_content"
            ) { step ->
                step?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = it.icon, fontSize = 34.sp)
                        Text(
                            text = it.titleAr,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = it.descriptionAr,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ─── Step dots ────────────────────────────────────────────────
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                steps.forEachIndexed { index, _ ->
                    val isActive = index == currentStep
                    val isDone   = index < currentStep
                    Box(
                        modifier = Modifier
                            .size(if (isActive) 10.dp else 6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                when {
                                    isDone   -> Color(0xFF1D9E75)
                                    isActive -> Color(0xFF7F77DD)
                                    else     -> Color(0xFF2A2A4A)
                                }
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ─── Step checklist ───────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF2A2A4A))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                steps.forEachIndexed { index, step ->
                    val isDone   = index < currentStep
                    val isActive = index == currentStep

                    AnimatedVisibility(
                        visible = index <= currentStep,
                        enter = fadeIn() + expandVertically()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                        when {
                                            isDone   -> Color(0xFF1D9E75)
                                            isActive -> Color(0xFF534AB7)
                                            else     -> Color(0xFF3A3A5A)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    isDone   -> Text("✓", fontSize = 11.sp, color = Color.White)
                                    isActive -> CircularProgressIndicator(
                                        modifier  = Modifier.size(12.dp),
                                        color     = Color.White,
                                        strokeWidth = 1.5.dp
                                    )
                                }
                            }

                            Text(
                                text = step.titleAr,
                                fontSize = 12.sp,
                                color = when {
                                    isDone   -> Color(0xFF5DCAA5)
                                    isActive -> Color.White
                                    else     -> Color.White.copy(alpha = 0.3f)
                                },
                                fontWeight = if (isActive) FontWeight.Medium else FontWeight.Normal,
                                textAlign = TextAlign.Right,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 10.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ─── Fun fact card ────────────────────────────────────────────
            AnimatedContent(
                targetState = funFacts.getOrNull(currentFact),
                transitionSpec = {
                    fadeIn(tween(600)) togetherWith fadeOut(tween(600))
                },
                label = "fun_fact"
            ) { fact ->
                fact?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF2A2A4A))
                            .padding(14.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            Text(
                                text = "💡 هل تعلم؟",
                                fontSize = 11.sp,
                                color = Color(0xFF7F77DD),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right
                            )
                            Text(
                                text = it,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.6f),
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth(),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ─── Status message ──────────────────
            AnimatedContent(
                targetState = state.questions.isNotEmpty(),
                label = "status_message"
            ) { isReady ->
                Text(
                    text = if (isReady)
                        "اختبارك جاهز! سيبدأ بعد لحظة ⚡"
                    else
                        "جاري تحضير اختبارك...",
                    fontSize = 11.sp,
                    color = if (isReady)
                        Color(0xFF5DCAA5)
                    else
                        Color.White.copy(alpha = 0.3f),
                    fontWeight = if (isReady) FontWeight.Medium else FontWeight.Normal
                )
            }
        }
    }
}

enum class LoadingSource { PDF, IMAGE, TOPIC }