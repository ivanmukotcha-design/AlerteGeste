package com.butembo.alertgeste.di;

import android.content.Context;
import androidx.room.Room;
import com.butembo.alertgeste.data.local.AlertGesteDatabase;
import com.butembo.alertgeste.data.local.dao.AlerteDao;
import com.butembo.alertgeste.data.local.dao.ContactDao;
import com.butembo.alertgeste.data.local.dao.GesteProfilDao;
import com.butembo.alertgeste.data.local.dao.UtilisateurDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0010"}, d2 = {"Lcom/butembo/alertgeste/di/AppModule;", "", "()V", "provideAlerteDao", "Lcom/butembo/alertgeste/data/local/dao/AlerteDao;", "db", "Lcom/butembo/alertgeste/data/local/AlertGesteDatabase;", "provideContactDao", "Lcom/butembo/alertgeste/data/local/dao/ContactDao;", "provideDatabase", "context", "Landroid/content/Context;", "provideGesteProfilDao", "Lcom/butembo/alertgeste/data/local/dao/GesteProfilDao;", "provideUtilisateurDao", "Lcom/butembo/alertgeste/data/local/dao/UtilisateurDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.butembo.alertgeste.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.data.local.AlertGesteDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.data.local.dao.UtilisateurDao provideUtilisateurDao(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.AlertGesteDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.data.local.dao.ContactDao provideContactDao(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.AlertGesteDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.data.local.dao.GesteProfilDao provideGesteProfilDao(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.AlertGesteDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.data.local.dao.AlerteDao provideAlerteDao(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.AlertGesteDatabase db) {
        return null;
    }
}