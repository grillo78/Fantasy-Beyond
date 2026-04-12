package grillo78.fantasy_beyond.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class TongsBEWLR extends BlockEntityWithoutLevelRenderer {
    public TongsBEWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    private BakedModel getModel() {
        return Minecraft.getInstance()
                .getModelManager()
                .getModel(new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "item/forging_tongs_model"), "standalone"));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        super.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        BakedModel model = getModel();

        Minecraft.getInstance().getItemRenderer().renderModelLists(
                model,
                stack,
                packedLight,
                packedOverlay,
                poseStack,
                buffer.getBuffer(RenderType.entitySolid(InventoryMenu.BLOCK_ATLAS)) // o solid(), cutout(), etc.
        );
        if (stack.has(ModDataComponents.TONGS_CONTENT)) {
            poseStack.pushPose();
            poseStack.translate(0.5,0.05,0.15);
            poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(90)));
            if(displayContext == ItemDisplayContext.GUI)
            poseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(45)));
            poseStack.scale(0.5F,0.5F,0.5F);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack.get(ModDataComponents.TONGS_CONTENT).getItem(), ItemDisplayContext.NONE, packedLight, packedOverlay, poseStack, buffer, null, 0);
            poseStack.popPose();
        }
    }
}
