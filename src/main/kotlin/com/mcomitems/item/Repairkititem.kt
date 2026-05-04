package com.mcomitems.item

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.world.World

class RepairKitItem(
    settings: Settings,
    private val repairPercent: Float
) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS

        val kitStack = user.getStackInHand(Hand.MAIN_HAND)
        val targetStack = user.getStackInHand(Hand.OFF_HAND)

        if (targetStack.isEmpty || !targetStack.isDamageable) {
            if (!world.isClient) user.sendMessage(
                Text.literal("Hold a tool, armor, or elytra in your offhand!").formatted(Formatting.RED), true)
            return ActionResult.FAIL
        }

        if (targetStack.damage == 0) {
            if (!world.isClient) user.sendMessage(
                Text.literal("That item is already at full durability!").formatted(Formatting.YELLOW), true)
            return ActionResult.FAIL
        }

        if (!world.isClient) {
            val repairAmount = (targetStack.maxDamage * repairPercent).toInt().coerceAtLeast(1)
            targetStack.damage = (targetStack.damage - repairAmount).coerceAtLeast(0)
            kitStack.damage(1, user, EquipmentSlot.MAINHAND)
            world.playSound(null, user.blockPos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 1.0f, 1.0f)
            user.sendMessage(
                Text.literal("Repaired ${repairAmount} durability! (${kitStack.maxDamage - kitStack.damage} uses left)")
                    .formatted(Formatting.GREEN), true)
        }

        return ActionResult.SUCCESS
    }
}