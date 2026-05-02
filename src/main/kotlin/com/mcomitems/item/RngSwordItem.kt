package com.mcomitems.item

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import kotlin.random.Random

class RngSwordItem(settings: Settings) : Item(settings) {

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        if (attacker is PlayerEntity) {
            val world = attacker.entityWorld
            if (world is ServerWorld) {
                val levelBefore = attacker.experienceLevel
                if (levelBefore > 0) attacker.addExperienceLevels(-1)
                val bonusDamage = 3f * levelBefore
                if (bonusDamage > 0f) {
                    target.damage(world, attacker.damageSources.magic(), bonusDamage)
                }
                if (Random.nextDouble() <= 0.01) {
                    target.damage(world, attacker.damageSources.magic(), 12f)
                }
            }
        }
        stack.damage(1, attacker, EquipmentSlot.MAINHAND)
    }
}
