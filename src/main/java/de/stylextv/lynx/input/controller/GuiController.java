package de.stylextv.lynx.input.controller;

import de.stylextv.lynx.context.PlayerContext;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;

public class GuiController {
	
	public static ItemStack bestTool(BlockState state) {
		Inventory inv = PlayerContext.inventory();
		
		ItemStack best = null;
		
		float score = Float.NEGATIVE_INFINITY;
		
		for(int i = 0; i < 9; i++) {
			
			ItemStack stack = inv.getItem(i);
			
			float f = digSpeed(stack, state);
			
			if(stack.isDamageableItem() && f <= 1) {
				
				f = -stack.getBaseRepairCost() - 1;
			}
			
			if(f > score) {
				
				best = stack;
				score = f;
			}
		}
		
		return best;
	}
	
	public static float digSpeed(ItemStack stack, BlockState state) {
		float f = stack.getDestroySpeed(state);
		
		if(f <= 1) return f;
		
		int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack);
		
		if(level > 0) f += level * level + 1;
		
		return f;
	}
	
	// TODO check for full blocks only (or maybe just DIRT, STONE, ...)
	public static ItemStack buildingMaterial() {
		Inventory inv = PlayerContext.inventory();
		
		for(int i = 0; i < 9; i++) {
			
			ItemStack stack = inv.getItem(i);
			
			Item item = stack.getItem();
			
			if(item instanceof BlockItem) return stack;
		}
		
		return null;
	}
	
	public static boolean hasBuildingMaterial() {
		return buildingMaterial() != null;
	}
	
	public static void selectItem(ItemStack stack) {
		int slot = slotOf(stack);
		
		if(slot == -1) return;
		
		selectSlot(slot);
	}
	
	public static void selectSlot(int slot) {
		Inventory inv = PlayerContext.inventory();
		
		inv.selected = slot;
	}
	
	public static int slotOf(ItemStack stack) {
		Inventory inv = PlayerContext.inventory();
		
		for(int i = 0; i < 9; i++) {
			
			ItemStack other = inv.getItem(i);
			
			if(stack.equals(other, false)) return i;
		}
		
		return -1;
	}
	
}
