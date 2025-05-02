package grillo78.fantasy_beyond.network.messages;

import grillo78.fantasy_beyond.capabilities.spells_book.SpellsBookProvider;
import grillo78.fantasy_beyond.items.SpellsBook;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScrollSpell implements IMessage<ScrollSpell> {

    private int direction;

    public ScrollSpell() {
    }

    public ScrollSpell(int direction) {
        this.direction = direction;
    }

    @Override
    public void encode(ScrollSpell message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.direction);
    }

    @Override
    public ScrollSpell decode(FriendlyByteBuf buffer) {
        return new ScrollSpell(buffer.readInt());
    }

    @Override
    public void handle(ScrollSpell message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(()->{
            if(supplier.get().getSender().getOffhandItem().getItem() instanceof SpellsBook)
                supplier.get().getSender().getOffhandItem().getCapability(SpellsBookProvider.SPELLS_BOOK).ifPresent(spellsBook->{
                    if(message.direction == 1)
                        spellsBook.increaseSpellIndex();
                    else
                        spellsBook.decreaseSpellIndex();
                });
        });
        supplier.get().setPacketHandled(true);
    }
}
