package thelm.jargca.compat.agricraft.api.agriplants;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import thelm.jaopca.api.forms.IFormSettings;
import thelm.jaopca.api.materials.IMaterial;

public interface IAgriPlantFormSettings extends IFormSettings {

	IAgriPlantFormSettings setAgriPlantCreator(IAgriPlantCreator agriPlantCreator);

	IAgriPlantCreator getAgriPlantCreator();

	IAgriPlantFormSettings setSeedFormatsFunction(Function<IMaterial, List<String>> seedFormatsFunction);

	Function<IMaterial, List<String>> getSeedFormatsFunction();

	IAgriPlantFormSettings setIsWeed(boolean isWeed);

	boolean getIsWeed();

	IAgriPlantFormSettings setIsAggressive(boolean isAggressive);

	boolean getIsAggressive();

	IAgriPlantFormSettings setIsFertilizable(boolean isFertilizable);

	boolean getIsFertilizable();

	IAgriPlantFormSettings setSpreadChanceFunction(ToDoubleFunction<IMaterial> spreadChanceFunction);

	ToDoubleFunction<IMaterial> getSpreadChanceFunction();

	IAgriPlantFormSettings setSpawnChanceFunction(ToDoubleFunction<IMaterial> spawnChanceFunction);

	ToDoubleFunction<IMaterial> getSpawnChanceFunction();

	IAgriPlantFormSettings setGrowthChanceBaseFunction(ToDoubleFunction<IMaterial> growthChanceBaseFunction);

	ToDoubleFunction<IMaterial> getGrowthChanceBaseFunction();

	IAgriPlantFormSettings setGrowthChanceBonusFunction(ToDoubleFunction<IMaterial> growthChanceBonusFunction);

	ToDoubleFunction<IMaterial> getGrowthChanceBonusFunction();

	IAgriPlantFormSettings setSeedDropChanceBaseFunction(ToDoubleFunction<IMaterial> seedDropChanceBaseFunction);

	ToDoubleFunction<IMaterial> getSeedDropChanceBaseFunction();

	IAgriPlantFormSettings setSeedDropChanceBonusFunction(ToDoubleFunction<IMaterial> seedDropChanceBonusFunction);

	ToDoubleFunction<IMaterial> getSeedDropChanceBonusFunction();

	IAgriPlantFormSettings setGrassDropChanceFunction(ToDoubleFunction<IMaterial> grassDropChanceFunction);

	ToDoubleFunction<IMaterial> getGrassDropChanceFunction();

	IAgriPlantFormSettings setGrowthStagesFunction(ToIntFunction<IMaterial> growthStagesFunction);

	ToIntFunction<IMaterial> getGrowthStagesFunction();

	IAgriPlantFormSettings setTierFunction(ToIntFunction<IMaterial> tierFunction);

	ToIntFunction<IMaterial> getTierFunction();

	IAgriPlantFormSettings setSoilsFunction(Function<IMaterial, List<String>> soilsFunction);

	Function<IMaterial, List<String>> getSoilsFunction();

	IAgriPlantFormSettings setConditionsFunction(Function<IMaterial, List<Condition>> conditionsFunction);

	Function<IMaterial, List<Condition>> getConditionsFunction();

	IAgriPlantFormSettings setMinLightFunction(ToIntFunction<IMaterial> minLightFunction);

	ToIntFunction<IMaterial> getMinLightFunction();

	IAgriPlantFormSettings setMaxLightFunction(ToIntFunction<IMaterial> maxLightFunction);

	ToIntFunction<IMaterial> getMaxLightFunction();

	IAgriPlantFormSettings setProductsFunction(Function<IMaterial, List<Product>> productsFunction);

	Function<IMaterial, List<Product>> getProductsFunction();

	IAgriPlantFormSettings setAgriPlantTextureFunctionCreator(IAgriPlantTextureFunctionCreator agriPlantTextureFunctionCreator);

	IAgriPlantTextureFunctionCreator getAgriPlantTextureFunctionCreator();

	IAgriPlantFormSettings setRenderType(RenderType renderType);

	RenderType getRenderType();

	IAgriPlantFormSettings setDummyAgriPlantCreator(IDummyAgriPlantCreator dummyAgriPlantCreator);

	IDummyAgriPlantCreator getDummyAgriPlantCreator();
}
