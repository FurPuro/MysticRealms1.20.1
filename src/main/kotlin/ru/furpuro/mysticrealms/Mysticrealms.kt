package ru.furpuro.mysticrealms

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import ru.furpuro.mysticrealms.blocks.ModBlocks
import ru.furpuro.mysticrealms.items.ModItems
import ru.hollowhorizon.hc.client.utils.rl

class Mysticrealms : ModInitializer {
    companion object {
        const val MOD_ID = "mysticrealms"
    }
    val DEBILIUM_ORE_PLACED_KEY:ResourceKey<PlacedFeature> = ResourceKey.create(Registries.PLACED_FEATURE,"${Mysticrealms.MOD_ID}:debilium_ore".rl)
    override fun onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register { entries ->
            entries.prepend { ModItems.DEBILIUM.get() }
            entries.prepend { ModItems.RAW_DEBILIUM.get() }
        }
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register { entries ->
            entries.prepend { ModItems.DEBILIUM_PICKAXE.get() }
            entries.prepend { ModItems.DEBILIUM_AXE.get() }
            entries.prepend { ModItems.DEBILIUM_SHOVEL.get() }
            entries.prepend { ModItems.DEBILIUM_HOE.get() }
        }
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register { entries ->
            entries.prepend { ModItems.DEBILIUM_SWORD.get() }
        }
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register { entries ->
            entries.prepend { ModBlocks.DEBILIUM_BLOCK.get().asItem() }
        }
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register { entries ->
            entries.prepend { ModBlocks.DEBILIUM_ORE.get().asItem() }
            entries.prepend { ModBlocks.DEEPSLATE_DEBILIUM_ORE.get().asItem() }
        }
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),GenerationStep.Decoration.UNDERGROUND_ORES,DEBILIUM_ORE_PLACED_KEY)
    }
}
