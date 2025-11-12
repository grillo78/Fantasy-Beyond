package grillo78.fantasy_beyond.network;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ChangeAbilityIndex(int amount) implements CustomPacketPayload {

    public static final Type<ChangeAbilityIndex> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "change_ability_index"));
    public static final StreamCodec<ByteBuf, ChangeAbilityIndex> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ChangeAbilityIndex::amount,
            ChangeAbilityIndex::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final ChangeAbilityIndex data, final IPayloadContext context) {
        Player entity = context.player();
        CharacterData characterData = entity.getData(ModAttachments.CHARACTER_DATA);
        if (characterData.getPlayerClass() != null && characterData.getPlayerClass().getUnlockedAbilities().size() != 0) {
            int index = characterData.getPlayerClass().getSelectedAbilityIndex();
            for (int j = 0; j < Math.abs(data.amount); j++) {
                index += data.amount < 0 ? 1 : -1;
                if (index < 0)
                    index = characterData.getPlayerClass().getUnlockedAbilities().size() - 1;
                if (index >= characterData.getPlayerClass().getUnlockedAbilities().size())
                    index = 0;
            }
            characterData.getPlayerClass().setSelectedAbilityIndex(index);
            Component abilityComponent = characterData.getPlayerClass().getUnlockedAbilities().get(characterData.getPlayerClass().getSelectedAbilityIndex()).getDisplayName();
            entity.displayClientMessage(Component.translatable("set_ability.message", abilityComponent), true);
        } else {
            entity.displayClientMessage(Component.translatable("no_ability.message"), true);
        }
    }
}
