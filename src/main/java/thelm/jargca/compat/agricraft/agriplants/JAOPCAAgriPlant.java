package thelm.jargca.compat.agricraft.agriplants;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.infinityraider.agricraft.api.v1.AgriApi;
import com.infinityraider.agricraft.api.v1.crop.IAgriCrop;
import com.infinityraider.agricraft.api.v1.plant.IAgriPlant;
import com.infinityraider.agricraft.api.v1.render.RenderMethod;
import com.infinityraider.agricraft.api.v1.requirement.BlockCondition;
import com.infinityraider.agricraft.api.v1.requirement.IGrowthReqBuilder;
import com.infinityraider.agricraft.api.v1.requirement.IGrowthRequirement;
import com.infinityraider.agricraft.api.v1.stat.IAgriStat;
import com.infinityraider.agricraft.api.v1.util.BlockRange;
import com.infinityraider.agricraft.api.v1.util.FuzzyStack;
import com.infinityraider.agricraft.farming.PlantStats;
import com.infinityraider.agricraft.farming.growthrequirement.GrowthReqBuilder;
import com.infinityraider.agricraft.init.AgriItems;
import com.infinityraider.agricraft.reference.AgriNBT;
import com.infinityraider.infinitylib.render.tessellation.ITessellator;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;
import thelm.jargca.compat.agricraft.AgriCraftHelper;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantFormSettings;
import thelm.jargca.compat.agricraft.api.agriplants.IMaterialFormAgriPlant;
import thelm.jargca.compat.agricraft.api.agriplants.Product;
import thelm.jargca.compat.agricraft.api.agriplants.RenderType;
import thelm.jargca.compat.agricraft.client.renderer.JAOPCAPlantRenderer;

public class JAOPCAAgriPlant implements IAgriPlant, IMaterialFormAgriPlant {

	private final String id;
	private final IForm form;
	private final IMaterial material;
	protected final IAgriPlantFormSettings settings;

	protected Optional<String> nameTranslationKey = Optional.empty();
	protected List<FuzzyStack> seedItems = Collections.emptyList();
	protected boolean isWeed;
	protected boolean isAggressive;
	protected boolean isFertilizable;
	protected OptionalDouble spreadChance = OptionalDouble.empty();
	protected OptionalDouble spawnChance = OptionalDouble.empty();
	protected OptionalDouble growthChanceBase = OptionalDouble.empty();
	protected OptionalDouble growthChanceBonus = OptionalDouble.empty();
	protected OptionalDouble grassDropChance = OptionalDouble.empty();
	protected OptionalDouble seedDropChanceBase = OptionalDouble.empty();
	protected OptionalDouble seedDropChanceBonus = OptionalDouble.empty();
	protected OptionalInt growthStages = OptionalInt.empty();
	protected OptionalInt tier = OptionalInt.empty();
	protected Optional<String> descTranslationKey = Optional.empty();
	protected Optional<IGrowthRequirement> growthRequirement = Optional.empty();
	protected List<Product> products = Collections.emptyList();
	protected RenderType renderType = RenderType.HASH;

	public JAOPCAAgriPlant(IForm form, IMaterial material, IAgriPlantFormSettings settings) {
		id = MiscHelper.INSTANCE.getFluidName(form.getSecondaryName(), material.getName());
		this.form = form;
		this.material = material;
		this.settings = settings;

		isWeed = settings.getIsWeed();
		isAggressive = settings.getIsAggressive();
		isFertilizable = settings.getIsFertilizable();
		renderType = settings.getRenderType();
	}

	@Override
	public IForm getForm() {
		return form;
	}

	@Override
	public IMaterial getMaterial() {
		return material;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getUnlocalizedPlantName() {
		if(!nameTranslationKey.isPresent()) {
			nameTranslationKey = Optional.of("agri_plant.jaopca."+MiscHelper.INSTANCE.toLowercaseUnderscore(material.getName()));
		}
		return nameTranslationKey.get();
	}

	@Override
	public String getPlantName() {
		return ApiImpl.INSTANCE.currentLocalizer().localizeMaterialForm("agri_plant.jaopca."+form.getName(), material, getUnlocalizedPlantName());
	}

	@Override
	public String getSeedName() {
		return getSeed().getDisplayName();
	}

	@Override
	public Collection<FuzzyStack> getSeedItems() {
		if(seedItems.isEmpty()) {
			seedItems = settings.getSeedFormatsFunction().apply(material).stream().
					map(format->AgriCraftHelper.INSTANCE.getFuzzyStack(String.format(format, material.getName()), 1)).
					collect(Collectors.toList());
		}
		return seedItems;
	}

	@Override
	public boolean isWeed() {
		return isWeed;
	}

	@Override
	public boolean isAggressive() {
		return isAggressive;
	}

	@Override
	public boolean isFertilizable() {
		return isFertilizable;
	}

	@Override
	public double getSpreadChance() {
		if(!spreadChance.isPresent()) {
			spreadChance = OptionalDouble.of(settings.getSpreadChanceFunction().applyAsDouble(material));
		}
		return spreadChance.getAsDouble();
	}

	@Override
	public double getSpawnChance() {
		if(!spawnChance.isPresent()) {
			spawnChance = OptionalDouble.of(settings.getSpawnChanceFunction().applyAsDouble(material));
		}
		return spawnChance.getAsDouble();
	}

	@Override
	public double getGrowthChanceBase() {
		if(!growthChanceBase.isPresent()) {
			growthChanceBase = OptionalDouble.of(settings.getGrowthChanceBaseFunction().applyAsDouble(material));
		}
		return growthChanceBase.getAsDouble();
	}

	@Override
	public double getGrowthChanceBonus() {
		if(!growthChanceBonus.isPresent()) {
			growthChanceBonus = OptionalDouble.of(settings.getGrowthChanceBonusFunction().applyAsDouble(material));
		}
		return growthChanceBonus.getAsDouble();
	}

	@Override
	public double getSeedDropChanceBase() {
		if(!seedDropChanceBase.isPresent()) {
			seedDropChanceBase = OptionalDouble.of(settings.getSeedDropChanceBaseFunction().applyAsDouble(material));
		}
		return seedDropChanceBase.getAsDouble();
	}

	@Override
	public double getSeedDropChanceBonus() {
		if(!seedDropChanceBonus.isPresent()) {
			seedDropChanceBonus = OptionalDouble.of(settings.getSeedDropChanceBonusFunction().applyAsDouble(material));
		}
		return seedDropChanceBonus.getAsDouble();
	}

	@Override
	public double getGrassDropChance() {
		if(!grassDropChance.isPresent()) {
			grassDropChance = OptionalDouble.of(settings.getGrassDropChanceFunction().applyAsDouble(material));
		}
		return grassDropChance.getAsDouble();
	}

	@Override
	public int getGrowthStages() {
		if(!growthStages.isPresent()) {
			growthStages = OptionalInt.of(settings.getGrowthStagesFunction().applyAsInt(material));
		}
		return growthStages.getAsInt();
	}

	@Override
	public int getTier() {
		if(!tier.isPresent()) {
			tier = OptionalInt.of(settings.getTierFunction().applyAsInt(material));
		}
		return tier.getAsInt();
	}

	public String getUnlocalizedInformation() {
		if(!descTranslationKey.isPresent()) {
			descTranslationKey = Optional.of("agri_plant.desc.jaopca."+MiscHelper.INSTANCE.toLowercaseUnderscore(material.getName()));
		}
		return descTranslationKey.get();
	}

	@Override
	public String getInformation() {
		return ApiImpl.INSTANCE.currentLocalizer().localizeMaterialForm("agri_plant.desc.jaopca."+form.getName(), material, getUnlocalizedInformation());
	}

	@Override
	public ItemStack getSeed() {
		ItemStack stack = getSeedItems().stream().
				map(FuzzyStack::toStack).
				findFirst().
				orElse(new ItemStack(AgriItems.getInstance().AGRI_SEED));
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(AgriNBT.SEED, getId());
		new PlantStats().writeToNBT(tag);
		stack.setTagCompound(tag);
		return stack;
	}

	@Override
	public IGrowthRequirement getGrowthRequirement() {
		if(!growthRequirement.isPresent()) {
			IGrowthReqBuilder builder = new GrowthReqBuilder();
			settings.getSoilsFunction().apply(material).stream().
			map(AgriApi.getSoilRegistry()::get).
			filter(Optional::isPresent).
			map(Optional::get).
			forEach(builder::addSoil);
			settings.getConditionsFunction().apply(material).stream().
			map(cond->new BlockCondition(AgriCraftHelper.INSTANCE.getFuzzyStack(String.format(cond.getFormat(), material.getName()), cond.getAmount()),
					new BlockRange(cond.getX1(), cond.getY1(), cond.getZ1(), cond.getX2(), cond.getY2(), cond.getZ2()))).
			forEach(builder::addCondition);
			builder.setMinLight(settings.getMinLightFunction().applyAsInt(material));
			builder.setMaxLight(settings.getMaxLightFunction().applyAsInt(material));
			growthRequirement = Optional.of(builder.build());
		}
		return growthRequirement.get();
	}

	public List<Product> getProducts() {
		if(products.isEmpty()) {
			products = settings.getProductsFunction().apply(material);
		}
		return products;
	}

	@Override
	public void getPossibleProducts(Consumer<ItemStack> consumer) {
		getProducts().stream().
		map(product->MiscHelper.INSTANCE.getItemStack(String.format(product.getFormat(), material.getName()), 1)).
		forEach(consumer);
	}

	@Override
	public void getHarvestProducts(Consumer<ItemStack> consumer, IAgriCrop crop, IAgriStat stat, Random rand) {
		getProducts().stream().
		filter(product->product.getChance() > rand.nextDouble()).
		map(product->MiscHelper.INSTANCE.getItemStack(String.format(product.getFormat(), material.getName()), product.getMin()+rand.nextInt(product.getRange()+1))).
		forEach(consumer);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getSeedTexture() {
		if(MiscHelper.INSTANCE.hasResource(new ResourceLocation("jaopca", "textures/items/"+form.getName()+'.'+material.getName()+"_seeds.png"))) {
			return new ResourceLocation("jaopca", "items/"+form.getName()+'.'+material.getName()+"_seeds");
		}
		return new ResourceLocation("jaopca", "items/"+material.getModelType()+'/'+form.getName()+"_seeds");
	}

	@Override
	public float getHeight(int meta) {
		return 13/16F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public RenderMethod getRenderMethod() {
		return RenderMethod.CUSTOM;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getPrimaryPlantTexture(int growthStage) {
		List<Pair<ResourceLocation, Boolean>> textureList = settings.getAgriPlantTextureFunctionCreator().create(this, settings).apply(growthStage);
		return textureList.size() > 0 ? textureList.get(0).getLeft() : null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getSecondaryPlantTexture(int growthStage) {
		List<Pair<ResourceLocation, Boolean>> textureList = settings.getAgriPlantTextureFunctionCreator().create(this, settings).apply(growthStage);
		return textureList.size() > 1 ? textureList.get(1).getLeft() : null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<BakedQuad> getPlantQuads(IExtendedBlockState state, int growthStage, EnumFacing direction, Function<ResourceLocation, TextureAtlasSprite> textureToIcon) {
		if(textureToIcon instanceof ITessellator) {
			List<Pair<ResourceLocation, Boolean>> textureList = settings.getAgriPlantTextureFunctionCreator().create(this, settings).apply(growthStage);
			JAOPCAPlantRenderer.renderPlant((ITessellator)textureToIcon, textureList, renderType, material.getColor());
		}
		return Collections.emptyList();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof IAgriPlant && id.equals(((IAgriPlant)obj).getId());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
