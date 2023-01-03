package thelm.jargca.compat.agricraft;

import com.infinityraider.agricraft.api.v1.util.FuzzyStack;

import thelm.jaopca.utils.MiscHelper;

public class AgriCraftHelper {

	public static final AgriCraftHelper INSTANCE = new AgriCraftHelper();

	private AgriCraftHelper() {}

	public FuzzyStack getFuzzyStack(Object obj, int count) {
		return new FuzzyStack(MiscHelper.INSTANCE.getItemStack(obj, count), false, false, "*");
	}
}
