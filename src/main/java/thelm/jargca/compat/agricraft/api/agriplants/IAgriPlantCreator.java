package thelm.jargca.compat.agricraft.api.agriplants;

import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.materials.IMaterial;

public interface IAgriPlantCreator {

	IMaterialFormAgriPlant create(IForm form, IMaterial material, IAgriPlantFormSettings settings);
}
