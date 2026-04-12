package grillo78.fantasy_beyond.client.item;

import grillo78.fantasy_beyond.client.entity.GrimoireArmPoseTransformer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class GrimoireClientExtensions implements IClientItemExtensions {
    @Override
    public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        return HumanoidModel.ArmPose.valueOf("FANTASY_BEYOND_GRIMOIRE");
    }
}
