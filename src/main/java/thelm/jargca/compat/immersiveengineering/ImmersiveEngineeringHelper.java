package thelm.jargca.compat.immersiveengineering;

import net.minecraft.util.ResourceLocation;
import thelm.jaopca.utils.ApiImpl;
import thelm.jargca.compat.immersiveengineering.recipes.BelljarCropRecipeAction;

public class ImmersiveEngineeringHelper {

	public static final ImmersiveEngineeringHelper INSTANCE = new ImmersiveEngineeringHelper();

	private ImmersiveEngineeringHelper() {}

	public boolean registerBelljarCropRecipe(ResourceLocation key, Object seed, Object soil, Object[] output, Object[] render) {
		return ApiImpl.INSTANCE.registerLateRecipe(key, new BelljarCropRecipeAction(key, seed, soil, output, render));
	}
}
