# Offline Connectivity Handling for Scan Screen

This plan details how to disable and visually dim scan actions (Camera, PDF, Gallery) in the `ScanScreen` when the device is offline, providing consistent feedback across the app.

## User Review Required

> [!IMPORTANT]
> The Camera, Gallery, and PDF buttons on the Scan screen will be visually disabled when offline. Clicking them will show a localized Arabic error message.

## Proposed Changes

### Core Logic

#### [MODIFY] [QuizViewModel.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/quiz/QuizViewModel.kt)
- Update `QuizUiState` to include `val isOffline: Boolean = false`.
- Update `observeConnectivity` to update the state when network status changes.

---

### UI Components

#### [MODIFY] [CameraButton.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/scan/components/CameraButton.kt)
- Add an `enabled` parameter.
- If `enabled` is false:
    - Change the purple gradient to a gray gradient.
    - Dim the overall opacity.
    - Disable the click action.

#### [MODIFY] [SecondaryActionCard.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/scan/components/SecondaryActionCard.kt)
- Add an `enabled` parameter.
- If `enabled` is false:
    - Change the icon tint to gray.
    - Dim the text color.
    - Disable the click action.

#### [MODIFY] [ScanContent.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/scan/ScanContent.kt)
- Add an `isOffline` parameter.
- Pass `enabled = !isOffline` to all action buttons.

#### [MODIFY] [ScanScreen.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/scan/ScanScreen.kt)
- Collect the `isOffline` state from the `QuizViewModel`.
- Pass `isOffline` down to `ScanContent`.
- Handle click events when offline by showing a Toast message.

---

## Verification Plan

### Automated Tests
- Verify that `QuizUiState.isOffline` correctly reflects the `ConnectivityObserver` status in unit tests.

### Manual Verification
1. **Turn off Wi-Fi/Data**: Navigate to the Scan screen.
2. **Verify UI**: Ensure the "Open Camera", "From Gallery", and "PDF File" buttons are all dimmed and gray.
3. **Attempt Action**: Click any disabled button and verify the Arabic error Toast appears.
4. **Reconnect**: Turn on Wi-Fi and verify the buttons instantly return to their purple/vibrant state.
