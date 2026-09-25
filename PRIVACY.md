# Données et confidentialité — AlertGeste

Notice technique à compléter avec l’identité et les coordonnées de l’éditeur avant publication publique.

## Sur le téléphone

AlertGeste conserve nom, téléphone, message, contacts, configuration du geste et les 200 dernières alertes dans une base privée Android. L’historique peut contenir position et résultats d’envoi. Aucun chiffrement applicatif supplémentaire n’est ajouté à la protection du système.

La base est exclue des sauvegardes Android et du transfert vers un autre appareil. La réinitialisation efface profil, contacts, geste, historique et préférences d’énergie.

## Transmission

Un geste reconnu ou un SOS lance un délai d’annulation puis transmet par SMS le message, l’identité configurée et la position disponible aux contacts choisis. L’opérateur et les destinataires reçoivent ces données. Les SMS peuvent être facturés et visibles dans l’application SMS. Effacer l’historique local ne les supprime pas chez l’opérateur ou les destinataires.

Le lien de carte utilise Google Maps si le destinataire l’ouvre. La localisation utilise les services Android natifs et les réglages du téléphone ; aucune dépendance Google Play Services n’est nécessaire. Aucun serveur AlertGeste, publicité ou outil d’analyse d’utilisation n’est intégré.

## Autorisations

- SMS : transmettre l’alerte demandée par geste ou SOS.
- Localisation : obtenir ponctuellement la position pendant une alerte ; position approximative acceptée.
- Localisation en arrière-plan facultative : reprendre le service après redémarrage. Elle n’active pas un suivi GPS permanent.
- Notifications : afficher la surveillance, permettre l’annulation avant transmission et présenter le résultat.
- Capteur : reconnaître les mouvements, sans conserver ni transmettre les échantillons.
- Maintien éveillé : limité au parcours d’alerte ou explicitement activé pour la surveillance sur un appareil sans capteur de réveil.

Les autorisations peuvent être retirées dans Android ; cela peut empêcher la protection. La surveillance peut être arrêtée et les données effacées dans l’application.
