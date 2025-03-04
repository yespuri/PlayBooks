package com.goodwy.audiobook.features.prefAppearanceUI

import de.paulwoitaschek.flowpref.Pref
import com.goodwy.audiobook.common.pref.PrefKeys
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Named

class PrefAppearanceUIViewModel
@Inject constructor(
  @Named(PrefKeys.MINI_PLAYER_STYLE)
  private val miniPlayerStylePref: Pref<Int>,
  @Named(PrefKeys.SHOW_PROGRESS_BAR)
  private val showProgressBarPref: Pref<Boolean>,
  @Named(PrefKeys.SHOW_DIVIDER)
  private val showDividerPref: Pref<Boolean>,
  @Named(PrefKeys.ICON_MODE)
private val iconModePref: Pref<Boolean>
) {

  private val _viewEffects = BroadcastChannel<PrefAppearanceUIViewEffect>(1)
  val viewEffects: Flow<PrefAppearanceUIViewEffect> get() = _viewEffects.asFlow()

  fun viewState(): Flow<PrefAppearanceUIViewState> {
    return combine(
      miniPlayerStylePref.flow,
      showProgressBarPref.flow,
      showDividerPref.flow,
      iconModePref.flow
    ) { miniPlayerStylePref, showProgressBarPref, showDividerPref, iconMode ->
      PrefAppearanceUIViewState(
        miniPlayerStylePref = miniPlayerStylePref,
        showProgressBarPref = showProgressBarPref,
        showDividerPref = showDividerPref,
        iconModePref = iconMode
      )
    }
  }

  fun changeMiniPlayerStyle() {
    _viewEffects.offer(PrefAppearanceUIViewEffect.ShowChangeMiniPlayerStyleDialog(miniPlayerStylePref.value))
  }

  fun toggleProgressBar() {
    showProgressBarPref.value = !showProgressBarPref.value
  }

  fun toggleDivider() {
    showDividerPref.value = !showDividerPref.value
  }

  fun toggleIconMode() {
    iconModePref.value = !iconModePref.value
  }
}
