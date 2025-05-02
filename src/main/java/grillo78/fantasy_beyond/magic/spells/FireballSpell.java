package grillo78.fantasy_beyond.magic.spells;

import grillo78.fantasy_beyond.capabilities.MagicProvider;
import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.magic.magic_circles.MagicCircleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Arrays;

public class FireballSpell extends Spell {

    public FireballSpell(Player player, Level level, SpellType type) {
        super(player, level, type);
    }

    @Override
    public void cast() {
        super.cast();
        if (!getLevel().isClientSide)
            getLevel().getCapability(MagicProvider.MAGIC).ifPresent(magic -> magic.getMagicCircles().add(MagicCircleType.FIREBALL_CIRCLE.get().createInstance((ServerLevel) getLevel(), getPlayer().getEyePosition().add(getPlayer().getViewVector(0)), Arrays.asList(getPlayer()))));
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount > 110)
            getPlayer().getCapability(PlayerDataProvider.DATA).ifPresent(playerMagic -> playerMagic.removeSpell());
    }
}
