package thelm.jargca.compat.mysticalagriculture;

import java.util.EnumSet;
import java.util.Set;

import com.blakebr0.mysticalagradditions.compat.jei.Tier6CropWrapper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;
import thelm.jargca.compat.jei.JARGCAJEIPlugin;

@JAOPCAModule(modDependencies = {"mysticalagriculture", "jei"})
public class MysticalAgricultureJEIModule implements IModule {

	private static final Set<String> BLACKLIST = MysticalAgricultureModule.BLACKLIST;

	@Override
	public String getName() {
		return "mysticalagriculture_jei";
	}

	@Override
	public Set<MaterialType> getMaterialTypes() {
		return EnumSet.allOf(MaterialType.class);
	}

	@Override
	public void onPostInit(IModuleData moduleData, FMLPostInitializationEvent event) {
		JAOPCAApi api = ApiImpl.INSTANCE;
		IMiscHelper miscHelper = MiscHelper.INSTANCE;
		Set<String> oredict = api.getOredict();
		for(IMaterial material : moduleData.getMaterials()) {
			String name = material.getName();
			String seedsOredict = miscHelper.getOredictName("mysticalSeeds", name);
			String cropOredict = miscHelper.getOredictName("mysticalCrop", name);
			String cruxOredict = miscHelper.getOredictName("crux", name);
			String essenceOredict = miscHelper.getOredictName("essence", name);
			if(!BLACKLIST.contains(name) &&
					oredict.contains(seedsOredict) && oredict.contains(cropOredict) &&
					oredict.contains(cruxOredict) && oredict.contains(essenceOredict) &&
					MysticalAgricultureModule.tierFunction.applyAsInt(material) >= 6) {
				ItemStack seeds = miscHelper.getItemStack(seedsOredict, 1);
				ItemStack crops = miscHelper.getItemStack(cropOredict, 1);
				ItemStack crux = miscHelper.getItemStack(cruxOredict, 1);
				ItemStack essence = miscHelper.getItemStack(essenceOredict, 1);
				JARGCAJEIPlugin.addJEIRecipe("mysticalagradditions:tier_6_crop_jei",
						new Tier6CropWrapper(seeds, crops, crux, essence));
			}
		}
	}
}
