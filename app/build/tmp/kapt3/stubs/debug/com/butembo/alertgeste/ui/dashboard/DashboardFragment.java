package com.butembo.alertgeste.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.*;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;
import com.butembo.alertgeste.R;
import com.butembo.alertgeste.databinding.FragmentDashboardBinding;
import com.butembo.alertgeste.service.*;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\rH\u0002J$\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0015H\u0016J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u001eH\u0016J\u001a\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0010\u0010$\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0002J\u0010\u0010&\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0002J\u0010\u0010\'\u001a\u00020\u00152\u0006\u0010%\u001a\u00020\tH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006("}, d2 = {"Lcom/butembo/alertgeste/ui/dashboard/DashboardFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/butembo/alertgeste/databinding/FragmentDashboardBinding;", "binding", "getBinding", "()Lcom/butembo/alertgeste/databinding/FragmentDashboardBinding;", "pendingSos", "", "permissions", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "viewModel", "Lcom/butembo/alertgeste/ui/dashboard/DashboardViewModel;", "getViewModel", "()Lcom/butembo/alertgeste/ui/dashboard/DashboardViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "message", "", "text", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onSaveInstanceState", "outState", "onViewCreated", "view", "requestStart", "sos", "startIfReady", "startService", "app_debug"})
public final class DashboardFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.butembo.alertgeste.databinding.FragmentDashboardBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private boolean pendingSos = false;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> permissions = null;
    
    public DashboardFragment() {
        super();
    }
    
    private final com.butembo.alertgeste.databinding.FragmentDashboardBinding getBinding() {
        return null;
    }
    
    private final com.butembo.alertgeste.ui.dashboard.DashboardViewModel getViewModel() {
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
    
    private final void requestStart(boolean sos) {
    }
    
    private final void startIfReady(boolean sos) {
    }
    
    private final void startService(boolean sos) {
    }
    
    private final void message(java.lang.String text) {
    }
    
    @java.lang.Override()
    public void onSaveInstanceState(@org.jetbrains.annotations.NotNull()
    android.os.Bundle outState) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}