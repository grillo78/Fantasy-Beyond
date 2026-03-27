package grillo78.fantasy_beyond.character.classes;

import grillo78.fantasy_beyond.character.classes.abilities.Ability;
import grillo78.fantasy_beyond.character.classes.abilities.EffectAbility;
import grillo78.fantasy_beyond.character.classes.abilities.SelfEffectAbility;
import grillo78.fantasy_beyond.items.CustomWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec2;

import java.awt.*;
import java.util.List;

public class Tank extends PlayerClass{

    @Override
    protected void addAbilities() {
        Vec2 zero = new Vec2(1,-1);
        Ability battleRoar = addAbility(new EffectAbility("battle_roar", null, zero, this, 1, 2000, MobEffects.DAMAGE_RESISTANCE, 2));
        Ability steelWill = addAbility(new SelfEffectAbility("steel_will", List.of(battleRoar), zero.add(new Vec2(0.75F, 0.5F)), this, 10, 5000, MobEffects.ABSORPTION, 10));
        Ability stomp = addAbility(new EffectAbility("stomp", List.of(battleRoar), zero.add(new Vec2(0.75F, -0.5F)), this, 10, 2000, MobEffects.MOVEMENT_SLOWDOWN, 2, false));
    }

    @Override
    public Component getDisplayName() {
        ResourceLocation registryName = PlayerClassType.PLAYER_CLASS_TYPES_REGISTRY.getKey(getType());
        return Component.translatable(registryName.getNamespace() + ".player_class." + registryName.getPath()).withColor(Color.ORANGE.hashCode());
    }

    @Override
    public float getAttackDamageBonus(CustomWeapon customWeapon) {
        return customWeapon.getWeaponType() == CustomWeapon.WeaponType.BOW? 4 : 0;
    }
}
