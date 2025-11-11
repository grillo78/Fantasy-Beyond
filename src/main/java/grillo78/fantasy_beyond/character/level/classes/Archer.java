package grillo78.fantasy_beyond.character.level.classes;

import grillo78.fantasy_beyond.items.CustomWeapon;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.UnknownNullability;

import java.awt.*;

public class Archer extends PlayerClass{

    @Override
    public Component getDisplayName() {
        return Component.translatable("fantasy_beyond.player_class.archer").withColor(Color.ORANGE.hashCode());
    }

    @Override
    public float getAttackDamageBonus(CustomWeapon customWeapon) {
        return customWeapon.getWeaponType() == CustomWeapon.WeaponType.BOW? 4 : 0;
    }
}
