package grillo78.fantasy_beyond.entities;

import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.awt.*;

public class LightSource extends Entity {

    private static int MAX_COUNT = 3000;

    private int count;
    private Entity owner;
    private ColorPointLight light;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;

    public LightSource(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if (level().isClientSide) {
            EntityEffect effect = new EntityEffect(FXHelper.getFX(new ResourceLocation(FantasyBeyond.MOD_ID, "light_spell")), pLevel, this);
            effect.start();
        }
    }

    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public boolean skipAttackInteraction(Entity pEntity) {
        return pEntity != getOwner();
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpSteps = steps;
    }

    @Override
    public void tick() {
        if (!level().isClientSide) {
            move(MoverType.SELF, getDeltaMovement());
            if (count < MAX_COUNT) {
                if (owner != null) {
                    Vec3 destiny = owner.getEyePosition().add(new Vec3(1, 1, 0).yRot((float) Math.toRadians(45)+ owner.getYRot()));
                    if (destiny.distanceToSqr(position()) > 2)
                        setDeltaMovement(destiny.subtract(position()).normalize().multiply(0.25, 0.25, 0.25));
                    else
                        setDeltaMovement(Vec3.ZERO);
                } else
                    count = MAX_COUNT;
                count++;
                if (count == 1)
                    level().playSound(null, getX(), getY(), getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.75F, 0.05F);
                if (random.nextInt(100) == 20)
                    level().playSound(null, getX(), getY(), getZ(), SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1, 1);
            } else
                discard();
        } else {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Vec3 position = getPosition(Minecraft.getInstance().getPartialTick());
                if (light == null)
                    light = LightManager.INSTANCE.addLight(position.toVector3f(), new Color(255, 239, 137, 255).hashCode(), 20);
                light.setPos((float) position.x, (float) position.y, (float) position.z);
                light.update();
            });
        }
        super.tick();
        tickLerp();
    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
        }

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        if (light != null)
            light.remove();
    }
}
