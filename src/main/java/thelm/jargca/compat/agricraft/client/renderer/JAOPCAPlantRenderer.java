package thelm.jargca.compat.agricraft.client.renderer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.infinityraider.infinitylib.render.tessellation.ITessellator;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jargca.compat.agricraft.api.agriplants.RenderType;

@SideOnly(Side.CLIENT)
public class JAOPCAPlantRenderer {

	public static void renderPlant(ITessellator tessellator, List<Pair<ResourceLocation, Boolean>> textureList, RenderType renderType, int color) {
		List<Pair<TextureAtlasSprite, Boolean>> icons = textureList.stream().
				map(pair->Pair.of(tessellator.getIcon(pair.getLeft()), pair.getRight())).
				collect(Collectors.toList());
		switch(renderType) {
		case CROSS:
			for(Pair<TextureAtlasSprite, Boolean> pair : icons) {
				if(pair.getLeft() != null) {
					renderCrossPattern(tessellator, pair.getLeft(), pair.getRight() ? color : 0xFFFFFF);
				}
			}
			break;
		case HASH:
			for(Pair<TextureAtlasSprite, Boolean> pair : icons) {
				if(pair.getLeft() != null) {
					renderHashPattern(tessellator, pair.getLeft(), pair.getRight() ? color : 0xFFFFFF);
				}
			}
			break;
		}
	}

	private static void renderHashPattern(ITessellator tessellator, TextureAtlasSprite icon, int color) {
		float rPrev = tessellator.getRed();
		float gPrev = tessellator.getGreen();
		float bPrev = tessellator.getBlue();
		float r = (color>>16&0xFF)/255F;
		float g = (color>> 8&0xFF)/255F;
		float b = (color    &0xFF)/255F;
		tessellator.setColorRGB(r, g, b);
		tessellator.drawScaledFaceDouble(0, 0, 16, 16, EnumFacing.NORTH, icon, 4);
		tessellator.drawScaledFaceDouble(0, 0, 16, 16, EnumFacing.EAST, icon, 4);
		tessellator.drawScaledFaceDouble(0, 0, 16, 16, EnumFacing.NORTH, icon, 12);
		tessellator.drawScaledFaceDouble(0, 0, 16, 16, EnumFacing.EAST, icon, 12);
		tessellator.setColorRGB(rPrev, gPrev, bPrev);
	}

	private static void renderCrossPattern(ITessellator tessellator, TextureAtlasSprite icon, int color) {
		float rPrev = tessellator.getRed();
		float gPrev = tessellator.getGreen();
		float bPrev = tessellator.getBlue();
		float r = (color>>16&0xFF)/255F;
		float g = (color>> 8&0xFF)/255F;
		float b = (color    &0xFF)/255F;
		tessellator.setColorRGB(r, g, b);
		tessellator.pushMatrix();
		tessellator.translate(0.5F, 0, 0.5F);
		tessellator.rotate(45, 0, 1, 0);
		tessellator.translate(-0.5F, 0, -0.5F);
		tessellator.drawScaledFaceDouble(0, 0, 16, 16, EnumFacing.NORTH, icon, 8);
		tessellator.drawScaledFaceDouble(0, 0, 16, 16, EnumFacing.EAST, icon, 8);
		tessellator.popMatrix();
		tessellator.setColorRGB(rPrev, gPrev, bPrev);
	}
}
