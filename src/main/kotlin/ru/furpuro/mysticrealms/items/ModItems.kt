package ru.furpuro.mysticrealms.items

import net.minecraft.world.item.*
import ru.furpuro.mysticrealms.Mysticrealms
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.common.registry.HollowRegistry

object ModItems : HollowRegistry() {
    val DEBILIUM by register("${Mysticrealms.MOD_ID}:debilium".rl) {
        object: Item(
            Properties()
        ) {}
    }
    val RAW_DEBILIUM by register("${Mysticrealms.MOD_ID}:raw_debilium".rl) {
        object: Item(
            Properties()
        ) {}
    }
    val DEBILIUM_SWORD by register("${Mysticrealms.MOD_ID}:debilium_sword".rl) {
        object: SwordItem(
            ModTiers.DEBILIUM,
            3,
            -2.4F,
            Properties()
        ) {}
    }
    val DEBILIUM_PICKAXE by register("${Mysticrealms.MOD_ID}:debilium_pickaxe".rl) {
        object: PickaxeItem(
            ModTiers.DEBILIUM,
            1,
            -2.8F,
            Properties()
        ) {}
    }
    val DEBILIUM_AXE by register("${Mysticrealms.MOD_ID}:debilium_axe".rl) {
        object: AxeItem(
            ModTiers.DEBILIUM,
            5.0f,
            -3.0F,
            Properties()
        ) {}
    }
    val DEBILIUM_SHOVEL by register("${Mysticrealms.MOD_ID}:debilium_shovel".rl) {
        object: ShovelItem(
            ModTiers.DEBILIUM,
            1.5f,
            -3.0F,
            Properties()
        ) {}
    }
    val DEBILIUM_HOE by register("${Mysticrealms.MOD_ID}:debilium_hoe".rl) {
        object: HoeItem(
            ModTiers.DEBILIUM,
            -3,
            0.0F,
            Properties()
        ) {}
    }
}