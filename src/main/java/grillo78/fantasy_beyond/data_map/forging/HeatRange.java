package grillo78.fantasy_beyond.data_map.forging;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.awt.*;

public class HeatRange {

    public static final Codec<HeatRange> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("minTemp").forGetter(HeatRange::getMinTemp),
            Codec.FLOAT.fieldOf("maxTemp").forGetter(HeatRange::getMaxTemp),
            Codec.FLOAT.fieldOf("startColorRed").forGetter(heatRange -> heatRange.getStartColor().getRed()/255F),
            Codec.FLOAT.fieldOf("startColorGreen").forGetter(heatRange -> heatRange.getStartColor().getGreen()/255F),
            Codec.FLOAT.fieldOf("startColorBlue").forGetter(heatRange -> heatRange.getStartColor().getBlue()/255F),
            Codec.FLOAT.fieldOf("endColorRed").forGetter(heatRange -> heatRange.getStartColor().getRed()/255F),
            Codec.FLOAT.fieldOf("endColorGreen").forGetter(heatRange -> heatRange.getStartColor().getGreen()/255F),
            Codec.FLOAT.fieldOf("endColorBlue").forGetter(heatRange -> heatRange.getStartColor().getBlue()/255F)
    ).apply(instance, HeatRange::new));
    private float minTemp;
    private float maxTemp;
    private Color startColor;
    private Color endColor;

    public HeatRange(float minTemp, float maxTemp, float startColorRed, float startColorGreen, float startColorBlue, float endColorRed, float endColorGreen, float endColorBlue) {
        this(minTemp, maxTemp, new Color(startColorRed, startColorGreen, startColorBlue),new Color(endColorRed, endColorGreen, endColorBlue));
    }

    public HeatRange(float minTemp, float maxTemp, Color startColor, Color endColor) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public Color getStartColor() {
        return startColor;
    }

    public Color getEndColor() {
        return endColor;
    }
}
