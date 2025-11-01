package grillo78.fantasy_beyond;

import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.network.OpenScreen;
import grillo78.fantasy_beyond.network.SyncCharacterData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(FantasyBeyond.MOD_ID)
public class FantasyBeyond {
    public static final String MOD_ID = "fantasy_beyond";
    private static final GameRules.Key<GameRules.BooleanValue> KEEP_CHARACTER = GameRules.register("keepCharacter", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));

    public FantasyBeyond(IEventBus modEventBus, ModContainer modContainer) {
        RaceType.RACE_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        modEventBus.addListener(this::register);
        NeoForge.EVENT_BUS.addListener(this::livingFall);
//        NeoForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);
        NeoForge.EVENT_BUS.addListener(this::entityJoin);
//        NeoForge.EVENT_BUS.addListener(this::onStartTracking);
        NeoForge.EVENT_BUS.addListener(this::resizePlayer);
        NeoForge.EVENT_BUS.addListener(this::playerTick);
        NeoForge.EVENT_BUS.addListener(this::levelTick);
        NeoForge.EVENT_BUS.addListener(this::onPlayerClone);
        NeoForge.EVENT_BUS.addListener(this::onHurt);
        NeoForge.EVENT_BUS.addListener(this::canBreath);
    }

    public void canBreath(LivingBreatheEvent event) {
        if (event.getEntity().hasData(ModAttachments.CHARACTER_DATA)) {
            CharacterData data = event.getEntity().getData(ModAttachments.CHARACTER_DATA);
            data.getPlayerCustomization().getRace().canBreath(event);
        }
    }

    private void onHurt(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasData(ModAttachments.CHARACTER_DATA)) {
            CharacterData data = event.getEntity().getData(ModAttachments.CHARACTER_DATA);
            data.getPlayerCustomization().getRace().onHurt(event);
        }
    }

    private void levelTick(final LevelTickEvent.Post event) {
    }

    private void onPlayerClone(final PlayerEvent.Clone event) {
        if (event.getEntity() instanceof Player && event.getEntity().level().getGameRules().getBoolean(KEEP_CHARACTER) && event.getOriginal().hasData(ModAttachments.CHARACTER_DATA)) {
            CharacterData data = event.getEntity().getData(ModAttachments.CHARACTER_DATA);
            data.deserializeNBT(null, event.getOriginal().getData(ModAttachments.CHARACTER_DATA).serializeNBT(null));
            PacketDistributor.sendToAllPlayers(new SyncCharacterData(event.getEntity().getId(), data.serializeNBT(null)));
        }
    }

    private void playerTick(EntityTickEvent.Pre event) {
        if (event.getEntity().hasData(ModAttachments.CHARACTER_DATA)) {
            CharacterData data = event.getEntity().getData(ModAttachments.CHARACTER_DATA);
            data.tick(event);
        }
    }

    private void livingFall(LivingFallEvent event) {
        if (event.getEntity().hasData(ModAttachments.CHARACTER_DATA)) {
            CharacterData data = event.getEntity().getData(ModAttachments.CHARACTER_DATA);
            data.getPlayerCustomization().getRace().livingFall(event);
        }
    }

    private void resizePlayer(EntityEvent.Size event) {
        if (event.getEntity().hasData(ModAttachments.CHARACTER_DATA)) {
            CharacterData data = event.getEntity().getData(ModAttachments.CHARACTER_DATA);
            event.setNewSize(data.getPlayerCustomization().getRace().getNewSize(event.getPose(),
                    event.getNewSize()).withEyeHeight(data.getPlayerCustomization().getRace().getNewEyeHeight(event.getPose(), event.getNewSize().eyeHeight())));
        }
    }

    private void entityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player) {
            CharacterData data = event.getEntity().getData(ModAttachments.CHARACTER_DATA);
            event.getEntity().refreshDimensions();
            if (!event.getEntity().level().isClientSide) {
                if (!data.getPlayerCustomization().isFinished())
                    PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new OpenScreen());
                else {
                    data.getPlayerCustomization().getRace().applyAttributes((Player) event.getEntity());
                    event.getEntity().refreshDimensions();
                    PacketDistributor.sendToAllPlayers(new SyncCharacterData(event.getEntity().getId(), data.serializeNBT(null)));
                }
            }
        }
    }

    private void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MOD_ID);
        registrar.commonToClient(OpenScreen.TYPE, OpenScreen.STREAM_CODEC, FMLLoader.getDist().isClient() ? OpenScreen::handle : OpenScreen::handleServer);
        registrar.commonBidirectional(SyncCharacterData.TYPE, SyncCharacterData.STREAM_CODEC, SyncCharacterData::handle);
    }
}
