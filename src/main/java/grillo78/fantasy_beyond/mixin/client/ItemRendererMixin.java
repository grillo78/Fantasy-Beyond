package grillo78.fantasy_beyond.mixin.client;


import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import grillo78.fantasy_beyond.client.ClientUtils;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;
import java.util.List;

@Mixin(value = ItemRenderer.class, priority = 1500)
public abstract class ItemRendererMixin {

    @Redirect(method = "renderQuadList", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFIIZ)V"))
    public void renderQuadList(VertexConsumer instance, PoseStack.Pose pose, BakedQuad bakedQuad, float red, float green, float blue, float alpha, int packedLight, int packedOverlay, boolean readExistingColor, PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack itemStack, int combinedLight, int combinedOverlay) {
        Color color = ClientUtils.getStackColor(itemStack);
        if (color != null)
            buffer.putBulkData(pose, bakedQuad, color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, color.getAlpha()/255F, combinedLight, combinedOverlay, false);
        else
            buffer.putBulkData(pose, bakedQuad, red, green, blue, alpha, combinedLight, combinedOverlay, readExistingColor);
    }

    @IfModLoaded("sodium")
    @Dynamic
    @TargetHandler(mixin = "net.caffeinemc.mods.sodium.mixin.features.render.model.item.ItemRendererMixin", name = "renderBakedItemQuads")
    @ModifyArg(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/immediate/model/BakedModelEncoder;writeQuadVertices(Lnet/caffeinemc/mods/sodium/api/vertex/buffer/VertexBufferWriter;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/caffeinemc/mods/sodium/client/model/quad/ModelQuadView;IIIZ)V"), index = 3)
    public int getColor(int color, @Local(argsOnly = true) ItemStack itemStack) {
        Color colorObject = ClientUtils.getStackColor(itemStack);
        return colorObject != null? argbToABGR(colorObject.getRGB()) : color;
    }

    private int argbToABGR(int argbColor) {
        int r = (argbColor >> 16) & 0xFF;
        int b = argbColor & 0xFF;
        return (argbColor & 0xFF00FF00) | (b << 16) | r;
    }
}