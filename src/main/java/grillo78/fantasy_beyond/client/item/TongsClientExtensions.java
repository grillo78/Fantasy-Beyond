package grillo78.fantasy_beyond.client.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class TongsClientExtensions implements IClientItemExtensions {

    private final TongsBEWLR tongsBEWLR = new TongsBEWLR();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return tongsBEWLR;
    }
}
