package com.vtol.zaka.presentation.onboarding.model

data class OnboardingPage(
    val title: String,
    val subtitle: String
)

val onboardingPages = listOf(

    OnboardingPage(
        title = "انشئ اختبارات\n" +
                "من اي شيء",
        subtitle = "بي دي اف، صور أو أي موضوع\nدراسي في ثواني معدودة"
    ),
    OnboardingPage(
        title = "ذكاء اصطناعي\n" +
                "يفهم دراستك",
        subtitle = "أسئلة ذكية و مخصصة مع شرح\nمفصل لكل إجابة"
    ),
    OnboardingPage(
        title = "تتبع تقدمك\n" +
                "يوماٌ بعد يوم",
        subtitle = "أعرف نقاط قوتك وطور مستواك،\nواصل رحلتك نحو التفوق"
    )
)
