package grillo78.fantasy_beyond.attachment;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.CharacterData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FantasyBeyond.MOD_ID);

    public static final Supplier<AttachmentType<CharacterData>> CHARACTER_DATA = ATTACHMENT_TYPES.register(
            "character_data", () -> AttachmentType.serializable(() -> new CharacterData()).build()
    );
    public static final Supplier<AttachmentType<List<Pair<String, Float>>>> DAMAGE_MANAGER = ATTACHMENT_TYPES.register(
            "damage_manager", () -> AttachmentType.builder(()-> Arrays.<Pair<String, Float>>asList()).serialize(Codec.compoundList(Codec.STRING, Codec.FLOAT)).build()
    );
}
