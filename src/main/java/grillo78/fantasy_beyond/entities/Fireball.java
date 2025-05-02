package grillo78.fantasy_beyond.entities;

import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.awt.*;

public class Fireball extends AbstractHurtingProjectile {

    private static int MAX_COUNT = 220;

    private int count;
    private ColorPointLight light;

    public Fireball(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if (level().isClientSide) {
            EntityEffect effect = new EntityEffect(FXHelper.getFX(new ResourceLocation(FantasyBeyond.MOD_ID, "fireball")), pLevel, this);
            effect.start();
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public boolean skipAttackInteraction(Entity pEntity) {
        return pEntity != getOwner();
    }

    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.setPos(pX, pY, pZ);
        this.setRot(pYaw, pPitch);
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        super.shoot(pX, pY, pZ, pVelocity, pInaccuracy);
        if (!level().isClientSide)
            level().playSound(null, position().x, position().y, position().z, SoundEvents.GHAST_SHOOT, SoundSource.NEUTRAL, 1, 1);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            level().explode(this, position().x, position().y, position().z, 10, true, Level.ExplosionInteraction.NONE);
            discard();
        }
    }


    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (count < MAX_COUNT) {
                count++;

                Vec3 vector3d = this.getDeltaMovement();
                double d0 = this.getX() + vector3d.x;
                double d1 = this.getY() + vector3d.y;
                double d2 = this.getZ() + vector3d.z;
                this.setPos(d0, d1, d2);
                if (count == 1)
                    level().playSound(null, d0, d1, d2, SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.75F, 0.05F);
                if (tickCount % 20 == 0)
                    level().playSound(null, d0, d1, d2, SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, 1, 1);
            } else
                discard();
        } else {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()-> ()->{
                Vec3 position = getPosition(Minecraft.getInstance().getPartialTick());
                if(light == null)
                    light = LightManager.INSTANCE.addLight(position.toVector3f(), Color.ORANGE.hashCode(), 10);
                light.setPos((float) position.x, (float) position.y, (float) position.z);
                light.update();
            });
        }
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        if(light!= null)
            light.remove();
    }
}
