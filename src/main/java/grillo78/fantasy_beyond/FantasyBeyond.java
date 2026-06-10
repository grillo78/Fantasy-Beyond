package grillo78.fantasy_beyond;

import com.mojang.datafixers.util.Pair;
import grillo78.fantasy_beyond.attachment.ModAttachments;
import grillo78.fantasy_beyond.block_entities.AnvilBlockEntity;
import grillo78.fantasy_beyond.block_entities.ModBlockEntities;
import grillo78.fantasy_beyond.blocks.ModBlocks;
import grillo78.fantasy_beyond.character.CharacterData;
import grillo78.fantasy_beyond.character.classes.PlayerClassType;
import grillo78.fantasy_beyond.character.customization.race.RaceType;
import grillo78.fantasy_beyond.data_map.ModDataMaps;
import grillo78.fantasy_beyond.entities.DemonLord;
import grillo78.fantasy_beyond.entities.Goblin;
import grillo78.fantasy_beyond.entities.ModEntities;
import grillo78.fantasy_beyond.items.ModItems;
import grillo78.fantasy_beyond.items.QuiverItem;
import grillo78.fantasy_beyond.items.components.ArrowItemCodec;
import grillo78.fantasy_beyond.items.components.ModDataComponents;
import grillo78.fantasy_beyond.items.components.QuiverContents;
import grillo78.fantasy_beyond.magic.spell.SpellType;
import grillo78.fantasy_beyond.network.*;
import grillo78.fantasy_beyond.recipes.ModRecipes;
import grillo78.fantasy_beyond.recipes.serializers.ModRecipeSerializers;
import grillo78.fantasy_beyond.worldgen.processors.ModProcessors;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mod(FantasyBeyond.MOD_ID)
public class FantasyBeyond {
    public static final String MOD_ID = "fantasy_beyond";
    private static final GameRules.Key<GameRules.BooleanValue> KEEP_CHARACTER = GameRules.register("keepCharacter", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));

    public FantasyBeyond(IEventBus modEventBus, ModContainer modContainer) {
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);
        RaceType.RACE_TYPES.register(modEventBus);
        SpellType.SPELL_TYPES.register(modEventBus);
        PlayerClassType.PLAYER_CLASS_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModRecipes.RECIPE_TYPES.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        ModProcessors.PROCESSORS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener(this::registerPackets);
        modEventBus.addListener(this::registerDataMapTypes);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::createAttributes);
//        NeoForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);
//        NeoForge.EVENT_BUS.addListener(this::onStartTracking);
        NeoForge.EVENT_BUS.addListener(this::livingFall);
        NeoForge.EVENT_BUS.addListener(this::entityJoin);
        NeoForge.EVENT_BUS.addListener(this::resizePlayer);
        NeoForge.EVENT_BUS.addListener(this::playerTick);
        NeoForge.EVENT_BUS.addListener(this::levelTick);
        NeoForge.EVENT_BUS.addListener(this::onPlayerClone);
        NeoForge.EVENT_BUS.addListener(this::onHurt);
        NeoForge.EVENT_BUS.addListener(this::canBreath);
        NeoForge.EVENT_BUS.addListener(this::getProjectile);
        NeoForge.EVENT_BUS.addListener(this::shrinkArrows);
        NeoForge.EVENT_BUS.addListener(this::onDead);
        NeoForge.EVENT_BUS.addListener(this::onLeftClickInteract);
        NeoForge.EVENT_BUS.addListener(this::onBlockInteract);
        if(ModList.get().isLoaded("millenaire"))
        try{
            Path origin = modContainer.getModInfo().getOwningFile().getFile().findResource("millenaire-pack/" + MOD_ID);
            Path target = Path.of("millenaire-custom/" + MOD_ID).toAbsolutePath();
            Files.walkFileTree(origin, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // Calcula la ruta equivalente en el destino
                    Path relative = origin.relativize(dir);
                    Path targetDir = target.resolve(relative.toString()); // .toString() para cruzar FileSystem
                    Files.createDirectories(targetDir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path relative = origin.relativize(file);
                    Path targetFile = target.resolve(relative.toString());
                    Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DEMON_LORD.get(), DemonLord.createAttributes().build());
        event.put(ModEntities.GOBLIN.get(), Goblin.createAttributes().build());
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, // capability to register for
                ModBlockEntities.BUCKET.get(),
                (be, side) -> be.getFluidHandler()
        );
    }

    public void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(ModDataMaps.HEATABLE_MATERIALS);
    }

    public void onDead(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide) {
            List<Pair<String, Float>> damageManager = event.getEntity().getData(ModAttachments.DAMAGE_MANAGER);
            for (int i = 0; i < damageManager.size(); i++) {
                Pair<String, Float> damage = damageManager.get(i);
                Player player = event.getEntity().level().getPlayerByUUID(UUID.fromString(damage.getFirst()));
                if (player != null) {
                    CharacterData data = player.getData(ModAttachments.CHARACTER_DATA);
                    data.getLevel().increaseXP(player, damage.getSecond());
                    PacketDistributor.sendToAllPlayers(new SyncCharacterData(player.getId(), data.serializeNBT(null)));
                }
            }
        }
    }

    public void onLeftClickInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getAction() == PlayerInteractEvent.LeftClickBlock.Action.START && event.getHand() == InteractionHand.MAIN_HAND && event.getEntity().getMainHandItem().is(ModItems.FORGING_HAMMER.get()) && event.getLevel().getBlockEntity(event.getPos()) instanceof AnvilBlockEntity) {
            event.setCanceled(true);
            ((AnvilBlockEntity) event.getLevel().getBlockEntity(event.getPos())).hitWithHammer(event.getEntity());
        }
    }

    public void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getEntity().level().isClientSide && event.getEntity().isShiftKeyDown() && event.getEntity().getItemInHand(event.getHand()).getItem() == Items.BUCKET) {
            switch (event.getFace()) {
                case UP:
                    event.getLevel().setBlock(event.getPos().above(), ModBlocks.IRON_BUCKET.get().defaultBlockState(), 3);
                    break;
                case DOWN:
                    event.getLevel().setBlock(event.getPos().below(), ModBlocks.IRON_BUCKET.get().defaultBlockState(), 3);
                    break;
                case NORTH:
                    event.getLevel().setBlock(event.getPos().north(), ModBlocks.IRON_BUCKET.get().defaultBlockState(), 3);
                    break;
                case SOUTH:
                    event.getLevel().setBlock(event.getPos().south(), ModBlocks.IRON_BUCKET.get().defaultBlockState(), 3);
                    break;
                case EAST:
                    event.getLevel().setBlock(event.getPos().east(), ModBlocks.IRON_BUCKET.get().defaultBlockState(), 3);
                    break;
                case WEST:
                    event.getLevel().setBlock(event.getPos().west(), ModBlocks.IRON_BUCKET.get().defaultBlockState(), 3);
                    break;
            }
            event.getLevel().playSound(null, event.getPos(), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1, 1);
            if (!event.getEntity().isCreative())
                event.getEntity().getItemInHand(event.getHand()).shrink(1);
        }
    }

    public void getProjectile(LivingGetProjectileEvent event) {
        Optional<ICuriosItemHandler> inventory = CuriosApi.getCuriosInventory(event.getEntity());
        if (inventory.isPresent()) {
            List<SlotResult> slots = inventory.get().findCurios("back");
            boolean notObtained = true;
            for (int i = 0; i < slots.size() && notObtained; i++) {
                SlotResult slot = slots.get(i);
                ItemStack stack = slot.stack();
                if (stack.getItem() instanceof QuiverItem) {
                    notObtained = false;
                    QuiverContents quiverContents = stack.get(ModDataComponents.QUIVER_CONTENTS);
                    int index = quiverContents.getIndex();
                    if (index >= quiverContents.getItems().size())
                        index = quiverContents.getItems().size() - 1;
                    if (index < quiverContents.getItems().size())
                        event.setProjectileItemStack(quiverContents.getItems().get(index).getItems().getLast().copy());
                }
            }
        }
    }

    public void shrinkArrows(ArrowLooseEvent event) {
        List<SlotResult> slots = CuriosApi.getCuriosInventory(event.getEntity()).get().findCurios("back");
        boolean notObtained = true;
        for (int i = 0; i < slots.size() && notObtained; i++) {
            SlotResult slot = slots.get(i);
            ItemStack stack = slot.stack();
            if (stack.getItem() instanceof QuiverItem) {
                notObtained = false;
                QuiverContents quiverContents = stack.get(ModDataComponents.QUIVER_CONTENTS);
                int index = quiverContents.getIndex();
                List<ArrowItemCodec> list = new ArrayList<>(quiverContents.getItems());
                if (index >= list.size())
                    index = list.size() - 1;
                List<ItemStack> items = new ArrayList<>(list.get(index).getItems());
                ItemStack stack1 = items.getLast();
                stack1.shrink(1);
                if (stack.isEmpty())
                    items.removeLast();
                if (items.isEmpty())
                    list.remove(index);
                stack.set(ModDataComponents.QUIVER_CONTENTS, new QuiverContents(List.copyOf(list), index));
            }
        }
    }

    private void levelTick(final LevelTickEvent.Post event) {
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
                    PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new OpenCharacterCreationScreen());
                else {
                    data.getPlayerCustomization().getRace().applyAttributes((Player) event.getEntity());
                    event.getEntity().refreshDimensions();
                    PacketDistributor.sendToAllPlayers(new SyncCharacterData(event.getEntity().getId(), data.serializeNBT(null)));
                    data.applyStats((Player) event.getEntity());
                }
            }
        }
    }

    private void registerPackets(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MOD_ID);
        registrar.commonToClient(OpenCharacterCreationScreen.TYPE, OpenCharacterCreationScreen.STREAM_CODEC, FMLLoader.getDist().isClient() ? OpenCharacterCreationScreen::handle : OpenCharacterCreationScreen::handleServer);
        registrar.commonBidirectional(SyncCharacterData.TYPE, SyncCharacterData.STREAM_CODEC, SyncCharacterData::handle);
        registrar.commonToServer(IncreaseStat.TYPE, IncreaseStat.STREAM_CODEC, IncreaseStat::handle);
        registrar.commonToServer(UpdateQuiverIndex.TYPE, UpdateQuiverIndex.STREAM_CODEC, UpdateQuiverIndex::handle);
        registrar.commonToServer(UpdateItemContainerItem.TYPE, UpdateItemContainerItem.STREAM_CODEC, UpdateItemContainerItem::handle);
        registrar.commonToServer(ChangeQuiverIndex.TYPE, ChangeQuiverIndex.STREAM_CODEC, ChangeQuiverIndex::handle);
        registrar.commonToServer(ChangeAbilityIndex.TYPE, ChangeAbilityIndex.STREAM_CODEC, ChangeAbilityIndex::handle);
        registrar.commonToServer(ActivateAbility.TYPE, ActivateAbility.STREAM_CODEC, ActivateAbility::handle);
        registrar.commonToServer(UnlockAbility.TYPE, UnlockAbility.STREAM_CODEC, UnlockAbility::handle);
    }
}
