package thelm.jargca.compat.jei;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;

@JEIPlugin
public class JARGCAJEIPlugin implements IModPlugin {

	private static final Multimap<String, IRecipeWrapper> RECIPES = MultimapBuilder.treeKeys().arrayListValues().build();

	@Override
	public void register(IModRegistry registry) {
		RECIPES.asMap().forEach((category, recipes)->registry.addRecipes(recipes, category));
	}

	public static void addJEIRecipe(String category, IRecipeWrapper recipe) {
		RECIPES.put(category, recipe);
	}
}
