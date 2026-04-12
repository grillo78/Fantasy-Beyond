package grillo78.fantasy_beyond.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.block_entities.AnvilBlockEntity;
import grillo78.fantasy_beyond.block_entities.BlacksmithTableBlockEntity;
import grillo78.fantasy_beyond.blocks.AnvilBlock;
import grillo78.fantasy_beyond.blocks.BlacksmithTable;
import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.recipes.ModRecipes;
import grillo78.fantasy_beyond.recipes.SmashingRecipe;
import grillo78.fantasy_beyond.recipes.inputs.SmashingItemInput;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import org.joml.Quaternionf;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlacksmithTableBlockEntityRenderer implements BlockEntityRenderer<BlacksmithTableBlockEntity> {

    public BlacksmithTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BlacksmithTableBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1, 0.5);
        poseStack.mulPose(blockEntity.getBlockState().getValue(BlacksmithTable.FACING).getRotation());
        poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(180)));
        poseStack.pushPose();
        poseStack.translate(0.25, -0.1, 0);
        if(blockEntity.getPiece().is(ModItems.FORGING_HAMMER)) {
            poseStack.translate(0,2/16F,1.25/16F);
            poseStack.mulPose(new Quaternionf().rotateZ((float) Math.toRadians(45)).rotateY((float) Math.toRadians(90)).rotateZ((float) Math.toRadians(5)));
        }else
        {
            if(blockEntity.getPiece().is(ModItems.FORGING_TONGS)) {
                poseStack.translate(0,0,8/16F);
                poseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(90)).rotateY((float) Math.toRadians(-30)));
            }else {
                poseStack.scale(0.5F, 0.5F, 0.5F);
                poseStack.translate(0, 0, 0.5F / 16F);
            }
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getPiece(), ItemDisplayContext.NONE, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);

        poseStack.popPose();
        List<RecipeHolder<SmashingRecipe>> recipes = blockEntity.getLevel().getRecipeManager().getRecipesFor(ModRecipes.SMASHING.get(), new SmashingItemInput(blockEntity.getPiece(), Optional.empty()), blockEntity.getLevel());
        if (recipes.size() > 0 && blockEntity.getRecipeIndex() != -1) {
            RecipeHolder<SmashingRecipe> recipeHolder = recipes.get(blockEntity.getRecipeIndex());
            ItemStack result = recipeHolder.value().getResult();
            poseStack.pushPose();
            poseStack.translate(1 + 3.75 / 16, 0, 2 / 16F);
            poseStack.scale(0.25F, 0.25F, 0.001F);
            Minecraft.getInstance().getItemRenderer().renderStatic(result, ItemDisplayContext.NONE, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(11.25 / 16F, 2 / 16F, 2 / 16F);
            Font font = Minecraft.getInstance().font;
            poseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(180)));

//            poseStack.pushPose();
//            poseStack.scale(0.005F, 0.005F, 0.005F);
//            font.drawInBatch(result.getHoverName(), 0, 0, Color.BLACK.hashCode(), false, poseStack.last().pose(), bufferSource, Font.DisplayMode.POLYGON_OFFSET, 0, packedLight);
//            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0,1/16F,0);
            poseStack.scale(0.0025F, 0.0025F, 0.0025F);
            Component description = Component.translatable("recipe.description." + recipeHolder.id().getNamespace() + "." + recipeHolder.id().getPath());
            String[] descriptionChunks = description.getString().split("\n");
            for (int i = 0; i < descriptionChunks.length; i++) {
                poseStack.translate(0, font.lineHeight, 0);
                font.drawInBatch(descriptionChunks[i], 0, 0, Color.BLACK.hashCode(), false, poseStack.last().pose(), bufferSource, Font.DisplayMode.POLYGON_OFFSET, 0, packedLight);
            }
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(6/16F,3.5/16F, 0);
            poseStack.scale(0.003F, 0.003F, 0.003F);
            List<Component> attributes = new ArrayList<>();
            AttributeUtil.applyModifierTooltips(result,attributes::add, AttributeTooltipContext.of(Minecraft.getInstance().player,Item.TooltipContext.of(blockEntity.getLevel()), TooltipFlag.NORMAL));
            for (int i = 0; i < attributes.size(); i++) {

                poseStack.translate(0, font.lineHeight+1, 0);
                font.drawInBatch(attributes.get(i), 0, 0, Color.BLACK.hashCode(), false, poseStack.last().pose(), bufferSource, Font.DisplayMode.POLYGON_OFFSET, 0, packedLight);
            }
            poseStack.popPose();
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}
