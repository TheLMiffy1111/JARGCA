package thelm.jargca.compat.agricraft.agriplants;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.agricraft.agricore.plant.AgriPlant;
import com.agricraft.agricore.plant.AgriTexture;

import net.minecraft.util.ResourceLocation;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantFormSettings;
import thelm.jargca.compat.agricraft.api.agriplants.IMaterialFormAgriPlant;
import thelm.jargca.compat.agricraft.api.agriplants.IMaterialFormDummyAgriPlant;

public class JAOPCADummyAgriPlant extends AgriPlant implements IMaterialFormDummyAgriPlant {

	private final IMaterialFormAgriPlant agriPlant;
	protected final IAgriPlantFormSettings settings;

	public JAOPCADummyAgriPlant(IMaterialFormAgriPlant agriPlant, IAgriPlantFormSettings settings) {
		this.agriPlant = agriPlant;
		this.settings = settings;
	}

	@Override
	public IForm getForm() {
		return agriPlant.getForm();
	}

	@Override
	public IMaterial getMaterial() {
		return agriPlant.getMaterial();
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public String getId() {
		return agriPlant.asAgriPlant().getId();
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public AgriTexture getTexture() {
		return new AgriTexture() {
			@Override
			public List<String> getAllTextures() {
				List<String> ret = new ArrayList<>();
				ret.add(agriPlant.asAgriPlant().getSeedTexture().toString());
				for(int i = 0; i < agriPlant.asAgriPlant().getGrowthStages(); ++i) {
					for(Pair<ResourceLocation, Boolean> pair : settings.getAgriPlantTextureFunctionCreator().create(agriPlant, settings).apply(i)) {
						ret.add(pair.getLeft().toString());
					}
				}
				return ret;
			}
		};
	}
}
