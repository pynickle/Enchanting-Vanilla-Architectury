package com.euphony.enc_vanilla.events.events;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.utils.HitUtils;
import dev.architectury.event.CompoundEventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;

public class SpongePlaceEvent {
    public static CompoundEventResult<ItemStack> rightClickItem(Player player, InteractionHand interactionHand) {
        boolean shouldPlace = !QolConfig.HANDLER.instance().enableSpongePlacingSneaking || player.isShiftKeyDown();

        if(!QolConfig.HANDLER.instance().enableSpongePlacing || !shouldPlace) return CompoundEventResult.pass();

        ItemStack stack = player.getItemInHand(interactionHand);
        if(stack.is(Items.SPONGE)) {
            Level level = player.level();

            BlockHitResult blockHitResult = HitUtils.getPlayerFluidHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            BlockPos pos = blockHitResult.getBlockPos();

            if(level.getBlockState(pos).is(Blocks.WATER)) {
                BlockHitResult blockHitResult1 = blockHitResult.withPosition(pos);
                InteractionResult result = Items.SPONGE.useOn(new UseOnContext(player, interactionHand, blockHitResult1));
                if(result != InteractionResult.PASS) {
                    return CompoundEventResult.interruptTrue(stack);
                }
            }
        }

        return CompoundEventResult.pass();
    }
}
