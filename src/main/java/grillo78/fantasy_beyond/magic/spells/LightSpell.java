package grillo78.fantasy_beyond.magic.spells;

import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.entities.LightSource;
import grillo78.fantasy_beyond.entities.ModEntities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public class LightSpell extends Spell {

    public LightSpell(Player player, Level level, SpellType type) {
        super(player, level, type);
    }

    @Override
    public void cast() {
        super.cast();
        if (!getLevel().isClientSide){
            Random random = new Random();
            LightSource lightSource = ModEntities.LIGHT_SOURCE.get().create(getLevel());
            lightSource.setPos(getPlayer().getEyePosition().add(random.nextBoolean()?1:-1, 0, random.nextBoolean()?1:-1));
            lightSource.setOwner(getPlayer());
            getLevel().addFreshEntity(lightSource);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount > 1)
            getPlayer().getCapability(PlayerDataProvider.DATA).ifPresent(playerMagic -> playerMagic.removeSpell());
    }
}
