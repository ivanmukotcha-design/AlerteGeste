package com.butembo.alertgeste.data.repository;

import com.butembo.alertgeste.data.local.dao.AlerteDao;
import com.butembo.alertgeste.data.local.dao.ContactDao;
import com.butembo.alertgeste.data.local.dao.GesteProfilDao;
import com.butembo.alertgeste.data.local.dao.UtilisateurDao;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.local.entity.Contact;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.local.entity.Utilisateur;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0010\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0010\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140 0\u001fJ\u000e\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u001fJ\u0012\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0 0\u001fJ\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000e0 H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0012\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0 0\u001fJ\u0012\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140 0\u001fJ\u000e\u0010&\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u001fJ\u0010\u0010\'\u001a\u0004\u0018\u00010\u001cH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010(\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010)\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010*\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010+\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010,\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010-\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010.\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010/\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "", "utilisateurDao", "Lcom/butembo/alertgeste/data/local/dao/UtilisateurDao;", "contactDao", "Lcom/butembo/alertgeste/data/local/dao/ContactDao;", "gesteProfilDao", "Lcom/butembo/alertgeste/data/local/dao/GesteProfilDao;", "alerteDao", "Lcom/butembo/alertgeste/data/local/dao/AlerteDao;", "(Lcom/butembo/alertgeste/data/local/dao/UtilisateurDao;Lcom/butembo/alertgeste/data/local/dao/ContactDao;Lcom/butembo/alertgeste/data/local/dao/GesteProfilDao;Lcom/butembo/alertgeste/data/local/dao/AlerteDao;)V", "ajouterContact", "", "contact", "Lcom/butembo/alertgeste/data/local/entity/Contact;", "(Lcom/butembo/alertgeste/data/local/entity/Contact;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "effacerHistorique", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enregistrerAlerte", "alerte", "Lcom/butembo/alertgeste/data/local/entity/Alerte;", "(Lcom/butembo/alertgeste/data/local/entity/Alerte;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enregistrerProfil", "profil", "Lcom/butembo/alertgeste/data/local/entity/GesteProfil;", "(Lcom/butembo/alertgeste/data/local/entity/GesteProfil;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enregistrerUtilisateur", "utilisateur", "Lcom/butembo/alertgeste/data/local/entity/Utilisateur;", "(Lcom/butembo/alertgeste/data/local/entity/Utilisateur;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHistoriqueAlertes", "Lkotlinx/coroutines/flow/Flow;", "", "getProfilActif", "getTousContacts", "getTousContactsOnce", "getTousLesContacts", "getToutesAlertes", "getUtilisateur", "getUtilisateurOnce", "mettreAJourContact", "mettreAJourUtilisateur", "reinitialiserCompte", "sauvegarderProfil", "sauvegarderUtilisateur", "supprimerAlerte", "supprimerContact", "updateUtilisateur", "app_debug"})
public final class AlertGesteRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.local.dao.UtilisateurDao utilisateurDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.local.dao.ContactDao contactDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.local.dao.GesteProfilDao gesteProfilDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.local.dao.AlerteDao alerteDao = null;
    
    @javax.inject.Inject()
    public AlertGesteRepository(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.dao.UtilisateurDao utilisateurDao, @org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.dao.ContactDao contactDao, @org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.dao.GesteProfilDao gesteProfilDao, @org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.dao.AlerteDao alerteDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.butembo.alertgeste.data.local.entity.Utilisateur> getUtilisateur() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUtilisateurOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.butembo.alertgeste.data.local.entity.Utilisateur> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object enregistrerUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur utilisateur, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur utilisateur, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sauvegarderUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur utilisateur, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object mettreAJourUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur utilisateur, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.butembo.alertgeste.data.local.entity.Contact>> getTousLesContacts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.butembo.alertgeste.data.local.entity.Contact>> getTousContacts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTousContactsOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.butembo.alertgeste.data.local.entity.Contact>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object ajouterContact(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object supprimerContact(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object mettreAJourContact(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.butembo.alertgeste.data.local.entity.GesteProfil> getProfilActif() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object enregistrerProfil(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.GesteProfil profil, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sauvegarderProfil(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.GesteProfil profil, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.butembo.alertgeste.data.local.entity.Alerte>> getHistoriqueAlertes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.butembo.alertgeste.data.local.entity.Alerte>> getToutesAlertes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object enregistrerAlerte(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Alerte alerte, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object supprimerAlerte(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Alerte alerte, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object effacerHistorique(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object reinitialiserCompte(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}