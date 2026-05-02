package com.mcomitems.item

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Box
import net.minecraft.world.event.GameEvent

class AmethystSwordItem(settings: Settings) : Item(settings) {

    companion object {
        const val HIGHLIGHT_RADIUS = 18.0
        fun vibrationDamage(distance: Double): Float = when {
            distance <= 3.0  -> 4f
            distance <= 8.0  -> 3f
            distance <= 13.0 -> 2f
            distance <= 18.0 -> 1f
            else             -> 0f
        }
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        val world = attacker.entityWorld
        if (world is ServerWorld && attacker is PlayerEntity) {
            world.emitGameEvent(GameEvent.ENTITY_DAMAGE, target.entityPos, GameEvent.Emitter.of(target, null))
            target.addStatusEffect(StatusEffectInstance(StatusEffects.GLOWING, 100, 0))

            val searchBox = Box.of(attacker.entityPos, HIGHLIGHT_RADIUS * 2, HIGHLIGHT_RADIUS * 2, HIGHLIGHT_RADIUS * 2)
            val nearbyMobs = world.getEntitiesByClass(LivingEntity::class.java, searchBox) { it != attacker && it != target }

            for (mob in nearbyMobs) {
                val distance = attacker.distanceTo(mob).toDouble()
                if (distance <= HIGHLIGHT_RADIUS) mob.addStatusEffect(StatusEffectInstance(StatusEffects.GLOWING, 100, 0))
                val damage = vibrationDamage(distance)
                if (damage > 0f) mob.damage(world, attacker.damageSources.magic(), damage)
            }

            val serverPlayer = attacker as? ServerPlayerEntity
            stack.damage(1, serverPlayer, EquipmentSlot.MAINHAND)
        }
    }
}
