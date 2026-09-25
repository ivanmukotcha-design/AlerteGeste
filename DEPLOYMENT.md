# Préparation de publication

## Livrables et signature

- app/build/outputs/apk/release/ : APK.
- app/build/outputs/bundle/release/ : AAB pour Google Play.
- app/build/outputs/mapping/release/mapping.txt : conserver avec chaque version pour interpréter les traces R8.

La signature n’est pas inventée ni stockée dans le dépôt. Fournir au processus Gradle :

- ALERTGESTE_KEYSTORE : chemin du keystore ;
- ALERTGESTE_STORE_PASSWORD ;
- ALERTGESTE_KEY_ALIAS ;
- ALERTGESTE_KEY_PASSWORD.

Utiliser un gestionnaire de secrets en CI. Pour une mise à jour existante, conserver la signature compatible. Sans ces variables, les builds restent volontairement non signés. Ne pas distribuer un APK debug comme version publique.

    .\gradlew.bat testDebugUnitTest lintRelease assembleRelease bundleRelease

Vérifier l’APK signé avec apksigner verify --verbose. Conserver les empreintes SHA-256 et augmenter versionCode à chaque publication.

## Google Play

Exigence API 36 vérifiée le 24 septembre 2026 :
https://developer.android.com/google/play/requirements/target-sdk

SEND_SMS exige une déclaration et l’examen de l’exception applicable aux alertes de sécurité physique/d’urgence. L’acceptation n’est pas acquise :
https://support.google.com/googleplay/android-developer/answer/10208820

Déclarer le service de premier plan location et, si conservée pour la reprise au démarrage, la localisation en arrière-plan. Fournir les démonstrations et informations demandées :
https://developer.android.com/develop/background-work/services/fgs/service-types
https://support.google.com/googleplay/android-developer/answer/9799150

Compléter l’identité et les coordonnées de l’éditeur, publier la notice PRIVACY.md à une URL accessible, remplir Data Safety selon le fonctionnement décrit dans cette notice et préparer la fiche Play. Ne pas affirmer que l’application remplace les services de secours.

Pour l’APK direct, les mêmes validations de signature et d’appareil s’appliquent. La disponibilité de la permission SMS dépend également de l’installateur.

## Essais sur appareils réels avant publication

Cette liste décrit les vérifications à effectuer, pas des résultats supposés. Inclure Android 8–10, 13 et 15/16, sur plusieurs fabricants.

- [ ] Installation neuve ; autorisations accordées, refusées et refusées définitivement ; position approximative.
- [ ] Migration depuis la base version 1, conservation des contacts et de l’historique.
- [ ] Une secousse prolongée ne déclenche pas ; trois secousses distinctes déclenchent.
- [ ] Marche, transport, mise en poche et calibration ne produisent pas d’alertes accidentelles.
- [ ] Annulation avant 5 secondes et pendant le GPS : aucun SMS soumis.
- [ ] GPS absent ou imprécis : délai borné et mention de position indisponible.
- [ ] SIM par défaut absente, double SIM, absence de réseau, crédit insuffisant, mode avion.
- [ ] SMS multiparties, échec partiel, callbacks absents ou tardifs.
- [ ] Rotation, retour arrière, grande police, mode sombre, petits écrans, zones système Android 15/16.
- [ ] Redémarrage avec/sans localisation Toujours autoriser ; fermeture forcée ; révocation des permissions.
- [ ] Arrêt/reset pendant le compte à rebours, le GPS et la transmission.
- [ ] Écran éteint 30 min, 2 h puis une nuit, dans chaque mode d’énergie.
- [ ] Notifications bloquées globalement ou par canal ; écran verrouillé.

Utiliser des contacts de test consentants. Les essais radio peuvent envoyer des SMS facturés. Ne pas automatiser ces essais vers les contacts personnels.

## Mesures de consommation

Comparer à durée identique : surveillance inactive, capteur de réveil actif, économie sans capteur de réveil, puis protection en veille avec verrou processeur. Relever batterie, température, CPU, wake locks et pertes de détection via Android Studio Profiler/Battery Historian ou adb shell dumpsys batterystats.

Aucune mesure réelle d’autonomie n’est encore établie. Ne pas annoncer de durée sans essai. Après arrêt, vérifier dans dumpsys sensorservice et dumpsys power que les ressources AlertGeste sont libérées.

## Hygiène Git

Le dépôt d’origine suit des fichiers sous app/build, .gradle et local.properties. Le .gitignore ajouté empêche de nouveaux ajouts mais ne retire pas les fichiers déjà suivis. Prévoir leur retrait de l’index dans un nettoyage dédié, après sauvegarde des modifications utiles. Aucun historique Git ni fichier local préexistant n’est supprimé automatiquement.
