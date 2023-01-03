package thelm.jargca.compat.agricraft;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;

import thelm.jaopca.api.config.IDynamicSpecConfig;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormRequest;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.utils.ApiImpl;
import thelm.jargca.compat.agricraft.agriplants.AgriPlantFormType;
import thelm.jargca.compat.agricraft.api.agriplants.Condition;
import thelm.jargca.compat.agricraft.api.agriplants.Product;
import thelm.jargca.compat.agricraft.api.agriplants.RenderType;
import thelm.jargca.compat.agricraft.client.resources.JAOPCAAgriPlantTextureFunctionCreator;
import thelm.jargca.compat.mysticalagriculture.MysticalAgricultureModule;

@JAOPCAModule(modDependencies = {"agricraft", "mysticalagriculture"})
public class AgriCraftMysticalAgricultureModule implements IModule {

	private static final Set<String> BLACKLIST = MysticalAgricultureModule.BLACKLIST;
	private static final int[] TEXTURE_INDEX = {0, 1, 2, 2, 3, 4, 4, 5};

	private final IForm mysticalForm = ApiImpl.INSTANCE.newForm(this, "agricraft_mysticalagriculture_mystical", AgriPlantFormType.INSTANCE).
			setMaterialTypes(MaterialType.values()).setSecondaryName("mystical").setDefaultMaterialBlacklist(BLACKLIST).
			setSettings(AgriPlantFormType.INSTANCE.getNewSettings().
					setSeedFormatsFunction(material->Collections.singletonList("mysticalSeeds%s")).
					setGrowthChanceBaseFunction(this::getGrowthChanceBase).
					setGrowthChanceBonusFunction(this::getGrowthChanceBonus).
					setConditionsFunction(material->MysticalAgricultureModule.tierFunction.applyAsInt(material) >= 6 ?
							Collections.singletonList(new Condition().setFormat("crux%s")) : Collections.emptyList()).
					setProductsFunction(material->Collections.singletonList(new Product().
							setFormat("essence%s").setMin(1).setRange(4).setChance(0.9))).
					setAgriPlantTextureFunctionCreator(new JAOPCAAgriPlantTextureFunctionCreator(i->i == 7, i->TEXTURE_INDEX[i])).
					setRenderType(RenderType.CROSS));

	private Map<IMaterial, IDynamicSpecConfig> configs;

	@Override
	public String getName() {
		return "agricraft_mysticalagriculture";
	}

	@Override
	public Multimap<Integer, String> getModuleDependencies() {
		ImmutableSetMultimap.Builder builder = ImmutableSetMultimap.builder();
		builder.put(0, "mysticalagriculture");
		return builder.build();
	}

	@Override
	public List<IFormRequest> getFormRequests() {
		return Collections.singletonList(mysticalForm.toRequest());
	}

	@Override
	public void defineMaterialConfig(IModuleData moduleData, Map<IMaterial, IDynamicSpecConfig> configs) {
		this.configs = configs;
	}

	public double getGrowthChanceBase(IMaterial material) {
		int tier = MysticalAgricultureModule.tierFunction.applyAsInt(material);
		return configs.get(material).getDefinedDouble("mysticalagriculture_agricraft.growthChanceBase", tier >= 6 ? 0.85 : 0.9, "The AgriCraft base growth chance of the Mystical Agriculture plant of this material.");
	}

	public double getGrowthChanceBonus(IMaterial material) {
		int tier = MysticalAgricultureModule.tierFunction.applyAsInt(material);
		return configs.get(material).getDefinedDouble("mysticalagriculture_agricraft.growthChanceBonus", tier >= 6 ? 0.02 : 0.025, "The AgriCraft bonus growth chance of the Mystical Agriculture plant of this material.");
	}
}
