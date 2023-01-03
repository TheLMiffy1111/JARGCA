package thelm.jargca.compat.mysticalagriculture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.ToIntFunction;

import org.apache.commons.lang3.StringUtils;

import com.blakebr0.mysticalagriculture.items.ModItems;
import com.google.common.collect.ImmutableMap;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntRBTreeMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.blocks.IBlockFormType;
import thelm.jaopca.api.blocks.IBlockInfo;
import thelm.jaopca.api.config.IDynamicSpecConfig;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormRequest;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.items.IItemFormType;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.blocks.BlockFormType;
import thelm.jaopca.items.ItemFormType;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;
import thelm.jargca.compat.mysticalagriculture.blocks.JARGCACropsBlock;
import thelm.jargca.compat.mysticalagriculture.items.JARGCASeedsItem;

@JAOPCAModule(modDependencies = "mysticalagriculture")
public class MysticalAgricultureModule implements IModule {

	public static final boolean ADDITIONS_LOADED = Loader.isModLoaded("mysticalagradditions");

	public static final Set<String> BLACKLIST = new TreeSet<>(Arrays.asList(
			"Aluminium", "Aluminum", "Amber", "BaseEssence", "Brass", "Bronze", "Chromium", "Coal", "Constantan",
			"Copper", "Diamond", "Electrum", "Emerald", "Enderium", "Glowstone", "Gold", "Graphite", "Inferium",
			"Intermedium", "Invar", "Iridium", "Iron", "Lapis", "Lead", "Lumium", "Mithril", "Nickel", "Peridot",
			"Platinum", "Prismarine", "Prudentium", "Quartz", "Redstone", "Ruby", "Saltpeter", "Sapphire",
			"Signalum", "Silicon", "Silver", "Soulium", "Steel", "Sulfur", "Superium", "Supremium", "Tin",
			"Titanium", "Tungsten", "Uranium", "Zinc"));

	static {
		if(Loader.isModLoaded("abyssalcraft")) {
			Collections.addAll(BLACKLIST, "Abyssalnite", "Coralium", "Dreadium");
		}
		if(Loader.isModLoaded("actuallyadditions")) {
			Collections.addAll(BLACKLIST, "QuartzBlack");
		}
		if(Loader.isModLoaded("appliedenergistics2")) {
			Collections.addAll(BLACKLIST, "CertusQuartz", "Fluix");
		}
		if(Loader.isModLoaded("arsmagica2")) {
			Collections.addAll(BLACKLIST, "BlueTopaz", "Chimerite", "Moonstone", "Sunstone", "Vinteum");
		}
		if(Loader.isModLoaded("astralsorcery")) {
			Collections.addAll(BLACKLIST, "Aquamarine", "AstralStarmetal");
		}
		if(Loader.isModLoaded("basemetals")) {
			Collections.addAll(BLACKLIST, "Adamantine", "Aquarium", "Coldiron", "Starsteel");
		}
		if(Loader.isModLoaded("bigreactors")) {
			Collections.addAll(BLACKLIST, "Yellorium");
		}
		if(Loader.isModLoaded("biomesoplenty")) {
			Collections.addAll(BLACKLIST, "Amethyst", "Malachite", "Tanzanite", "Topaz");
		}
		if(Loader.isModLoaded("botania")) {
			Collections.addAll(BLACKLIST, "ElvenElementium", "Manasteel", "Terrasteel");
		}
		if(Loader.isModLoaded("draconicevolution")) {
			Collections.addAll(BLACKLIST, "Draconium");
		}
		if(Loader.isModLoaded("embers")) {
			Collections.addAll(BLACKLIST, "Dawnstone");
		}
		if(Loader.isModLoaded("enderio")) {
			Collections.addAll(BLACKLIST, "Bedrock", "ConductiveIron", "DarkSteel", "ElecticalSteel", "EndSteel",
					"EnergeticAlloy", "PulsatingIron", "RedstoneAlloy", "Soularium", "VibrantAlloy");
		}
		if(Loader.isModLoaded("erebus")) {
			Collections.addAll(BLACKLIST, "Jade");
		}
		if(Loader.isModLoaded("evilcraft")) {
			Collections.addAll(BLACKLIST, "Dark");
		}
		if(Loader.isModLoaded("forestry")) {
			Collections.addAll(BLACKLIST, "Apatite");
		}
		if(Loader.isModLoaded("galacticraftcore")) {
			Collections.addAll(BLACKLIST, "MeteoricIron");
		}
		if(Loader.isModLoaded("galacticraftplanets")) {
			Collections.addAll(BLACKLIST, "Desh");
		}
		if(Loader.isModLoaded("immersiveengineering")) {
			Collections.addAll(BLACKLIST, "HOPGraphite");
		}
		if(Loader.isModLoaded("matteroverdrive")) {
			Collections.addAll(BLACKLIST, "Dilithium", "Tritanium");
		}
		if(Loader.isModLoaded("mekanism")) {
			Collections.addAll(BLACKLIST, "Osmium", "RefinedGlowstone", "RefinedObsidian");
		}
		if(Loader.isModLoaded("nuclearcraft")) {
			Collections.addAll(BLACKLIST, "Boron", "Lithium", "Magnesium", "Thorium");
		}
		if(Loader.isModLoaded("plustic")) {
			Collections.addAll(BLACKLIST, "Alumite");
		}
		if(Loader.isModLoaded("pneumaticraft")) {
			Collections.addAll(BLACKLIST, "CompressedIron");
		}
		if(Loader.isModLoaded("projectred-core")) {
			Collections.addAll(BLACKLIST, "Electrotine");
		}
		if(Loader.isModLoaded("quark")) {
			Collections.addAll(BLACKLIST, "EnderBiotite");
		}
		if(Loader.isModLoaded("redstonearsenal")) {
			Collections.addAll(BLACKLIST, "ElectrumFlux");
		}
		if(Loader.isModLoaded("refinedstorage")) {
			Collections.addAll(BLACKLIST, "QuartzEnrichedIron");
		}
		if(Loader.isModLoaded("tconstruct")) {
			Collections.addAll(BLACKLIST, "AluminiumBrass", "AluminumBrass", "Ardite", "Cobalt", "Knightslime", "Manyullyn");
		}
		if(Loader.isModLoaded("thaumcraft")) {
			Collections.addAll(BLACKLIST, "Quicksilver", "Thaumium", "Void");
		}
		if(Loader.isModLoaded("thebetweenlands")) {
			Collections.addAll(BLACKLIST, "Octine", "Syrmorite", "Valonite");
		}
		if(Loader.isModLoaded("twilightforest")) {
			Collections.addAll(BLACKLIST, "Fiery", "Ironwood", "Knightmetal", "Steeleaf");
		}
		if(ADDITIONS_LOADED) {
			Collections.addAll(BLACKLIST, "Insanium", "NetherStar");
			if(Loader.isModLoaded("avaritia")) {
				Collections.addAll(BLACKLIST, "Neutronium");
			}
			if(Loader.isModLoaded("draconicevolution")) {
				Collections.addAll(BLACKLIST, "DraconiumAwakened");
			}
		}
	}

	private static final Object[][] RECIPE_PATTERNS = {
			{"E",                 'E', null},
			{"E E",               'E', null},
			{"EEE",               'E', null},
			{" E ", "E E", " E ", 'E', null},
			{" E ", "EEE", " E ", 'E', null},
			{"EEE", "   ", "EEE", 'E', null},
			{"EEE", " E ", "EEE", 'E', null},
			{"EEE", "E E", "EEE", 'E', null},
			{"EEE", "EEE", "EEE", 'E', null},
	};

	private static Object2IntMap<IMaterial> tierMap = new Object2IntRBTreeMap<>();
	public static ToIntFunction<IMaterial> tierFunction;

	private final IForm essenceForm = ApiImpl.INSTANCE.newForm(this, "mysticalagriculture_essence", ItemFormType.INSTANCE).
			setMaterialTypes(MaterialType.values()).setSecondaryName("essence").setDefaultMaterialBlacklist(BLACKLIST);
	private final IForm seedForm = ApiImpl.INSTANCE.newForm(this, "mysticalagriculture_seeds", ItemFormType.INSTANCE).
			setMaterialTypes(MaterialType.values()).setSecondaryName("mysticalSeeds").setDefaultMaterialBlacklist(BLACKLIST).
			setSettings(ItemFormType.INSTANCE.getNewSettings().setItemCreator(JARGCASeedsItem::new));
	private final IForm cropForm = ApiImpl.INSTANCE.newForm(this, "mysticalagriculture_crop", BlockFormType.INSTANCE).
			setMaterialTypes(MaterialType.values()).setSecondaryName("mysticalCrop").setDefaultMaterialBlacklist(BLACKLIST).
			setSettings(BlockFormType.INSTANCE.getNewSettings().setBlockCreator(JARGCACropsBlock::new));
	private final IForm cruxForm = ApiImpl.INSTANCE.newForm(this, "mysticalagriculture_crux", BlockFormType.INSTANCE).
			setMaterialTypes(MaterialType.values()).setSecondaryName("crux").setDefaultMaterialBlacklist(BLACKLIST).
			setSettings(BlockFormType.INSTANCE.getNewSettings().
					setBlockHardnessFunction(material->5F).setExplosionResistanceFunction(material->10F));

	private Map<IMaterial, IDynamicSpecConfig> configs;

	public MysticalAgricultureModule() {
		MinecraftForge.EVENT_BUS.register(this);
		tierFunction = this::getTier;
	}

	@Override
	public String getName() {
		return "mysticalagriculture";
	}

	@Override
	public List<IFormRequest> getFormRequests() {
		return Collections.singletonList(ADDITIONS_LOADED ?
				ApiImpl.INSTANCE.newFormRequest(this, essenceForm, seedForm, cropForm, cruxForm).setGrouped(true) :
					ApiImpl.INSTANCE.newFormRequest(this, essenceForm, seedForm, cropForm).setGrouped(true));
	}

	@Override
	public void defineMaterialConfig(IModuleData moduleData, Map<IMaterial, IDynamicSpecConfig> configs) {
		this.configs = configs;
	}

	@Override
	public void onMaterialComputeComplete(IModuleData moduleData) {
		JAOPCAApi api = ApiImpl.INSTANCE;
		IItemFormType itemFormType = ItemFormType.INSTANCE;
		for(IMaterial material : essenceForm.getMaterials()) {
			IItemInfo essenceInfo = itemFormType.getMaterialFormInfo(essenceForm, material);
			ApiImpl.INSTANCE.registerOredict("essenceTier"+getTier(material), essenceInfo.asItem());
		}
		for(IMaterial material : seedForm.getMaterials()) {
			IItemInfo seedsInfo = itemFormType.getMaterialFormInfo(seedForm, material);
			ApiImpl.INSTANCE.registerOredict("seedsTier"+getTier(material), seedsInfo.asItem());
		}
	}

	@Override
	public void onInit(IModuleData moduleData, FMLInitializationEvent event) {
		JAOPCAApi api = ApiImpl.INSTANCE;
		MysticalAgricultureHelper helper = MysticalAgricultureHelper.INSTANCE;
		IMiscHelper miscHelper = MiscHelper.INSTANCE;
		IItemFormType itemFormType = ItemFormType.INSTANCE;
		IBlockFormType blockFormType = BlockFormType.INSTANCE;
		Set<String> oredict = api.getOredict();
		for(IMaterial material : essenceForm.getMaterials()) {
			MaterialType type = material.getType();
			int tier = getTier(material);
			String seedsOredict = miscHelper.getOredictName("mysticalSeeds", material.getName());
			IItemInfo essenceInfo = itemFormType.getMaterialFormInfo(essenceForm, material);
			helper.registerReprocessorRecipe(
					miscHelper.getRecipeKey("mysticalagriculture.seeds_to_essence", material.getName()),
					seedsOredict, essenceInfo, 2);
			String materialOredict = miscHelper.getOredictName(type.getFormName(), material.getName());
			if(tier >= 6) {
				String nuggetOredict = miscHelper.getOredictName("nugget", material.getName());
				String tinyDustOredict = miscHelper.getOredictName("dustTiny", material.getName());
				String smallDustOredict = miscHelper.getOredictName("dustSmall", material.getName());
				if(oredict.contains(nuggetOredict)) {
					materialOredict = nuggetOredict;
				}
				else if(oredict.contains(tinyDustOredict)) {
					materialOredict = tinyDustOredict;
				}
				else if(oredict.contains(smallDustOredict)) {
					materialOredict = smallDustOredict;
				}
			}

			IDynamicSpecConfig config = configs.get(material);
			int inputCount = tier >= 6 ? 9 : 8;
			inputCount = config.getDefinedInt("mysticalagriculture.inputCount", inputCount, 1, 9, "The amount of Mystical Agriculture essence required to craft materials.");
			int outputCount = tier >= 6 ? 3 : type.isDust() ? 6 : 4;
			outputCount = config.getDefinedInt("mysticalagriculture.outputCount", outputCount, "The amount of material obtained by crafting Mystical Agriculture essence.");

			api.registerShapedRecipe(
					miscHelper.getRecipeKey("mysticalagriculture.essence_to_material", material.getName()),
					materialOredict, outputCount, getRecipePattern(inputCount, essenceInfo));
		}
		for(IMaterial material : seedForm.getMaterials()) {
			int tier = getTier(material);
			String materialOredict = miscHelper.getOredictName(material.getType().getFormName(), material.getName());
			IItemInfo seedsInfo = itemFormType.getMaterialFormInfo(seedForm, material);
			api.registerShapedRecipe(
					miscHelper.getRecipeKey("mysticalagriculture.material_to_seeds", material.getName()),
					seedsInfo, 1, new Object[] {
							"MEM",
							"ESE",
							"MEM",
							'M', materialOredict,
							'E', getEssence(tier),
							'S', getCraftingSeeds(tier),
					});
		}
		for(IMaterial material : cruxForm.getMaterials()) {
			if(getTier(material) >= 6) {
				String materialOredict = miscHelper.getOredictName(material.getType().getFormName(), material.getName());
				IBlockInfo cruxInfo = blockFormType.getMaterialFormInfo(cruxForm, material);

				IDynamicSpecConfig config = configs.get(material);
				String configOreBase = config.getDefinedString("mysticalagriculture.cruxSpecial", '#'+materialOredict,
						this::isOredictOrItemValid, "The special item required to craft Mystical Agriculture cruxes.");
				Object cruxSpecial = getOredictOrItem(configOreBase);

				api.registerShapedRecipe(
						miscHelper.getRecipeKey("mysticalagriculture.material_to_crux", material.getName()),
						cruxInfo, 1, new Object[] {
								"MEM",
								"SDS",
								"MEM",
								'M', "essenceSupremium",
								'E', materialOredict,
								'S', cruxSpecial,
								'D', "blockDiamond",
						});
			}
		}
	}

	@Override
	public Map<String, String> getLegacyRemaps() {
		ImmutableMap.Builder builder = ImmutableMap.builder();
		builder.put("essence", "mysticalagriculture_essence");
		builder.put("mysticalseeds", "mysticalagriculture_seeds");
		builder.put("mysticalcrops", "mysticalagriculture_crop");
		builder.put("crux", "mysticalagriculture_crux");
		return builder.build();
	}

	public int getTier(IMaterial material) {
		return tierMap.computeIfAbsent(material, key->configs.get(key).getDefinedInt("mysticalagriculture.tier", 3, 1, ADDITIONS_LOADED ? 6 : 5, "The crop tier of this material."));
	}

	public Object[] getRecipePattern(int count, Object input) {
		int i = MathHelper.clamp(count, 1, 9)-1;
		Object[] arr = RECIPE_PATTERNS[i].clone();
		arr[arr.length-1] = input;
		return arr;
	}

	public Object getEssence(int tier) {
		tier = MathHelper.clamp(tier, 1, ADDITIONS_LOADED ? 6 : 5);
		switch(tier) {
		case 1: return "essenceInferium";
		case 2: return "essencePrudentium";
		default:
		case 3: return "essenceIntermedium";
		case 4: return "essenceSuperium";
		case 5: return "essenceSupremium";
		case 6: return "essenceInsanium";
		}
	}

	public Object getCraftingSeeds(int tier) {
		tier = MathHelper.clamp(tier, 1, ADDITIONS_LOADED ? 6 : 5);
		switch(tier) {
		case 1: return new ItemStack(ModItems.itemCrafting, 1, 17);
		case 2: return new ItemStack(ModItems.itemCrafting, 1, 18);
		default:
		case 3: return new ItemStack(ModItems.itemCrafting, 1, 19);
		case 4: return new ItemStack(ModItems.itemCrafting, 1, 20);
		case 5: return new ItemStack(ModItems.itemCrafting, 1, 21);
		case 6: return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mysticalagradditions", "insanium")), 1, 1);
		}
	}

	public boolean isOredictOrItemValid(String s) {
		if(StringUtils.startsWith(s, "#")) {
			return ApiImpl.INSTANCE.getOredict().contains(s.substring(1));
		}
		else {
			return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(s.split("@(?=\\d*$)")[0]));
		}
	}

	public Object getOredictOrItem(String s) {
		if(StringUtils.startsWith(s, "#")) {
			return s.substring(1);
		}
		else {
			return MiscHelper.INSTANCE.parseMetaItem(s);
		}
	}

	@SubscribeEvent
	public void onBonemealEvent(BonemealEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		IBlockState state = event.getBlock();
		if(state.getBlock() instanceof JARGCACropsBlock) {
			JARGCACropsBlock block = (JARGCACropsBlock)state.getBlock();
			if(player.getHeldItemMainhand().getItem() == ModItems.itemFertilizedEssence ||
					player.getHeldItemOffhand().getItem() == ModItems.itemFertilizedEssence) {
				if(block.canGrow(world, pos, state, world.isRemote)) {
					if(!world.isRemote) {
						block.grow(world, world.rand, pos, state);
					}
					event.setResult(Result.ALLOW);
				}
			}
			if(player.getHeldItemMainhand().getItem() == ModItems.itemMysticalFertilizer ||
					player.getHeldItemOffhand().getItem() == ModItems.itemMysticalFertilizer) {
				if(block.canGrow(world, pos, state, world.isRemote)) {
					if(!world.isRemote) {
						world.setBlockState(pos, block.withAge(block.getMaxAge()), 2);
					}
					event.setResult(Result.ALLOW);
				}
			}
		}
	}
}
