package thelm.jargca.compat.mysticalagriculture.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

import com.blakebr0.mysticalagriculture.config.ModConfig;
import com.blakebr0.mysticalagriculture.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import thelm.jaopca.api.blocks.IBlockFormSettings;
import thelm.jaopca.api.blocks.IMaterialFormBlock;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.blocks.BlockFormType;
import thelm.jaopca.forms.FormHandler;
import thelm.jaopca.items.ItemFormType;
import thelm.jaopca.utils.ApiImpl;
import thelm.jargca.compat.mysticalagriculture.MysticalAgricultureModule;

public class JARGCACropsBlock extends BlockCrops implements IMaterialFormBlock {

	public static final AxisAlignedBB CROPS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);

	private final IForm form;
	private final IMaterial material;
	protected final IBlockFormSettings settings;

	protected OptionalInt tier = OptionalInt.empty();
	protected Optional<Block> crux = Optional.empty();
	protected Optional<Item> seeds = Optional.empty();
	protected Optional<Item> essence = Optional.empty();
	protected Optional<String> translationKey = Optional.empty();

	public JARGCACropsBlock(IForm form, IMaterial material, IBlockFormSettings settings) {
		super();
		this.form = form;
		this.material = material;
		this.settings = settings;
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
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		checkAndDropBlock(world, pos, state);
		if(getTier() >= 6 && world.getBlockState(pos.down(2)).getBlock() != getCrux()) {
			return;
		}
		int i = getAge(state);
		if(world.getLightFromNeighbors(pos.up()) >= 9 && i < getMaxAge()) {
			float f = getGrowthChance(this, world, pos);
			if(rand.nextInt((int)(35/f) + 1) == 0) {
				world.setBlockState(pos, withAge(i+1), 2);
			}
		}
	}

	public int getTier() {
		if(!tier.isPresent()) {
			tier = OptionalInt.of(MysticalAgricultureModule.tierFunction.applyAsInt(getMaterial()));
		}
		return tier.getAsInt();
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.FARMLAND;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return CROPS_AABB;
	}

	public Block getCrux() {
		if(getTier() >= 6 && !crux.isPresent()) {
			crux = Optional.of(BlockFormType.INSTANCE.getMaterialFormInfo(FormHandler.getForm("mysticalagriculture_crux"), material).asBlock());
		}
		return crux.orElse(Blocks.AIR);
	}

	@Override
	public Item getSeed() {
		if(!seeds.isPresent()) {
			seeds = Optional.of(ItemFormType.INSTANCE.getMaterialFormInfo(FormHandler.getForm("mysticalagriculture_seeds"), material).asItem());
		}
		return seeds.get();
	}

	@Override
	public Item getCrop() {
		if(!essence.isPresent()) {
			essence = Optional.of(ItemFormType.INSTANCE.getMaterialFormInfo(FormHandler.getForm("mysticalagriculture_essence"), material).asItem());
		}
		return essence.get();
	}

	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList drops = new ArrayList();
		int age = state.getValue(AGE);
		Random rand = RANDOM;
		int essence = 0;
		int fertilizer = 0;
		int seeds = 1;
		if(age == 7) {
			if(getTier() < 6 && ModConfig.confSeedChance > 0) {
				if(rand.nextInt(100/ModConfig.confSeedChance) > 0) {
					seeds = 1;
				}
				else {
					seeds = 2;
				}
			}
			if(ModConfig.confFertilizedEssenceChance > 0) {
				if(rand.nextInt(100/ModConfig.confFertilizedEssenceChance) > 0) {
					fertilizer = 0;
				}
				else {
					fertilizer = 1;
				}
			}
			if(ModConfig.confEssenceChance > 0) {
				if(rand.nextInt(100/ModConfig.confEssenceChance) > 0) {
					essence = 1;
				}
				else {
					essence = 2;
				}
			}
		}
		drops.add(new ItemStack(this.getSeed(), seeds, 0));
		if(essence > 0) {
			drops.add(new ItemStack(this.getCrop(), essence, 0));
		}
		if(fertilizer > 0 && ModConfig.confFertilizedEssence) {
			drops.add(new ItemStack(ModItems.itemFertilizedEssence, fertilizer, 0));
		}
		return drops;
	}

	@Override
	public String getTranslationKey() {
		if(!translationKey.isPresent()) {
			ResourceLocation id = getRegistryName();
			translationKey = Optional.of("block."+id.getNamespace()+"."+id.getPath().replace('/', '.'));
		}
		return translationKey.get();
	}

	@Override
	public String getLocalizedName() {
		return ApiImpl.INSTANCE.currentLocalizer().localizeMaterialForm("block.jaopca."+form.getName(), material, getTranslationKey());
	}
}
