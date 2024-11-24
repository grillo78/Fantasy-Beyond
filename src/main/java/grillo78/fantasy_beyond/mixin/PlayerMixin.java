package grillo78.fantasy_beyond.mixin;

import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    @Shadow @Final private static Map<Pose, EntityDimensions> POSES;

    @Shadow @Final public static EntityDimensions STANDING_DIMENSIONS;

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "getDimensions", at = @At(value = "HEAD"), cancellable = true)
    public void getDimensionsInject(Pose pPose, CallbackInfoReturnable<EntityDimensions> cir) {
        this.getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            cir.setReturnValue(data.getPlayerCustomization().getRace().getNewSize(pPose, POSES.getOrDefault(pPose, STANDING_DIMENSIONS)));
        });
    }
}
