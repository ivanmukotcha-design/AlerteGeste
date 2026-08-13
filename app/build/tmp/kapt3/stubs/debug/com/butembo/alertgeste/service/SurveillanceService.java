package com.butembo.alertgeste.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.hardware.*;
import android.location.Location;
import android.os.*;
import android.telephony.SmsManager;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import com.butembo.alertgeste.R;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import com.butembo.alertgeste.receiver.AlerteActionReceiver;
import com.butembo.alertgeste.ui.MainActivity;
import com.google.android.gms.location.*;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.*;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 :2\u00020\u00012\u00020\u0002:\u0001:B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u001eH\u0002J\b\u0010 \u001a\u00020\u001eH\u0002J\b\u0010!\u001a\u00020\u001eH\u0002J\b\u0010\"\u001a\u00020\u001eH\u0002J\u0016\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020%H\u0082@\u00a2\u0006\u0002\u0010&J\b\u0010\'\u001a\u00020\u001eH\u0002J\u0010\u0010(\u001a\u0004\u0018\u00010)H\u0082@\u00a2\u0006\u0002\u0010*J\u0018\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\u00052\u0006\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020\u001eH\u0016J\b\u00100\u001a\u00020\u001eH\u0016J\u0010\u00101\u001a\u00020\u001e2\u0006\u00102\u001a\u000203H\u0016J\"\u00104\u001a\u00020.2\b\u00105\u001a\u0004\u0018\u0001062\u0006\u00107\u001a\u00020.2\u0006\u00108\u001a\u00020.H\u0016J\b\u00109\u001a\u00020\u001eH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0012\u001a\u00020\u00138\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006;"}, d2 = {"Lcom/butembo/alertgeste/service/SurveillanceService;", "Landroidx/lifecycle/LifecycleService;", "Landroid/hardware/SensorEventListener;", "()V", "accelerometre", "Landroid/hardware/Sensor;", "alerteEnCours", "", "alpha", "", "countdownJob", "Lkotlinx/coroutines/Job;", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "gravite", "", "profil", "Lcom/butembo/alertgeste/data/local/entity/GesteProfil;", "repository", "Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "getRepository", "()Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "setRepository", "(Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;)V", "sensorManager", "Landroid/hardware/SensorManager;", "timestampsDetection", "", "", "afficherNotifComptArebours", "", "annulerAlerte", "declencherAlerte", "demarrerForeground", "enregistrerCapteur", "enregistrerEchecAlerte", "raison", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "envoyerAlerte", "obtenirLocalisation", "Landroid/location/Location;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onAccuracyChanged", "sensor", "accuracy", "", "onCreate", "onDestroy", "onSensorChanged", "event", "Landroid/hardware/SensorEvent;", "onStartCommand", "intent", "Landroid/content/Intent;", "flags", "startId", "vibrer", "Companion", "app_debug"})
public final class SurveillanceService extends androidx.lifecycle.LifecycleService implements android.hardware.SensorEventListener {
    @javax.inject.Inject()
    public com.butembo.alertgeste.data.repository.AlertGesteRepository repository;
    private android.hardware.SensorManager sensorManager;
    @org.jetbrains.annotations.Nullable()
    private android.hardware.Sensor accelerometre;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    @org.jetbrains.annotations.Nullable()
    private com.butembo.alertgeste.data.local.entity.GesteProfil profil;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Long> timestampsDetection = null;
    private boolean alerteEnCours = false;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job countdownJob;
    private final float alpha = 0.8F;
    @org.jetbrains.annotations.NotNull()
    private float[] gravite;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_DEMARRER = "ACTION_DEMARRER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_ARRETER = "ACTION_ARRETER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_SOS_MANUEL = "ACTION_SOS_MANUEL";
    public static final int NOTIF_ID_SERVICE = 1;
    public static final int NOTIF_ID_ALERTE = 2;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_ANNULER_ALERTE = "ACTION_ANNULER_ALERTE";
    @org.jetbrains.annotations.NotNull()
    public static final com.butembo.alertgeste.service.SurveillanceService.Companion Companion = null;
    
    public SurveillanceService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.data.repository.AlertGesteRepository getRepository() {
        return null;
    }
    
    public final void setRepository(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.repository.AlertGesteRepository p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    private final void demarrerForeground() {
    }
    
    private final void enregistrerCapteur() {
    }
    
    @java.lang.Override()
    public void onSensorChanged(@org.jetbrains.annotations.NotNull()
    android.hardware.SensorEvent event) {
    }
    
    @java.lang.Override()
    public void onAccuracyChanged(@org.jetbrains.annotations.NotNull()
    android.hardware.Sensor sensor, int accuracy) {
    }
    
    private final void declencherAlerte() {
    }
    
    private final void annulerAlerte() {
    }
    
    private final void afficherNotifComptArebours() {
    }
    
    private final void envoyerAlerte() {
    }
    
    private final java.lang.Object obtenirLocalisation(kotlin.coroutines.Continuation<? super android.location.Location> $completion) {
        return null;
    }
    
    private final java.lang.Object enregistrerEchecAlerte(java.lang.String raison, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void vibrer() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/butembo/alertgeste/service/SurveillanceService$Companion;", "", "()V", "ACTION_ANNULER_ALERTE", "", "ACTION_ARRETER", "ACTION_DEMARRER", "ACTION_SOS_MANUEL", "NOTIF_ID_ALERTE", "", "NOTIF_ID_SERVICE", "arreter", "", "context", "Landroid/content/Context;", "declencherSos", "demarrer", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void demarrer(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        public final void arreter(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        public final void declencherSos(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
    }
}