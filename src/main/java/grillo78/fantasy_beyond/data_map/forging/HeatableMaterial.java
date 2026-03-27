package grillo78.fantasy_beyond.data_map.forging;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public class HeatableMaterial {

    public static final Codec<HeatableMaterial> CODEC = RecordCodecBuilder.create(instance -> instance.group(
           Codec.list(HeatRange.CODEC).fieldOf("ranges").forGetter(HeatableMaterial::getRanges),
            Codec.INT.fieldOf("workableAt").forGetter(HeatableMaterial::getWorkableAt),
            Codec.INT.fieldOf("breakAt").forGetter(HeatableMaterial::getBreakAt),
            Codec.FLOAT.fieldOf("heatingSpeed").forGetter(HeatableMaterial::getHeatingSpeed)
    ).apply(instance, HeatableMaterial::new));

    private List<HeatRange> ranges;
    private int workableAt;
    private int breakAt;
    private float heatingSpeed;

    public HeatableMaterial(List<HeatRange> ranges, int workableAt, int breakAt, float heatingSpeed) {
        this.ranges = ranges;
        this.workableAt = workableAt;
        this.breakAt = breakAt;
        this.heatingSpeed = heatingSpeed;
    }

    public float getHeatingSpeed() {
        return heatingSpeed;
    }

    public int getBreakAt() {
        return breakAt;
    }

    public int getWorkableAt() {
        return workableAt;
    }

    public List<HeatRange> getRanges() {
        return ranges;
    }
}
