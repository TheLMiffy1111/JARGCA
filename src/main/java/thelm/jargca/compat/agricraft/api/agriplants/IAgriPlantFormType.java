package thelm.jargca.compat.agricraft.api.agriplants;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormType;
import thelm.jaopca.api.materials.IMaterial;

public interface IAgriPlantFormType extends IFormType {

	@Override
	IAgriPlantFormSettings getNewSettings();

	@Override
	IAgriPlantFormSettings deserializeSettings(JsonElement jsonElement, JsonDeserializationContext context);

	@Override
	IAgriPlantInfo getMaterialFormInfo(IForm form, IMaterial material);
}
