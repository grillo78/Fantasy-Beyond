package grillo78.fantasy_beyond.attachment;

import grillo78.fantasy_beyond.FantasyBeyond;
import grillo78.fantasy_beyond.character.CharacterData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FantasyBeyond.MOD_ID);

    public static final Supplier<AttachmentType<CharacterData>> CHARACTER_DATA = ATTACHMENT_TYPES.register(
            "character_data", () -> AttachmentType.serializable(() -> new CharacterData()).build()
    );
}
