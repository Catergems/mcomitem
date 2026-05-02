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

    private fun register(name: String, factory: (RegistryKey<Item>) -> Item): Item {
        val key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MoreCombatItem.MOD_ID, name))
        return Registry.register(Registries.ITEM, key, factory(key))
    }

    fun initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register { group ->
            group.add(STAR_SWORD)
            group.add(RNG_SWORD)
            group.add(AMETHYST_SWORD)
        }
    }
}
