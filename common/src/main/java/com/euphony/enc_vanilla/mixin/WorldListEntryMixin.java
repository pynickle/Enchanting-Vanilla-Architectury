package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListEntryMixin {
    @Redirect(method = "getNarration", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelSummary;isExperimental()Z"))
    private boolean disableExperimentalWarning(LevelSummary instance) {
        if(ClientConfig.HANDLER.instance().enableNoExperimentalWarning
                && !ClientConfig.HANDLER.instance().enableExperimentalDisplay) {
            return false;
        }
        return instance.isExperimental();
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/LevelSummary;getInfo()Lnet/minecraft/network/chat/Component;"
            )
    )
    private Component modifyInfo(LevelSummary levelSummary) {
        if(ClientConfig.HANDLER.instance().enableNoExperimentalWarning
                && !ClientConfig.HANDLER.instance().enableExperimentalDisplay) {
            if (levelSummary.info == null) {
                levelSummary.info = this.enc_vanilla$createInfo(levelSummary);
            }

            return levelSummary.info;
        }
        return levelSummary.getInfo();
    }

    @Unique
    private Component enc_vanilla$createInfo(LevelSummary levelSummary) {
        if (levelSummary.isLocked()) {
            return Component.translatable("selectWorld.locked").withStyle(ChatFormatting.RED);
        } else if (levelSummary.requiresManualConversion()) {
            return Component.translatable("selectWorld.conversion").withStyle(ChatFormatting.RED);
        } else if (!levelSummary.isCompatible()) {
            return Component.translatable("selectWorld.incompatible.info", new Object[]{levelSummary.getWorldVersionName()}).withStyle(ChatFormatting.RED);
        } else {
            MutableComponent mutableComponent = levelSummary.isHardcore() ? Component.empty().append(Component.translatable("gameMode.hardcore").withColor(-65536)) : Component.translatable("gameMode." + levelSummary.getGameMode().getName());
            if (levelSummary.hasCommands()) {
                mutableComponent.append(", ").append(Component.translatable("selectWorld.commands"));
            }

            MutableComponent mutableComponent2 = levelSummary.getWorldVersionName();
            MutableComponent mutableComponent3 = Component.literal(", ").append(Component.translatable("selectWorld.version")).append(CommonComponents.SPACE);
            if (levelSummary.shouldBackup()) {
                mutableComponent3.append(mutableComponent2.withStyle(levelSummary.isDowngrade() ? ChatFormatting.RED : ChatFormatting.ITALIC));
            } else {
                mutableComponent3.append(mutableComponent2);
            }

            mutableComponent.append(mutableComponent3);
            return mutableComponent;
        }
    }
}
