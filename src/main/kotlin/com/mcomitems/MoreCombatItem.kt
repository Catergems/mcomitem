package com.mcomitems

import com.mcomitems.item.ModItems
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object MoreCombatItem : ModInitializer {
    const val MOD_ID = "mcomitem"
    private val logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        ModItems.initialize()
        logger.info("More Combat Item initialized!")
    }
}
