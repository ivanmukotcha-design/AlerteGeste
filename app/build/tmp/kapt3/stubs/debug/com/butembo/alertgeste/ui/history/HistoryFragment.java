package com.butembo.alertgeste.ui.history;

import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AlertDialog;
import com.butembo.alertgeste.R;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.butembo.alertgeste.databinding.FragmentHistoryBinding;
import dagger.hilt.android.AndroidEntryPoint;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J$\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u000eH\u0016J\u001a\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001c"}, d2 = {"Lcom/butembo/alertgeste/ui/history/HistoryFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/butembo/alertgeste/databinding/FragmentHistoryBinding;", "deleteDialog", "Landroidx/appcompat/app/AlertDialog;", "viewModel", "Lcom/butembo/alertgeste/ui/history/HistoryViewModel;", "getViewModel", "()Lcom/butembo/alertgeste/ui/history/HistoryViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "confirmDelete", "", "alerte", "Lcom/butembo/alertgeste/data/local/entity/Alerte;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "app_debug"})
public final class HistoryFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.butembo.alertgeste.databinding.FragmentHistoryBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.appcompat.app.AlertDialog deleteDialog;
    
    public HistoryFragment() {
        super();
    }
    
    private final com.butembo.alertgeste.ui.history.HistoryViewModel getViewModel() {
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
    
    private final void confirmDelete(com.butembo.alertgeste.data.local.entity.Alerte alerte) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}