package grillo78.fantasy_beyond.items;

import grillo78.fantasy_beyond.capabilities.spells_book.SpellsBookProvider;
import grillo78.fantasy_beyond.client.item.SpellsBookRenderer;
import grillo78.fantasy_beyond.magic.spells.SpellType;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SpellsBook extends Item {

    private List<RegistryObject<SpellType>> spellTypes;

    public SpellsBook(Properties pProperties, List<RegistryObject<SpellType>> spellTypes) {
        super(pProperties);
        this.spellTypes = spellTypes;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new SpellsBookProvider(stack, spellTypes);
    }

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {

        CompoundTag baseTag = stack.getTag();

        CompoundTag combinedTag = new CompoundTag();

        if (baseTag != null) {
            combinedTag.put("base_tag", baseTag);
        }

        stack.getCapability(SpellsBookProvider.SPELLS_BOOK).ifPresent(spellsBook -> {
            combinedTag.put("capability_tag", spellsBook.serializeNBT());
        });
        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @javax.annotation.Nullable CompoundTag nbt) {
        if (nbt != null) {
            if(nbt.contains("base_tag")) {
                CompoundTag baseTag = nbt.getCompound("base_tag");
                stack.setTag(baseTag);
            }
            if(nbt.contains("capability_tag")){
                CompoundTag capabilityTag = nbt.getCompound("capability_tag");
                stack.getCapability(SpellsBookProvider.SPELLS_BOOK).ifPresent(spellsBook -> spellsBook.deserializeNBT(capabilityTag));
            }
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private SpellsBookRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new SpellsBookRenderer();

                return this.renderer;
            }
        });
    }
}
