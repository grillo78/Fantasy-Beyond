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

    public CustomWeapon(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public float getAttackDamageBonus(Entity target, float damage, DamageSource damageSource) {
        float bonus = 0;
        if (damageSource.getEntity() != null && damageSource.getEntity().hasData(ModAttachments.CHARACTER_DATA)) {
            CharacterData data = damageSource.getEntity().getData(ModAttachments.CHARACTER_DATA);
            if (data.getPlayerClass() != null)
                bonus = data.getPlayerClass().getAttackDamageBonus(damageSource.getWeaponItem());
        }
        return bonus + super.getAttackDamageBonus(target, damage, damageSource);
    }
}
