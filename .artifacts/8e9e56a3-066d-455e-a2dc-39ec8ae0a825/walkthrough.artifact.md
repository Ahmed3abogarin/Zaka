# Custom In-App Updates Walkthrough

I have implemented a custom, flexible, and force update system for the Zaka app. This system avoids the official Google Play API to give you full control over the UI and integration, while using **Firebase Remote Config** as the backend.

## How it works

### 1. The Trigger (Connectivity Integration)
The update check is directly tied to the `ConnectivityObserver` we previously implemented.
- **Offline Mode**: If the user opens the app while offline, they can still use local features (like history) without being interrupted by a failing update check.
- **Auto-Check**: As soon as the device goes online, the `UpdateViewModel` detects the `Available` status and triggers the Firebase Remote Config fetch. This ensures the update check is reliable and doesn't happen when it's guaranteed to fail.

### 2. Force vs. Flexible Update Logic
The logic resides in the `UpdateViewModel`:
- **Force Update**: Triggered if the app's `VERSION_CODE` is lower than the `force_update_version` value in Firebase. This blocks the entire app with a custom screen.
- **Flexible Update**: Triggered if the `VERSION_CODE` is lower than `latest_version_code`. This shows a non-intrusive dialog on top of the app content, allowing the user to skip it.

### 3. Custom UI Components
- **[ForceUpdateScreen](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/update/ForceUpdateScreen.kt)**: A professional, full-screen UI in Arabic that explains why the update is mandatory.
- **[FlexibleUpdateDialog](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/update/FlexibleUpdateDialog.kt)**: A sleek, modern dialog for optional updates.
- **[MainActivity Integration](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/MainActivity.kt)**: The entry point now coordinates these states, ensuring `ForceUpdateScreen` truly blocks the app.

## Setup Requirements (Firebase Console)
To make this work, you must add these parameters to **Firebase Remote Config**:
1. `force_update_version` (Long/Number): e.g., `2`.
2. `latest_version_code` (Long/Number): e.g., `3`.
3. `update_url` (String): e.g., `https://play.google.com/store/apps/details?id=com.vtol.zaka`.

## Verification Results
- **Connectivity**: Verified that the update check only triggers when the network is `Available`.
- **UI Logic**: `MainActivity` correctly swaps screens based on the `UpdateState`.
- **Build**: Successfully compiled the project with the new Firebase Config dependencies.

> [!TIP]
> You can test the "Force Update" by temporarily setting your app's `versionCode` to `1` in `build.gradle.kts` and setting `force_update_version` to `2` in Firebase.
