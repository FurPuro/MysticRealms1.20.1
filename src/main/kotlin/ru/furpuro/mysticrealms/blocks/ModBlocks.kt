package ru.furpuro.mysticrealms.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Direction.Axis
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import ru.furpuro.mysticrealms.Mysticrealms
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.common.objects.blocks.BlockItemProperties
import ru.hollowhorizon.hc.common.registry.AutoModelType
import ru.hollowhorizon.hc.common.registry.HollowRegistry

object ModBlocks : HollowRegistry() {
    val DEBILIUM_ORE by register("${Mysticrealms.MOD_ID}:debilium_ore".rl, AutoModelType.CUBE_ALL) {
        Block(
            BlockBehaviour.Properties.copy(Blocks.STONE)
                .requiresCorrectToolForDrops()
                .strength(1.75f)
                .sound(SoundType.STONE)
            ,
            Properties()
        )
    }
    val DEBILIUM_BLOCK by register("${Mysticrealms.MOD_ID}:debilium_block".rl, AutoModelType.CUBE_ALL) {
        Block(
            BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                .requiresCorrectToolForDrops()
                .strength(2.25f)
                .sound(SoundType.METAL)
            ,
            Properties()
        )
    }
    val DEEPSLATE_DEBILIUM_ORE by register("${Mysticrealms.MOD_ID}:deepslate_debilium_ore".rl, autoModel = null) {
        RotatableBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE)
                .requiresCorrectToolForDrops()
                .strength(3.25f)
                .sound(SoundType.DEEPSLATE)
            ,
            Properties()
        )
    }
}

class Block(settings : Properties, itemProperties: Item.Properties): net.minecraft.world.level.block.Block(settings), BlockItemProperties {
    override val properties = itemProperties
}

class RotatableBlock(settings : Properties, itemProperties: Item.Properties) : RotatedPillarBlock(settings), BlockItemProperties {
    override val properties = itemProperties

    companion object {
        val AXIS: EnumProperty<Axis> = RotatedPillarBlock.AXIS
    }

    init {
        registerDefaultState(defaultBlockState().setValue(AXIS, Axis.Y))
    }


    @Deprecated("Deprecated in Java", ReplaceWith("rotateBlock(blockState, rotation)"))
    override fun rotate(blockState: BlockState, rotation: Rotation): BlockState? {
        return rotateBlock(blockState,rotation)
    }

    fun rotateBlock(blockState: BlockState, rotation: Rotation): BlockState? {
        when (rotation) {
            Rotation.COUNTERCLOCKWISE_90, Rotation.CLOCKWISE_90 -> {
                when ((blockState.getValue(AXIS) as Direction.Axis)) {
                    Axis.X -> return blockState.setValue(AXIS, Axis.Z)
                    Axis.Z -> return blockState.setValue(AXIS, Axis.X)
                    else -> return blockState
                }
            }
            else -> return blockState
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState>) {
        builder.add(AXIS)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("Shapes.block()", "net.minecraft.world.phys.shapes.Shapes"))
    override fun getShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos, collisionContext: CollisionContext): VoxelShape {
        return Shapes.block()
    }

    override fun getStateForPlacement(blockPlaceContext: BlockPlaceContext): BlockState? {
        return defaultBlockState().setValue(AXIS, blockPlaceContext.clickedFace.axis)
    }
}