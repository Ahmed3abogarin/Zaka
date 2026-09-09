# Zaka (ذكاء) - AI-Powered Quiz Generator

Zaka is a modern, educational Android application designed to transform your learning materials into interactive quizzes. Using advanced AI, Zaka scans your documents, notes, and PDFs to generate personalized quizzes, helping students and learners reinforce their knowledge efficiently.

## 🚀 Features

*   **AI-Powered Quiz Generation**: Automatically generates questions and answers from your educational content using Gemini AI (Vertex AI for Firebase).
*   **PDF Support**: Upload and process PDF notebooks, textbooks, or summaries.
*   **Camera & Gallery Integration**: Capture photos of physical notes or select images from your gallery to create quizzes on the fly.
*   **Progress Tracking**: Keep track of your learning journey with a history of recent quizzes and detailed result analytics.
*   **Interactive Quiz Sessions**: Take quizzes with real-time feedback and review your answers.
*   **Smart Quota System**: Manage your daily usage with a built-in quota system, expandable through rewarded advertisements.
*   **Content Viewer**: Built-in viewer for scanned images and seamless integration with system PDF viewers.
*   **Modern & Intuitive UI**: A sleek, Material 3-based interface with full support for RTL (Arabic) languages.
*   **Secure Authentication**: Easy sign-in and account management powered by Firebase Authentication and Google Sign-In.

## 🛠 Tech Stack

*   **Language**: [Kotlin](https://kotlinlang.org/)
*   **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3
*   **Architecture**: Clean Architecture / MVVM
*   **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
*   **Database**: [Room](https://developer.android.com/training/data-storage/room) (SQLite)
*   **Local Storage**: [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (Preferences)
*   **Networking & Backend**: 
    *   [Firebase Auth](https://firebase.google.com/docs/auth)
    *   [Cloud Firestore](https://firebase.google.com/docs/firestore)
    *   [Vertex AI for Firebase](https://firebase.google.com/docs/vertex-ai) (Gemini)
*   **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
*   **Monetization**: [Google Mobile Ads (AdMob)](https://developers.google.com/admob/android/quick-start)

## 🏗 Architecture

The project follows the **Clean Architecture** pattern to ensure separation of concerns, testability, and scalability:

*   **Presentation Layer**: Jetpack Compose screens, ViewModels, and UI state management using Kotlin Flows.
*   **Domain Layer**: Business logic, Use Cases, and Repository interfaces.
*   **Data Layer**: Room Database implementation, Data sources, and Repository implementations.

## 📱 Screenshots

| Home Screen | Scan Options | Quiz Interface |
| :---: | :---: | :---: |
 <img width="250" alt="Home Screen" src="https://github.com/user-attachments/assets/e4ba7402-77d3-4b07-8e87-fb018f5b0ef4" /> | <img width="250" alt="Scan Options" src="https://github.com/user-attachments/assets/349c5104-9528-4fbb-a98b-416a959212a5" /> | <img width="250" alt="Quiz Interface" src="https://github.com/user-attachments/assets/31892773-36e1-4cf4-9b2c-f34730f81c30" /> |

## ⚙️ Installation

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/Zaka.git
    ```
2.  **Add Firebase Configuration**:
    *   Create a project on the [Firebase Console](https://console.firebase.google.com/).
    *   Add an Android app with the package name `com.vtol.zaka`.
    *   Download `google-services.json` and place it in the `app/` directory.
3.  **Setup AdMob**:
    *   Update the `APPLICATION_ID` in `AndroidManifest.xml` if using your own AdMob account.
4.  **Build the project**:
    *   Open the project in **Android Studio Ladybug (2024.2.1)** or newer.
    *   Sync Gradle and run the `:app` module.

## 📋 Requirements

*   **Android Studio**: Ladybug (2024.2.1)+
*   **JDK**: 17+
*   **Minimum SDK**: API 24 (Android 7.0)
*   **Target SDK**: API 36

## 🔮 Future Improvements

*   [ ] **Multi-language Support**: Expanding beyond Arabic to support English and other global languages.
*   [ ] **Cloud Sync**: Synchronize quiz history and progress across multiple devices.
*   [ ] **Community Quizzes**: Allow users to share generated quizzes with friends or classmates.
*   [ ] **Advanced Analytics**: Detailed insights into learning patterns and weak areas.
*   [ ] **OCR Optimization**: Improving text extraction for handwriting and low-quality images.

---
Developed with ❤️ by the Zaka Team.
