package thelm.jargca.compat.agricraft.client.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.ResourceLocation;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.utils.MiscHelper;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantFormSettings;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantTextureFunctionCreator;
import thelm.jargca.compat.agricraft.api.agriplants.IMaterialFormAgriPlant;

public class JAOPCAAgriPlantTextureFunctionCreator implements IAgriPlantTextureFunctionCreator {

	public static final JAOPCAAgriPlantTextureFunctionCreator INSTANCE = new JAOPCAAgriPlantTextureFunctionCreator(i->true, i->i);

	private final IntPredicate hasColor;
	private final IntUnaryOperator textureIndex;

	public JAOPCAAgriPlantTextureFunctionCreator(IntPredicate hasColor, IntUnaryOperator textureIndex) {
		this.hasColor = hasColor;
		this.textureIndex = textureIndex;
	}

	@Override
	public IntFunction<List<Pair<ResourceLocation, Boolean>>> create(IMaterialFormAgriPlant agriPlant, IAgriPlantFormSettings settings) {
		return growthStage->{
			growthStage %= agriPlant.asAgriPlant().getGrowthStages();
			List<Pair<ResourceLocation, Boolean>> ret = new ArrayList<>(2);
			ResourceLocation baseLocation = getBaseTextureLocation(agriPlant, growthStage);
			if(hasColor.test(growthStage)) {
				ResourceLocation overlayLocation = getOverlayTextureLocation(agriPlant, growthStage);
				if(overlayLocation != null) {
					ret.add(Pair.of(overlayLocation, false));
				}
				ret.add(Pair.of(baseLocation, true));
			}
			else {
				ret.add(Pair.of(baseLocation, false));
			}
			return ret;
		};
	}

	public ResourceLocation getBaseTextureLocation(IMaterialFormAgriPlant agriPlant, int growthStage) {
		IForm form = agriPlant.getForm();
		IMaterial material = agriPlant.getMaterial();
		int index = textureIndex.applyAsInt(growthStage);
		if(MiscHelper.INSTANCE.hasResource(new ResourceLocation("jaopca", "textures/blocks/"+form.getName()+'.'+material.getName()+'_'+index+".png"))) {
			return new ResourceLocation("jaopca", "blocks/"+form.getName()+'.'+material.getName()+'_'+index);
		}
		return new ResourceLocation("jaopca", "blocks/"+material.getModelType()+'/'+form.getName()+'_'+index);
	}

	public ResourceLocation getOverlayTextureLocation(IMaterialFormAgriPlant agriPlant, int growthStage) {
		IForm form = agriPlant.getForm();
		IMaterial material = agriPlant.getMaterial();
		int index = textureIndex.applyAsInt(growthStage);
		if(MiscHelper.INSTANCE.hasResource(new ResourceLocation("jaopca", "textures/blocks/"+form.getName()+'.'+material.getName()+'_'+index+"_overlay.png"))) {
			return new ResourceLocation("jaopca", "blocks/"+form.getName()+'.'+material.getName()+'_'+index+"_overlay");
		}
		if(MiscHelper.INSTANCE.hasResource(new ResourceLocation("jaopca", "textures/blocks/"+material.getModelType()+'/'+form.getName()+'_'+index+"_overlay.png"))) {
			return new ResourceLocation("jaopca", "blocks/"+material.getModelType()+'/'+form.getName()+'_'+index+"_overlay");
		}
		return null;
	}
}
