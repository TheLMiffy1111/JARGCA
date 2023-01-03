package thelm.jargca.compat.agricraft.api.agriplants;

import com.agricraft.agricore.plant.AgriPlant;
import com.infinityraider.agricraft.api.v1.plant.IAgriPlant;

import thelm.jaopca.api.materialforms.IMaterialForm;
import thelm.jaopca.api.materialforms.IMaterialFormInfo;

public interface IAgriPlantInfo extends IMaterialFormInfo, IAgriPlantProvider {

	IMaterialFormAgriPlant getMaterialFormAgriPlant();

	IMaterialFormDummyAgriPlant getMaterialFormDummyAgriPlant();

	@Override
	default IAgriPlant asAgriPlant() {
		return getMaterialFormAgriPlant().asAgriPlant();
	}

	default AgriPlant asDummyAgriPlant() {
		return getMaterialFormDummyAgriPlant().asDummyAgriPlant();
	}

	@Override
	default IMaterialForm getMaterialForm() {
		return getMaterialFormAgriPlant();
	}
}
