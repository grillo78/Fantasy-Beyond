package grillo78.fantasy_beyond.magic.spells;

import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.magic.magic_circles.FallingCannonMagicCircleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FallingCannonMagicSpell extends Spell{
    public FallingCannonMagicSpell(Player player, Level level, SpellType spellType) {
        super(player, level, spellType);
    }

    @Override
    public void cast() {
        FallingCannonMagicCircleType.createInstanceStatic(getLevel(), getPlayer());

    }

    @Override
    public void tick() {
        getPlayer().getCapability(PlayerDataProvider.DATA).ifPresent(playerMagic -> playerMagic.removeSpell());
        super.tick();
    }
}
