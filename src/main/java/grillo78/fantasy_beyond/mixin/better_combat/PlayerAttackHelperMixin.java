package grillo78.fantasy_beyond.mixin.better_combat;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.classes.abilities.Ability;
import grillo78.fantasy_beyond.character.classes.abilities.AttackAbility;
import net.bettercombat.api.AttackHand;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.logic.PlayerAttackHelper;
import net.bettercombat.logic.WeaponRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerAttackHelper.class)
public class PlayerAttackHelperMixin {

    @Inject(method = "getCurrentAttack", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getCurrentAttack(Player player, int comboCount, CallbackInfoReturnable<AttackHand> cir){
        CharacterData data = player.getData(ModAttachments.CHARACTER_DATA);
        if (data.getPlayerClass() != null){
            List<Ability> unlockedAbilities = data.getPlayerClass().getUnlockedAbilities();
            if (!unlockedAbilities.isEmpty()){
                Ability ability = unlockedAbilities.get(data.getPlayerClass().getSelectedAbilityIndex());
                if (ability.isActive() && ability instanceof AttackAbility attackAbility) {
                    ItemStack itemStack = player.getMainHandItem();
                    WeaponAttributes attributes = WeaponRegistry.getAttributes(itemStack);
                    if (attributes != null && attributes.attacks() != null) {
                        cir.setReturnValue(new AttackHand(attackAbility.getAttack(), attackAbility.getCombo(comboCount), false, attributes, itemStack));
                    }
                }
            }
        }
    }


}
