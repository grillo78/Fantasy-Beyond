package grillo78.fantasy_beyond.character;

import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.level.Level;
import grillo78.fantasy_beyond.character.level.stats.Stat;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CharacterData implements INBTSerializable<CompoundTag> {
    private PlayerCustomization playerCustomization = new PlayerCustomization();
    private LinkedHashMap<String, Stat> stats = new LinkedHashMap<>();
    private int statPoints = 0;
    private Level level = new Level(this);

    public CharacterData() {
        stats.put("Health", new Stat(Attributes.MAX_HEALTH, 100));
        stats.put("Strength", new Stat(Attributes.ATTACK_DAMAGE, 2));
        stats.put("Agility", new Stat(Attributes.MOVEMENT_SPEED, 0.001F));
    }

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    public void tick(EntityTickEvent event) {
        if (event.getEntity().level().isClientSide)
            this.clientTick();
        if (playerCustomization.isFinished())
            playerCustomization.getRace().tick(event);
    }

    @OnlyIn(Dist.CLIENT)
    private void clientTick() {
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("customization", playerCustomization.serializeNBT(provider));

        CompoundTag stats = new CompoundTag();
        this.stats.forEach((statKey, stat) -> {
            stats.put(statKey, stat.serializeNBT(provider));
        });
        compoundTag.put("stats", stats);
        compoundTag.put("level", level.serializeNBT(provider));
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        playerCustomization.deserializeNBT(provider, tag.getCompound("customization"));
        CompoundTag stats = tag.getCompound("stats");
        this.stats.forEach((statKey, stat) -> {
            stat.deserializeNBT(provider, stats.getCompound(statKey));
        });
        statPoints = tag.getInt("statPoints");
        level.deserializeNBT(provider, tag.getCompound("level"));
    }

    public void applyStats(LivingEntity player) {
        if (!player.level().isClientSide)
            stats.forEach((key, stat) -> {
                stat.apply(player, level);
            });
    }

    public int getStatPoints() {
        return statPoints;
    }

    public void increaseStatPoints() {
        statPoints += 3;
    }

    public void decreaseStatPoint() {
        statPoints--;
    }

    public HashMap<String, Stat> getStats() {
        return stats;
    }
}
