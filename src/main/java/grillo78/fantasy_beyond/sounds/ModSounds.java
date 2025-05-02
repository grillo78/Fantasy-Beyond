package grillo78.fantasy_beyond.sounds;

import grillo78.fantasy_beyond.FantasyBeyond;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FantasyBeyond.MOD_ID);

    public static final RegistryObject<SoundEvent> LOPUS_INVOCATION = register("lopus_invocation");
    public static final RegistryObject<SoundEvent> LOPUS_INVOCATION_SFX_1 = register("lopus_invocation_sfx_1");
    public static final RegistryObject<SoundEvent> LOPUS_INVOCATION_SFX_2 = register("lopus_invocation_sfx_2");

    public static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(FantasyBeyond.MOD_ID, name)));
    }
}

