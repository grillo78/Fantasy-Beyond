package grillo78.fantasy_beyond.character.level.classes.abilities;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.level.classes.PlayerClass;
import grillo78.fantasy_beyond.client.ClientUtils;
import grillo78.fantasy_beyond.network.SyncCharacterData;
import net.bettercombat.api.ComboState;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.logic.WeaponRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.network.PacketDistributor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AttackAbility extends Ability {

    private List<WeaponAttributes.Attack> attacks = new ArrayList<>();
    private int comboIndex = 0;
    private String category;

    public AttackAbility(String name, List<Ability> parents, Vec2 mapPosition, PlayerClass playerClass, int requiredPoints, String category) {
        super(name, parents, mapPosition, playerClass, requiredPoints);
        this.category = category;
        attacks.add(new WeaponAttributes.Attack(new WeaponAttributes.Condition[]{WeaponAttributes.Condition.MAIN_HAND_ONLY}, WeaponAttributes.HitBoxShape.VERTICAL_PLANE, 10, 10, 0.5, "bettercombat:two_handed_slash_vertical_left", new WeaponAttributes.Sound("bettercombat:katana_slash"), new WeaponAttributes.Sound()));
        attacks.add(new WeaponAttributes.Attack(new WeaponAttributes.Condition[]{WeaponAttributes.Condition.MAIN_HAND_ONLY}, WeaponAttributes.HitBoxShape.VERTICAL_PLANE, 10, 10, 0.5, "bettercombat:two_handed_slash_vertical_right", new WeaponAttributes.Sound("bettercombat:katana_slash"), new WeaponAttributes.Sound()));
        attacks.add(new WeaponAttributes.Attack(new WeaponAttributes.Condition[]{WeaponAttributes.Condition.MAIN_HAND_ONLY}, WeaponAttributes.HitBoxShape.VERTICAL_PLANE, 10, 10, 0.5, "bettercombat:two_handed_slash_vertical_left", new WeaponAttributes.Sound("bettercombat:katana_slash"), new WeaponAttributes.Sound()));
        attacks.add(new WeaponAttributes.Attack(new WeaponAttributes.Condition[]{WeaponAttributes.Condition.MAIN_HAND_ONLY}, WeaponAttributes.HitBoxShape.VERTICAL_PLANE, 10, 10, 0.5, "bettercombat:two_handed_slash_vertical_right", new WeaponAttributes.Sound("bettercombat:katana_slash"), new WeaponAttributes.Sound()));
        attacks.add(new WeaponAttributes.Attack(new WeaponAttributes.Condition[]{WeaponAttributes.Condition.MAIN_HAND_ONLY}, WeaponAttributes.HitBoxShape.VERTICAL_PLANE, 10, 10, 0.5, "bettercombat:two_handed_slash_vertical_left", new WeaponAttributes.Sound("bettercombat:katana_slash"), new WeaponAttributes.Sound()));
        attacks.add(new WeaponAttributes.Attack(new WeaponAttributes.Condition[]{WeaponAttributes.Condition.MAIN_HAND_ONLY}, WeaponAttributes.HitBoxShape.VERTICAL_PLANE, 10, 10, 0.5, "bettercombat:two_handed_slash_vertical_right", new WeaponAttributes.Sound("bettercombat:katana_slash"), new WeaponAttributes.Sound()));
    }

    public AttackAbility(String name, List<Ability> parents, Vec2 mapPosition, PlayerClass playerClass, String category) {
        this(name, parents, mapPosition, playerClass, 1, category);
    }

    public WeaponAttributes.Attack getAttack() {
        return attacks.get((comboIndex < attacks.size() ? comboIndex : 0));
    }

    public ComboState getCombo(int comboCount) {
        comboIndex = comboCount % (this.attacks.size() + 1);
        return new ComboState(comboIndex + 1, this.attacks.size());
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (active) {
            if (this.tick > 0 && entity.isControlledByLocalInstance()) {
                if (comboIndex >= attacks.size() && entity.level().isClientSide) {
                    active = false;
                    comboIndex = 0;
                    PacketDistributor.sendToServer(new SyncCharacterData(entity.getId(), entity.getData(ModAttachments.CHARACTER_DATA).serializeNBT(null)));
                } else {
                    ClientUtils.startAttack();
                    System.out.println(comboIndex);
                }
            }
            tick++;
        }
    }

    @Override
    public List<FormattedText> getTooltip(LivingEntity entity) {
        List<FormattedText> lines = super.getTooltip(entity);
        lines.add(Component.translatable("fantasy_beyond.tooltip.weapon", Component.translatable("fantasy_beyond.tooltip." + category).withColor(Color.LIGHT_GRAY.hashCode())).withColor(Color.ORANGE.hashCode()));
        return lines;
    }

    @Override
    public boolean canActivate(Entity entity) {
        if (entity instanceof Player player && WeaponRegistry.getAttributes(player.getMainHandItem()) != null) {
            return this.category.equals(WeaponRegistry.getAttributes(player.getMainHandItem()).category());
        }

        return false;
    }

    @Override
    public void activate() {
        if (!active)
            comboIndex = 0;
        super.activate();
    }
}
