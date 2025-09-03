package grillo78.fantasy_beyond.mixin;

import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.capabilities.customization.race.elf.Elf;
import grillo78.fantasy_beyond.util.ProjectilesUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract float getJumpBoostPower();

    @Shadow public abstract <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing);

    protected LivingEntityMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "getJumpPower", at = @At(value = "HEAD"), cancellable = true)
    public void getJumpPower(CallbackInfoReturnable<Float> cir) {
        getCapability(PlayerDataProvider.DATA).ifPresent(playerData->{
            cir.setReturnValue(playerData.getPlayerCustomization().getRace().getJumpScale() * 0.42F * this.getBlockJumpFactor() + this.getJumpBoostPower());
        });
    }

    @Inject(method = "isCurrentlyGlowing", at = @At(value = "HEAD"), cancellable = true)
    public void getDimensionsInject(CallbackInfoReturnable<Boolean> cir) {
        if(level().isClientSide)
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            if (Minecraft.getInstance().player != null)
                Minecraft.getInstance().player.getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
                    if (data.getPlayerCustomization().isFinished() && data.getPlayerCustomization().getRace() instanceof Elf && distanceTo(Minecraft.getInstance().player) < 15) {
                        Player player = Minecraft.getInstance().player;
                        HitResult result = ProjectilesUtil.getHitResult( player.getEyePosition(0), (entity) -> !entity.isSpectator(), getEyePosition(0).subtract(player.getEyePosition(0)).multiply(2,2,2), level(), player);
                        cir.setReturnValue(result != null && (result.getType() == HitResult.Type.BLOCK));
                    }
                });
        });
    }
}
