package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.client.ClientConfig;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(SoundOptionsScreen.class)
public abstract class SoundsOptionScreenMixin extends OptionsSubScreen {
    private final OptionInstance<Boolean> pauseMusic = OptionInstance.createBoolean("options.pauseMusic", true);

    public SoundsOptionScreenMixin(Screen screen, Options options, Component component, OptionInstance<Boolean> showSubtitles) {
        super(screen, options, component);
    }

    @Inject(method = "addOptions", at = @At("TAIL"))
    private void addOptions(CallbackInfo ci) {
        if(ClientConfig.HANDLER.instance().enableMusicPause) {
            this.list.addSmall(pauseMusic);
        }
    }
}
