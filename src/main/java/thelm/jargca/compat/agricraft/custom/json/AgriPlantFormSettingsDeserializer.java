package thelm.jargca.compat.agricraft.custom.json;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import thelm.jaopca.api.helpers.IJsonHelper;
import thelm.jaopca.forms.FormTypeHandler;
import thelm.jaopca.utils.JsonHelper;
import thelm.jargca.compat.agricraft.agriplants.AgriPlantFormType;
import thelm.jargca.compat.agricraft.api.agriplants.IAgriPlantFormSettings;
import thelm.jargca.compat.agricraft.api.agriplants.RenderType;

public class AgriPlantFormSettingsDeserializer implements JsonDeserializer<IAgriPlantFormSettings> {

	public static final AgriPlantFormSettingsDeserializer INSTANCE = new AgriPlantFormSettingsDeserializer();

	private AgriPlantFormSettingsDeserializer() {}

	public IAgriPlantFormSettings deserialize(JsonElement jsonElement, JsonDeserializationContext context) {
		return deserialize(jsonElement, IAgriPlantFormSettings.class, context);
	}

	@Override
	public IAgriPlantFormSettings deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		IJsonHelper helper = JsonHelper.INSTANCE;
		JsonObject json = helper.getJsonObject(jsonElement, "settings");
		IAgriPlantFormSettings settings = AgriPlantFormType.INSTANCE.getNewSettings();
		if(json.has("seedFormats")) {
			JsonObject functionJson = helper.getJsonObject(json, "seedFormats");
			if(!functionJson.has("default")) {
				functionJson.add("default", new JsonArray());
			}
			settings.setSeedFormatsFunction(helper.deserializeType(json, "seedFormats", context, AgriPlantFormType.STRING_LIST_FUNCTION_TYPE));
		}
		if(json.has("isWeed")) {
			settings.setIsWeed(helper.getBoolean(json, "isWeed"));
		}
		if(json.has("isAggressive")) {
			settings.setIsAggressive(helper.getBoolean(json, "isAggressive"));
		}
		if(json.has("isFertilizable")) {
			settings.setIsFertilizable(helper.getBoolean(json, "isFertilizable"));
		}
		if(json.has("spreadChance")) {
			JsonObject functionJson = helper.getJsonObject(json, "spreadChance");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 0.1);
			}
			settings.setSpreadChanceFunction(helper.deserializeType(json, "spreadChance", context, FormTypeHandler.DOUBLE_FUNCTION_TYPE));
		}
		if(json.has("spawnChance")) {
			JsonObject functionJson = helper.getJsonObject(json, "spawnChance");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 0);
			}
			settings.setSpawnChanceFunction(helper.deserializeType(json, "spawnChance", context, FormTypeHandler.DOUBLE_FUNCTION_TYPE));
		}
		if(json.has("growthChanceBase")) {
			JsonObject functionJson = helper.getJsonObject(json, "growthChanceBase");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 0.9);
			}
			settings.setGrowthChanceBaseFunction(helper.deserializeType(json, "growthChanceBase", context, FormTypeHandler.DOUBLE_FUNCTION_TYPE));
		}
		if(json.has("growthChanceBonus")) {
			JsonObject functionJson = helper.getJsonObject(json, "growthChanceBonus");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 0.025);
			}
			settings.setGrowthChanceBonusFunction(helper.deserializeType(json, "growthChanceBonus", context, FormTypeHandler.DOUBLE_FUNCTION_TYPE));
		}
		if(json.has("seedDropChanceBase")) {
			JsonObject functionJson = helper.getJsonObject(json, "seedDropChanceBase");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 1);
			}
			settings.setSeedDropChanceBaseFunction(helper.deserializeType(json, "seedDropChanceBase", context, FormTypeHandler.DOUBLE_FUNCTION_TYPE));
		}
		if(json.has("seedDropChanceBonus")) {
			JsonObject functionJson = helper.getJsonObject(json, "seedDropChanceBonus");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 0);
			}
			settings.setSeedDropChanceBonusFunction(helper.deserializeType(json, "seedDropChanceBonus", context, FormTypeHandler.DOUBLE_FUNCTION_TYPE));
		}
		if(json.has("grassDropChance")) {
			JsonObject functionJson = helper.getJsonObject(json, "grassDropChance");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 0);
			}
			settings.setGrassDropChanceFunction(helper.deserializeType(json, "grassDropChance", context, FormTypeHandler.DOUBLE_FUNCTION_TYPE));
		}
		if(json.has("growthStages")) {
			JsonObject functionJson = helper.getJsonObject(json, "growthStages");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 8);
			}
			settings.setGrowthStagesFunction(helper.deserializeType(json, "growthStages", context, FormTypeHandler.INT_FUNCTION_TYPE));
		}
		if(json.has("tier")) {
			JsonObject functionJson = helper.getJsonObject(json, "tier");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 1);
			}
			settings.setTierFunction(helper.deserializeType(json, "tier", context, FormTypeHandler.INT_FUNCTION_TYPE));
		}
		if(json.has("soils")) {
			JsonObject functionJson = helper.getJsonObject(json, "soils");
			if(!functionJson.has("default")) {
				JsonArray jsonArr = new JsonArray();
				jsonArr.add("farmland_soil");
				functionJson.add("default", jsonArr);
			}
			settings.setSoilsFunction(helper.deserializeType(json, "soils", context, AgriPlantFormType.STRING_LIST_FUNCTION_TYPE));
		}
		if(json.has("conditions")) {
			JsonObject functionJson = helper.getJsonObject(json, "conditions");
			if(!functionJson.has("default")) {
				functionJson.add("default", new JsonArray());
			}
			settings.setConditionsFunction(helper.deserializeType(json, "conditions", context, AgriPlantFormType.CONDITION_LIST_FUNCTION_TYPE));
		}
		if(json.has("minLight")) {
			JsonObject functionJson = helper.getJsonObject(json, "minLight");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 10);
			}
			settings.setTierFunction(helper.deserializeType(json, "minLight", context, FormTypeHandler.INT_FUNCTION_TYPE));
		}
		if(json.has("maxLight")) {
			JsonObject functionJson = helper.getJsonObject(json, "maxLight");
			if(!functionJson.has("default")) {
				functionJson.addProperty("default", 16);
			}
			settings.setTierFunction(helper.deserializeType(json, "maxLight", context, FormTypeHandler.INT_FUNCTION_TYPE));
		}
		if(json.has("products")) {
			JsonObject functionJson = helper.getJsonObject(json, "products");
			if(!functionJson.has("default")) {
				functionJson.add("default", new JsonArray());
			}
			settings.setProductsFunction(helper.deserializeType(json, "products", context, AgriPlantFormType.PRODUCT_LIST_FUNCTION_TYPE));
		}
		if(json.has("renderType")) {
			settings.setRenderType(helper.deserializeType(json, "renderType", context, RenderType.class));
		}
		return settings;
	}
}
