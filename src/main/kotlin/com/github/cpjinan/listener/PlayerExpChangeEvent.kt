package com.github.cpjinan.listener

import com.github.cpjinan.api.LevelAPI
import org.bukkit.event.player.PlayerExpChangeEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerExpChangeEvent {
    @SubscribeEvent
    fun onPlayerExpChange(event : PlayerExpChangeEvent) {
        event.amount = 0
        LevelAPI.refreshPlayerLevel(event.player)
    }

}