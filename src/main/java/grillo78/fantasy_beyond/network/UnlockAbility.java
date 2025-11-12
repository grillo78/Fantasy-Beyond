package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.items.QuiverItem;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.QuiverContents;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.awt.*;
import java.util.List;

public record UnlockAbility(int abilityIndex, int entityId) implements CustomPacketPayload {

    public static final Type<UnlockAbility> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "unlock_ability"));
    public static final StreamCodec<ByteBuf, UnlockAbility> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            UnlockAbility::abilityIndex,
            ByteBufCodecs.INT,
            UnlockAbility::entityId,
            UnlockAbility::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final UnlockAbility data, final IPayloadContext context) {
        Entity entity = context.player().level().getEntity(data.entityId);
        if(entity instanceof LivingEntity){
            CharacterData characterData = entity.getData(ModAttachments.CHARACTER_DATA);
            if (characterData.getPlayerClass().getAbilities().get(data.abilityIndex).canBeUnlocked((LivingEntity) entity)) {
                characterData.getPlayerClass().getAbilities().get(data.abilityIndex).unlock();
                characterData.getPlayerClass().decreaseAbilityPoints(characterData.getPlayerClass().getAbilities().get(data.abilityIndex).getRequiredPoints());
            }
            if (!entity.level().isClientSide)
                PacketDistributor.sendToAllPlayers(new SyncCharacterData(entity.getId(), entity.getData(ModAttachments.CHARACTER_DATA).serializeNBT(null)));
        }
    }
}
