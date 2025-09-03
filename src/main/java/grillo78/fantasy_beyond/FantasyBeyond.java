package grillo78.fantasy_beyond;

import com.lowdragmc.shimmer.client.light.LightCounter;
import com.lowdragmc.shimmer.platform.Services;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import grillo78.clothes_mod.client.event.PreRenderCloth;
import grillo78.fantasy_beyond.blockentities.ModBlockEntities;
import grillo78.fantasy_beyond.blocks.ModBlocks;
import grillo78.fantasy_beyond.capabilities.MagicProvider;
import grillo78.fantasy_beyond.capabilities.PlayerDataProvider;
import grillo78.fantasy_beyond.capabilities.customization.race.RaceType;
import grillo78.fantasy_beyond.capabilities.spells_book.SpellsBookProvider;
import grillo78.fantasy_beyond.client.KeyMappings;
import grillo78.fantasy_beyond.client.entity.CustomizationLayer;
import grillo78.fantasy_beyond.client.entity.FireballRenderer;
import grillo78.fantasy_beyond.client.entity.LightSourceSpellRenderer;
import grillo78.fantasy_beyond.client.entity.ModModelLayers;
import grillo78.fantasy_beyond.client.entity.goblin.GoblinModel;
import grillo78.fantasy_beyond.client.entity.goblin.GoblinRenderer;
import grillo78.fantasy_beyond.client.entity.lopus.LopusModel;
import grillo78.fantasy_beyond.client.entity.lopus.LopusRenderer;
import grillo78.fantasy_beyond.client.entity.mimic.MimicModel;
import grillo78.fantasy_beyond.client.entity.mimic.MimicRenderer;
import grillo78.fantasy_beyond.client.entity.race.automaton.AutomatonModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.FemaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.dwarf.MaleDwarfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.FemaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.elf.MaleElfModel;
import grillo78.fantasy_beyond.client.entity.race.human.FemaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.human.MaleHumanModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.FemaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.MaleMerfolkModel;
import grillo78.fantasy_beyond.client.entity.race.merfolk.tails.MerfolkTail1Model;
import grillo78.fantasy_beyond.client.entity.race.orc.FemaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.orc.MaleOrcModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.FemaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.MaleTieflingModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.BigHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.MediumHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.horns.TallHornsModel;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail1Model;
import grillo78.fantasy_beyond.client.entity.race.tiefling.tails.TieflingTail2Model;
import grillo78.fantasy_beyond.entities.Goblin;
import grillo78.fantasy_beyond.entities.Lopus;
import grillo78.fantasy_beyond.entities.Mimic;
import grillo78.fantasy_beyond.entities.ModEntities;
import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.items.SpellsBook;
import grillo78.fantasy_beyond.magic.magic_circles.MagicCircleType;
import grillo78.fantasy_beyond.magic.spells.SpellType;
import grillo78.fantasy_beyond.network.PacketHandler;
import grillo78.fantasy_beyond.network.messages.ApplyAnimation;
import grillo78.fantasy_beyond.network.messages.OpenCustomizationScreen;
import grillo78.fantasy_beyond.network.messages.ScrollSpell;
import grillo78.fantasy_beyond.sounds.ModSounds;
import grillo78.fantasy_beyond.structures.ModStructureProcessors;
import grillo78.fantasy_beyond.structures.ModStructures;
import grillo78.fantasy_beyond.tabs.ModTabs;
import grillo78.fantasy_beyond.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import org.joml.Quaternionf;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import java.awt.*;
import java.math.BigDecimal;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FantasyBeyond.MOD_ID)
public class FantasyBeyond {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "fantasy_beyond";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final GameRules.Key<GameRules.BooleanValue> KEEP_CHARACTER = GameRules.register("keepCharacter", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));

    @SuppressWarnings("removal")
    public FantasyBeyond() {
        this(FMLJavaModLoadingContext.get());
    }

    public FantasyBeyond(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        RaceType.RACE_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        SpellType.SPELLS.register(modEventBus);
        MagicCircleType.MAGIC_CIRCLES.register(modEventBus);
        ModStructureProcessors.STRUCTURE_PROCESSOR.register(modEventBus);
        ModStructures.STRUCTURE_TYPES.register(modEventBus);
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerEntityAttributes);

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachCapabilities);
        MinecraftForge.EVENT_BUS.addGenericListener(Level.class, this::attachLevelCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::livingFall);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);
        MinecraftForge.EVENT_BUS.addListener(this::entityJoin);
        MinecraftForge.EVENT_BUS.addListener(this::onStartTracking);
        MinecraftForge.EVENT_BUS.addListener(this::resizePlayer);
        MinecraftForge.EVENT_BUS.addListener(this::playerTick);
        MinecraftForge.EVENT_BUS.addListener(this::levelTick);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(this::onHurt);
        MinecraftForge.EVENT_BUS.addListener(this::canBreath);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register((player, animationStack) -> {
                player.getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
                    ModifierLayer<IAnimation> layer = data.getLayer();
                    animationStack.addAnimLayer(69, layer);
                    PlayerAnimationAccess.getPlayerAssociatedData(player).set(new ResourceLocation(MOD_ID, "data"), layer);
                });
            });
            if (Services.PLATFORM.isDevelopmentEnvironment())
                LightCounter.Render.enable = false;
            modEventBus.addListener(this::onClientSetup);
            modEventBus.addListener(this::registerLayerDefinitions);
            modEventBus.addListener(this::registerModel);
            modEventBus.addListener(EventPriority.HIGHEST, this::addLayers);
            modEventBus.addListener(this::registerKeys);
            MinecraftForge.EVENT_BUS.addListener(this::preRenderClothes);
            MinecraftForge.EVENT_BUS.addListener(this::renderFirstPersonHand);
            MinecraftForge.EVENT_BUS.addListener(this::keyInput);
            MinecraftForge.EVENT_BUS.addListener(this::renderLevelLast);
            MinecraftForge.EVENT_BUS.addListener(this::scrollMouse);
            MinecraftForge.EVENT_BUS.addListener(this::renderHUD);
        });
    }

    public void canBreath(LivingBreatheEvent event) {
        event.getEntity().getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            data.getPlayerCustomization().getRace().canBreath(event);
        });
    }

    private void onHurt(LivingAttackEvent event) {
        event.getEntity().getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            data.getPlayerCustomization().getRace().onHurt(event);
        });
    }

    private void levelTick(final TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            event.level.getCapability(MagicProvider.MAGIC).ifPresent(magic -> {
                magic.tick();
            });
    }

    private void onPlayerClone(final PlayerEvent.Clone event) {
        if (event.getEntity() instanceof Player && event.getEntity().level().getGameRules().getBoolean(KEEP_CHARACTER)) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerDataProvider.DATA).ifPresent(h ->
                    event.getEntity().getCapability(PlayerDataProvider.DATA).ifPresent(c -> {
                        c.deserializeNBT(h.serializeNBT());
                        c.sync();
                    })
            );
            event.getOriginal().invalidateCaps();
        }
    }

    private void playerTick(TickEvent.PlayerTickEvent event) {
        event.player.getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            data.tick(event);
        });
        event.player.getCapability(PlayerDataProvider.DATA).ifPresent(magic -> {
            magic.tick(event);
        });
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void livingFall(LivingFallEvent event) {
        event.getEntity().getCapability(PlayerDataProvider.DATA).ifPresent(data -> {
            data.getPlayerCustomization().getRace().livingFall(event);
        });
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
                if (!event.getEntity().level().isClientSide) {
                    if (!data.getPlayerCustomization().isFinished())
                        PacketHandler.INSTANCE.sendTo(new OpenCustomizationScreen(), ((ServerPlayer) event.getEntity()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                    else {
                        data.getPlayerCustomization().getRace().applyAttributes((Player) event.getEntity());
                        data.sync();
                    }
                }
            });
        }
    }

    private void onStartTracking(PlayerEvent.StartTracking event) {
        if (!event.getEntity().level().isClientSide)
            event.getEntity().getCapability(PlayerDataProvider.DATA).ifPresent(cap -> {
                cap.sync();
            });
    }


    private void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide)
            event.getEntity().getCapability(PlayerDataProvider.DATA).ifPresent(cap -> {
                cap.sync();
            });
    }

    private void attachLevelCapabilities(AttachCapabilitiesEvent<Level> event) {
        event.addCapability(new ResourceLocation(MOD_ID, "magic"), new MagicProvider(event.getObject()));
    }

    private void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(MOD_ID, "player_data"), new PlayerDataProvider((Player) event.getObject()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.GOBLIN.get(), GoblinRenderer::new);
        EntityRenderers.register(ModEntities.LOPUS.get(), LopusRenderer::new);
        EntityRenderers.register(ModEntities.MIMIC.get(), MimicRenderer::new);
        EntityRenderers.register(ModEntities.FIREBALL.get(), FireballRenderer::new);
        EntityRenderers.register(ModEntities.LIGHT_SOURCE.get(), LightSourceSpellRenderer::new);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GOBLIN.get(), Goblin.createAttributes());
        event.put(ModEntities.LOPUS.get(), Lopus.createAttributes());
        event.put(ModEntities.MIMIC.get(), Mimic.createAttributes());
    }

    @OnlyIn(Dist.CLIENT)
    private void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.DODGE);
    }

    @OnlyIn(Dist.CLIENT)
    private void keyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().player != null) {
            if (event.getKey() == KeyMappings.DODGE.getKey().getValue() && event.getAction() == GLFW.GLFW_PRESS && Minecraft.getInstance().player.onGround()) {
                int angle = 0;
                String animation = "dodge_forward";
                if (Minecraft.getInstance().player.input.left) {
                    angle = 90;
                    animation = "dodge_left";
                }
                if (Minecraft.getInstance().player.input.right) {
                    angle = -90;
                    animation = "dodge_right";
                }
                if (Minecraft.getInstance().player.input.down) {
                    angle = 180;
                    animation = "dodge_back";
                }
                PacketHandler.INSTANCE.sendToServer(new ApplyAnimation(animation));
                Minecraft.getInstance().player.setDeltaMovement(new Vec3(0, 0.2, 1).yRot((float) Math.toRadians(angle - Minecraft.getInstance().player.yHeadRot)));
            }
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
    private void addLayers(EntityRenderersEvent.AddLayers event) {
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().forEach((s, renderer) -> {
            ((PlayerRenderer) renderer).addLayer(new CustomizationLayer((PlayerRenderer) renderer));
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MALE_HUMAN, () -> MaleHumanModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_HUMAN, () -> MaleHumanModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_HUMAN, () -> FemaleHumanModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_HUMAN, () -> FemaleHumanModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.MALE_ELF, () -> MaleElfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_ELF, () -> MaleElfModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_ELF, () -> FemaleElfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_ELF, () -> FemaleElfModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.MALE_MERFLOK, () -> MaleMerfolkModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_MERFLOK, () -> MaleMerfolkModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_MERFLOK, () -> FemaleMerfolkModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_MERFLOK, () -> FemaleMerfolkModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.MALE_ORC, () -> MaleOrcModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_ORC, () -> MaleOrcModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_ORC, () -> FemaleOrcModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_ORC, () -> FemaleOrcModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.MALE_DWARF, () -> MaleDwarfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_DWARF, () -> MaleDwarfModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_DWARF, () -> FemaleDwarfModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_DWARF, () -> FemaleDwarfModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.AUTOMATON, () -> AutomatonModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_AUTOMATON, () -> AutomatonModel.createBodyLayer(new CubeDeformation(0.1F)));

        event.registerLayerDefinition(ModModelLayers.MALE_TIEFLING, () -> MaleTieflingModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_MALE_TIEFLING, () -> MaleTieflingModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.FEMALE_TIEFLING, () -> FemaleTieflingModel.createBodyLayer(new CubeDeformation(0)));
        event.registerLayerDefinition(ModModelLayers.CLOTH_FEMALE_TIEFLING, () -> FemaleTieflingModel.createBodyLayer(new CubeDeformation(0.1F)));
        event.registerLayerDefinition(ModModelLayers.BIG_HORNS, () -> BigHornsModel.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.MEDIUM_HORNS, () -> MediumHornsModel.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.TALL_HORNS, () -> TallHornsModel.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.TIEFLING_TAIL_1, () -> TieflingTail1Model.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.TIEFLING_TAIL_2, () -> TieflingTail2Model.createBodyLayer());
        event.registerLayerDefinition(ModModelLayers.MERFOLK_TAIL_1, () -> MerfolkTail1Model.createBodyLayer());

        event.registerLayerDefinition(ModModelLayers.GOBLIN, GoblinModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LOPUS, LopusModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MIMIC, MimicModel::createBodyLayer);
    }

    @OnlyIn(Dist.CLIENT)
    private void registerModel(final ModelEvent.RegisterAdditional event) {
        event.register(new ResourceLocation(FantasyBeyond.MOD_ID, "block/book"));
    }

    @OnlyIn(Dist.CLIENT)
    private void renderLevelLast(final RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            Vec3 view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            poseStack.translate(-view.x(), -view.y(), -view.z());
            Minecraft.getInstance().level.getCapability(MagicProvider.MAGIC).ifPresent(magic -> {
                magic.render(poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), Minecraft.getInstance().level, event.getPartialTick());
            });
            poseStack.popPose();
            ClientUtil.renderFirstPersonModel(event);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderHUD(RenderGuiOverlayEvent.Pre event) {
        if (!Minecraft.getInstance().player.isCreative() && !Minecraft.getInstance().player.isSpectator()) {
            NamedGuiOverlay type = event.getOverlay();
            switch (type.id().getPath()) {
                case "player_health":
                    renderHealth(event);
//                case "crosshair":
                case "boss_event_progress":
                case "armor_level":
                case "experience_bar":
                case "food_level":
                case "air_level":
                case "potion_icons":
                    event.setCanceled(true);
                    break;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void renderHealth(RenderGuiOverlayEvent.Pre event) {

        Minecraft.getInstance().player.getCapability(PlayerDataProvider.DATA).ifPresent(playerData -> {
            GuiGraphics graphics = event.getGuiGraphics();
            Player player = Minecraft.getInstance().player;

            int offset = 10;
            double factor = Minecraft.getInstance().getWindow().getGuiScale();

            graphics.blit(new ResourceLocation(MOD_ID, "textures/screen/hud/health_indicator.png"), offset - 8, 5, 0, 0, 140, 60, 140, 60);

            RenderSystem.enableScissor((int) ((offset + 10) * factor), (int) (Minecraft.getInstance().getWindow().getHeight() - 60 * factor), (int) (factor * (49)), (int) (49 * factor));
            InventoryScreen.renderEntityInInventory(graphics, offset + 35, 60, playerData.getPlayerCustomization().getRace().getHUDViewerScale(), new Quaternionf().rotateX((float) Math.PI).rotateY((float) Math.toRadians(player.yBodyRot)), new Quaternionf().rotationX((float) 0), Minecraft.getInstance().player);
            RenderSystem.disableScissor();

            graphics.fillGradient(offset + 70, 11, offset + (int) (70 + (56 * Minecraft.getInstance().player.getHealth() / Minecraft.getInstance().player.getMaxHealth())), 16, new Color(250,100,100).hashCode(), Color.RED.hashCode());

            graphics.fillGradient(offset + 70, 19, offset + (int) (70 + (56 * Minecraft.getInstance().player.getArmorCoverPercentage())), 24, Color.LIGHT_GRAY.hashCode(), Color.GRAY.hashCode());

            graphics.fillGradient(offset + 70, 27, offset + 70 + (56 * Minecraft.getInstance().player.getFoodData().getFoodLevel() / 20), 32, Color.GREEN.hashCode(), new Color(0,200,0).hashCode());

            graphics.fillGradient(offset + 70, 35, offset + 70 + Mth.clamp((56 * Minecraft.getInstance().player.getAirSupply() / Minecraft.getInstance().player.getMaxAirSupply()), 0, 56), 40, new Color(100,100,250).hashCode(), Color.BLUE.hashCode());

            graphics.fillGradient(offset, (int) (61 - (50 * Minecraft.getInstance().player.experienceProgress)), offset + 7, 60, new Color(0, 150, 10).hashCode(), new Color(0, 200, 10).hashCode());

            Component text = Component.literal(String.valueOf(Minecraft.getInstance().player.experienceLevel));
            graphics.drawString(Minecraft.getInstance().font, Component.translatable(MOD_ID+".hud.level"), offset + 63, 42, new Color(0, 150, 10).hashCode());
            graphics.drawString(Minecraft.getInstance().font, text, offset + 63, 52, new Color(0, 150, 10).hashCode());

            graphics.pose().pushPose();
            graphics.pose().scale(0.5F, 0.5F, 0.5F);

            text = Component.literal(round(Minecraft.getInstance().player.getHealth(), 2) + " / " + Minecraft.getInstance().player.getMaxHealth());

            graphics.drawString(Minecraft.getInstance().font, text, (offset + 96) * 2 - (Minecraft.getInstance().font.width(text) / 2), 24, Color.WHITE.hashCode(), false);
            text = Component.literal(String.valueOf(Minecraft.getInstance().player.getArmorCoverPercentage()));
            graphics.drawString(Minecraft.getInstance().font, text, (offset + 96) * 2 - (Minecraft.getInstance().font.width(text) / 2), 40, Color.WHITE.hashCode(), false);
            text = Component.literal(Minecraft.getInstance().player.getFoodData().getFoodLevel() + "/ 20");
            graphics.drawString(Minecraft.getInstance().font, text, (int) ((offset + 93.5) * 2 - (Minecraft.getInstance().font.width(text) / 2)), 56, Color.WHITE.hashCode(), false);
            text = Component.literal(Minecraft.getInstance().player.getAirSupply() + " / " + Minecraft.getInstance().player.getMaxAirSupply());
            graphics.drawString(Minecraft.getInstance().font, text, (int) ((offset + 93.5) * 2 - (Minecraft.getInstance().font.width(text) / 2)), 72, Color.WHITE.hashCode(), false);
            graphics.pose().popPose();
        });
    }
    public float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @OnlyIn(Dist.CLIENT)
    private void scrollMouse(InputEvent.MouseScrollingEvent event) {
        if (Minecraft.getInstance().player.getOffhandItem().getItem() instanceof SpellsBook && Minecraft.getInstance().player.isCrouching()) {
            Minecraft.getInstance().player.getOffhandItem().getCapability(SpellsBookProvider.SPELLS_BOOK).ifPresent(omnitrix -> {
                PacketHandler.INSTANCE.sendToServer(new ScrollSpell(event.getScrollDelta() > 0 ? 1 : -1));
                event.setCanceled(true);
            });
        }
    }
}
