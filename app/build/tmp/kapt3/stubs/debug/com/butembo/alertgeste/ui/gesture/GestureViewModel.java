package com.butembo.alertgeste.ui.gesture;

import androidx.lifecycle.ViewModel;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import com.butembo.alertgeste.domain.ShakeCalibration;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u000bJ\u0006\u0010\u0015\u001a\u00020\u0013J\u0016\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001a"}, d2 = {"Lcom/butembo/alertgeste/ui/gesture/GestureViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "(Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;)V", "detector", "Lcom/butembo/alertgeste/domain/ShakeCalibration;", "mutable", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/butembo/alertgeste/ui/gesture/GestureUiState;", "saveConfirmationPending", "", "startedAt", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "commencerEntrainement", "", "consumeSaveConfirmation", "interrompre", "onValeurAccelerometre", "magnitude", "", "timestampMs", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class GestureViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.repository.AlertGesteRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.butembo.alertgeste.ui.gesture.GestureUiState> mutable = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.butembo.alertgeste.ui.gesture.GestureUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private com.butembo.alertgeste.domain.ShakeCalibration detector;
    private long startedAt = 0L;
    private boolean saveConfirmationPending = false;
    
    @javax.inject.Inject()
    public GestureViewModel(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.repository.AlertGesteRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.butembo.alertgeste.ui.gesture.GestureUiState> getUiState() {
        return null;
    }
    
    public final boolean consumeSaveConfirmation() {
        return false;
    }
    
    public final void commencerEntrainement() {
    }
    
    public final void interrompre() {
    }
    
    public final void onValeurAccelerometre(float magnitude, long timestampMs) {
    }
}