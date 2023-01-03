package thelm.jargca.compat.agricraft.api.agriplants;

import com.agricraft.agricore.plant.AgriPlant;

import thelm.jaopca.api.materialforms.IMaterialForm;

public interface IMaterialFormDummyAgriPlant extends IMaterialForm {

	default AgriPlant asDummyAgriPlant() {
		return (AgriPlant)this;
	}
}
