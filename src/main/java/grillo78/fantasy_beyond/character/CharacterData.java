package grillo78.fantasy_beyond.character;

import grillo78.fantasy_beyond.character.customization.PlayerCustomization;
import grillo78.fantasy_beyond.character.level.Level;
import grillo78.fantasy_beyond.character.classes.PlayerClass;
import grillo78.fantasy_beyond.character.classes.PlayerClassType;
import grillo78.fantasy_beyond.character.level.stats.Stat;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
    private int statPoints = 4;
    private Level level = new Level(this);
    private PlayerClass playerClass = null;

    public CharacterData() {
        HashMap<Holder<Attribute>, Float> map = new HashMap();
        map.put(Attributes.MAX_HEALTH, 5F);
        stats.put("health", new Stat(map));
        map = new HashMap();
        map.put(Attributes.ATTACK_DAMAGE, 2F);
        stats.put("strength", new Stat(map));
        map = new HashMap();
        map.put(Attributes.MOVEMENT_SPEED, 0.001F);
        map.put(Attributes.ATTACK_SPEED, 0.001F);
        stats.put("agility", new Stat(map));
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    public void tick(EntityTickEvent event) {
        if (event.getEntity().level().isClientSide)
            this.clientTick();
        if (playerCustomization.isFinished()) {
            playerCustomization.getRace().tick(event);
            if (playerClass != null)
                for (int i = 0; i < playerClass.getAbilities().size(); i++) {
                    playerClass.getAbilities().get(i).tick((LivingEntity) event.getEntity());
                }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void clientTick() {
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
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
        compoundTag.putInt("statPoints", statPoints);
        compoundTag.put("level", level.serializeNBT(provider));
        if (playerClass != null)
            compoundTag.put("playerClass", playerClass.serializeNBT(provider));
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        playerCustomization.deserializeNBT(provider, tag.getCompound("customization"));
        CompoundTag stats = tag.getCompound("stats");
        this.stats.forEach((statKey, stat) -> {
            if (stats.contains(statKey))
                stat.deserializeNBT(provider, stats.getCompound(statKey));
        });
        statPoints = tag.getInt("statPoints");
        level.deserializeNBT(provider, tag.getCompound("level"));
        if (tag.contains("playerClass")) {
            CompoundTag raceCompound = tag.getCompound("playerClass");
            if (playerClass == null || playerClass.getType() != PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.get(ResourceLocation.parse(raceCompound.getString("type"))))
                playerClass = PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.get(ResourceLocation.parse(raceCompound.getString("type"))).createPlayerClass();
            playerClass.deserializeNBT(provider, tag.getCompound("playerClass"));
        }
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
        statPoints += 1;
    }

    public void decreaseStatPoint() {
        statPoints--;
    }

    public LinkedHashMap<String, Stat> getStats() {
        return stats;
    }

    public Level getLevel() {
        return level;
    }
}
