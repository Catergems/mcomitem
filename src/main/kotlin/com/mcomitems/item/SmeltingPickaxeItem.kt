package com.mcomitems.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.world.World

class SmeltingPickaxeItem(settings: Item.Settings) : Item(settings) {

    companion object {
        val SMELT_MAP: Map<String, () -> ItemStack> = mapOf(
            "minecraft:raw_iron"    to { ItemStack(Items.IRON_INGOT) },
            "minecraft:raw_gold"    to { ItemStack(Items.GOLD_INGOT) },
            "minecraft:raw_copper"  to { ItemStack(Items.COPPER_INGOT) },
            "minecraft:gold_nugget" to { ItemStack(Items.GOLD_INGOT) },
            "mcomitem:raw_tin"      to { ItemStack(ModItems.TIN_INGOT) },
        )
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        // Must use from main hand (pickaxe is in main hand)
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS

        val offStack = user.getStackInHand(Hand.OFF_HAND)
        if (offStack.isEmpty) {
            if (!world.isClient) user.sendMessage(
                Text.literal("Hold a raw ore in your offhand!").formatted(Formatting.RED), true)
            return ActionResult.FAIL
        }

        val itemId = Registries.ITEM.getId(offStack.item).toString()
        val smeltedFactory = SMELT_MAP[itemId]

        if (smeltedFactory == null) {
            if (!world.isClient) user.sendMessage(
                Text.literal("That item can't be smelted!").formatted(Formatting.RED), true)
            return ActionResult.FAIL
        }

        if (!world.isClient) {
            val count = offStack.count
            val smelted = smeltedFactory()
            smelted.count = count

            // Remove raw ore from offhand
            user.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY)

            // Give smelted result
            if (!user.inventory.insertStack(smelted)) {
                user.dropItem(smelted, false)
            }

            world.playSound(null, user.blockPos,
                SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.PLAYERS, 1.0f, 1.2f)
            user.sendMessage(
                Text.literal("Smelted ${count}x into ingots!").formatted(Formatting.GOLD), true)
        }

        return ActionResult.SUCCESS
    }
}