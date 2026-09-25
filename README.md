# AlertGeste

Application Android native Kotlin d’alerte personnelle par geste ou bouton SOS.
Android 8 (API 26) minimum ; compilation et ciblage Android 16 (API 36).

## Fonctionnement

Configurer son profil, ajouter de 1 à 10 contacts, puis ouvrir « Configurer le geste » et appuyer sur « Commencer ». Faire trois secousses rapprochées en 2,5 secondes, sans forcer ni revenir au repos entre chacune. La jauge confirme la réception du capteur et le compteur confirme les secousses reconnues. La sensibilité est ajustée aux pics mesurés. Aucun SMS n’est envoyé pendant cet entraînement. Les autorisations SMS, localisation et notifications sont demandées avant le démarrage de la surveillance.

Les profils existants restent conservés ; recommencer la configuration permet de bénéficier de la nouvelle fenêtre de 2,5 secondes et de la sensibilité personnalisée. Le service utilise le seuil et la fenêtre sauvegardés. Les écrans natifs disposent de couleurs claires/sombres et d’une navigation de retour, sans bibliothèque graphique supplémentaire.

Depuis la version 1.3, deux vibrations courtes confirment la sauvegarde du geste. Une double vibration plus marquée annonce le déclenchement d’une alerte (geste ou SOS manuel), puis une impulsion accompagne chaque seconde restante du compte à rebours de cinq secondes. Annuler depuis la notification ou l’accueil arrête les vibrations et empêche l’envoi tant que la transmission n’est pas engagée. Les vibrations s’arrêtent aussi avant la recherche de position, en cas d’erreur ou à l’arrêt du service. Les effets sont courts et sans répétition autonome ; les réglages de vibration et « Ne pas déranger » d’Android restent applicables. Aucun son supplémentaire n’est ajouté.

Un geste ou un SOS ouvre un délai d’annulation de 5 secondes, puis une recherche de position de 8 secondes maximum. L’annulation reste possible pendant la recherche GPS. Dès la transmission des SMS, elle n’est plus possible. Une position absente, ancienne ou trop imprécise est indiquée comme indisponible.

Les callbacks de l’opérateur déterminent le résultat par partie de SMS. Sans confirmation, le résultat reste inconnu. La réception par le destinataire n’est jamais affirmée. Aucun renvoi automatique n’est effectué.

Les SMS peuvent être facturés et apparaître dans l’application SMS du téléphone. Utiliser de préférence des numéros internationaux et configurer une SIM SMS par défaut.

## Autonomie

- Accéléromètre demandé à 25 Hz uniquement pendant la surveillance.
- Préférence pour un capteur de réveil, sans verrou processeur permanent ; livraison des mesures par lots de 400 ms si le capteur possède une FIFO.
- Sans capteur de réveil : choix explicite entre économie (détection non garantie en veille) et protection en veille (consommation accrue).
- Capteur suspendu pendant la calibration et le parcours d’alerte ; ressources libérées à l’arrêt.
- Localisation ponctuelle et bornée lors d’une alerte ; aucun suivi GPS continu.
- Verrou temporaire de 60 secondes maximum pendant l’alerte.
- Aucun serveur AlertGeste, publicité, analyse d’utilisation ou tâche réseau périodique applicative.
- Historique limité à 200 alertes ; version release réduite par R8 et suppression des ressources inutilisées.

Un arrêt forcé, le retrait des autorisations ou les restrictions d’un fabricant peuvent interrompre la protection. L’autonomie et la fiabilité en veille doivent être mesurées sur les appareils cibles.

## Architecture

View Binding, Navigation Component, Hilt, Coroutines/Flow et Room.

- domain : filtre de gravité, détection des pics, validation et agrégation SMS.
- data : base version 2, migration depuis la version 1, transactions et repository.
- service : surveillance, état observable, notifications, localisation bornée et SMS.
- receiver : démarrage, actions utilisateur et confirmations SMS persistantes.
- ui : profil, tableau de bord, contacts, calibration, historique et paramètres.

La migration conserve contacts et historique, sélectionne le dernier calibrage et désactive la surveillance jusqu’à sa réactivation explicite. Les anciennes alertes précisent que leur résultat n’avait pas été vérifié.

## Construction

JDK 17 ou 21, SDK Android 36, accès aux dépôts Google/Maven Central. Wrapper Gradle 8.14 inclus. Configurer sdk.dir dans local.properties.

    .\gradlew.bat testDebugUnitTest lintDebug assembleDebug
    .\gradlew.bat lintRelease assembleRelease bundleRelease

Sans variables de signature, les builds release sont non signés. Voir [DEPLOYMENT.md](DEPLOYMENT.md).

## Tests

Tests JVM : pics distincts, anti-rebond, fenêtre temporelle, initialisation du filtre, validation des entrées, résultats SMS.

Tests Android : migration Room, profil unique, recalibrage, reset, callbacks dupliqués et résultats tardifs.

    .\gradlew.bat connectedDebugAndroidTest

Aucun test automatisé n’envoie de SMS. Les scénarios radio, capteur et veille nécessitent des appareils réels.

## Données

Depuis la version 1.5, chaque alerte terminée peut être supprimée depuis la corbeille de l’historique, après confirmation. Les alertes en cours de préparation ou d’envoi sont protégées jusque dans la base de données. La liste se rafraîchit automatiquement ; la suppression efface aussi le suivi local des parties SMS, sans effacer les SMS chez les destinataires. Cette fonction ne demande aucune permission supplémentaire.

Depuis la version 1.4, le découpage des SMS dispose d’un secours local si Android refuse l’accès aux informations SIM (`getGroupIdLevel1`). Aucune permission `READ_PHONE_STATE` n’est ajoutée. Le texte, les accents, les liens et les paires UTF-16 des emojis sont conservés. Le découpage de secours réserve de la place pour les en-têtes et les anciens opérateurs ; il peut produire davantage de parties que le découpage optimisé d’Android. Seule la préparation est reprise : aucun SMS déjà soumis n’est renvoyé automatiquement. Le test Android `SmsPreparationTest` vérifie cette préparation avec le vrai `SmsManager`, sans envoyer de SMS.

Stockage privé local. Sauvegarde Android et transfert de la base désactivés. La réinitialisation efface les données locales, sans rappeler les SMS transmis. Voir [PRIVACY.md](PRIVACY.md).
