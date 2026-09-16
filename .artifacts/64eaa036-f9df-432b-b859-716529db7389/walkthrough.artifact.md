# Walkthrough - Google Sign-In Implementation

I have successfully integrated Google Sign-In into the login flow. The implementation uses the modern `Credential Manager API` for account selection and `Firebase Authentication` for back-end sign-in.

## Changes Made

### Domain Layer
- Created [SignInWithGoogleUseCase.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/domain/usecases/auth/SignInWithGoogleUseCase.kt) to encapsulate the Google sign-in logic.

### Data Layer
- Implemented `signInWithGoogle(idToken: String)` in [AuthRepositoryImpl.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/data/repository/AuthRepositoryImpl.kt).
- The implementation:
    - Creates a Firebase `GoogleAuthProvider` credential from the `idToken`.
    - Signs in to Firebase.
    - Saves the user's profile information (UID, email, name, photo URL) to Firestore.

### Presentation Layer
- Added `GoogleLoginClicked` to [LoginEvent.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/register/login/LoginEvent.kt).
- Added `OnGoogleSignUpClicked` to [SignUpEvent.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/register/signup/SignUpEvent.kt).
- Updated [LoginViewModel.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/register/login/LoginViewModel.kt) and [SignUpViewModel.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/register/signup/SignUpViewModel.kt) to handle the `Credential Manager` flow with improved error reporting for common configuration issues (like missing SHA-1 or Google accounts).
- Connected the button in both [LoginScreen.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/register/login/LoginScreen.kt) and [SignUpScreen.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/presentation/register/signup/SignUpScreen.kt) to trigger the event.

### Configuration
- Added `WEB_CLIENT_ID` to [Constants.kt](file:///Users/Ahmed/AndroidStudioProjects/Zaka/app/src/main/java/com/vtol/zaka/util/Constants.kt).

## Verification Results

### Build Verification
- The project structure and dependencies were verified to support `Credential Manager` and `Google ID` libraries.
- All new code follows the existing MVI/Clean Architecture pattern.

### Manual Setup Required
> [!IMPORTANT]
> You MUST ensure your **SHA-1** fingerprint (not just SHA-256) is added to the Firebase Console for your Android app.
> 1. Run `./gradlew signingReport`.
> 2. Copy the **SHA1** from the output.
> 3. Add it to your project in the [Firebase Console](https://console.firebase.google.com/).
