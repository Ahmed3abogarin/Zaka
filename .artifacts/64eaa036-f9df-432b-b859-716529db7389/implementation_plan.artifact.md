# Implementation Plan - Fix "No credentials available" Google Sign-In Error

The "No credentials available" error ( `GetCredentialException.TYPE_NO_CREDENTIAL`) usually indicates that the Credential Manager API couldn't find any Google accounts to present or the request was invalid for the current environment.

## User Review Required

> [!IMPORTANT]
> This error is often configuration-related. Please verify the following:
> 1.  **SHA-1 Fingerprint**: Ensure you have added the SHA-1 fingerprint of your debug signing certificate to your project in the [Firebase Console](https://console.firebase.google.com/).
>     - You can get it by running `./gradlew signingReport` in the terminal.
> 2.  **Device/Emulator**: Ensure the device or emulator has **Google Play Services** installed and you are **signed in with a Google account** in the system settings.
> 3.  **Client ID**: Confirm that `WEB_CLIENT_ID` in `Constants.kt` is the **Web client ID** (not the Android client ID) from the Firebase Console -> Authentication -> Settings -> Google -> Web SDK configuration.

## Proposed Changes

### Presentation Layer

#### [MODIFY] [LoginViewModel.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/register/login/LoginViewModel.kt)
- Add a generated `nonce` to `GetGoogleIdOption`.
- Improve error logging to distinguish between user cancellation and other errors.

#### [MODIFY] [SignUpViewModel.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/register/signup/SignUpViewModel.kt)
- Add a generated `nonce` to `GetGoogleIdOption`.
- Improve error logging.

## Verification Plan

### Manual Verification
1.  Run `./gradlew signingReport` to get the SHA-1.
2.  Verify the SHA-1 in Firebase Console.
3.  Run the app and try Google Sign-In again.
4.  If it fails, check the Toast/Error message for more specific details (e.g., if it's a cancellation or a different exception).
