package thelm.jargca.compat.thermalexpansion.recipes;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cofh.thermalexpansion.util.managers.machine.InsolatorManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thelm.jaopca.api.recipes.IRecipeAction;
import thelm.jaopca.utils.MiscHelper;

public class InsolatorRecipeAction implements IRecipeAction {

	private static final Logger LOGGER = LogManager.getLogger();

	public final ResourceLocation key;
	public final Object input;
	public final int inputCount;
	public final Object secondInput;
	public final int secondInputCount;
	public final Object output;
	public final int outputCount;
	public final Object secondOutput;
	public final int secondOutputCount;
	public final int secondOutputChance;
	public final int energy;
	public final int water;
	public final boolean isTree;

	public InsolatorRecipeAction(ResourceLocation key, Object input, int inputCount, Object secondInput, int secondInputCount, Object output, int outputCount, Object secondOutput, int secondOutputCount, int secondOutputChance, int energy, int water) {
		this(key, input, inputCount, secondInput, secondInputCount, output, outputCount, secondOutput, secondOutputCount, secondOutputChance, energy, water, false);
	}

	public InsolatorRecipeAction(ResourceLocation key, Object input, int inputCount, Object secondInput, int secondInputCount, Object output, int outputCount, Object secondOutput, int secondOutputCount, int secondOutputChance, int energy, int water, boolean isTree) {
		this.key = Objects.requireNonNull(key);
		this.input = input;
		this.inputCount = inputCount;
		this.secondInput = secondInput;
		this.secondInputCount = secondInputCount;
		this.output = output;
		this.outputCount = outputCount;
		this.secondOutput = secondOutput;
		this.secondOutputCount = secondOutputCount;
		this.secondOutputChance = secondOutputChance;
		this.energy = energy;
		this.water = water;
		this.isTree = isTree;
	}

	@Override
	public boolean register() {
		ItemStack in1 = MiscHelper.INSTANCE.getItemStack(input, inputCount);
		if(in1.isEmpty()) {
			throw new IllegalArgumentException("Empty ingredient in recipe "+key+": "+input);
		}
		ItemStack in2 = MiscHelper.INSTANCE.getItemStack(secondInput, secondInputCount);
		if(in2.isEmpty()) {
			throw new IllegalArgumentException("Empty ingredient in recipe "+key+": "+secondInput);
		}
		ItemStack stack1 = MiscHelper.INSTANCE.getItemStack(output, outputCount);
		ItemStack stack2 = MiscHelper.INSTANCE.getItemStack(secondOutput, secondOutputCount);
		if(stack1.isEmpty() && stack2.isEmpty()) {
			throw new IllegalArgumentException("Empty outputs in recipe "+key+": "+output+", "+secondOutput);
		}
		return InsolatorManager.addRecipe(energy, water, in1, in2, stack1, stack2, secondOutputChance, isTree ? InsolatorManager.Type.TREE : InsolatorManager.Type.STANDARD) != null;
	}
}
