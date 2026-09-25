package com.butembo.alertgeste.domain

object AlertStatus {
    const val COUNTDOWN = "COMPTE_A_REBOURS"
    const val LOCATING = "LOCALISATION"
    const val SENDING = "EN_COURS"
    const val SENT = "ENVOYEE"
    const val PARTIAL = "PARTIELLE"
    const val FAILED = "ECHEC"
    const val UNKNOWN = "INCONNU"
    const val CANCELLED = "ANNULEE"
    const val INTERRUPTED = "INTERROMPUE"
    const val PENDING = "EN_ATTENTE"

    fun isInProgress(status: String): Boolean =
        status == COUNTDOWN || status == LOCATING || status == SENDING

    fun aggregate(parts: List<String>): String = when {
        parts.isEmpty() -> FAILED
        PENDING in parts -> SENDING
        parts.all { it == SENT } -> SENT
        UNKNOWN in parts -> UNKNOWN
        SENT in parts -> PARTIAL
        else -> FAILED
    }

    fun summary(status: String, sent: Int, total: Int): String {
        val count = if (sent == 0) "Aucun envoi complet confirmé (0/$total destinataire(s))."
            else "$sent/$total destinataire(s) avec envoi complet confirmé par Android."
        val result = when (status) {
            SENDING -> "Envoi encore en cours."
            SENT -> "Réception non vérifiée."
            FAILED -> "Échec signalé lors de l’envoi."
            PARTIAL -> "Certaines parties ont été envoyées, d’autres ont échoué. Réception non vérifiée."
            UNKNOWN -> "Certaines confirmations d’envoi ne sont pas parvenues. Le résultat reste inconnu."
            else -> label(status)
        }
        return "$count $result"
    }

    fun label(status: String): String = when (status) {
        COUNTDOWN -> "Annulation possible"
        LOCATING -> "Recherche de position"
        SENDING -> "Envoi en cours"
        SENT -> "SMS envoyés — réception non confirmée"
        PARTIAL -> "Envoi partiel"
        FAILED -> "Échec de l’envoi"
        UNKNOWN -> "Résultat d’envoi non confirmé"
        CANCELLED -> "Alerte annulée avant envoi"
        INTERRUPTED -> "Alerte interrompue avant envoi"
        else -> status
    }
}

object InputRules {
    fun phone(raw: String): String? {
        val normalized = raw.trim().replace(Regex("[\\s().-]"), "")
        return normalized.takeIf { Regex("\\+?[0-9]{7,15}").matches(it) }
    }
    fun validProfile(name: String, telephone: String, message: String) =
        name.trim().length in 1..80 && phone(telephone) != null && message.trim().length in 1..480
}
