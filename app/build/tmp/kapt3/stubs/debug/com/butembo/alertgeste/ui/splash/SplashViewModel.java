package com.butembo.alertgeste.ui.splash;

import androidx.lifecycle.ViewModel;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/butembo/alertgeste/ui/splash/SplashViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "(Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;)V", "_destination", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/butembo/alertgeste/ui/splash/SplashDestination;", "destination", "Lkotlinx/coroutines/flow/StateFlow;", "getDestination", "()Lkotlinx/coroutines/flow/StateFlow;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SplashViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.repository.AlertGesteRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.butembo.alertgeste.ui.splash.SplashDestination> _destination = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.butembo.alertgeste.ui.splash.SplashDestination> destination = null;
    
    @javax.inject.Inject()
    public SplashViewModel(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.repository.AlertGesteRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.butembo.alertgeste.ui.splash.SplashDestination> getDestination() {
        return null;
    }
}