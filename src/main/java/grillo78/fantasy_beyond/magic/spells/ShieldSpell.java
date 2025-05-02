package grillo78.fantasy_beyond.magic.spells;

import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ShieldSpell extends Spell{
    public ShieldSpell(Player player, Level level, SpellType spellType) {
        super(player, level, spellType);
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount > 1000)
            getPlayer().getCapability(PlayerDataProvider.DATA).ifPresent(playerMagic -> playerMagic.removeSpell());
    }
}
