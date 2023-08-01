package com.github.cpjinan.api

import com.github.cpjinan.manager.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.module.kether.KetherShell
import taboolib.module.kether.ScriptOptions
import taboolib.module.lang.sendLang
import taboolib.platform.util.sendLang

object LevelAPI {

    // 增加等级方法
    fun addPlayerLevel(player: String, amount: String){
        ConfigManager.player["$player.level"] = ConfigManager.player.getInt("$player.level") + amount.toInt()
        ConfigManager.dataConfig.saveToFile()
        ConfigManager.dataConfig.reload()
        refreshPlayerLevel(Bukkit.getPlayerExact(player)!!)
        runLevelAction(Bukkit.getPlayerExact(player)!!)
    }

    // 移除等级方法
    fun removePlayerLevel(player: String, amount: String){
        ConfigManager.player["$player.level"] = ConfigManager.player.getInt("$player.level") - amount.toInt()
        ConfigManager.dataConfig.saveToFile()
        ConfigManager.dataConfig.reload()
        refreshPlayerLevel(Bukkit.getPlayerExact(player)!!)
        runLevelAction(Bukkit.getPlayerExact(player)!!)
    }

    // 设置等级方法
    fun setPlayerLevel(player: String, amount: String){
        ConfigManager.player["$player.level"] = amount.toInt()
        ConfigManager.dataConfig.saveToFile()
        ConfigManager.dataConfig.reload()
        refreshPlayerLevel(Bukkit.getPlayerExact(player)!!)
        runLevelAction(Bukkit.getPlayerExact(player)!!)
    }

    // 获取等级方法
    fun getPlayerLevel(player: String):Int{
        return ConfigManager.player.getInt("$player.level")
    }

    // 刷新玩家等级方法
    fun refreshPlayerLevel(player: Player){
        val level: Int = ConfigManager.player.getInt("${player.name}.level")
        if( level < (ConfigManager.level.getString("max-level")!!.toInt()) ){
            val exp: Int = ConfigManager.player.getInt("${player.name}.exp")
            val expToLevel: Int = ConfigManager.level.getString("${level + 1}.exp")?.toInt() ?: 0

            player.level = level

            if (exp >= expToLevel) player.exp = 1.toFloat()
            else player.exp = (exp.toFloat() / expToLevel.toFloat())
        } else {
            player.level = ConfigManager.level.getString("max-level")!!.toInt()
            player.exp = 1.toFloat()
        }
    }

    // 执行等级动作方法
    fun runLevelAction(player: Player){
        KetherShell.eval(
            ConfigManager.level.getStringList("${ConfigManager.player.getInt("${player.name}.level")}.action"),
            ScriptOptions.builder().namespace(emptyList()).sender(sender = adaptPlayer(player)).build()
        )
    }

    // 玩家升级方法
    fun playerLevelUP(player: String){
        val level: Int = ConfigManager.player.getInt("$player.level")
        if( level < (ConfigManager.level.getString("max-level")!!.toInt()) ){
            val exp: Int = ConfigManager.player.getInt("$player.exp")
            val expToLevel: Int = ConfigManager.level.getString("${level + 1}.exp")?.toInt() ?: 0

            if (exp >= expToLevel){
                ConfigManager.player["$player.exp"] = exp - expToLevel
                ConfigManager.player["$player.level"] = ConfigManager.player.getInt("$player.level") + 1
                refreshPlayerLevel(Bukkit.getPlayerExact(player)!!)
                runLevelAction(Bukkit.getPlayerExact(player)!!)
                Bukkit.getPlayerExact(player)!!.sendLang("level-up-success")
            }
            else Bukkit.getPlayerExact(player)!!.sendLang("level-up-fail")
        } else {
            Bukkit.getPlayerExact(player)!!.sendLang("max-level")
        }
    }

    // 增加经验方法
    fun addPlayerExp(player: String, amount: String){
        ConfigManager.player["$player.exp"] = ConfigManager.player.getInt("$player.exp") + amount.toInt()
        ConfigManager.dataConfig.saveToFile()
        ConfigManager.dataConfig.reload()
        refreshPlayerLevel(Bukkit.getPlayerExact(player)!!)
    }

    // 移除经验方法
    fun removePlayerExp(player: String, amount: String){
        ConfigManager.player["$player.exp"] = ConfigManager.player.getInt("$player.exp") - amount.toInt()
        ConfigManager.dataConfig.saveToFile()
        ConfigManager.dataConfig.reload()
        refreshPlayerLevel(Bukkit.getPlayerExact(player)!!)
    }

    // 设置经验方法
    fun setPlayerExp(player: String, amount: String){
        ConfigManager.player["$player.exp"] = amount.toInt()
        ConfigManager.dataConfig.saveToFile()
        ConfigManager.dataConfig.reload()
        refreshPlayerLevel(Bukkit.getPlayerExact(player)!!)
    }

    // 获取等级方法
    fun getPlayerExp(player: String):Int{
        return ConfigManager.player.getInt("$player.exp")
    }

}