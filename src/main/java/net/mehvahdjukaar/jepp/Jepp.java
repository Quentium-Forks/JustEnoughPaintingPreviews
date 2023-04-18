package net.mehvahdjukaar.jepp;

import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Jepp.MOD_ID)
@JeiPlugin
public class Jepp implements IModPlugin {
    public static final String MOD_ID = "jepp";

    private static final ResourceLocation ID = res("jei_plugin");

    public static ResourceLocation res(String name) {
        return new ResourceLocation("jepp", name);
    }

    public Jepp() {
    }

    public ResourceLocation getPluginUid() {
        return ID;
    }

    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new IRecipeCategory[] { new PaintingRecipeCategory(registry.getJeiHelpers().getGuiHelper()) });
    }

    public void registerRecipes(IRecipeRegistration registry) {
        for (PaintingVariant painting : ForgeRegistries.PAINTING_VARIANTS) {
            PaintingInfoRecipe recipe = new PaintingInfoRecipe(painting);
            registry.addRecipes(PaintingRecipeCategory.TYPE, List.of(recipe));
        }
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack((ItemLike) Items.PAINTING), new RecipeType[] { PaintingRecipeCategory.TYPE });
    }
}
