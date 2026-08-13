package com.butembo.alertgeste.ui.splash

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.butembo.alertgeste.R

public class SplashFragmentDirections private constructor() {
  public companion object {
    public fun actionSplashToDashboard(): NavDirections =
        ActionOnlyNavDirections(R.id.action_splash_to_dashboard)

    public fun actionSplashToRegister(): NavDirections =
        ActionOnlyNavDirections(R.id.action_splash_to_register)
  }
}
