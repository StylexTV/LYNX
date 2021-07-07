package de.stylextv.lynx.command.commands;

import de.stylextv.lynx.command.Command;
import de.stylextv.lynx.pathing.calc.goal.Goal;
import de.stylextv.lynx.pathing.calc.goal.InvertedGoal;
import de.stylextv.lynx.state.StateManager;
import de.stylextv.lynx.util.ChatUtil;

public class InvertCommand extends Command {
	
	public InvertCommand() {
		super("invert", "Inverts the set goal.");
	}
	
	@Override
	public void execute(String[] args) {
		Goal goal = StateManager.getGoal();
		
		if(goal == null) {
			ChatUtil.sendToUser("�cNo goal present!");
			
			return;
		}
		
		StateManager.setGoal(new InvertedGoal(goal));
		
		ChatUtil.sendToUser("Goal inverted.");
	}
	
}
