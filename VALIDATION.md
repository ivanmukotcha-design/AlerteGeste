# Validation technique — version 1.6 — 25 septembre 2026

VersionCode 7. Correction du compte rendu ambigu « 0/1 destinataire(s) : toutes les parties confirmées ». Zéro destinataire confirmé est maintenant indiqué explicitement ; les résultats en cours, partiels, inconnus et échoués restent distincts. Une confirmation d’envoi Android ne devient pas une confirmation de réception.

Les exceptions pendant la soumission à Android et les codes de retour d’échec sont conservés par partie SMS, puis affichés par destinataire. Aucun diagnostic de crédit insuffisant n’est déduit d’une erreur générique. Les confirmations tardives et dupliquées conservent leurs règles précédentes. Aucun renvoi automatique, aucune transmission de test et aucune permission supplémentaire.

Room passe de la version 2 à la version 3 avec une colonne failureReason. Les migrations 1→2→3 et 2→3 préservent profil, contacts, geste et historique ; le texte trompeur des anciennes alertes est rectifié. Les causes qui n’avaient pas été conservées restent irrécupérables.

## Vérifications de la version 1.6

- Builds debug, release, APK AndroidTest et AAB réussis.
- 46 tests JVM réussis, dont les nouveaux cas 0/1, envoi partiel, résultat inconnu, exception de sécurité et code Android générique.
- Lint debug/release : 0 erreur, 98 avertissements par variante.
- 7 tests DatabaseTest réussis sur le Pixel 7a. Bases fictives uniquement ; vérification des deux chemins de migration, des erreurs persistantes, des callbacks dupliqués et de la suppression.
- Rapport : app/build/reports/sms-diagnostics-device.txt.
- Installation en mise à jour de la version 1.6 avec conservation des données, suppression du seul paquet temporaire de tests, puis réouverture de l’application.
- Surveillance arrêtée après ces opérations : réactivation manuelle nécessaire.

La cause du non-envoi réel signalé par l’utilisateur reste à identifier. Un nouvel essai utilisateur doit fournir le détail d’erreur maintenant conservé ; les tests exécutés ne valident pas la transmission radio ni la réception chez les contacts.

## APK de test actuel

AlertGeste-1.6-test.apk, à la racine du projet : 6 582 157 octets. Copie vérifiée de app/build/outputs/apk/debug/app-debug.apk, signée avec la clé debug.

SHA-256 : 92835FF24202C19BA89E168AAA8500A923605A7A0060EB8C2E1A8CC9482E6E78.

Les builds release actuels restent non signés. Les mesures et empreintes des sections archivées ci-dessous concernent leurs versions respectives ; les sorties de compilation ont été remplacées par la version 1.6.

## Archive de validation 1.5

VersionCode 6. Ajout de la suppression individuelle dans l’historique : corbeille accessible, confirmation avec date, retour de succès ou d’erreur et rafraîchissement automatique de la liste, y compris lorsqu’elle devient vide. Aucune dépendance ni permission supplémentaire.

Les alertes en compte à rebours, localisation ou envoi ne sont pas supprimables. Une requête DELETE conditionnelle vérifie atomiquement le statut réel en base, même si la sélection affichée est ancienne. Les parties SMS liées sont supprimées par la clé étrangère existante ; les callbacks tardifs sont ignorés sans recréer l’alerte. Aucun changement du schéma Room.

## Vérifications de la version 1.5

- Construction debug, release, APK AndroidTest et AAB : réussie.
- 39 tests JVM réussis ; aucun échec ni erreur.
- Lint debug et release : 0 erreur, 98 avertissements par variante.
- DatabaseTest exécuté sur le Pixel 7a : 5 tests réussis. Les deux nouveaux tests couvrent la suppression ciblée, la cascade SMS, la préservation des autres alertes, un callback tardif, une seconde suppression, un historique vide et la protection des trois statuts actifs malgré une sélection périmée.
- Tests exclusivement sur bases fictives en mémoire ou base de migration séparée : aucun effacement de l’historique réel et aucun envoi de SMS.
- Version 1.5 installée avec adb install -r, données conservées. Paquet de tests retiré, application rouverte. La surveillance est arrêtée après ces opérations et doit être réactivée depuis l’accueil.
- Rapport appareil : app/build/reports/history-deletion-device.txt.
- Le parcours visuel complet de confirmation/annulation reste à vérifier manuellement ; les tests exécutés valident la base et le repository.

## Artefacts actuels

| Fichier | Taille exacte | Signature |
|---|---:|---|
| app/build/outputs/apk/debug/app-debug.apk | 6 578 305 octets | Debug, installé pour essais |
| app/build/outputs/apk/release/app-release-unsigned.apk | 1 807 669 octets | Non signé |
| app/build/outputs/bundle/release/app-release.aab | 2 907 440 octets | Non signé |

SHA-256 :

- APK debug : F8BF6DD8B746BB1AD280906CD30DFC59588BBE358A94A93D0CD9CFF3B59EACE5
- APK release : E41C7F7176D2C5DD6B103EED7A2DE3372A4AFEDCEB1E801680382C7F425B8DAE
- AAB release : 19D7C0D6F54FFB41B18D980EFFA1F918904CA24822445744154ED878A13E76EE

## Archive de validation 1.4

Les mesures et empreintes ci-dessous concernent la précédente version 1.4 ; les fichiers de sortie ont depuis été remplacés par la version 1.5 décrite ci-dessus.

Version 1.4, versionCode 5. Correction du blocage SMS « getGroupIdLevel1 ». Les interfaces, le geste et les vibrations des versions précédentes sont conservés.

## Correction

Le code appelait directement SmsManager.divideMessage avant toute transmission. Sur certaines configurations Android, cette préparation lit des informations SIM et peut lever une SecurityException. Le chemin est visible dans le [code Android de SmsMessage](https://android.googlesource.com/platform/frameworks/base/+/refs/heads/main/telephony/java/android/telephony/SmsMessage.java).

SmsText.prepare conserve le découpage Android lorsqu’il fonctionne. En cas de SecurityException pendant cette seule préparation, un découpage local de secours est utilisé :

- Aucune nouvelle permission téléphonique ; READ_PHONE_STATE n’est pas déclarée.
- Limites de 160 septets en SMS ASCII GSM simple et 153 en multiparties, avec deux septets pour les caractères de la table d’extension.
- Autres caractères traités prudemment en Unicode : 70 unités UTF-16 en SMS simple, 66 par partie en multiparties. La marge couvre aussi la numérotation de pages des anciens opérateurs sans EMS. Le découpage Android optimisé reste prioritaire.
- Aucune coupure entre les deux unités UTF-16 d’un emoji ; texte, espaces, accents, liens et ordre des caractères conservés.
- Ce découpage conservateur peut produire davantage de parties que les optimisations propres à un opérateur.
- Le mécanisme de secours se situe avant la création des enregistrements de suivi et avant l’appel d’envoi. Aucun envoi déjà soumis n’est réessayé.
- Le suivi et les confirmations de chaque partie restent utilisés comme auparavant.

## Vérifications exécutées

- Compilation debug, release, APK AndroidTest et AAB : réussie.
- Tests JVM : 39 tests réussis, aucun échec ni erreur. Les 11 nouveaux tests couvrent le refus getGroupIdLevel1, le chemin Android normal, les limites ASCII/Unicode, les extensions GSM, les caractères accentués, les liens, les emojis, les espaces et les erreurs non concernées.
- Android Lint debug/release : 0 erreur, 99 avertissements par variante, sans baseline.
- git diff --check sur les sources et configurations : réussi.
- Aucun changement du schéma de base de données ; les tests Room sont compilés mais n’ont pas été réexécutés sur téléphone pour cette correction.

Commande de compilation :

    gradlew.bat :app:testDebugUnitTest :app:lintDebug :app:lintRelease :app:assembleDebug :app:assembleDebugAndroidTest :app:assembleRelease :app:bundleRelease --max-workers=2

## Test sur le Pixel 7a

- Version 1.4 installée en mise à jour avec adb install -r, sans effacement des données.
- Test instrumenté SmsPreparationTest exécuté sur le Pixel 7a sous Android 17 / API 37 : 1 test réussi.
- Le test vérifie que READ_PHONE_STATE n’est pas accordée et appelle le vrai SmsManager.divideMessage avec un message Unicode long.
- Android refuse effectivement l’accès aux informations SIM ; le correctif prépare ensuite cinq parties, dont la concaténation restitue exactement le message initial.
- Résultat du journal : SIM access denied=true; prepared parts=5; no SMS sent.
- Aucun appel d’envoi de SMS, aucune lecture des contacts réels et aucun SMS transmis pendant ce test.
- Journaux : app/build/reports/sms-preparation-device.txt et app/build/reports/sms-preparation-device-log.txt.
- Le paquet de tests temporaire a été retiré après réussite. AlertGeste 1.4 est conservé et l’application a été rouverte.
- La mise à jour et le test ont interrompu la surveillance auparavant active. Elle doit être réactivée depuis l’accueil ; elle n’a pas été relancée automatiquement.

Ce test valide la correction du blocage de préparation sur l’appareil concerné. Il ne vérifie pas la transmission radio ni la réception d’un SMS par un contact.

## Artefacts

| Fichier | Taille exacte | Signature |
|---|---:|---|
| app/build/outputs/apk/debug/app-debug.apk | 6 569 481 octets | Debug, installé pour essais |
| app/build/outputs/apk/release/app-release-unsigned.apk | 1 792 785 octets | Non signé |
| app/build/outputs/bundle/release/app-release.aab | 2 880 427 octets | Non signé |

SHA-256 :

- APK debug : B27CFB9C63036932E73BB2BE86520B57155F9C0656664EEBE53C4C64AE382DED
- APK release : 541874515D0DDCD28730BA66B2F5BC72E68B8E42BEC79462B4CAD1CCD7CAD0B2
- AAB release : A285BE0A295F46CFE9CA7A388A84DC3585F454A6855D1D6597BE02CEB4EA3C4C

La taille de l’APK ne mesure ni la RAM ni la consommation réelle. Signature de production et vérifications de distribution restent nécessaires avant publication.

## Vérifications restantes

Un essai radio avec un contact informé reste nécessaire pour vérifier l’envoi et la réception réels. Les scénarios de capteurs, faux positifs, vibrations, veille et consommation restent ceux de DEPLOYMENT.md et des précédentes versions.
