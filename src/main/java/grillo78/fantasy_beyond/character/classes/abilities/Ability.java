package grillo78.fantasy_beyond.character.classes.abilities;

import grillo78.fantasy_beyond.character.classes.PlayerClass;
import grillo78.fantasy_beyond.character.classes.PlayerClassType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ability implements INBTSerializable<CompoundTag> {
    private PlayerClass playerClass;
    private Vec2 mapPosition;
    private boolean unlocked = false;
    protected boolean active = false;
    private List<Ability> parents;
    private String name;
    private int requiredPoints = 1;
    protected int tick;

    public Ability(String name, List<Ability> parents, Vec2 mapPosition, PlayerClass playerClass, int requiredPoints) {
        this(name, parents, mapPosition, playerClass);
        this.requiredPoints = requiredPoints;
    }

    public Ability(String name, List<Ability> parents, Vec2 mapPosition, PlayerClass playerClass) {
        this.name = name;
        this.parents = parents;
        this.mapPosition = mapPosition;
        this.playerClass = playerClass;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public void unlock() {
        this.unlocked = true;
    }

    public Vec2 getMapPosition() {
        return mapPosition;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public List<Ability> getParents() {
        return parents;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getTexture() {
        ResourceLocation classRegistryName = PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.getKey(playerClass.getType());
        return ResourceLocation.fromNamespaceAndPath(classRegistryName.getNamespace(), "textures/ability/" + name + ".png");
    }

    public Component getDisplayName() {
        ResourceLocation classRegistryName = PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.getKey(playerClass.getType());
        return Component.translatable(classRegistryName.getNamespace() + ".ability." + name);
    }

    public List<Component> getTitleTooltip() {
        List<Component> list = new ArrayList<>();

        list.add(getDisplayName());

        return list;
    }

    public List<FormattedText> getTooltip(LivingEntity entity) {
        List<FormattedText> list = new ArrayList<>();

        list.add(Component.translatable("fantasy_beyond.tooltip." + (unlocked ? "unlocked" : "locked")));
        if (!unlocked && canBeUnlocked(entity))
            list.add(Component.translatable("fantasy_beyond.tooltip.can_be_unlocked").withColor(Color.ORANGE.hashCode()));

        ResourceLocation classRegistryName = PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.getKey(playerClass.getType());
        List<FormattedText> descriptionLines = new ArrayList<>();
        String[] splitDescription = Component.translatable(classRegistryName.getNamespace() + ".ability." + name + ".description").getString().split("\n");

        for (int i = 0; i < splitDescription.length; i++) {
            descriptionLines.add(Component.literal(splitDescription[i]).withColor(Color.LIGHT_GRAY.hashCode()));
        }

        list.addAll(descriptionLines);

        return list;
    }

    public void tick(LivingEntity entity) {
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("unlocked", unlocked);
        compoundTag.putBoolean("active", active);
        compoundTag.putInt("tick", tick);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        unlocked = nbt.getBoolean("unlocked");
        active = nbt.getBoolean("active");
        tick = nbt.getInt("tick");
    }

    public void activate(LivingEntity entity) {
        if (!active) {
            active = true;
            tick = 0;
        }
    }

    public boolean canActivate(Entity entity){
        return true;
    }

    public boolean canBeUnlocked(LivingEntity livingEntity) {
        boolean parentUnlocked = false;
        if (getParents() != null)
            for (int i = 0; i < getParents().size(); i++) {
                if (getParents().get(i).isUnlocked())
                    parentUnlocked = true;
            }
        return playerClass.getAbilityPoints() >= requiredPoints && (getParents() == null || parentUnlocked);
    }

    public boolean isActive() {
        return active;
    }
}
