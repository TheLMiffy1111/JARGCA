package thelm.jargca.compat.thermalexpansion;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cofh.thermalfoundation.item.ItemFertilizer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.config.IDynamicSpecConfig;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;
import thelm.jargca.compat.mysticalagriculture.MysticalAgricultureModule;

@JAOPCAModule(modDependencies = {"thermalexpansion", "mysticalagriculture"})
public class ThermalExpansionMysticalAgricultureModule implements IModule {

	private static final Set<String> BLACKLIST = MysticalAgricultureModule.BLACKLIST;

	private static Set<String> configBasicBlacklist = new TreeSet<>();
	private static Set<String> configRichBlacklist = new TreeSet<>();
	private static Set<String> configFluxBlacklist = new TreeSet<>();

	private Map<IMaterial, IDynamicSpecConfig> configs;

	@Override
	public String getName() {
		return "thermalexpansion_mysticalagriculture";
	}

	@Override
	public Set<MaterialType> getMaterialTypes() {
		return EnumSet.allOf(MaterialType.class);
	}

	@Override
	public void defineModuleConfig(IModuleData moduleData, IDynamicSpecConfig config) {
		IMiscHelper helper = MiscHelper.INSTANCE;
		helper.caclulateMaterialSet(
				config.getDefinedStringList("recipes.basicMaterialBlacklist", new ArrayList<>(),
						helper.configMaterialPredicate(), "The materials that should not have insolator Phyto-Gro recipes added."),
				configBasicBlacklist);
		helper.caclulateMaterialSet(
				config.getDefinedStringList("recipes.richMaterialBlacklist", new ArrayList<>(),
						helper.configMaterialPredicate(), "The materials that should not have insolator Rich Phyto-Gro recipes added."),
				configRichBlacklist);
		helper.caclulateMaterialSet(
				config.getDefinedStringList("recipes.fluxMaterialBlacklist", new ArrayList<>(),
						helper.configMaterialPredicate(), "The materials that should not have insolator Fluxed Phyto-Gro recipes added."),
				configFluxBlacklist);
	}

	@Override
	public void defineMaterialConfig(IModuleData moduleData, Map<IMaterial, IDynamicSpecConfig> configs) {
		this.configs = configs;
	}

	@Override
	public void onPostInit(IModuleData moduleData, FMLPostInitializationEvent event) {
		JAOPCAApi api = ApiImpl.INSTANCE;
		ThermalExpansionHelper helper = ThermalExpansionHelper.INSTANCE;
		IMiscHelper miscHelper = MiscHelper.INSTANCE;
		Set<String> oredict = api.getOredict();
		int[] defaultChances = helper.getDefaultMysticalAgricultureChances();
		ItemStack fertilizerBasic = ItemFertilizer.fertilizerBasic;
		ItemStack fertilizerRich = ItemFertilizer.fertilizerRich;
		ItemStack fertilizerFlux = ItemFertilizer.fertilizerFlux;
		for(IMaterial material : moduleData.getMaterials()) {
			String name = material.getName();
			String seedsOredict = miscHelper.getOredictName("mysticalSeeds", name);
			String essenceOredict = miscHelper.getOredictName("essence", name);
			if(!BLACKLIST.contains(name) &&
					oredict.contains(seedsOredict) && oredict.contains(essenceOredict) &&
					MysticalAgricultureModule.tierFunction.applyAsInt(material) < 6) {
				IDynamicSpecConfig config = configs.get(material);
				if(!configBasicBlacklist.contains(name)) {
					int configChance = config.getDefinedInt("thermalexpansion_mysticalagriculture.basicChance", defaultChances[0],
							"The base seed output chance when growing Mystical Agriculture seeds in Thermal Expansion's Insolator with Phyto-Gro.");
					helper.registerInsolatorRecipe(
							miscHelper.getRecipeKey("thermalexpansion_mysticalagriculture.seed_to_essence_basic", name),
							seedsOredict, 1, fertilizerBasic, 1,
							essenceOredict, 1, seedsOredict, 1, configChance,
							9600, 2400);
				}
				if(!configRichBlacklist.contains(name)) {
					int configChance = config.getDefinedInt("thermalexpansion_mysticalagriculture.richChance", defaultChances[1],
							"The base seed output chance when growing Mystical Agriculture seeds in Thermal Expansion's Insolator with Rich Phyto-Gro.");
					helper.registerInsolatorRecipe(
							miscHelper.getRecipeKey("thermalexpansion_mysticalagriculture.seed_to_essence_rich", name),
							seedsOredict, 1, fertilizerRich, 1,
							essenceOredict, 2, seedsOredict, 1, configChance,
							14400, 3600);
				}
				if(!configFluxBlacklist.contains(name)) {
					int configChance = config.getDefinedInt("thermalexpansion_mysticalagriculture.fluxChance", defaultChances[2],
							"The base seed output chance when growing Mystical Agriculture seeds in Thermal Expansion's Insolator with Fluxed Phyto-Gro.");
					helper.registerInsolatorRecipe(
							miscHelper.getRecipeKey("thermalexpansion_mysticalagriculture.seed_to_essence_flux", name),
							seedsOredict, 1, fertilizerFlux, 1,
							essenceOredict, 3, seedsOredict, 1, configChance,
							19200, 4800);
				}
			}
		}
	}
}
