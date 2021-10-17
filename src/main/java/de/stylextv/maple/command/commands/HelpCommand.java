package de.stylextv.maple.command.commands;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.maple.command.ArgumentHelper;
import de.stylextv.maple.command.Command;
import de.stylextv.maple.command.CommandManager;
import de.stylextv.maple.context.WorldContext;
import de.stylextv.maple.util.TextUtil;
import de.stylextv.maple.util.chat.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.math.Vec3d;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "Gives useful information about commands.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Block block = Blocks.REDSTONE_ORE;
		
		LootContext.Builder builder = (new LootContext.Builder(WorldContext.world())).random(world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity);
		
		System.out.println(block.getDroppedStacks(block.getDefaultState(), builder));
		
		Integer page = null;
		
		if(args.length == 0) {
			page = 1;
		} else {
			page = ArgumentHelper.toInt(args[0]);
		}
		
		if(page != null) {
			ChatUtil.send("Use §o#help <command> §7to get help for individual commands.", "", "Commands:");
			
			List<String> list = new ArrayList<>();
			
			for(Command c : CommandManager.getCommands()) {
				String name = c.getName();
				
				list.add(name);
			}
			
			ChatUtil.sendList(list, 4, page, getName());
			
			return true;
		}
		
		Command c = CommandManager.getCommand(args[0]);
		
		if(c == null) {
			ChatUtil.send("§cCouldn't find command!");
			
			return true;
		}
		
		String name = c.getName();
		String description = c.getDescription();
		String[] aliases = c.getAliases();
		
		ChatUtil.send("#" + name + ":", description);
		
		if(aliases.length != 0) {
			
			String s2 = TextUtil.combine(aliases, ", ");
			
			ChatUtil.send("Aliases: " + s2);
		}
		
		String[] usages = c.getUsages();
		
		if(usages == null) return true;
		
		ChatUtil.send("", "Usages:");
		
		for(String s2 : usages) {
			ChatUtil.send(" - " + name + " " + s2);
		}
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return new String[] {"[page]", "[command]"};
	}
	
}
