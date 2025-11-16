package grillo78.fantasy_beyond.mixin;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Vec3 getEyePosition(float partialTicks);

    @Shadow public abstract Vec3 getViewVector(float partialTicks);

    @Inject(method = "pick", at = @At("HEAD"), cancellable = true)
    public void onPick(double hitDistance, float partialTicks, boolean hitFluids, CallbackInfoReturnable<HitResult> cir) {
        if (((Entity) (Object) this) instanceof Player){
            CharacterData data = ((Player) (Object) this).getData(ModAttachments.CHARACTER_DATA);
            boolean activeAttack = data.getPlayerClass().getUnlockedAbilities().size()>data.getPlayerClass().getSelectedAbilityIndex() && data.getPlayerClass().getUnlockedAbilities().get(data.getPlayerClass().getSelectedAbilityIndex()).isActive();
            if(activeAttack){
                Vec3 vec3 = this.getEyePosition(partialTicks);
                Vec3 vec31 = this.getViewVector(partialTicks);
                Vec3 vec32 = vec3.add(vec31.x * hitDistance, vec31.y * hitDistance, vec31.z * hitDistance);
                cir.setReturnValue(BlockHitResult.miss(vec32, Direction.getNearest(vec31.subtract(vec32)), BlockPos.containing(vec32)));
            }
        }
    }
}
