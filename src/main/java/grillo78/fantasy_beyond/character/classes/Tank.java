package grillo78.fantasy_beyond.character.classes;

import grillo78.fantasy_beyond.character.classes.abilities.Ability;
import grillo78.fantasy_beyond.character.classes.abilities.AttackAbility;
import grillo78.fantasy_beyond.items.CustomWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

import java.awt.*;
import java.util.List;

public class Tank extends PlayerClass{

    @Override
    protected void addAbilities() {
        Vec2 zero = new Vec2(1,-1);
        Ability test1 = addAbility(new AttackAbility("test_attack", null, zero, this, "katana"));
        Ability test2 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(0.75F, 0.5F)), this));
        Ability test3 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(0.5F, -0.5F)), this));
        Ability test4 = addAbility(new Ability("test", List.of(test2), zero.add(new Vec2(1.25F, 0.75F)), this));
        Ability test6 = addAbility(new Ability("test", List.of(test2), zero.add(new Vec2(2F, 0.25F)), this));
        Ability test5 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(1.5F, 0)), this));

        zero = new Vec2(1,1);
        test1 = addAbility(new Ability("test", null, zero, this));
        test2 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(0.75F, -0.5F)), this));
        test3 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(0.75F, 0.5F)), this));
        test4 = addAbility(new Ability("test", List.of(test2, test3), zero.add(new Vec2(1.25F, 0F)), this));
        test5 = addAbility(new Ability("test", List.of(test4), zero.add(new Vec2(1.75F, 0F)), this));

        zero = new Vec2(-1,-1);
        test1 = addAbility(new Ability("test", null, zero, this));
        test2 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(-0.75F, 0.5F)), this));
        test3 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(-0.5F, -0.5F)), this));
        test4 = addAbility(new Ability("test", List.of(test2), zero.add(new Vec2(-1.25F, 0.75F)), this));
        test5 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(-1.5F, 0)), this));
        test6 = addAbility(new Ability("test", List.of(test2, test5), zero.add(new Vec2(-2F, 0.25F)), this));

        zero = new Vec2(-1,1);
        test1 = addAbility(new Ability("test", null, zero, this));
        test2 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(-0.75F, -0.5F)), this));
        test3 = addAbility(new Ability("test", List.of(test1), zero.add(new Vec2(-0.75F, 0.5F)), this));
        test4 = addAbility(new Ability("test", List.of(test2, test3), zero.add(new Vec2(-1.25F, 0F)), this));
        test5 = addAbility(new Ability("test", List.of(test4), zero.add(new Vec2(-1.75F, 0F)), this));
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
