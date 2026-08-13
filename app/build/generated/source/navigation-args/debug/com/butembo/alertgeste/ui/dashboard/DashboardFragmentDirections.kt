package com.butembo.alertgeste.ui.dashboard

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.butembo.alertgeste.R

public class DashboardFragmentDirections private constructor() {
  public companion object {
    public fun actionDashboardToContacts(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_contacts)

    public fun actionDashboardToGesture(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_gesture)

    public fun actionDashboardToHistory(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_history)

    public fun actionDashboardToSettings(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboard_to_settings)
  }
}
