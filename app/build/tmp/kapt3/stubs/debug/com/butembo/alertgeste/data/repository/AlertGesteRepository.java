package com.butembo.alertgeste.data.repository;

import com.butembo.alertgeste.data.local.AlertGesteDatabase;
import com.butembo.alertgeste.data.local.entity.*;
import com.butembo.alertgeste.domain.AlertStatus;
import com.butembo.alertgeste.domain.InputRules;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010 J\u0016\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#H\u0086@\u00a2\u0006\u0002\u0010$J\u0016\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\'H\u0086@\u00a2\u0006\u0002\u0010(J\u0018\u0010)\u001a\u0004\u0018\u00010\u001f2\u0006\u0010*\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010+J\u0018\u0010,\u001a\u0004\u0018\u00010\u001f2\u0006\u0010*\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010+J\u0012\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0/0.J\u000e\u00100\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010#0.J\u0012\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180/0.J\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020\u00180/H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0012\u00103\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180/0.J\u0012\u00104\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0/0.J\u000e\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\'0.J\u0010\u00106\u001a\u0004\u0018\u00010\'H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u00107\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u00108\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\'H\u0086@\u00a2\u0006\u0002\u0010(J\u0016\u00109\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001f0.2\u0006\u0010*\u001a\u00020\u001dJ$\u0010:\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u001d2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020<0/H\u0086@\u00a2\u0006\u0002\u0010=J \u0010>\u001a\u0004\u0018\u00010\u001f2\u0006\u0010*\u001a\u00020?2\u0006\u0010@\u001a\u00020?H\u0086@\u00a2\u0006\u0002\u0010AJ\u000e\u0010B\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010C\u001a\u0004\u0018\u00010\u001f2\u0006\u0010*\u001a\u00020\u001dH\u0082@\u00a2\u0006\u0002\u0010+J\u000e\u0010D\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010E\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#H\u0086@\u00a2\u0006\u0002\u0010$J\u0016\u0010F\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\'H\u0086@\u00a2\u0006\u0002\u0010(J\u0016\u0010G\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0082@\u00a2\u0006\u0002\u0010\u0019J(\u0010H\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u001d2\u0006\u0010@\u001a\u00020?2\b\b\u0002\u0010I\u001a\u00020?H\u0086@\u00a2\u0006\u0002\u0010JJ*\u0010K\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u001d2\b\u0010L\u001a\u0004\u0018\u00010M2\b\u0010N\u001a\u0004\u0018\u00010MH\u0086@\u00a2\u0006\u0002\u0010OJ\u0016\u0010P\u001a\u00020\u00162\u0006\u0010Q\u001a\u00020RH\u0086@\u00a2\u0006\u0002\u0010SJ\u0016\u0010T\u001a\u00020R2\u0006\u0010\u001e\u001a\u00020\u001fH\u0086@\u00a2\u0006\u0002\u0010 J\u0016\u0010U\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010V\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\'H\u0086@\u00a2\u0006\u0002\u0010(R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006W"}, d2 = {"Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "", "db", "Lcom/butembo/alertgeste/data/local/AlertGesteDatabase;", "(Lcom/butembo/alertgeste/data/local/AlertGesteDatabase;)V", "alerts", "Lcom/butembo/alertgeste/data/local/dao/AlerteDao;", "getAlerts", "()Lcom/butembo/alertgeste/data/local/dao/AlerteDao;", "contacts", "Lcom/butembo/alertgeste/data/local/dao/ContactDao;", "getContacts", "()Lcom/butembo/alertgeste/data/local/dao/ContactDao;", "profiles", "Lcom/butembo/alertgeste/data/local/dao/GesteProfilDao;", "getProfiles", "()Lcom/butembo/alertgeste/data/local/dao/GesteProfilDao;", "users", "Lcom/butembo/alertgeste/data/local/dao/UtilisateurDao;", "getUsers", "()Lcom/butembo/alertgeste/data/local/dao/UtilisateurDao;", "ajouterContact", "", "contact", "Lcom/butembo/alertgeste/data/local/entity/Contact;", "(Lcom/butembo/alertgeste/data/local/entity/Contact;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "effacerHistorique", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enregistrerAlerte", "", "alert", "Lcom/butembo/alertgeste/data/local/entity/Alerte;", "(Lcom/butembo/alertgeste/data/local/entity/Alerte;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enregistrerProfil", "profile", "Lcom/butembo/alertgeste/data/local/entity/GesteProfil;", "(Lcom/butembo/alertgeste/data/local/entity/GesteProfil;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "enregistrerUtilisateur", "user", "Lcom/butembo/alertgeste/data/local/entity/Utilisateur;", "(Lcom/butembo/alertgeste/data/local/entity/Utilisateur;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "expireAlert", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAlerte", "getHistoriqueAlertes", "Lkotlinx/coroutines/flow/Flow;", "", "getProfilActif", "getTousContacts", "getTousContactsOnce", "getTousLesContacts", "getToutesAlertes", "getUtilisateur", "getUtilisateurOnce", "mettreAJourContact", "mettreAJourUtilisateur", "observeAlerte", "prepareParts", "parts", "Lcom/butembo/alertgeste/data/local/entity/SmsPart;", "(JLjava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordPart", "", "status", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recoverInterrupted", "refreshResult", "reinitialiserCompte", "sauvegarderProfil", "sauvegarderUtilisateur", "saveContact", "setAlertStatus", "detail", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setLocation", "latitude", "", "longitude", "(JLjava/lang/Double;Ljava/lang/Double;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setSurveillance", "active", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "supprimerAlerte", "supprimerContact", "updateUtilisateur", "app_debug"})
public final class AlertGesteRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.local.AlertGesteDatabase db = null;
    
    @javax.inject.Inject()
    public AlertGesteRepository(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.AlertGesteDatabase db) {
        super();
    }
    
    private final com.butembo.alertgeste.data.local.dao.UtilisateurDao getUsers() {
        return null;
    }
    
    private final com.butembo.alertgeste.data.local.dao.ContactDao getContacts() {
        return null;
    }
    
    private final com.butembo.alertgeste.data.local.dao.GesteProfilDao getProfiles() {
        return null;
    }
    
    private final com.butembo.alertgeste.data.local.dao.AlerteDao getAlerts() {
        return null;
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
    com.butembo.alertgeste.data.local.entity.Utilisateur user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sauvegarderUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object mettreAJourUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setSurveillance(boolean active, @org.jetbrains.annotations.NotNull()
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
    public final java.lang.Object mettreAJourContact(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object saveContact(com.butembo.alertgeste.data.local.entity.Contact contact, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object supprimerContact(@org.jetbrains.annotations.NotNull()
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
    com.butembo.alertgeste.data.local.entity.GesteProfil profile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sauvegarderProfil(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.GesteProfil profile, @org.jetbrains.annotations.NotNull()
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
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.butembo.alertgeste.data.local.entity.Alerte> observeAlerte(long id) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAlerte(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.butembo.alertgeste.data.local.entity.Alerte> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object enregistrerAlerte(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Alerte alert, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setAlertStatus(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    java.lang.String detail, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setLocation(long id, @org.jetbrains.annotations.Nullable()
    java.lang.Double latitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object prepareParts(long id, @org.jetbrains.annotations.NotNull()
    java.util.List<com.butembo.alertgeste.data.local.entity.SmsPart> parts, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recordPart(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.butembo.alertgeste.data.local.entity.Alerte> $completion) {
        return null;
    }
    
    private final java.lang.Object refreshResult(long id, kotlin.coroutines.Continuation<? super com.butembo.alertgeste.data.local.entity.Alerte> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object expireAlert(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.butembo.alertgeste.data.local.entity.Alerte> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recoverInterrupted(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object supprimerAlerte(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Alerte alert, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
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