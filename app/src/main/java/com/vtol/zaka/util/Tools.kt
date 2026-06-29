package com.vtol.zaka.util

import java.util.Locale

// ── Helper: format number with Arabic-style comma ─────────────────────────────
// produces "1,250"
fun Int.formatArabicNumber(): String =
    String.format(Locale.ENGLISH,"%,d", this)