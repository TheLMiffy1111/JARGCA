package thelm.jargca.compat.skyresources;

import net.minecraft.util.ResourceLocation;
import thelm.jaopca.utils.ApiImpl;
import thelm.jargca.compat.skyresources.recipes.CauldronCleanRecipeAction;
import thelm.jargca.compat.skyresources.recipes.CondenserRecipeAction;
import thelm.jargca.compat.skyresources.recipes.FusionRecipeAction;
import thelm.jargca.compat.skyresources.recipes.RockGrinderRecipeAction;

public class SkyResourcesHelper {

	public static final SkyResourcesHelper INSTANCE = new SkyResourcesHelper();

	private SkyResourcesHelper() {}

	public boolean registerFusionRecipe(ResourceLocation key, Object output, int count, float usage, Object... input) {
		return ApiImpl.INSTANCE.registerRecipe(key, new FusionRecipeAction(key, output, count, usage, input));
	}

	public boolean registerCondenserRecipe(ResourceLocation key, Object itemInput, Object blockInput, boolean isFluid, Object output, int count, float usage) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CondenserRecipeAction(key, itemInput, blockInput, isFluid, output, count, usage));
	}

	public boolean registerRockGrinderRecipe(ResourceLocation key, Object input, Object output, int count, float chance) {
		return ApiImpl.INSTANCE.registerRecipe(key, new RockGrinderRecipeAction(key, input, output, count, chance));
	}

	public boolean registerCauldronCleanRecipe(ResourceLocation key, Object input, Object output, int count, float chance) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CauldronCleanRecipeAction(key, input, output, count, chance));
	}
}
