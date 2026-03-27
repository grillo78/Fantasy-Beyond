package grillo78.fantasy_beyond.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import grillo78.fantasy_beyond.data_map.ModDataMaps;
import grillo78.fantasy_beyond.data_map.forging.HeatableMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

//Manager for temperature in forging system
public class TemperatureManager implements TooltipComponent {
    public static int AMBIENT_TEMP = 25;
    public static final TemperatureManager EMPTY = new TemperatureManager(AMBIENT_TEMP);
    public static final Codec<TemperatureManager> CODEC = RecordCodecBuilder.create(
            temperatureManagerInstance -> temperatureManagerInstance.group(
                            Codec.FLOAT.fieldOf("temperature").forGetter(TemperatureManager::getTemperature)
                    )
                    .apply(temperatureManagerInstance, TemperatureManager::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, TemperatureManager> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            TemperatureManager::getTemperature,
            TemperatureManager::new);

    private float temperature;

    public TemperatureManager(float temperature) {
        this.temperature = temperature;
    }
    public TemperatureManager() {
        this(AMBIENT_TEMP);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return !(other instanceof TemperatureManager) ? false : temperature == ((TemperatureManager) other).temperature;
        }
    }

    public void tick(Entity entity) {
    }

    public void quench(Level level, BlockPos pos, ItemStack item) {
        item.set(ModDataComponents.TEMPERATURE_MANAGER, new TemperatureManager(AMBIENT_TEMP));
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
        level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
    }

    public void decreaseTemperature(ItemStack stack) {
        if (temperature > AMBIENT_TEMP) {
            HeatableMaterial heatableMaterial = stack.getItemHolder().getData(ModDataMaps.HEATABLE_MATERIALS);
            stack.set(ModDataComponents.TEMPERATURE_MANAGER, new TemperatureManager(temperature - heatableMaterial.getHeatingSpeed() / 4));
        }
    }

    public void increaseTemperature(ItemStack stack) {
        HeatableMaterial heatableMaterial = stack.getItemHolder().getData(ModDataMaps.HEATABLE_MATERIALS);
        stack.set(ModDataComponents.TEMPERATURE_MANAGER, new TemperatureManager(temperature + heatableMaterial.getHeatingSpeed()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.temperature);
    }

    public float getTemperature() {
        return temperature;
    }
}
