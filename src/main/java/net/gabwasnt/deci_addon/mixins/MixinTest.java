package net.gabwasnt.deci_addon.mixins;

import net.gabwasnt.deci_addon.Addon;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public class MixinTest {
    @Inject(method = "passSpecialRender", at = @At("HEAD"), cancellable = true)
    protected void passSpecialRender(EntityLivingBase p_77033_1_, double p_77033_2_, double p_77033_4_, double p_77033_6_, CallbackInfo ci) {
        Addon.logger.info("MIXIN!");
        ci.cancel();
    }
}
