package thelm.jargca.compat.agricraft.api.agriplants;

import com.infinityraider.agricraft.api.v1.plant.IAgriPlant;

import thelm.jaopca.api.materialforms.IMaterialForm;

public interface IMaterialFormAgriPlant extends IMaterialForm {

	default IAgriPlant asAgriPlant() {
		return (IAgriPlant)this;
	}
}
