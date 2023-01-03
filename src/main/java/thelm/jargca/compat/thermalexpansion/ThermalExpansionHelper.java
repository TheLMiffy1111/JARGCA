package thelm.jargca.compat.thermalexpansion;

import java.lang.reflect.Field;

import cofh.thermalexpansion.init.TEPlugins;
import cofh.thermalexpansion.plugins.PluginMysticalAgriculture;
import net.minecraft.util.ResourceLocation;
import thelm.jaopca.utils.ApiImpl;
import thelm.jargca.compat.thermalexpansion.recipes.InsolatorRecipeAction;

public class ThermalExpansionHelper {

	public static final ThermalExpansionHelper INSTANCE = new ThermalExpansionHelper();

	private ThermalExpansionHelper() {}

	public int[] getDefaultMysticalAgricultureChances() {
		int chanceBase = 100;
		int chanceRich = 110;
		int chanceFlux = 115;
		Field pluginField;
		try {
			pluginField = TEPlugins.class.getDeclaredField("pluginMysticalAgriculture");
			pluginField.setAccessible(true);
			PluginMysticalAgriculture plugin = (PluginMysticalAgriculture)pluginField.get(null);
			chanceBase = plugin.secondaryChanceBase;
			chanceRich = plugin.secondaryChanceRich;
			chanceFlux = plugin.secondaryChanceFlux;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new int[] {chanceBase, chanceRich, chanceFlux};
	}

	public boolean registerInsolatorRecipe(ResourceLocation key, Object input, int inputCount, Object secondInput, int secondInputCount, Object output, int outputCount, Object secondOutput, int secondOutputCount, int secondOutputChance, int energy, int water, boolean isTree) {
		return ApiImpl.INSTANCE.registerLateRecipe(key, new InsolatorRecipeAction(key, input, inputCount, secondInput, secondInputCount, output, outputCount, secondOutput, secondOutputCount, secondOutputChance, energy, water, isTree));
	}

	public boolean registerInsolatorRecipe(ResourceLocation key, Object input, int inputCount, Object secondInput, int secondInputCount, Object output, int outputCount, Object secondOutput, int secondOutputCount, int secondOutputChance, int energy, int water) {
		return ApiImpl.INSTANCE.registerLateRecipe(key, new InsolatorRecipeAction(key, input, inputCount, secondInput, secondInputCount, output, outputCount, secondOutput, secondOutputCount, secondOutputChance, energy, water));
	}
}
