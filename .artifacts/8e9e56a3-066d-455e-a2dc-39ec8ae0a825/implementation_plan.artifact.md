# Offline Connectivity Handling Implementation Plan

This plan outlines how to handle scenarios where the user has no internet connection, ensuring a professional user experience with clear feedback instead of technical errors.

## User Review Required

> [!IMPORTANT]
> The "Generate Quiz" buttons (PDF & Camera) on the Home Screen will be visually disabled when offline. Users will be able to see their history but not start new scans.

## Proposed Changes

### Core Utilities

#### [NEW] [ConnectivityObserver.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/util/ConnectivityObserver.kt)
Implement a modern network observer using `ConnectivityManager` to provide a real-time `Flow<Boolean>` of internet status across the app.

---

### UI Components

#### [MODIFY] [ActionCard.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/home/components/ActionCard.kt)
Add an `enabled` parameter to the `ActionCard`. When `enabled = false`:
- Apply `alpha = 0.6f` to the entire card.
- Change the icon background tint to a neutral gray.
- Add a "Cloud Off" icon badge in the corner.

#### [MODIFY] [HomeScreen.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/home/HomeScreen.kt)
Integrate the connectivity status:
- Collect the network state from the `HomeViewModel`.
- Pass `isOffline` to the `ActionCard` components.
- If clicked while offline, show a snackbar with: `"عذراً، لا يوجد اتصال بالإنترنت. يرجى التحقق من الشبكة والمحاولة مرة أخرى."`

---

### Logic & Error Handling

#### [MODIFY] [QuizViewModel.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/quiz/QuizViewModel.kt)
Enhance the error handling in `generateFromPdf` and `generateFromImage`:
- Before starting the AI request, check the network status.
- If offline, immediately set `QuizScreenState.Error` with a localized "No Internet" message instead of waiting for a network timeout exception.

#### [MODIFY] [QuizScreen.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/quiz/QuizScreen.kt)
Improve the error state UI:
- Add a specific "Offline" illustration/icon when the error message indicates a connection issue.
- Change the "Retry" button to "Check Connection".

## Verification Plan

### Automated Tests
- Mock `ConnectivityObserver` in `QuizViewModelTest` to verify it fails fast when offline.

### Manual Verification
1. **Turn off Wi-Fi/Data**: Open the app and verify the Home screen cards are dimmed.
2. **Attempt Scan**: Click a dimmed card and verify the Arabic error message appears.
3. **Reconnect**: Turn on Wi-Fi and verify the buttons animate back to full color instantly.
4. **Mid-process disconnection**: Start a scan, disconnect mid-way, and verify the loading screen transitions to a clean "No Internet" error instead of a technical crash.
