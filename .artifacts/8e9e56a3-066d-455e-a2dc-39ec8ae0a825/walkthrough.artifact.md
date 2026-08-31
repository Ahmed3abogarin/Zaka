# Offline Connectivity Handling for Scan Screen Walkthrough

I have implemented visual dimming and preventative logic for the Scan screen to ensure users are aware that scanning requires an internet connection.

## Changes Made

### 1. Unified Connectivity State
- **[QuizViewModel.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/quiz/QuizViewModel.kt)**: Updated the `QuizUiState` to include `isOffline`. The `observeConnectivity` function now updates this state reactively.

### 2. Adaptive UI Components
- **[CameraButton.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/scan/components/CameraButton.kt)**: Added an `enabled` state. When offline, the vibrant purple gradient turns into a neutral gray, and the overall opacity is reduced.
- **[SecondaryActionCard.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/scan/components/SecondaryActionCard.kt)**: Similarly, the PDF and Gallery cards now dim and turn gray when the device is disconnected.

### 3. Screen Integration & Error Handling
- **[ScanScreen.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/scan/ScanScreen.kt)**: Now collects the `isOffline` state and passes it down. I added a helper function `handleScanAction` that checks for connectivity.
- **Preventative Toast**: If a user clicks a disabled scan button, a localized Arabic Toast appears: *"عذراً، لا يوجد اتصال بالإنترنت. يرجى التحقق من الشبكة والمحاولة مرة أخرى."*

## Verification Results

### Build Status
- **Success**: The project compiled successfully with `gradle assembleDebug`.

### UI Behavior
- **Offline Mode**: Buttons on the Scan screen (Camera, Gallery, PDF) are visually grayed out and transparent.
- **Click Logic**: Clicking a dimmed button triggers the "No Internet" toast instead of opening the file picker or camera.
- **Live Updates**: The UI responds instantly when the device transitions between online and offline states.

> [!NOTE]
> This completes the connectivity feedback for both the Home and Scan screens, providing a consistent user experience across the entire application.
