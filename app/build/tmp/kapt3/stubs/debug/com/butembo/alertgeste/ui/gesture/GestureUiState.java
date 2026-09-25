package com.butembo.alertgeste.ui.gesture;

import androidx.lifecycle.ViewModel;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import com.butembo.alertgeste.domain.ShakeCalibration;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\tH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00c6\u0003J;\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u00072\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001e\u001a\u00020\tH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001f"}, d2 = {"Lcom/butembo/alertgeste/ui/gesture/GestureUiState;", "", "etape", "Lcom/butembo/alertgeste/ui/gesture/GestureEtape;", "secoussesDetectees", "", "sauvegardeFait", "", "message", "", "saving", "(Lcom/butembo/alertgeste/ui/gesture/GestureEtape;IZLjava/lang/String;Z)V", "getEtape", "()Lcom/butembo/alertgeste/ui/gesture/GestureEtape;", "getMessage", "()Ljava/lang/String;", "getSauvegardeFait", "()Z", "getSaving", "getSecoussesDetectees", "()I", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class GestureUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.ui.gesture.GestureEtape etape = null;
    private final int secoussesDetectees = 0;
    private final boolean sauvegardeFait = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String message = null;
    private final boolean saving = false;
    
    public GestureUiState(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.ui.gesture.GestureEtape etape, int secoussesDetectees, boolean sauvegardeFait, @org.jetbrains.annotations.NotNull()
    java.lang.String message, boolean saving) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.ui.gesture.GestureEtape getEtape() {
        return null;
    }
    
    public final int getSecoussesDetectees() {
        return 0;
    }
    
    public final boolean getSauvegardeFait() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessage() {
        return null;
    }
    
    public final boolean getSaving() {
        return false;
    }
    
    public GestureUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.ui.gesture.GestureEtape component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.butembo.alertgeste.ui.gesture.GestureUiState copy(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.ui.gesture.GestureEtape etape, int secoussesDetectees, boolean sauvegardeFait, @org.jetbrains.annotations.NotNull()
    java.lang.String message, boolean saving) {
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