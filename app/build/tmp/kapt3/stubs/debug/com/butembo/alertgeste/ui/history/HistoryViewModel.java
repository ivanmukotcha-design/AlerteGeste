package com.butembo.alertgeste.ui.history;

import androidx.lifecycle.ViewModel;
import com.butembo.alertgeste.R;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u000bR\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/butembo/alertgeste/ui/history/HistoryViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "(Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;)V", "_message", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "alertes", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/butembo/alertgeste/data/local/entity/Alerte;", "getAlertes", "()Lkotlinx/coroutines/flow/StateFlow;", "deleting", "", "message", "getMessage", "messageShown", "", "supprimerAlerte", "alerte", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HistoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.repository.AlertGesteRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.butembo.alertgeste.data.local.entity.Alerte>> alertes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _message = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> message = null;
    private boolean deleting = false;
    
    @javax.inject.Inject()
    public HistoryViewModel(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.repository.AlertGesteRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.butembo.alertgeste.data.local.entity.Alerte>> getAlertes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getMessage() {
        return null;
    }
    
    public final void supprimerAlerte(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Alerte alerte) {
    }
    
    public final void messageShown() {
    }
}