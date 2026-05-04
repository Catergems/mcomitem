package com.mcomitems.item

import com.mcomitems.MoreCombatItem
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.ToolMaterial
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object ModItems {

    val STAR_SWORD: Item = register("star_sword") { key ->
        StarSwordItem(Item.Settings().sword(ToolMaterial.DIAMOND, 2f, -2.4f).maxDamage(500).registryKey(key))
    }
    val RNG_SWORD: Item = register("rng_sword") { key ->
        RngSwordItem(Item.Settings().sword(ToolMaterial.DIAMOND, 3.0f, 2.4f).maxDamage(1000).registryKey(key))
    }
    val AMETHYST_SWORD: Item = register("amethyst_sword") { key ->
        AmethystSwordItem(Item.Settings().sword(ToolMaterial.IRON, 3f, -2.6f).maxDamage(750).registryKey(key))
    }
    val BRONZE_SWORD: Item = register("bronze_sword") { key ->
        BronzeSwordItem(Item.Settings().sword(ToolMaterial.IRON, 4f, -2.4f).maxDamage(600).registryKey(key))
    }
    val REPAIR_KIT_T1: Item = register("repair_kit_t1") { key ->
        RepairKitItem(Item.Settings().maxDamage(5).maxCount(1).registryKey(key), repairPercent = 0.05f)
    }
    val REPAIR_KIT_T2: Item = register("repair_kit_t2") { key ->
        RepairKitItem(Item.Settings().maxDamage(15).maxCount(1).registryKey(key), repairPercent = 0.175f)
    }
    val SMELTING_PICKAXE: Item = register("smelting_pickaxe") { key ->
        SmeltingPickaxeItem(
            Item.Settings().pickaxe(ToolMaterial.IRON, 1f, -2.8f).maxDamage(400).registryKey(key)
        )
    }
    val RAW_TIN: Item = register("raw_tin") { key ->
        Item(Item.Settings().registryKey(key))
    }
    val TIN_INGOT: Item = register("tin_ingot") { key ->
        Item(Item.Settings().registryKey(key))
    }
    val BRONZE_INGOT: Item = register("bronze_ingot") { key ->
        Item(Item.Settings().registryKey(key))
    }

    private fun register(name: String, factory: (RegistryKey<Item>) -> Item): Item {
        val key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MoreCombatItem.MOD_ID, name))
        return Registry.register(Registries.ITEM, key, factory(key))
    }

    fun initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register { group ->
            group.add(STAR_SWORD)
            group.add(RNG_SWORD)
            group.add(AMETHYST_SWORD)
            group.add(BRONZE_SWORD)
            group.add(REPAIR_KIT_T1)
            group.add(REPAIR_KIT_T2)
        }
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register { group ->
            group.add(SMELTING_PICKAXE)
        }
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register { group ->
            group.add(RAW_TIN)
            group.add(TIN_INGOT)
            group.add(BRONZE_INGOT)
        }
    }
}