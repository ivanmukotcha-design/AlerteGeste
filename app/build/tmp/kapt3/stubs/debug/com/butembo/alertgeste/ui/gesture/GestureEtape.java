package com.butembo.alertgeste.ui.gesture;

import androidx.lifecycle.ViewModel;
import com.butembo.alertgeste.data.local.entity.GesteProfil;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import com.butembo.alertgeste.domain.ShakeCalibration;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/butembo/alertgeste/ui/gesture/GestureEtape;", "", "(Ljava/lang/String;I)V", "REPOS", "EN_COURS", "TERMINE", "app_debug"})
public enum GestureEtape {
    /*public static final*/ REPOS /* = new REPOS() */,
    /*public static final*/ EN_COURS /* = new EN_COURS() */,
    /*public static final*/ TERMINE /* = new TERMINE() */;
    
    GestureEtape() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.butembo.alertgeste.ui.gesture.GestureEtape> getEntries() {
        return null;
    }
}