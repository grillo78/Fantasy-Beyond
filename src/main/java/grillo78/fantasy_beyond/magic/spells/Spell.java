package grillo78.fantasy_beyond.magic.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;

public class Spell implements INBTSerializable<CompoundTag> {

    private Player player;
    private Level level;
    private SpellType type;

    protected int tickCount = 0;

    public Spell(Player player, Level level, SpellType type) {
        this.player = player;
        this.level = level;
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

    public SpellType getType() {
        return type;
    }

    public void cast() {
    }

    public void tick() {
        tickCount++;
    }

    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, ClientLevel level, float partialTick) {
    }

    public void onClose() {

    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("type", type.getRegistryName().toString());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
