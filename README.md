<div align="center">

<img src="https://img.shields.io/badge/Platform-Android%20%7C%20iOS-3fff8b?style=for-the-badge&logoColor=black" />
<img src="https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
<img src="https://img.shields.io/badge/Architecture-MVI-dfe1f9?style=for-the-badge" />
<img src="https://img.shields.io/badge/Status-In%20Development-ffcc60?style=for-the-badge" />

<br/><br/>

```
███████╗ █████╗ ███╗   ██╗ ██████╗████████╗██╗   ██╗███╗   ███╗
██╔════╝██╔══██╗████╗  ██║██╔════╝╚══██╔══╝██║   ██║████╗ ████║
███████╗███████║██╔██╗ ██║██║        ██║   ██║   ██║██╔████╔██║
╚════██║██╔══██║██║╚██╗██║██║        ██║   ██║   ██║██║╚██╔╝██║
███████║██║  ██║██║ ╚████║╚██████╗   ██║   ╚██████╔╝██║ ╚═╝ ██║
╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝   ╚═╝    ╚═════╝ ╚═╝     ╚═╝
```

### *Your body. Your sanctuary.*

A premium cross-platform nutrition tracking app built with **Kotlin Multiplatform** and **Jetpack Compose**.  
Scan food barcodes, track macros, and own your metabolic momentum — on Android and iOS from a single codebase.

<br/>

</div>

---

## ✦ Overview

**Sanctum** is a personal learning project exploring the full KMP stack — from shared business logic in `commonMain` to platform-specific camera integrations and a polished Compose UI. It follows a strict **MVI (Model-View-Intent)** architecture with unidirectional data flow throughout.

The design language — *The Kinetic Sanctuary* — pairs deep obsidian surfaces with electric malachite accents to create a UI that feels carved out of a single dark canvas rather than assembled from components.

<br/>

## ✦ Screenshots

| Daily Diary | Barcode Scanner | Weekly Insights | Food Search | Profile |
|:-----------:|:---------------:|:---------------:|:-----------:|:-------:|
| *(coming soon)* | *(coming soon)* | *(coming soon)* | *(coming soon)* | *(coming soon)* |

<br/>

## ✦ Features

- **🔍 Barcode Scanner** — Scan any food product using your camera. Powered by ML Kit on Android and AVFoundation on iOS, backed by the Open Food Facts API
- **📊 Macro Tracking** — Animated daily calorie ring with per-meal protein, carbs, and fat breakdowns
- **🗓 Food Diary** — Organised by meal type (Breakfast, Lunch, Dinner, Snacks) with per-item calorie counts
- **📈 Weekly Insights** — Bar chart calorie trends, macro sparklines, and weekly performance summaries
- **⚡ Offline First** — Scanned foods are cached locally via SQLDelight — works without a network connection after first scan
- **🎯 Custom Goals** — Set personal calorie and macro targets, persisted across sessions
- **🔥 Cross-Platform** — One shared codebase targets Android (Jetpack Compose) and iOS (SwiftUI)

<br/>

## ✦ Tech Stack

### Shared (`commonMain`)

| Library | Purpose |
|---------|---------|
| [Ktor Client](https://ktor.io/docs/client-overview.html) | HTTP client — Open Food Facts API |
| [SQLDelight](https://cashapp.github.io/sqldelight/) | Type-safe local database |
| [Koin](https://insert-koin.io/) | Dependency injection |
| [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) | JSON parsing |
| [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) | Async / Flow |
| [Lifecycle ViewModel KMP](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-viewmodel.html) | Shared ViewModels in commonMain |
| [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings) | Key-value preferences storage |

### Android (`composeApp`)

| Library | Purpose |
|---------|---------|
| Jetpack Compose + Material3 | UI layer |
| CameraX + ML Kit Barcode | Barcode scanner |
| Navigation Compose | Screen routing |
| Koin Compose | DI in composables |
| Ktor OkHttp Engine | HTTP engine |
| SQLDelight Android Driver | SQLite driver |

### iOS (`iosApp`)

| Library | Purpose |
|---------|---------|
| SwiftUI | UI layer |
| AVFoundation | Barcode scanner |
| [SKIE](https://skie.touchlab.co/) | StateFlow → Swift AsyncStream bridging |
| Ktor Darwin Engine | HTTP engine |
| SQLDelight Native Driver | SQLite driver |

<br/>

## ✦ Architecture

Sanctum follows **MVI** with strict unidirectional data flow. Every screen owns a single immutable `State` data class and a sealed `Intent` class. The ViewModel is the only place state can change.

```
┌──────────────────────────────────────────────────────┐
│                      UI Layer                        │
│          (Compose Screen / SwiftUI View)             │
│                                                      │
│    collectAsState()            vm.handleIntent(...)  │
└─────────────┬────────────────────────────┬───────────┘
              │ State                      │ Intent
              ▼                            ▼
┌──────────────────────────────────────────────────────┐
│              ViewModel  (commonMain)                 │
│                                                      │
│   StateFlow<DiaryState>   handleIntent(DiaryIntent)  │
│         ↑                          │                 │
│   _state.update { }        viewModelScope.launch     │
└──────────────────────────────┬───────────────────────┘
                               │
          ┌────────────────────┼────────────────────┐
          ▼                    ▼                    ▼
  FoodLogRepository     FoodApiService     UserPrefsRepository
    (SQLDelight)           (Ktor)        (multiplatform-settings)
```

<br/>

## ✦ Module Structure

```
sanctum/
├── shared/
│   └── src/
│       ├── commonMain/              # All shared business logic
│       │   ├── data/
│       │   │   ├── local/           # SQLDelight schema + repositories
│       │   │   ├── remote/          # Ktor API service + response models
│       │   │   └── repository/      # Unified repository layer + cache
│       │   ├── domain/
│       │   │   └── model/           # FoodItem, FoodLog, MealType, MacroSummary
│       │   ├── presentation/
│       │   │   ├── diary/           # DiaryViewModel, DiaryState, DiaryIntent
│       │   │   ├── search/          # FoodSearchViewModel, state, intent
│       │   │   └── profile/         # ProfileViewModel, state, intent
│       │   └── di/                  # Koin AppModule
│       ├── androidMain/
│       │   └── scanner/             # BarcodeScanner actual (CameraX + ML Kit)
│       └── iosMain/
│           └── scanner/             # BarcodeScanner actual (AVFoundation)
│
├── composeApp/                      # Android — Jetpack Compose UI
│   └── src/main/
│       ├── ui/
│       │   ├── theme/               # SanctumTheme, color tokens, typography
│       │   ├── diary/               # DiaryScreen, CalorieRing, MealCard
│       │   ├── scanner/             # ScannerScreen, ProductBottomSheet
│       │   ├── search/              # FoodSearchScreen
│       │   ├── progress/            # ProgressScreen, BarChart, MacroTrend
│       │   ├── profile/             # ProfileScreen
│       │   └── components/          # EmptyState, LoadingShimmer, MacroBar
│       └── navigation/              # NavHost, Routes, BottomNavBar
│
└── iosApp/                          # iOS — SwiftUI entry point
    └── Views/
        ├── DiaryView.swift
        ├── ScannerView.swift
        ├── SearchView.swift
        ├── ProgressView.swift
        └── ProfileView.swift
```

<br/>

## ✦ Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Xcode 15+ (for iOS target)
- JDK 17+
- A physical device for barcode scanner testing — simulators have no camera

### Clone & build

```bash
git clone https://github.com/yourusername/sanctum.git
cd sanctum
```

**Run on Android:**
```bash
./gradlew :composeApp:assembleDebug
# or open in Android Studio and run directly
```

**Run on iOS:**
```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
# then open iosApp/iosApp.xcodeproj in Xcode and run
```

### API

No API keys required. Sanctum uses the [Open Food Facts API](https://world.openfoodfacts.org/data) — free, open, and unauthenticated.

```
GET https://world.openfoodfacts.org/api/v0/product/{barcode}.json
```

<br/>

## ✦ Design System

Sanctum follows the **Kinetic Sanctuary** design language — a premium dark editorial aesthetic built on tonal layering with no borders.

| Token | Hex | Role |
|-------|-----|------|
| `background` | `#0c0e14` | Deep Obsidian — app canvas |
| `surface` | `#171921` | Standard cards |
| `surface_high` | `#1d1f27` | Nested / elevated cards |
| `primary` | `#3fff8b` | Electric Malachite — actions, progress |
| `secondary` | `#dfe1f9` | Cool Periwinkle — fat macro, subtle UI |
| `tertiary` | `#7ae6ff` | Glacier Blue — carbs macro, info states |
| `on_surface` | `#e5e4ed` | Primary body text |

**Core rules:**
- No borders for sectioning — use background tonal shifts only
- **Lexend** for display numbers and headings
- **Manrope** for body text and labels
- Pill-shaped CTAs with gradient fill (`#3fff8b` → `#13ea79`)
- No white (`#ffffff`) text — always use `on_surface` (`#e5e4ed`)

<br/>

## ✦ Roadmap

- [x] Project setup & KMP configuration
- [x] SQLDelight schema & local data layer  
- [x] Open Food Facts API integration with offline cache
- [x] MVI ViewModels — Diary, Scanner, Search, Profile
- [x] Barcode scanner — Android (CameraX + ML Kit)
- [x] Design system & Kinetic Sanctuary tokens
- [ ] Barcode scanner — iOS (AVFoundation)
- [ ] Android Compose UI — all 5 screens
- [ ] iOS SwiftUI UI
- [ ] User preferences & goals persistence
- [ ] Unit tests (Turbine + fake repositories)
- [ ] Manual food search (Open Food Facts search endpoint)
- [ ] Weight trend graph
- [ ] Water intake tracking
- [ ] Push notifications for meal reminders
- [ ] Export diary as CSV

<br/>

## ✦ Data Source

Food and nutrition data is provided by [Open Food Facts](https://world.openfoodfacts.org/) — a free, open, collaborative food products database made by everyone, for everyone. Licensed under the [Open Database License (ODbL)](https://opendatacommons.org/licenses/odbl/1-0/).

<br/>

## ✦ License

```
MIT License — Copyright (c) 2026 Sanctum

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
```

<br/>

<div align="center">

*Built with* ♥ *using Kotlin Multiplatform*

`Kotlin` · `Compose` · `KMP` · `MVI` · `SQLDelight` · `Ktor` · `Koin`

<br/>

*"Where discipline meets design."*

</div>
