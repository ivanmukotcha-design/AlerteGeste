# AlertGeste — Application Android d'alerte d'urgence discrète

## Description
AlertGeste permet d'envoyer silencieusement un SMS d'alerte GPS à des contacts de confiance en secouant le téléphone 3 fois, même écran éteint. Conçue pour Butembo (RDC).

---

## Architecture
| Composant | Technologie |
|---|---|
| Pattern | MVVM |
| Base de données | Room (SQLite) |
| Injection de dépendances | Hilt |
| Navigation | Navigation Component |
| Asynchrone | Kotlin Coroutines + Flow |
| Capteur | SensorManager (TYPE_ACCELEROMETER) |
| Localisation | FusedLocationProviderClient |
| SMS | SmsManager |
| Service arrière-plan | Foreground Service |

---

## Prérequis
- Android Studio Hedgehog (2023.1.1) ou supérieur
- JDK 17
- Android SDK 34
- Google Play Services installé sur l'appareil cible

---

## Installation

1. **Cloner / extraire** le projet dans un dossier local
2. **Ouvrir** dans Android Studio : `File > Open > dossier AlertGeste`
3. **Modifier** `local.properties` avec le chemin de votre SDK :
   ```
   sdk.dir=C:/Users/VOTRE_NOM/AppData/Local/Android/Sdk
   ```
4. **Synchroniser Gradle** : cliquer sur "Sync Now"
5. **Connecter** un appareil Android (API 26+) ou lancer un émulateur
6. **Exécuter** : `Run > Run 'app'`

---

## Permissions requises (accordées au premier lancement)
- `SEND_SMS` — envoi des alertes
- `ACCESS_FINE_LOCATION` — coordonnées GPS précises
- `ACCESS_BACKGROUND_LOCATION` — GPS en arrière-plan
- `FOREGROUND_SERVICE` — surveillance continue
- `POST_NOTIFICATIONS` — notifications Android 13+
- `RECEIVE_BOOT_COMPLETED` — redémarrage auto au boot

---

## Structure des packages

```
com.butembo.alertgeste/
├── AlertGesteApp.kt           # Application Hilt + canaux notif
├── data/
│   ├── local/
│   │   ├── AlertGesteDatabase.kt   # Room Database
│   │   ├── dao/Daos.kt             # DAOs Room
│   │   └── entity/Entities.kt      # Entités Room
│   └── repository/
│       └── AlertGesteRepository.kt # Source de vérité unique
├── di/
│   └── AppModule.kt           # Injection Hilt
├── receiver/
│   └── Receivers.kt           # BootReceiver + AlerteActionReceiver
├── service/
│   └── SurveillanceService.kt # Foreground Service (accéléromètre + SMS)
└── ui/
    ├── MainActivity.kt
    ├── splash/                # Écran de démarrage
    ├── register/              # Inscription
    ├── dashboard/             # Tableau de bord
    ├── contacts/              # Gestion contacts + dialog
    ├── gesture/               # Entraînement geste
    ├── history/               # Historique alertes
    └── settings/              # Paramètres + reset
```

---

## Fonctionnement de la détection

1. Le `SurveillanceService` (Foreground) écoute l'accéléromètre à 50 Hz
2. Un **filtre passe-bas** (α=0.8) isole les mouvements brusques de la gravité
3. La **magnitude linéaire** = √(lx²+ly²+lz²) est comparée aux seuils du `GesteProfil`
4. Si **3 pics** sont détectés dans une fenêtre de **1500 ms** → alerte déclenchée
5. Une **notification avec compte à rebours 5s** permet l'annulation
6. Si non annulé : GPS acquis → SMS envoyé à tous les contacts

---

## Couleurs du thème
| Nom | Hex |
|---|---|
| Primary | `#1F3864` |
| Secondary | `#2E75B6` |
| Danger | `#E74C3C` |
| Success | `#27AE60` |
| Warning | `#F39C12` |
| Background | `#F8F9FC` |
