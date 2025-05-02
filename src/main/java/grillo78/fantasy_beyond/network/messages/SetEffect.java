package grillo78.fantasy_beyond.network.messages;

import com.lowdragmc.photon.client.fx.BlockEffect;
import com.lowdragmc.photon.client.fx.FXHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetEffect implements IMessage<SetEffect> {

    private ResourceLocation effect;
    private BlockPos position;

    public SetEffect() {
    }

    public SetEffect(BlockPos position, ResourceLocation effect) {
        this.position = position;
        this.effect = effect;
    }

    @Override
    public void encode(SetEffect message, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(message.position);
        buffer.writeResourceLocation(message.effect);
    }

    @Override
    public SetEffect decode(FriendlyByteBuf buffer) {
        return new SetEffect(buffer.readBlockPos(), buffer.readResourceLocation());
    }

    @Override
    public void handle(SetEffect message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            BlockPos position = message.position;
            Minecraft.getInstance().level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
            BlockEffect effect = new BlockEffect(FXHelper.getFX(message.effect), Minecraft.getInstance().level, position);
            effect.setCheckState(false);
            effect.start();
        });
        supplier.get().setPacketHandled(true);
    }
}
