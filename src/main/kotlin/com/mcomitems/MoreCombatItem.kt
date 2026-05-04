package com.mcomitems

import com.mcomitems.block.ModBlocks
import com.mcomitems.item.ModItems
import com.mcomitems.worldgen.OreGeneration
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object MoreCombatItem : ModInitializer {
    const val MOD_ID = "mcomitem"
    private val logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        ModBlocks.initialize()
        ModItems.initialize()
        OreGeneration.initialize()
        logger.info("More Combat Item initialized!")
    }
}