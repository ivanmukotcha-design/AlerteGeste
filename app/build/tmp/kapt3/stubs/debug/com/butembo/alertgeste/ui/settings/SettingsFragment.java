package com.butembo.alertgeste.ui.settings;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.*;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.butembo.alertgeste.R;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import com.butembo.alertgeste.databinding.FragmentSettingsBinding;
import com.butembo.alertgeste.service.*;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.*;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u0002J$\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0010H\u0016J\b\u0010\u001b\u001a\u00020\u0010H\u0016J\u001a\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001e\u001a\u00020\u0010H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u001f"}, d2 = {"Lcom/butembo/alertgeste/ui/settings/SettingsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/butembo/alertgeste/databinding/FragmentSettingsBinding;", "backgroundPermission", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "repository", "Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "getRepository", "()Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "setRepository", "(Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;)V", "message", "", "text", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onResume", "onViewCreated", "view", "updateBackgroundLabel", "app_debug"})
public final class SettingsFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.butembo.alertgeste.databinding.FragmentSettingsBinding _binding;
    @javax.inject.Inject()
    public com.butembo.alertgeste.data.repository.AlertGesteRepository repository;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> backgroundPermission = null;
    
    public SettingsFragment() {
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
    
    private final void updateBackgroundLabel() {
    }
    
    private final void message(java.lang.String text) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}