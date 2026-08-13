package com.butembo.alertgeste.data.local.dao;

import androidx.room.*;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.local.entity.Contact;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.local.entity.Utilisateur;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\'J\u0016\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/butembo/alertgeste/data/local/dao/GesteProfilDao;", "", "clearProfils", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProfilActif", "Lkotlinx/coroutines/flow/Flow;", "Lcom/butembo/alertgeste/data/local/entity/GesteProfil;", "insertProfil", "profil", "(Lcom/butembo/alertgeste/data/local/entity/GesteProfil;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface GesteProfilDao {
    
    @androidx.room.Query(value = "SELECT * FROM geste_profils LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.butembo.alertgeste.data.local.entity.GesteProfil> getProfilActif();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertProfil(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.GesteProfil profil, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM geste_profils")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearProfils(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}