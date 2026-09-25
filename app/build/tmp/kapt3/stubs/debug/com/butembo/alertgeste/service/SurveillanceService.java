package com.butembo.alertgeste.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.hardware.*;
import android.os.*;
import androidx.core.app.ServiceCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleService;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import com.butembo.alertgeste.domain.*;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0007\u0018\u0000 G2\u00020\u00012\u00020\u0002:\u0002GHB\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010.\u001a\u00020!H\u0003J\b\u0010/\u001a\u00020!H\u0002J\u0010\u00100\u001a\u00020!2\u0006\u00101\u001a\u00020\u001cH\u0002J\u001a\u00102\u001a\u00020!2\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u00103\u001a\u000204H\u0016J\b\u00105\u001a\u00020!H\u0016J\b\u00106\u001a\u00020!H\u0016J\u0010\u00107\u001a\u00020!2\u0006\u00108\u001a\u000209H\u0016J\"\u0010:\u001a\u0002042\b\u0010;\u001a\u0004\u0018\u00010<2\u0006\u0010=\u001a\u0002042\u0006\u0010>\u001a\u000204H\u0016J\u0010\u0010?\u001a\u00020!2\u0006\u0010>\u001a\u000204H\u0016J\u0018\u0010?\u001a\u00020!2\u0006\u0010>\u001a\u0002042\u0006\u0010@\u001a\u000204H\u0016J\b\u0010A\u001a\u00020!H\u0002J\u0012\u0010B\u001a\u00020!2\b\u0010C\u001a\u0004\u0018\u00010\u001cH\u0002J\b\u0010D\u001a\u00020!H\u0002J\b\u0010E\u001a\u00020!H\u0002J\b\u0010F\u001a\u00020!H\u0002R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0018\u00010\nR\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\"\u001a\u00020#8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R\u0010\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010*\u001a\b\u0018\u00010\nR\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006I"}, d2 = {"Lcom/butembo/alertgeste/service/SurveillanceService;", "Landroidx/lifecycle/LifecycleService;", "Landroid/hardware/SensorEventListener;", "()V", "alertId", "", "Ljava/lang/Long;", "alertJob", "Lkotlinx/coroutines/Job;", "alertWakeLock", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "commands", "Lkotlinx/coroutines/sync/Mutex;", "cooldownUntil", "destroyed", "", "detector", "Lcom/butembo/alertgeste/domain/ShakeDetector;", "filter", "Lcom/butembo/alertgeste/domain/MotionFilter;", "haptics", "Lcom/butembo/alertgeste/service/PhoneHaptics;", "listening", "monitoringRequested", "notifications", "Lcom/butembo/alertgeste/service/AlertNotifications;", "phase", "", "profile", "Lcom/butembo/alertgeste/data/local/entity/GesteProfil;", "recovery", "Lkotlinx/coroutines/Deferred;", "", "repository", "Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "getRepository", "()Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "setRepository", "(Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;)V", "sensor", "Landroid/hardware/Sensor;", "sensorWakeLock", "sensors", "Landroid/hardware/SensorManager;", "watcher", "acquireSensorLock", "cancelAlert", "fail", "message", "onAccuracyChanged", "accuracy", "", "onCreate", "onDestroy", "onSensorChanged", "event", "Landroid/hardware/SensorEvent;", "onStartCommand", "intent", "Landroid/content/Intent;", "flags", "startId", "onTimeout", "fgsType", "refreshSensor", "setPhase", "value", "stopSensor", "triggerAlert", "watchConfiguration", "Companion", "Configuration", "app_debug"})
public final class SurveillanceService extends androidx.lifecycle.LifecycleService implements android.hardware.SensorEventListener {
    @javax.inject.Inject()
    public com.butembo.alertgeste.data.repository.AlertGesteRepository repository;
    private android.hardware.SensorManager sensors;
    private com.butembo.alertgeste.service.AlertNotifications notifications;
    private com.butembo.alertgeste.service.PhoneHaptics haptics;
    @org.jetbrains.annotations.Nullable()
    private android.hardware.Sensor sensor;
    private boolean listening = false;
    private boolean monitoringRequested = false;
    @org.jetbrains.annotations.Nullable()
    private com.butembo.alertgeste.data.local.entity.GesteProfil profile;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job watcher;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job alertJob;
    private kotlinx.coroutines.Deferred<kotlin.Unit> recovery;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Long alertId;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String phase;
    private boolean destroyed = false;
    private long cooldownUntil = 0L;
    @org.jetbrains.annotations.NotNull()
    private com.butembo.alertgeste.domain.ShakeDetector detector;
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.domain.MotionFilter filter = null;
    @org.jetbrains.annotations.Nullable()
    private android.os.PowerManager.WakeLock sensorWakeLock;
    @org.jetbrains.annotations.Nullable()
    private android.os.PowerManager.WakeLock alertWakeLock;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex commands = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_DEMARRER = "ACTION_DEMARRER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_ARRETER = "ACTION_ARRETER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_SOS_MANUEL = "ACTION_SOS_MANUEL";
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
    
    private final void watchConfiguration() {
    }
    
    private final void refreshSensor() {
    }
    
    @android.annotation.SuppressLint(value = {"WakelockTimeout"})
    private final void acquireSensorLock() {
    }
    
    private final void stopSensor() {
    }
    
    @java.lang.Override()
    public void onSensorChanged(@org.jetbrains.annotations.NotNull()
    android.hardware.SensorEvent event) {
    }
    
    @java.lang.Override()
    public void onAccuracyChanged(@org.jetbrains.annotations.Nullable()
    android.hardware.Sensor sensor, int accuracy) {
    }
    
    private final void setPhase(java.lang.String value) {
    }
    
    private final void cancelAlert() {
    }
    
    private final void triggerAlert() {
    }
    
    private final void fail(java.lang.String message) {
    }
    
    @java.lang.Override()
    public void onTimeout(int startId) {
    }
    
    @java.lang.Override()
    public void onTimeout(int startId, int fgsType) {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\n\u001a\u00020\u000b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\n\u001a\u00020\u000b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0010J+\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0004H\u0002\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0017"}, d2 = {"Lcom/butembo/alertgeste/service/SurveillanceService$Companion;", "", "()V", "ACTION_ANNULER_ALERTE", "", "ACTION_ARRETER", "ACTION_DEMARRER", "ACTION_SOS_MANUEL", "annuler", "", "context", "Landroid/content/Context;", "arreter", "declencherSos", "Lkotlin/Result;", "declencherSos-IoAF18A", "(Landroid/content/Context;)Ljava/lang/Object;", "demarrer", "demarrer-IoAF18A", "start", "action", "start-gIAlu-s", "(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/Object;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void arreter(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        public final void annuler(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u001a"}, d2 = {"Lcom/butembo/alertgeste/service/SurveillanceService$Configuration;", "", "active", "", "profile", "Lcom/butembo/alertgeste/data/local/entity/GesteProfil;", "hasContacts", "training", "(ZLcom/butembo/alertgeste/data/local/entity/GesteProfil;ZZ)V", "getActive", "()Z", "getHasContacts", "getProfile", "()Lcom/butembo/alertgeste/data/local/entity/GesteProfil;", "getTraining", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
    static final class Configuration {
        private final boolean active = false;
        @org.jetbrains.annotations.Nullable()
        private final com.butembo.alertgeste.data.local.entity.GesteProfil profile = null;
        private final boolean hasContacts = false;
        private final boolean training = false;
        
        public Configuration(boolean active, @org.jetbrains.annotations.Nullable()
        com.butembo.alertgeste.data.local.entity.GesteProfil profile, boolean hasContacts, boolean training) {
            super();
        }
        
        public final boolean getActive() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.butembo.alertgeste.data.local.entity.GesteProfil getProfile() {
            return null;
        }
        
        public final boolean getHasContacts() {
            return false;
        }
        
        public final boolean getTraining() {
            return false;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.butembo.alertgeste.data.local.entity.GesteProfil component2() {
            return null;
        }
        
        public final boolean component3() {
            return false;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.butembo.alertgeste.service.SurveillanceService.Configuration copy(boolean active, @org.jetbrains.annotations.Nullable()
        com.butembo.alertgeste.data.local.entity.GesteProfil profile, boolean hasContacts, boolean training) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}