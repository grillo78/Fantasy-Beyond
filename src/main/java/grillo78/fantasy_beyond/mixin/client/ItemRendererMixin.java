package grillo78.fantasy_beyond.mixin.client;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import grillo78.fantasy_beyond.client.ClientUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;
import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Redirect(method = "renderQuadList", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFIIZ)V"))
    public void translate(VertexConsumer instance, PoseStack.Pose pose, BakedQuad bakedQuad, float red, float green, float blue, float alpha, int packedLight, int packedOverlay, boolean readExistingColor, PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack itemStack, int combinedLight, int combinedOverlay) {
        Color color = ClientUtils.getStackColor(itemStack);
        if (color != null)
            buffer.putBulkData(pose, bakedQuad, color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, color.getAlpha()/255F, combinedLight, combinedOverlay, false);
        else
            buffer.putBulkData(pose, bakedQuad, red, green, blue, alpha, combinedLight, combinedOverlay, readExistingColor);
    }
}