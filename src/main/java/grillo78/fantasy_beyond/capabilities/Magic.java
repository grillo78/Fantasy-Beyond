package grillo78.fantasy_beyond.capabilities;

import com.mojang.blaze3d.vertex.PoseStack;
import grillo78.fantasy_beyond.magic.magic_circles.MagicCircleType;
import grillo78.fantasy_beyond.magic.magic_circles.MagicCircleType.MagicCircle;
import grillo78.fantasy_beyond.network.PacketHandler;
import grillo78.fantasy_beyond.network.messages.SyncMagic;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkDirection;

import java.util.ArrayList;
import java.util.List;

public class Magic implements INBTSerializable<CompoundTag> {

    private Level level;
    private List<MagicCircle> magicCircles = new ArrayList<>();

    public Magic(Level level) {
        this.level = level;
    }

    public List<MagicCircle> getMagicCircles() {
        return magicCircles;
    }

    public void removeMagicCircle(MagicCircle circle){
        circle.onRemove();
        magicCircles.remove(circle);
    }

    public void render(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, ClientLevel level, float partialTick) {
        magicCircles.forEach(magicCircle -> magicCircle.render(poseStack, bufferSource,level, partialTick));
    }

    public void tick() {
        List<MagicCircle> magicCircles = new ArrayList(this.magicCircles);
        magicCircles.forEach(magicCircle -> magicCircle.tick());
        if(!level.isClientSide) {
            CompoundTag nbt = serializeNBT();
            for (ServerPlayer player : ((ServerLevel) level).players()){
                PacketHandler.INSTANCE.sendTo(new SyncMagic(nbt), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        CompoundTag magicCirclesCompound = new CompoundTag();
        for (int i = 0; i < magicCircles.size(); i++) {
            magicCirclesCompound.put(String.valueOf(i), magicCircles.get(i).serializeNBT());
        }
        compound.put("magic_circles", magicCirclesCompound);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        magicCircles.clear();
        CompoundTag magicCirclesCompound = nbt.getCompound("magic_circles");

        for (int i = 0; i < magicCirclesCompound.size(); i++) {
            CompoundTag magicCircleCompound = magicCirclesCompound.getCompound(String.valueOf(i));
            MagicCircle circle = MagicCircleType.CIRCLES_REGISTRY.get().getValue(new ResourceLocation(magicCircleCompound.getString("type"))).createInstance(level);
            circle.deserializeNBT(magicCircleCompound);
            magicCircles.add(i, circle);
        }
    }
}
