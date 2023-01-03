package thelm.jargca.compat.immersiveengineering.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import blusunrize.immersiveengineering.api.tool.BelljarHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import thelm.jaopca.api.recipes.IRecipeAction;
import thelm.jaopca.utils.MiscHelper;

public class BelljarCropRecipeAction implements IRecipeAction {

	private static final Logger LOGGER = LogManager.getLogger();

	public final ResourceLocation key;
	public final Object seed;
	public final Object soil;
	public final Object[] output;
	public final Object[] render;

	public BelljarCropRecipeAction(ResourceLocation key, Object seed, Object soil, Object[] output, Object[] render) {
		this.key = Objects.requireNonNull(key);
		this.seed = seed;
		this.soil = soil;
		this.output = output;
		this.render = render;
	}

	@Override
	public boolean register() {
		Ingredient seedIng = MiscHelper.INSTANCE.getIngredient(seed);
		if(seedIng == null) {
			throw new IllegalArgumentException("Empty ingredient in recipe "+key+": "+seed);
		}
		Ingredient soilIng = MiscHelper.INSTANCE.getIngredient(soil);
		if(soilIng == null) {
			throw new IllegalArgumentException("Empty ingredient in recipe "+key+": "+soil);
		}
		List<ItemStack> outputs = new ArrayList<>();
		int i = 0;
		while(i < output.length) {
			Object out = output[i];
			++i;
			Integer count = 1;
			if(i < output.length && output[i] instanceof Integer) {
				count = (Integer)output[i];
				++i;
			}
			ItemStack stack = MiscHelper.INSTANCE.getItemStack(out, count);
			if(stack.isEmpty()) {
				LOGGER.warn("Empty output in recipe {}: {}", key, out);
				continue;
			}
			outputs.add(stack);
		}
		if(outputs.isEmpty()) {
			throw new IllegalArgumentException("Empty outputs in recipe "+key+": "+Arrays.deepToString(output));
		}
		List<IBlockState> renders = new ArrayList<>();
		i = 0;
		while(i < render.length) {
			Object ren = render[i];
			++i;
			if(ren instanceof IBlockState) {
				renders.add((IBlockState)ren);
				continue;
			}
			if(ren instanceof Block) {
				renders.add(((Block)ren).getDefaultState());
				continue;
			}
			ItemStack stack = MiscHelper.INSTANCE.getItemStack(ren, 1);
			Block block = Block.getBlockFromItem(stack.getItem());
			renders.add(block.getDefaultState());
		}
		for(ItemStack in : seedIng.getMatchingStacks()) {
			BelljarHandler.cropHandler.register(in, outputs.toArray(new ItemStack[outputs.size()]), soilIng, renders.toArray(new IBlockState[renders.size()]));
		}
		return true;
	}
}
