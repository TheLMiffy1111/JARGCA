package thelm.jargca.compat.agricraft.api.agriplants;

import java.util.List;
import java.util.function.IntFunction;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.ResourceLocation;

public interface IAgriPlantTextureFunctionCreator {

	IntFunction<List<Pair<ResourceLocation, Boolean>>> create(IMaterialFormAgriPlant agriPlant, IAgriPlantFormSettings settings);
}
