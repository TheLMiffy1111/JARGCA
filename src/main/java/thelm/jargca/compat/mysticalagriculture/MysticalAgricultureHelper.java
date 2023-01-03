package thelm.jargca.compat.mysticalagriculture;

import net.minecraft.util.ResourceLocation;
import thelm.jaopca.utils.ApiImpl;
import thelm.jargca.compat.mysticalagriculture.recipes.ReprocessorRecipeAction;

public class MysticalAgricultureHelper {

	public static final MysticalAgricultureHelper INSTANCE = new MysticalAgricultureHelper();

	private MysticalAgricultureHelper() {}

	public boolean registerReprocessorRecipe(ResourceLocation key, Object input, Object output, int outputCount) {
		return ApiImpl.INSTANCE.registerRecipe(key, new ReprocessorRecipeAction(key, input, output, outputCount));
	}
}
