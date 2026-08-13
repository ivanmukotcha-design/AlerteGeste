package com.butembo.alertgeste.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.butembo.alertgeste.data.local.dao.AlerteDao;
import com.butembo.alertgeste.data.local.dao.ContactDao;
import com.butembo.alertgeste.data.local.dao.GesteProfilDao;
import com.butembo.alertgeste.data.local.dao.UtilisateurDao;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.local.entity.Contact;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.local.entity.Utilisateur;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&\u00a8\u0006\u000b"}, d2 = {"Lcom/butembo/alertgeste/data/local/AlertGesteDatabase;", "Landroidx/room/RoomDatabase;", "()V", "alerteDao", "Lcom/butembo/alertgeste/data/local/dao/AlerteDao;", "contactDao", "Lcom/butembo/alertgeste/data/local/dao/ContactDao;", "gesteProfilDao", "Lcom/butembo/alertgeste/data/local/dao/GesteProfilDao;", "utilisateurDao", "Lcom/butembo/alertgeste/data/local/dao/UtilisateurDao;", "app_debug"})
@androidx.room.Database(entities = {com.butembo.alertgeste.data.local.entity.Utilisateur.class, com.butembo.alertgeste.data.local.entity.Contact.class, com.butembo.alertgeste.data.local.entity.GesteProfil.class, com.butembo.alertgeste.data.local.entity.Alerte.class}, version = 1, exportSchema = false)
public abstract class AlertGesteDatabase extends androidx.room.RoomDatabase {
    
    public AlertGesteDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.butembo.alertgeste.data.local.dao.UtilisateurDao utilisateurDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.butembo.alertgeste.data.local.dao.ContactDao contactDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.butembo.alertgeste.data.local.dao.GesteProfilDao gesteProfilDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.butembo.alertgeste.data.local.dao.AlerteDao alerteDao();
}