package ru.furpuro.mysticrealms.items

import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient

enum class ModTiers(
    private val level:Int,
    private val uses:Int,
    private val speed:Float,
    private val damage:Float,
    private val enchantmentValue:Int,
    private val repairIngredient: () -> Ingredient
): Tier {
    DEBILIUM(3,1796,8.5f,3.5f,13,{ Ingredient.of(ModItems.DEBILIUM.get()) });

    override fun getUses(): Int = uses

    override fun getSpeed(): Float = speed

    override fun getAttackDamageBonus(): Float = damage

    override fun getLevel(): Int = level

    override fun getEnchantmentValue(): Int = enchantmentValue

    override fun getRepairIngredient(): Ingredient = repairIngredient.invoke()
}