package thelm.jargca.compat.agricraft;

import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jargca.compat.agricraft.agriplants.AgriPlantFormType;

//Currently only holds the AgriPlant form type
@JAOPCAModule(modDependencies = "agricraft")
public class AgriCraftModule implements IModule {

	public AgriCraftModule() {
		AgriPlantFormType.init();
	}

	@Override
	public String getName() {
		return "agricraft";
	}

	@Override
	public void onMaterialComputeComplete(IModuleData moduleData) {
		AgriPlantFormType.registerEntries();
	}
}
