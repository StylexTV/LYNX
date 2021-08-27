package de.stylextv.lynx.command.commands;

import java.util.ArrayList;
import java.util.List;

import de.stylextv.lynx.command.ArgumentHelper;
import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.option.Option;
import de.stylextv.lynx.option.Options;
import de.stylextv.lynx.util.chat.ChatUtil;

public class ModifiedCommand extends Command {
	
	public ModifiedCommand() {
		super("modified", "Shows all modified options.");
	}
	
	@Override
	public boolean execute(String[] args) {
		Integer page = null;
		
		if(args.length != 0) {
			page = ArgumentHelper.toInt(args[0]);
		}
		
		if(page == null) page = 1;
		
		ChatUtil.send("Modified options:");
		
		List<String> list = new ArrayList<>();
		
		Option<?>[] options = Options.getOptions();
		
		for(Option<?> option : options) {
			if(option.wasModified()) {
				
				String name = option.getName();
				String type = "Boolean";
				
				String s = String.format("�7%s �8(%s)", name, type);
				
				list.add(s);
			}
		}
		
		ChatUtil.sendList(list, 7, page, getName());
		
		return true;
	}
	
	@Override
	public String[] getUsages() {
		return new String[] {"[page]"};
	}
	
}
