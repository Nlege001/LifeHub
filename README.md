# 📱 LifeHub — Android App

LifeHub is a modern Android application focused on personal well-being and productivity. Built with Jetpack Compose, Firebase, and a clean architecture, it offers a high-quality, animated, and secure user experience with modular navigation and real-time features.

This project reflects a real-world, production-ready mobile app that integrates authentication, analytics, and emotional tracking — while still actively evolving. 🔨

---

## 🌟 Project Intent

LifeHub aims to become a **hub for holistic user experience** — blending secure authentication, personalized onboarding, daily mental wellness tracking, and real-time emotional feedback.

### ✅ Immediate Goals:
- Full sign-up/login flow with **real email verification**
- Secure, **PIN-based logins**
- Store **user profile info** (first name, last name, DOB) in Firestore
- Provide a personalized experience via messages, quotes, and analytics
- Track **user mood and progress** over time

---

## ⚙️ Features

| Feature | Status |
|--------|--------|
| 🔐 Email/password authentication (Firebase Auth) | ✅ |
| ✅ Email verification with deep links | ✅ |
| 📄 User profile creation in Firestore | ✅ |
| 🔒 PIN-based secure login | ✅ |
| 🧠 Mood tracker modal (1-question tracker) | ✅ |
| 🧭 Progress tracker screen | ✅ |
| ✉️ Personalized message after login | ✅ |
| 💬 Quote of the day (auto-refreshing) | ✅ |
| 🎬 Cinematic typing effects & modern UI | ✅ |
| 🧭 Declarative, modular Compose Navigation | ✅ |
| 📊 Implicit Firebase Analytics (screens, actions) | ✅ |
| 🔗 Deep linking with Firebase Dynamic Links | ✅ |

---

## 🛠 Tech Stack

| Layer | Tech | Purpose |
|-------|------|---------|
| **UI** | Jetpack Compose | Declarative modern UI |
| **State** | MVVM | Separation of concerns |
| **Navigation** | Compose Navigation | Multi-screen flows |
| **Backend** | Firebase Auth | Secure login |
| **Database** | Firebase Firestore | Profile & mood data |
| **Analytics** | Firebase Analytics | Auto event tracking |
| **DI** | Hilt | Dependency injection |
| **Async** | Kotlin Coroutines | Background work |

---

## 📈 Architecture

Built using **Clean Architecture**:

- **Presentation Layer**: Compose UI + ViewModels
- **Domain Layer**: Repositories for logic and data coordination
- **Data Layer**: Firebase services (Auth, Firestore, Analytics)

### Design Patterns:
- ✅ Repository pattern
- ✅ Service layer abstraction over Firebase
- ✅ Sealed classes for API state management (`PostResult`)
- ✅ Modular navigation flows

---

## 🧪 Completed Core Features

- ✅ Sign-up with email/password
- ✅ Real-time email verification
- ✅ Secure PIN setup and login
- ✅ Password reset flow
- ✅ User profile saved to Firestore
- ✅ Personalized login greeting based on time of day
- ✅ Mood tracker modal (with emoji slider)
- ✅ Quote of the day from Firestore (dynamic + randomized)
- ✅ Progress tracker screen to visualize user data
- ✅ Implicit Firebase Analytics integration

---

## 🔥 Why LifeHub Stands Out

- 🔵 **Real-world auth**: Verified emails before login
- 🔐 **PIN login**: Quick secure access post-login
- 💬 **Quote of the Day**: New inspiration every login
- 🧠 **Mood Tracker**: Lightweight emotional check-ins
- 📈 **Progress Tracker**: Visualize your emotional trends
- 💡 **Professional Architecture**: Clean, testable, modular
- 🔄 **Zero-boilerplate Analytics**: Automatically tracked actions

---

## 📋 Setup Instructions

### 🔧 Clone the repo:
```bash
git clone https://github.com/yourusername/lifehub.git

```

🔥 Set up Firebase:
	1.	Enable:
	•	Authentication (Email/Password)
	•	Firestore Database
	•	Firebase Analytics
	•	Firebase Dynamic Links
	2.	Add your dynamic link domain in Firebase console
	3.	Download google-services.json and place it under /app/
	4.	Sync Gradle and run!

⸻

🚧 Future Roadmap
	•	🧠 Full onboarding questionnaire
	•	📱 Personalized dashboard
	•	🔔 Push notifications
	•	🗂 Profile editing + settings
	•	🌓 Dark mode and accessibility features
	•	📊 Graphical analytics of mood and behavior over time

⸻

📰 Enable News Section (Optional)

To enable the News section on the Dashboard Feed:

1. Sign up at [https://newsdata.io/](https://newsdata.io/) to get a free API key.
2. Open your project's `local.properties` file.
3. Add the following line:

```
NEWS_API_KEY=YOUR_API_KEY
```
4. Rebuild the project.

This will activate the news modal that displays self-care, mindfulness, and personal development articles.

⸻

👨‍💻 Author

Built with passion by Naol.
This project reflects my focus on building clean, scalable, and production-grade Android apps with real-world features and intuitive design.

⸻

📢 Final Notes

LifeHub is actively evolving.
If you’re reviewing this today, you’re seeing a functional but expanding version with secure login, profile management, and emotional intelligence features already in place.

✅ Core auth flow works
✅ Mood + progress tracking are live
✅ Clean architecture is enforced throughout
