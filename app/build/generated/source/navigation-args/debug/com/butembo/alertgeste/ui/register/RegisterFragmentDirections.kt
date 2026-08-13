package com.butembo.alertgeste.ui.register

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.butembo.alertgeste.R

public class RegisterFragmentDirections private constructor() {
  public companion object {
    public fun actionRegisterToDashboard(): NavDirections =
        ActionOnlyNavDirections(R.id.action_register_to_dashboard)
  }
}
