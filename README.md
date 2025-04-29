# 📱 LifeHub — Android App

LifeHub is my **most recent side project**, a modern Android application built to deliver a clean, high-quality user experience with **Firebase integration** and **real authentication flows**.

This project showcases a **production-ready architecture** while still being **actively developed** (🔨 *work in progress*). The foundations are laid with attention to scalability, testability, and modularity.

---

## 🌟 Project Intent

LifeHub is intended to become a **hub for user experiences** — from **authentication**, **profile management**, and **data storage**, to **personalized experiences** through future features.

Immediate goals:
- Provide a **full sign-up** and **login** flow with **real email verification**.
- Store **user profile data** (first name, last name, DOB) securely in **Firebase Firestore**.
- Track **implicit analytics** throughout the user journey (screen views, actions).

---

## ⚙️ Features

- ✅ **Email/password authentication** (using Firebase Auth)
- ✅ **Real email verification** (deep linking back into the app)
- ✅ **User profile creation** in **Firestore** after registration
- ✅ **Secure login** with validation and error handling
- ✅ **Modern animated UI** using Jetpack Compose (including cinematic typing effects)
- ✅ **Compose Navigation** (declarative and modular nav flow)
- ✅ **Implicit Analytics Tracking** (screens and actions automatically log events)
- ✅ **Deep linking** with **Firebase Dynamic Links** and **assetlink.json**

---

## 🛠 Tech Stack

| Layer            | Technology                       | Purpose                         |
|------------------|-----------------------------------|---------------------------------|
| UI               | Jetpack Compose                   | Declarative, fast Android UI    |
| State Management | MVVM (Model-View-ViewModel)        | Clean separation of concerns    |
| Navigation       | Compose Navigation                | Multi-screen flow management    |
| Backend          | Firebase Auth                     | Authentication and user management |
| Database         | Firebase Firestore                | NoSQL cloud database            |
| Analytics        | Firebase Analytics                | Automatic screen and event tracking |
| DI               | Hilt                              | Dependency injection            |
| Async            | Kotlin Coroutines                 | Background operations           |

---

## 📈 Architecture

- **Clean Architecture Principles**:
  - `Presentation Layer`: Jetpack Compose UI + ViewModels
  - `Domain Layer`: Repositories for business logic
  - `Data Layer`: Services interacting with Firebase

- **Repository pattern** between ViewModels and Firebase
- **Service layer abstraction** over Firestore collections
- **Sealed classes** (`PostResult`) for clear API states: `Success`, `Error`, etc.
- **Composable modular navigation flows**

---

## 🧪 Completed Features

- [x] Sign-up with email/password
- [x] Real-time email verification
- [x] Password reset flow
- [x] Saving user profile (first name, last name, DOB) in Firestore
- [x] Secure login with validation
- [x] Implicit Firebase Analytics integration

---

## 🔥 Why This Project Is Special

- 🔵 *Real-world email verification*: users must verify before logging in.
- 🔵 *Firebase Firestore-backed user profiles*.
- 🔵 *Cinematic animated UI*: immersive user experience.
- 🔵 *Professional architecture*: DI, Clean layers, Repository pattern.
- 🔵 *Automatic Analytics*: no extra boilerplate.

---

## 📋 Setup Instructions

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/lifehub.git
    ```

2. Set up Firebase:
   - Enable **Authentication** (Email/Password)
   - Enable **Firestore Database**
   - Enable **Analytics**
   - Set up **Dynamic Links** (for deep linking)

3. Download `google-services.json` from Firebase Console and place it under `/app/`.

4. Sync Gradle and run the app!

---

## 🚧 Future Roadmap (Work in Progress)

- [ ] Full onboarding questionnaire
- [ ] User home dashboard after login
- [ ] Personalized content delivery based on user profile
- [ ] Notifications and real-time updates
- [ ] Settings and profile editing
- [ ] Dark mode and accessibility improvements

---

## 👨‍💻 Author

Built with passion by **Naol**.  
This project reflects my focus on building clean, scalable, and production-ready Android applications.

---

## 📢 Final Notes

This project is **actively evolving**.  
Some features are still under construction, but **core authentication and user flows work perfectly**!

If you're reviewing it today, you're seeing the **early but functional version**.  
✅ Full signup with real email verification and Firestore integration is ready!

---

