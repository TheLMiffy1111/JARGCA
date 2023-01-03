package thelm.jargca.compat.immersiveengineering;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.init.Blocks;
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

@JAOPCAModule(modDependencies = {"immersiveengineering", "mysticalagriculture"})
public class ImmersiveEngineeringMysticalAgricultureModule implements IModule {

	private static final Set<String> BLACKLIST = MysticalAgricultureModule.BLACKLIST;

	private static Set<String> configBelljarBlacklist = new TreeSet<>();

	@Override
	public String getName() {
		return "immersiveengineering_mysticalagriculture";
	}

	@Override
	public Set<MaterialType> getMaterialTypes() {
		return EnumSet.allOf(MaterialType.class);
	}

	@Override
	public void defineModuleConfig(IModuleData moduleData, IDynamicSpecConfig config) {
		IMiscHelper helper = MiscHelper.INSTANCE;
		helper.caclulateMaterialSet(
				config.getDefinedStringList("recipes.belljarMaterialBlacklist", new ArrayList<>(),
						helper.configMaterialPredicate(), "The materials that should not have belljar recipes added."),
				configBelljarBlacklist);
	}

	@Override
	public void onPostInit(IModuleData moduleData, FMLPostInitializationEvent event) {
		JAOPCAApi api = ApiImpl.INSTANCE;
		ImmersiveEngineeringHelper helper = ImmersiveEngineeringHelper.INSTANCE;
		IMiscHelper miscHelper = MiscHelper.INSTANCE;
		Set<String> oredict = api.getOredict();
		for(IMaterial material : moduleData.getMaterials()) {
			String name = material.getName();
			String seedsOredict = miscHelper.getOredictName("mysticalSeeds", name);
			String essenceOredict = miscHelper.getOredictName("essence", name);
			String cropOredict = miscHelper.getOredictName("mysticalCrop", name);
			if(!BLACKLIST.contains(name) && !configBelljarBlacklist.contains(name) &&
					oredict.contains(seedsOredict) && oredict.contains(essenceOredict) &&
					MysticalAgricultureModule.tierFunction.applyAsInt(material) < 6) {
				helper.registerBelljarCropRecipe(
						miscHelper.getRecipeKey("immersiveengineering_mysticalagriculture.seed_to_essence", name),
						seedsOredict, Blocks.DIRT, new Object[] {
								essenceOredict, 1,
						}, new Object[] {
								cropOredict,
						});
			}
		}
	}
}
