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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ActivateAbility(int entityId) implements CustomPacketPayload {

    public static final Type<ActivateAbility> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(FantasyBeyond.MOD_ID, "activate_ability"));
    public static final StreamCodec<ByteBuf, ActivateAbility> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ActivateAbility::entityId,
            ActivateAbility::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final ActivateAbility data, final IPayloadContext context) {
        Entity entity = context.player().level().getEntity(data.entityId);
        if (entity instanceof LivingEntity) {
            CharacterData characterData = entity.getData(ModAttachments.CHARACTER_DATA);
            if (characterData.getPlayerClass() != null && characterData.getPlayerClass().getUnlockedAbilities().get(characterData.getPlayerClass().getSelectedAbilityIndex()).isUnlocked() && characterData.getPlayerClass().getUnlockedAbilities().get(characterData.getPlayerClass().getSelectedAbilityIndex()).canActivate(entity)) {
                characterData.getPlayerClass().getUnlockedAbilities().get(characterData.getPlayerClass().getSelectedAbilityIndex()).activate();
                if (entity instanceof Player player) {
                    Component abilityComponent = characterData.getPlayerClass().getUnlockedAbilities().get(characterData.getPlayerClass().getSelectedAbilityIndex()).getDisplayName();
                    player.displayClientMessage(Component.translatable("activate_ability.message", abilityComponent), true);
                }
                if (!entity.level().isClientSide)
                    PacketDistributor.sendToAllPlayers(new SyncCharacterData(entity.getId(), entity.getData(ModAttachments.CHARACTER_DATA).serializeNBT(null)));
            } else if (entity instanceof Player player)
                player.displayClientMessage(Component.translatable("cant_activate_ability.message"), true);
        }
    }
}
