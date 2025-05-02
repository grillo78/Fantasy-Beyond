package grillo78.fantasy_beyond.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.spells_book.SpellsBookProvider;
import grillo78.fantasy_beyond.magic.spells.SpellType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.renderable.BakedModelRenderable;
import org.joml.Quaternionf;

import java.awt.*;
import java.util.List;

public class SpellsBookRenderer extends BlockEntityWithoutLevelRenderer {
    private RandomSource random = RandomSource.create();
    private Font font;
    private static Style TITLE_STYLE = Style.EMPTY.withUnderlined(true).withBold(true);

    public SpellsBookRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        font = Minecraft.getInstance().font;
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.renderByItem(pStack, pDisplayContext, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        BakedModelRenderable modelRenderable = BakedModelRenderable.of(new ResourceLocation(FantasyBeyond.MOD_ID, "block/book"));
        modelRenderable.render(pPoseStack, pBuffer, RenderType::entityTranslucent, pPackedLight, pPackedOverlay, Minecraft.getInstance().getPartialTick(), new BakedModelRenderable.Context(ModelData.EMPTY));

        pPoseStack.pushPose();
        pPoseStack.mulPose(new Quaternionf().rotationX((float) Math.toRadians(90)));
        pPoseStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(22.5)));
        pPoseStack.mulPose(new Quaternionf().rotationZ((float) Math.toRadians(180)));
        pPoseStack.translate(-0.855, -0.775, 0.07);

        float scale = 0.0025F;
        pPoseStack.scale(scale, scale, scale);

        pStack.getCapability(SpellsBookProvider.SPELLS_BOOK).ifPresent(spellsBookCap -> {
            SpellType spellType = spellsBookCap.getCurrentSpell();
            Component component = Component.translatable(spellType.getRegistryName().getNamespace() + ".spell."+spellType.getRegistryName().getPath()).withStyle(TITLE_STYLE);
            font.drawInBatch(component,  0, 0, Color.BLACK.hashCode(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, 200);
            component = Component.translatable(spellType.getRegistryName().getNamespace() + ".spell_description."+spellType.getRegistryName().getPath());
            List<FormattedText> components = font.getSplitter().splitLines(component, 123, Style.EMPTY);
            for (int i = 0; i < components.size(); i++) {
                font.drawInBatch(Component.literal(components.get(i).getString()), 0, 10+i*(font.lineHeight+5), Color.BLACK.hashCode(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, 200);
            }
            pPoseStack.translate(0, 10+components.size()*(font.lineHeight+5), 0);
        });
        pPoseStack.popPose();
    }
}
