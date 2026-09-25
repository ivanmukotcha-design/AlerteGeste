package com.butembo.alertgeste.data.local.dao;

import androidx.room.*;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.local.entity.Contact;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.local.entity.Utilisateur;
import com.butembo.alertgeste.data.local.entity.SmsPart;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u0006\n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00100\u000fH\'J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0007\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u00102\u0006\u0010\u000b\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\u00020\u00032\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00120\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000f2\u0006\u0010\u0007\u001a\u00020\bH\'J\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u0010H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001f\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010 \u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010!\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\"J*\u0010#\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010%H\u00a7@\u00a2\u0006\u0002\u0010\'J\u001e\u0010(\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010*J&\u0010+\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010)\u001a\u00020\u00132\u0006\u0010,\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010-\u00a8\u0006."}, d2 = {"Lcom/butembo/alertgeste/data/local/dao/AlerteDao;", "", "clearHistorique", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteCompletedAlerte", "", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "expireParts", "alertId", "getAlerte", "Lcom/butembo/alertgeste/data/local/entity/Alerte;", "getHistoriqueAlertes", "Lkotlinx/coroutines/flow/Flow;", "", "getPart", "Lcom/butembo/alertgeste/data/local/entity/SmsPart;", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getParts", "insertAlerte", "alerte", "(Lcom/butembo/alertgeste/data/local/entity/Alerte;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertParts", "parts", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "interruptPreparations", "observeAlerte", "pendingAlerts", "pruneHistory", "setContacts", "contacts", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setLocation", "latitude", "", "longitude", "(JLjava/lang/Double;Ljava/lang/Double;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setPartStatus", "status", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setStatus", "detail", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface AlerteDao {
    
    @androidx.room.Query(value = "SELECT * FROM alertes ORDER BY horodatage DESC LIMIT 200")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.butembo.alertgeste.data.local.entity.Alerte>> getHistoriqueAlertes();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAlerte(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Alerte alerte, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM alertes WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAlerte(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.butembo.alertgeste.data.local.entity.Alerte> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM alertes WHERE id = :id")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.butembo.alertgeste.data.local.entity.Alerte> observeAlerte(long id);
    
    @androidx.room.Query(value = "UPDATE alertes SET statut = :status, detail = :detail WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setStatus(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    java.lang.String detail, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE alertes SET latitude = :latitude, longitude = :longitude WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setLocation(long id, @org.jetbrains.annotations.Nullable()
    java.lang.Double latitude, @org.jetbrains.annotations.Nullable()
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE alertes SET contactsNotifies = :contacts WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setContacts(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String contacts, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertParts(@org.jetbrains.annotations.NotNull()
    java.util.List<com.butembo.alertgeste.data.local.entity.SmsPart> parts, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM sms_parts WHERE alerteId = :alertId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getParts(long alertId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.butembo.alertgeste.data.local.entity.SmsPart>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM sms_parts WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPart(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.butembo.alertgeste.data.local.entity.SmsPart> $completion);
    
    @androidx.room.Query(value = "UPDATE sms_parts SET statut = :status WHERE id = :id AND statut IN (\'EN_ATTENTE\', \'INCONNU\')")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setPartStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE sms_parts SET statut = \'INCONNU\' WHERE alerteId = :alertId AND statut = \'EN_ATTENTE\'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object expireParts(long alertId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE alertes SET statut = \'INTERROMPUE\', detail = \'Service interrompu avant envoi.\' WHERE statut IN (\'COMPTE_A_REBOURS\', \'LOCALISATION\')")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object interruptPreparations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT id FROM alertes WHERE statut = \'EN_COURS\'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object pendingAlerts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Query(value = "DELETE FROM alertes WHERE id NOT IN (SELECT id FROM alertes ORDER BY horodatage DESC LIMIT 200) AND statut NOT IN (\'COMPTE_A_REBOURS\', \'LOCALISATION\', \'EN_COURS\')")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object pruneHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM alertes WHERE id = :id AND statut NOT IN (\'COMPTE_A_REBOURS\', \'LOCALISATION\', \'EN_COURS\')")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteCompletedAlerte(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "DELETE FROM alertes")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearHistorique(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}