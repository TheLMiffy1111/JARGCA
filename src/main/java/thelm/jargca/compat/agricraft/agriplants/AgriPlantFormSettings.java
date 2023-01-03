package thelm.jargca.compat.agricraft.agriplants;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

import thelm.jaopca.api.forms.IFormType;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jargca.compat.agricraft.api.agriplants.Condition;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantCreator;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantFormSettings;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantTextureFunctionCreator;
import thelm.jargca.compat.agricraft.api.agriplants.IDummyAgriPlantCreator;
import thelm.jargca.compat.agricraft.api.agriplants.Product;
import thelm.jargca.compat.agricraft.api.agriplants.RenderType;

public class AgriPlantFormSettings implements IAgriPlantFormSettings {

	AgriPlantFormSettings() {}

	private IAgriPlantCreator agriPlantCreator = JAOPCAAgriPlant::new;
	private Function<IMaterial, List<String>> seedFormatsFunction = material->Collections.emptyList();
	private boolean isWeed = false;
	private boolean isAggressive = false;
	private boolean isFertilizable = false;
	private ToDoubleFunction<IMaterial> spreadChanceFunction = material->0.1;
	private ToDoubleFunction<IMaterial> spawnChanceFunction = material->0;
	private ToDoubleFunction<IMaterial> growthChanceBaseFunction = material->0.9;
	private ToDoubleFunction<IMaterial> growthChanceBonusFunction = material->0.025;
	private ToDoubleFunction<IMaterial> seedDropChanceBaseFunction = material->1;
	private ToDoubleFunction<IMaterial> seedDropChanceBonusFunction = material->0;
	private ToDoubleFunction<IMaterial> grassDropChanceFunction = material->0;
	private ToIntFunction<IMaterial> growthStagesFunction = material->8;
	private ToIntFunction<IMaterial> tierFunction = material->1;
	private Function<IMaterial, List<String>> soilsFunction = material->Collections.singletonList("farmland_soil");
	private Function<IMaterial, List<Condition>> conditionsFunction = material->Collections.emptyList();
	private ToIntFunction<IMaterial> minLightFunction = material->10;
	private ToIntFunction<IMaterial> maxLightFunction = material->16;
	private Function<IMaterial, List<Product>> productsFunction = material->Collections.emptyList();
	private IAgriPlantTextureFunctionCreator agriPlantTextureFunctionCreator;
	private RenderType renderType = RenderType.HASH;
	private IDummyAgriPlantCreator dummyAgriPlantCreator = JAOPCADummyAgriPlant::new;

	@Override
	public IFormType getType() {
		return AgriPlantFormType.INSTANCE;
	}

	@Override
	public IAgriPlantFormSettings setAgriPlantCreator(IAgriPlantCreator agriPlantCreator) {
		this.agriPlantCreator = agriPlantCreator;
		return this;
	}

	@Override
	public IAgriPlantCreator getAgriPlantCreator() {
		return agriPlantCreator;
	}

	@Override
	public IAgriPlantFormSettings setSeedFormatsFunction(Function<IMaterial, List<String>> seedFormatsFunction) {
		this.seedFormatsFunction = seedFormatsFunction;
		return this;
	}

	@Override
	public Function<IMaterial, List<String>> getSeedFormatsFunction() {
		return seedFormatsFunction;
	}

	@Override
	public IAgriPlantFormSettings setIsWeed(boolean isWeed) {
		this.isWeed = isWeed;
		return this;
	}

	@Override
	public boolean getIsWeed() {
		return isWeed;
	}

	@Override
	public IAgriPlantFormSettings setIsAggressive(boolean isAggressive) {
		this.isAggressive = isAggressive;
		return this;
	}

	@Override
	public boolean getIsAggressive() {
		return isAggressive;
	}

	@Override
	public IAgriPlantFormSettings setIsFertilizable(boolean isFertilizable) {
		this.isFertilizable = isFertilizable;
		return this;
	}

	@Override
	public boolean getIsFertilizable() {
		return isFertilizable;
	}

	@Override
	public IAgriPlantFormSettings setSpreadChanceFunction(ToDoubleFunction<IMaterial> spreadChanceFunction) {
		this.spreadChanceFunction = spreadChanceFunction;
		return this;
	}

	@Override
	public ToDoubleFunction<IMaterial> getSpreadChanceFunction() {
		return spreadChanceFunction;
	}

	@Override
	public IAgriPlantFormSettings setSpawnChanceFunction(ToDoubleFunction<IMaterial> spawnChanceFunction) {
		this.spawnChanceFunction = spawnChanceFunction;
		return this;
	}

	@Override
	public ToDoubleFunction<IMaterial> getSpawnChanceFunction() {
		return spawnChanceFunction;
	}

	@Override
	public IAgriPlantFormSettings setGrowthChanceBaseFunction(ToDoubleFunction<IMaterial> growthChanceBaseFunction) {
		this.growthChanceBaseFunction = growthChanceBaseFunction;
		return this;
	}

	@Override
	public ToDoubleFunction<IMaterial> getGrowthChanceBaseFunction() {
		return growthChanceBaseFunction;
	}

	@Override
	public IAgriPlantFormSettings setGrowthChanceBonusFunction(ToDoubleFunction<IMaterial> growthChanceBonusFunction) {
		this.growthChanceBonusFunction = growthChanceBonusFunction;
		return this;
	}

	@Override
	public ToDoubleFunction<IMaterial> getGrowthChanceBonusFunction() {
		return growthChanceBonusFunction;
	}

	@Override
	public IAgriPlantFormSettings setSeedDropChanceBaseFunction(ToDoubleFunction<IMaterial> seedDropChanceBaseFunction) {
		this.seedDropChanceBaseFunction = seedDropChanceBaseFunction;
		return this;
	}

	@Override
	public ToDoubleFunction<IMaterial> getSeedDropChanceBaseFunction() {
		return seedDropChanceBaseFunction;
	}

	@Override
	public IAgriPlantFormSettings setSeedDropChanceBonusFunction(ToDoubleFunction<IMaterial> seedDropChanceBonusFunction) {
		this.seedDropChanceBonusFunction = seedDropChanceBonusFunction;
		return this;
	}

	@Override
	public ToDoubleFunction<IMaterial> getSeedDropChanceBonusFunction() {
		return seedDropChanceBonusFunction;
	}

	@Override
	public IAgriPlantFormSettings setGrassDropChanceFunction(ToDoubleFunction<IMaterial> grassDropChanceFunction) {
		this.grassDropChanceFunction = grassDropChanceFunction;
		return this;
	}

	@Override
	public ToDoubleFunction<IMaterial> getGrassDropChanceFunction() {
		return grassDropChanceFunction;
	}

	@Override
	public IAgriPlantFormSettings setGrowthStagesFunction(ToIntFunction<IMaterial> growthStagesFunction) {
		this.growthStagesFunction = growthStagesFunction;
		return this;
	}

	@Override
	public ToIntFunction<IMaterial> getGrowthStagesFunction() {
		return growthStagesFunction;
	}

	@Override
	public IAgriPlantFormSettings setTierFunction(ToIntFunction<IMaterial> tierFunction) {
		this.tierFunction = tierFunction;
		return this;
	}

	@Override
	public ToIntFunction<IMaterial> getTierFunction() {
		return tierFunction;
	}

	@Override
	public IAgriPlantFormSettings setSoilsFunction(Function<IMaterial, List<String>> soilsFunction) {
		this.soilsFunction = soilsFunction;
		return this;
	}

	@Override
	public Function<IMaterial, List<String>> getSoilsFunction() {
		return soilsFunction;
	}

	@Override
	public IAgriPlantFormSettings setConditionsFunction(Function<IMaterial, List<Condition>> conditionsFunction) {
		this.conditionsFunction = conditionsFunction;
		return this;
	}

	@Override
	public Function<IMaterial, List<Condition>> getConditionsFunction() {
		return conditionsFunction;
	}

	@Override
	public IAgriPlantFormSettings setMinLightFunction(ToIntFunction<IMaterial> minLightFunction) {
		this.minLightFunction = minLightFunction;
		return this;
	}

	@Override
	public ToIntFunction<IMaterial> getMinLightFunction() {
		return minLightFunction;
	}

	@Override
	public IAgriPlantFormSettings setMaxLightFunction(ToIntFunction<IMaterial> maxLightFunction) {
		this.maxLightFunction = maxLightFunction;
		return this;
	}

	@Override
	public ToIntFunction<IMaterial> getMaxLightFunction() {
		return maxLightFunction;
	}

	@Override
	public IAgriPlantFormSettings setProductsFunction(Function<IMaterial, List<Product>> productsFunction) {
		this.productsFunction = productsFunction;
		return this;
	}

	@Override
	public Function<IMaterial, List<Product>> getProductsFunction() {
		return productsFunction;
	}

	@Override
	public IAgriPlantFormSettings setAgriPlantTextureFunctionCreator(IAgriPlantTextureFunctionCreator agriPlantTextureFunctionCreator) {
		this.agriPlantTextureFunctionCreator = agriPlantTextureFunctionCreator;
		return this;
	}

	@Override
	public IAgriPlantTextureFunctionCreator getAgriPlantTextureFunctionCreator() {
		return agriPlantTextureFunctionCreator;
	}

	@Override
	public IAgriPlantFormSettings setRenderType(RenderType renderType) {
		this.renderType = renderType;
		return this;
	}

	@Override
	public RenderType getRenderType() {
		return renderType;
	}

	@Override
	public IAgriPlantFormSettings setDummyAgriPlantCreator(IDummyAgriPlantCreator dummyAgriPlantCreator) {
		this.dummyAgriPlantCreator = dummyAgriPlantCreator;
		return this;
	}

	@Override
	public IDummyAgriPlantCreator getDummyAgriPlantCreator() {
		return dummyAgriPlantCreator;
	}
}
