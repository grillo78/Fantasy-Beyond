package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class CustomWeapon extends SwordItem {
    private WeaponType weaponType;

    public CustomWeapon(WeaponType weaponType,Tier tier, Properties properties) {
        super(tier, properties);
        this.weaponType = weaponType;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return super.getDefaultAttributeModifiers(stack);
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        float bonus = 0;
        if(damageSource.getEntity() != null && damageSource.getEntity().hasData(ModAttachments.CHARACTER_DATA)){
            CharacterData data = damageSource.getEntity().getData(ModAttachments.CHARACTER_DATA);
            bonus = data.getPlayerClass().getAttackDamageBonus(this);
        }
        return bonus + super.getAttackDamageBonus(target, damage, damageSource);
    }

    public WeaponType getWeaponType() {
        return this.weaponType;
    }

    public static enum WeaponType{
        BOW, KATANA, SPEAR, SCYTHE, GREAT_SWORD, GAUNTLET, GREAT_HAMMER, BATTLE_AXE, HALBERD, SWORD
    }
}
