package thelm.jargca.compat.mysticalagriculture.items;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.items.IItemFormSettings;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.blocks.BlockFormType;
import thelm.jaopca.forms.FormHandler;
import thelm.jaopca.items.JAOPCAItem;
import thelm.jargca.compat.mysticalagriculture.MysticalAgricultureModule;

public class JARGCASeedsItem extends JAOPCAItem implements IPlantable {


	protected OptionalInt tier = OptionalInt.empty();
	protected Optional<Block> crops = Optional.empty();

	public JARGCASeedsItem(IForm form, IMaterial material, IItemFormSettings settings) {
		super(form, material, settings);
	}

	public int getTier() {
		if(!tier.isPresent()) {
			tier = OptionalInt.of(MysticalAgricultureModule.tierFunction.applyAsInt(getMaterial()));
		}
		return tier.getAsInt();
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);
		IBlockState state = worldIn.getBlockState(pos);
		if(facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up())) {
			worldIn.setBlockState(pos.up(), this.getPlant(worldIn, pos));

			if(player instanceof EntityPlayerMP) {
				CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos.up(), itemstack);
			}

			itemstack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		if(!crops.isPresent()) {
			crops = Optional.of(BlockFormType.INSTANCE.getMaterialFormInfo(FormHandler.getForm("mysticalagriculture_crop"), getMaterial()).asBlock());
		}
		return crops.get().getDefaultState();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		String text = I18n.translateToLocal("tooltip.ma.tier")+TextFormatting.GRAY+": ";
		int tier = this.getTier();
		switch(tier) {
		case 1:
			tooltip.add(text+TextFormatting.YELLOW+tier);
			break;
		case 2:
			tooltip.add(text+TextFormatting.GREEN+tier);
			break;
		case 3:
			tooltip.add(text+TextFormatting.GOLD+tier);
			break;
		case 4:
			tooltip.add(text+TextFormatting.AQUA+tier);
			break;
		case 5:
			tooltip.add(text+TextFormatting.RED+tier);
			break;
		case 6:
			tooltip.add(text+TextFormatting.DARK_PURPLE+tier);
		}
	}
}
