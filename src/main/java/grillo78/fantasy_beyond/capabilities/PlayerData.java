package grillo78.fantasy_beyond.capabilities;

import com.mojang.blaze3d.vertex.PoseStack;
//import dev.kosmx.playerAnim.api.layered.IAnimation;
//import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
//import dev.kosmx.playerAnim.api.layered.ModifierLayer;
//import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.capabilities.customization.PlayerCustomization;
import grillo78.fantasy_beyond.magic.spells.Spell;
import grillo78.fantasy_beyond.magic.spells.SpellType;
import grillo78.fantasy_beyond.network.PacketHandler;
import grillo78.fantasy_beyond.network.messages.SyncPlayerData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.network.NetworkDirection;

public class PlayerData implements INBTSerializable<CompoundTag> {
    private Player player;
    private PlayerCustomization playerCustomization = new PlayerCustomization();
    private ModifierLayer layer = new ModifierLayer();
    @OnlyIn(Dist.CLIENT)
    private Spell spell;
    private IAnimation stop = new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(FantasyBeyond.MOD_ID, "stop")));

    public PlayerData(Player player) {
        this.player = player;
    }

    public PlayerCustomization getPlayerCustomization() {
        return playerCustomization;
    }

    public Player getPlayer() {
        return player;
    }

    public void tick(TickEvent.PlayerTickEvent event) {
        if (player.level().isClientSide)
            this.clientTick();
        if (spell != null)
            spell.tick();
        if (playerCustomization.isFinished())
            playerCustomization.getRace().tick(event);
    }

    @OnlyIn(Dist.CLIENT)
    private void clientTick() {
        if (layer.getAnimation() != null && layer.getAnimation() != this.stop && ((KeyframeAnimationPlayer) layer.getAnimation()).getCurrentTick() == ((KeyframeAnimationPlayer) layer.getAnimation()).getStopTick() - 5) {
            layer.setAnimation(stop = new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(new ResourceLocation(FantasyBeyond.MOD_ID, "stop"))));
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("customization", playerCustomization.serializeNBT());
        if (spell != null) {
            CompoundTag spellsCompound = spell.serializeNBT();
            compoundTag.put("spell", spellsCompound);
        }
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerCustomization.deserializeNBT(nbt.getCompound("customization"));
        if (nbt.contains("spell")) {
            CompoundTag spellCompound = nbt.getCompound("spell");
            if (spell == null && spellCompound.contains("type"))
                spell = SpellType.SPELLS_REGISTRY.get().getValue(new ResourceLocation(spellCompound.getString("type"))).createInstance(player, player.level());
            spell.deserializeNBT(spellCompound);
        } else {
            if (spell != null)
                spell.onClose();
            spell = null;
        }
    }

    public ModifierLayer getLayer() {
        return layer;
    }

    public void applyAnimation(String animation) {
        IAnimation animator = new KeyframeAnimationPlayer(PlayerAnimationRegistry.getAnimation(animation.contains(":") ? new ResourceLocation(animation) : new ResourceLocation(FantasyBeyond.MOD_ID, animation)));
        if (animator != null) {
            layer.setAnimation(animator);
        }
    }

    public void sync() {
        ((ServerLevel) player.level()).players().forEach(playerAux -> {
            PacketHandler.INSTANCE.sendTo(new SyncPlayerData(serializeNBT(), player.getId()), playerAux.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        });
    }

    public void removeSpell() {
        spell.onClose();
        spell = null;
    }

    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, ClientLevel level, float partialTick) {
        if (spell != null)
            spell.render(poseStack, bufferSource, level, partialTick);
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }
}
