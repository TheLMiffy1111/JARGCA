package thelm.jargca.compat.agricraft.agriplants;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import com.agricraft.agricore.core.AgriCore;
import com.agricraft.agricore.plant.AgriPlant;
import com.google.common.collect.TreeBasedTable;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.infinityraider.agricraft.api.v1.AgriApi;
import com.infinityraider.agricraft.api.v1.plant.IAgriPlant;

import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.custom.json.EnumDeserializer;
import thelm.jaopca.custom.json.MaterialFunctionDeserializer;
import thelm.jaopca.forms.FormTypeHandler;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;
import thelm.jargca.compat.agricraft.api.agriplants.Condition;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantFormSettings;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantFormType;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantInfo;
import thelm.jargca.compat.agricraft.api.agriplants.IMaterialFormAgriPlant;
import thelm.jargca.compat.agricraft.api.agriplants.IMaterialFormDummyAgriPlant;
import thelm.jargca.compat.agricraft.api.agriplants.Product;
import thelm.jargca.compat.agricraft.api.agriplants.RenderType;
import thelm.jargca.compat.agricraft.custom.json.AgriPlantFormSettingsDeserializer;

public class AgriPlantFormType implements IAgriPlantFormType {

	private AgriPlantFormType() {}

	public static final AgriPlantFormType INSTANCE = new AgriPlantFormType();
	private static final TreeSet<IForm> FORMS = new TreeSet<>();
	private static final TreeBasedTable<IForm, IMaterial, IMaterialFormAgriPlant> AGRI_PLANTS = TreeBasedTable.create();
	private static final TreeBasedTable<IForm, IMaterial, IMaterialFormDummyAgriPlant> DUMMY_AGRI_PLANTS = TreeBasedTable.create();
	private static final TreeBasedTable<IForm, IMaterial, IAgriPlantInfo> AGRI_PLANT_INFOS = TreeBasedTable.create();

	public static final Type STRING_LIST_FUNCTION_TYPE = new TypeToken<Function<IMaterial, List<String>>>(){}.getType();
	public static final Type CONDITION_LIST_FUNCTION_TYPE = new TypeToken<Function<IMaterial, List<Condition>>>(){}.getType();
	public static final Type PRODUCT_LIST_FUNCTION_TYPE = new TypeToken<Function<IMaterial, List<Product>>>(){}.getType();

	public static void init() {
		FormTypeHandler.registerFormType(INSTANCE);
	}

	@Override
	public String getName() {
		return "agri_plant";
	}

	@Override
	public void addForm(IForm form) {
		FORMS.add(form);
	}

	@Override
	public Set<IForm> getForms() {
		return Collections.unmodifiableNavigableSet(FORMS);
	}

	@Override
	public boolean shouldRegister(IForm form, IMaterial material) {
		String fluidName = MiscHelper.INSTANCE.getFluidName(form.getSecondaryName(), material.getName());
		return !AgriApi.getPlantRegistry().has(fluidName);
	}

	@Override
	public IAgriPlantInfo getMaterialFormInfo(IForm form, IMaterial material) {
		IAgriPlantInfo info = AGRI_PLANT_INFOS.get(form, material);
		if(info == null && FORMS.contains(form) && form.getMaterials().contains(material)) {
			info = new AgriPlantInfo(AGRI_PLANTS.get(form, material), DUMMY_AGRI_PLANTS.get(form, material));
			AGRI_PLANT_INFOS.put(form, material, info);
		}
		return info;
	}

	@Override
	public IAgriPlantFormSettings getNewSettings() {
		return new AgriPlantFormSettings();
	}

	@Override
	public GsonBuilder configureGsonBuilder(GsonBuilder builder) {
		return builder.
				registerTypeAdapter(STRING_LIST_FUNCTION_TYPE, MaterialFunctionDeserializer.INSTANCE).
				registerTypeAdapter(CONDITION_LIST_FUNCTION_TYPE, MaterialFunctionDeserializer.INSTANCE).
				registerTypeAdapter(PRODUCT_LIST_FUNCTION_TYPE, MaterialFunctionDeserializer.INSTANCE).
				registerTypeAdapter(RenderType.class, EnumDeserializer.INSTANCE);
	}

	@Override
	public IAgriPlantFormSettings deserializeSettings(JsonElement jsonElement, JsonDeserializationContext context) {
		return AgriPlantFormSettingsDeserializer.INSTANCE.deserialize(jsonElement, context);
	}

	public static void registerEntries() {
		ApiImpl api = ApiImpl.INSTANCE;
		MiscHelper helper = MiscHelper.INSTANCE;
		for(IForm form : FORMS) {
			IAgriPlantFormSettings settings = (IAgriPlantFormSettings)form.getSettings();
			String secondaryName = form.getSecondaryName();
			for(IMaterial material : form.getMaterials()) {
				IMaterialFormAgriPlant materialFormAgriPlant = settings.getAgriPlantCreator().create(form, material, settings);
				IAgriPlant agriPlant = materialFormAgriPlant.asAgriPlant();
				AGRI_PLANTS.put(form, material, materialFormAgriPlant);
				AgriApi.getPlantRegistry().add(agriPlant);

				IMaterialFormDummyAgriPlant materialFormDummyAgriPlant = settings.getDummyAgriPlantCreator().create(materialFormAgriPlant, settings);
				AgriPlant dummyAgriPlant = materialFormDummyAgriPlant.asDummyAgriPlant();
				DUMMY_AGRI_PLANTS.put(form, material, materialFormDummyAgriPlant);
				AgriCore.getPlants().addPlant(dummyAgriPlant);
			}
		}
	}

	public static Collection<IMaterialFormAgriPlant> getAgriPlants() {
		return AGRI_PLANTS.values();
	}

	public static Collection<IMaterialFormDummyAgriPlant> getDummyAgriPlants() {
		return DUMMY_AGRI_PLANTS.values();
	}
}
