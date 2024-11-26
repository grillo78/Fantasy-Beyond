package grillo78.fantasy_beyond;

import com.lowdragmc.shimmer.client.light.LightCounter;
import com.mojang.logging.LogUtils;
import grillo78.clothes_mod.client.event.PreRenderCloth;
import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.capabilities.customization.race.RaceType;
import grillo78.fantasy_beyond.client.entity.CustomizationLayer;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.human.FemaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.human.MaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.FemaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.MaleTieflingModel;
import grillo78.fantasy_beyond.network.PacketHandler;
import grillo78.fantasy_beyond.network.messages.OpenCustomizationScreen;
import grillo78.fantasy_beyond.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FantasyBeyond.MOD_ID)
public class FantasyBeyond {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "fantasy_beyond";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public FantasyBeyond(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        RaceType.RACE_TYPES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::entityJoin);
        MinecraftForge.EVENT_BUS.addListener(this::resizePlayer);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            LightCounter.Render.enable = false;
            modEventBus.addListener(this::registerLayerDefinitions);
            modEventBus.addListener(this::addLayers);
            MinecraftForge.EVENT_BUS.addListener(this::preRenderClothes);
            MinecraftForge.EVENT_BUS.addListener(this::renderFirstPersonHand);
            MinecraftForge.EVENT_BUS.addListener(this::renderWorld);
        });
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    @SuppressWarnings("removal")
    private void resizePlayer(EntityEvent.Size event) {
        event.getEntity().getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            event.setNewSize(data.getPlayerCustomization().getRace().getNewSize(event.getPose(), event.getNewSize()));
            event.setNewEyeHeight(data.getPlayerCustomization().getRace().getNewEyeHeight(event.getPose(), event.getNewEyeHeight()));
        });
    }

    private void entityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player) {
            event.getEntity().refreshDimensions();
            event.getEntity().getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
                if(!data.getPlayerCustomization().isFinished() && !event.getEntity().level().isClientSide) {
                    PacketHandler.INSTANCE.sendTo(new OpenCustomizationScreen(), ((ServerPlayer)event.getEntity()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                }
            });
        }
    }

    private void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(MOD_ID, "player_data"), new PlayerDataProvider((Player) event.getObject()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderFirstPersonHand(RenderHandEvent event) {
        event.setCanceled(true);
    }
    @OnlyIn(Dist.CLIENT)
    private void preRenderClothes(PreRenderCloth event) {
        if (ClientUtil.renderingFirstPersonModel) {
            event.getModel().head.visible = false;
            event.getModel().hat.visible = false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            ClientUtil.renderFirstPersonModel(event);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void addLayers(EntityRenderersEvent.AddLayers event) {
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().forEach((s, renderer) -> {
            ((PlayerRenderer) renderer).addLayer(new CustomizationLayer((PlayerRenderer) renderer));
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MALE_HUMAN, () -> MaleHumanModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_HUMAN, () -> FemaleHumanModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.MALE_ELF, () -> MaleElfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_ELF, () -> FemaleElfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.MALE_TIEFLING, () -> MaleTieflingModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_TIEFLING, () -> FemaleTieflingModel.createBodyLayer(new CubeDeformation(0)));
    }
}
