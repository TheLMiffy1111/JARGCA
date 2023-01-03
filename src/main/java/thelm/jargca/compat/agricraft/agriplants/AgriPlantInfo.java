package thelm.jargca.compat.agricraft.agriplants;

import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantInfo;
import thelm.jargca.compat.agricraft.api.agriplants.IMaterialFormAgriPlant;
import thelm.jargca.compat.agricraft.api.agriplants.IMaterialFormDummyAgriPlant;

class AgriPlantInfo implements IAgriPlantInfo {

	private IMaterialFormAgriPlant agriPlant;
	private IMaterialFormDummyAgriPlant dummyAgriPlant;

	AgriPlantInfo(IMaterialFormAgriPlant agriPlant, IMaterialFormDummyAgriPlant dummyAgriPlant) {
		this.agriPlant = agriPlant;
		this.dummyAgriPlant = dummyAgriPlant;
	}

	@Override
	public IMaterialFormAgriPlant getMaterialFormAgriPlant() {
		return agriPlant;
	}

	@Override
	public IMaterialFormDummyAgriPlant getMaterialFormDummyAgriPlant() {
		return dummyAgriPlant;
	}
}
