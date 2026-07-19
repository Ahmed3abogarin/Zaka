package com.vtol.zaka.presentation.onboarding.model

data class OnboardingPage(
    val title: String,
    val subtitle: String
)

val onboardingPages = listOf(
    OnboardingPage(
        title = "انشئ اختبارات من اي شيء",
        subtitle = "بي دي اف، صور أو أي موضوع دراسي في ثواني معدودة"
    ),
    OnboardingPage(
        title = "ذكاء اصطناعي\n" +
                "يفهم دراستك",
        subtitle = "أسئلة ذكية و مخصصة مع شرح مفصل لكل إجابة"
    ),
    OnboardingPage(
        title = "Track Your Progress",
        subtitle = "See your growth with clear, real-time analytics"
    )
)
