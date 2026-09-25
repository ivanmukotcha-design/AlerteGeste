package com.butembo.alertgeste.data.local.dao;

import androidx.room.*;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.local.entity.Contact;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.local.entity.Utilisateur;
import com.butembo.alertgeste.data.local.entity.SmsPart;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\'J\u0010\u0010\b\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\u0011"}, d2 = {"Lcom/butembo/alertgeste/data/local/dao/UtilisateurDao;", "", "clearUtilisateurs", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUtilisateur", "Lkotlinx/coroutines/flow/Flow;", "Lcom/butembo/alertgeste/data/local/entity/Utilisateur;", "getUtilisateurOnce", "insertUtilisateur", "utilisateur", "(Lcom/butembo/alertgeste/data/local/entity/Utilisateur;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setSurveillance", "active", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUtilisateur", "app_debug"})
@androidx.room.Dao()
public abstract interface UtilisateurDao {
    
    @androidx.room.Query(value = "SELECT * FROM utilisateurs ORDER BY id ASC LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.butembo.alertgeste.data.local.entity.Utilisateur> getUtilisateur();
    
    @androidx.room.Query(value = "SELECT * FROM utilisateurs ORDER BY id ASC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUtilisateurOnce(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.butembo.alertgeste.data.local.entity.Utilisateur> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur utilisateur, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateUtilisateur(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Utilisateur utilisateur, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM utilisateurs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearUtilisateurs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE utilisateurs SET surveillanceActive = :active")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setSurveillance(boolean active, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}