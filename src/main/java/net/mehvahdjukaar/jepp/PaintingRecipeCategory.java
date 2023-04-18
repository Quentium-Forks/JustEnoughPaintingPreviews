package net.mehvahdjukaar.jepp;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class PaintingRecipeCategory implements IRecipeCategory<PaintingInfoRecipe> {
    public static final RecipeType<PaintingInfoRecipe> TYPE = RecipeType.create("jepp", "effects", PaintingInfoRecipe.class);

    public static final int recipeWidth = 160;

    public static final int recipeHeight = 126;

    private final IDrawable background;

    private final IDrawable icon;

    private final Component localizedName;

    public PaintingRecipeCategory(IGuiHelper guiHelper) {
        this.background = (IDrawable) guiHelper.createBlankDrawable(160, 125);

        this.localizedName = (Component) Component.translatable("jepp.category.paintings_info");

        this.icon = guiHelper.createDrawableIngredient((IIngredientType) VanillaTypes.ITEM_STACK, new ItemStack((ItemLike) Items.PAINTING));
    }

    public RecipeType<PaintingInfoRecipe> getRecipeType() {
        return TYPE;
    }

    public Component getTitle() {
        return this.localizedName;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public void draw(PaintingInfoRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        int yPos = 117;

        Font font = (Minecraft.getInstance()).font;

        MutableComponent name = recipe.getName();
        name.setStyle(Style.EMPTY.withBold(Boolean.valueOf(true)));
        float centerX = 80.0F - font.width((FormattedText) name) / 2.0F;
        font.draw(matrixStack, Language.getInstance().getVisualOrder((FormattedText) name), centerX, 0.0F, -16777216);

        FormattedText descriptionLine = recipe.getDescription();
        centerX = 80.0F - font.width(descriptionLine) / 2.0F;
        font.draw(matrixStack, Language.getInstance().getVisualOrder(descriptionLine), centerX, yPos, -16777216);

        PaintingVariant motive = recipe.getMotive();

        float spacing = 12.0F;
        float maxWidth = 160.0F;
        float maxHeight = 125.0F - spacing - 12.0F;

        matrixStack.translate((maxWidth / 2.0F), (spacing + maxHeight / 2.0F), 0.0D);

        int pWidth = motive.getWidth();
        int pHeight = motive.getHeight();

        float ratio = pHeight / pWidth;
        float screenRatio = maxHeight / maxWidth;

        float scale = (ratio < screenRatio) ? (maxWidth / pWidth) : (maxHeight / pHeight);

        matrixStack.scale(scale, scale, scale);

        ResourceLocation texture = Minecraft.getInstance().getPaintingTextures().getBackSprite().atlasLocation();
        PaintingTextureManager paintingtexturemanager = Minecraft.getInstance().getPaintingTextures();
        TextureAtlasSprite sprite = paintingtexturemanager.get(motive);

        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);

        matrixStack.translate((-pWidth / 2.0F), (-pHeight / 2.0F), 0.0D);
        GuiComponent.blit(matrixStack, 0, 0, 0, pWidth, pHeight, sprite);

        RenderSystem.applyModelViewMatrix();
    }

    public void setRecipe(IRecipeLayoutBuilder builder, PaintingInfoRecipe paintingInfoRecipe, IFocusGroup iFocusGroup) {
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(new ItemStack((ItemLike) Items.PAINTING));
    }
}
