package com.butembo.alertgeste.ui.gesture;

import android.hardware.*;
import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;
import com.butembo.alertgeste.R;
import com.butembo.alertgeste.databinding.FragmentGestureBinding;
import com.butembo.alertgeste.domain.MotionFilter;
import com.butembo.alertgeste.service.SurveillanceState;
import com.butembo.alertgeste.service.PhoneHaptics;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001a\u0010\u001b\u001a\u00020\u001c2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J$\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\b\u0010\'\u001a\u00020\u001cH\u0016J\b\u0010(\u001a\u00020\u001cH\u0016J\b\u0010)\u001a\u00020\u001cH\u0016J\u0010\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020,H\u0016J\u001a\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020 2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u00168BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006/"}, d2 = {"Lcom/butembo/alertgeste/ui/gesture/GestureFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/hardware/SensorEventListener;", "()V", "_binding", "Lcom/butembo/alertgeste/databinding/FragmentGestureBinding;", "binding", "getBinding", "()Lcom/butembo/alertgeste/databinding/FragmentGestureBinding;", "filter", "Lcom/butembo/alertgeste/domain/MotionFilter;", "haptics", "Lcom/butembo/alertgeste/service/PhoneHaptics;", "sensor", "Landroid/hardware/Sensor;", "sensorManager", "Landroid/hardware/SensorManager;", "sensorResponding", "", "sensorWatchdog", "Lkotlinx/coroutines/Job;", "viewModel", "Lcom/butembo/alertgeste/ui/gesture/GestureViewModel;", "getViewModel", "()Lcom/butembo/alertgeste/ui/gesture/GestureViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "onAccuracyChanged", "", "accuracy", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onPause", "onResume", "onSensorChanged", "event", "Landroid/hardware/SensorEvent;", "onViewCreated", "view", "app_debug"})
public final class GestureFragment extends androidx.fragment.app.Fragment implements android.hardware.SensorEventListener {
    @org.jetbrains.annotations.Nullable()
    private com.butembo.alertgeste.databinding.FragmentGestureBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private android.hardware.SensorManager sensorManager;
    @org.jetbrains.annotations.Nullable()
    private android.hardware.Sensor sensor;
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.domain.MotionFilter filter = null;
    private boolean sensorResponding = false;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job sensorWatchdog;
    private com.butembo.alertgeste.service.PhoneHaptics haptics;
    
    public GestureFragment() {
        super();
    }
    
    private final com.butembo.alertgeste.databinding.FragmentGestureBinding getBinding() {
        return null;
    }
    
    private final com.butembo.alertgeste.ui.gesture.GestureViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void onSensorChanged(@org.jetbrains.annotations.NotNull()
    android.hardware.SensorEvent event) {
    }
    
    @java.lang.Override()
    public void onAccuracyChanged(@org.jetbrains.annotations.Nullable()
    android.hardware.Sensor sensor, int accuracy) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}