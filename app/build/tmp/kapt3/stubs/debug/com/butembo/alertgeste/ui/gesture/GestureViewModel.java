package com.butembo.alertgeste.ui.gesture;

import androidx.lifecycle.ViewModel;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0006J\u0006\u0010\u0018\u001a\u00020\u0015R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/butembo/alertgeste/ui/gesture/GestureViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "(Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;)V", "SEUIL_BRUT_DETECTION", "", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/butembo/alertgeste/ui/gesture/GestureUiState;", "dernierePicDetectee", "", "enCours", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "valeursMesures", "", "commencerEntrainement", "", "onValeurAccelerometre", "magnitude", "sauvegarderProfil", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class GestureViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.repository.AlertGesteRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.butembo.alertgeste.ui.gesture.GestureUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.butembo.alertgeste.ui.gesture.GestureUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Float> valeursMesures = null;
    private boolean enCours = false;
    private long dernierePicDetectee = 0L;
    private final float SEUIL_BRUT_DETECTION = 5.0F;
    
    @javax.inject.Inject()
    public GestureViewModel(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.repository.AlertGesteRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.butembo.alertgeste.ui.gesture.GestureUiState> getUiState() {
        return null;
    }
    
    public final void commencerEntrainement() {
    }
    
    public final void onValeurAccelerometre(float magnitude) {
    }
    
    public final void sauvegarderProfil() {
    }
}