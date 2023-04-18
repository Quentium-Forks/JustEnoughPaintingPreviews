package net.mehvahdjukaar.jepp;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

public class PaintingInfoRecipe {
    private final PaintingVariant motive;
    private final MutableComponent name;
    private final MutableComponent description;

    public PaintingInfoRecipe(PaintingVariant painting) {
        ResourceLocation r = ForgeRegistries.PAINTING_VARIANTS.getKey(painting);
        this.description = Component.translatable("jepp.painting.description", new Object[] {
                formatName(r.getNamespace()),
                Integer.valueOf(painting.getWidth()), Integer.valueOf(painting.getHeight())
        });
        String name = r.getPath();

        MutableComponent text = Component.translatable(name);
        if (text.getString().equals(name))
            text = formatName(name);

        this.name = text;
        this.motive = painting;
    }

    private MutableComponent formatName(String name) {
        name = name.replace("_", " ");
        name = StringUtils.capitalize(name);
        return Component.literal(name);
    }

    public FormattedText getDescription() {
        return (FormattedText) this.description;
    }

    public MutableComponent getName() {
        return this.name;
    }

    public PaintingVariant getMotive() {
        return this.motive;
    }
}
