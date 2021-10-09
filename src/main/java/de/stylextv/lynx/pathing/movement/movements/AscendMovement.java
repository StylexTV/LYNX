package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Cost;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;
import de.stylextv.lynx.pathing.movement.MovementState;

public class AscendMovement extends Movement {
	
	public AscendMovement(Node source, Node destination) {
		super(source, destination);
	}
	
	@Override
	public void updateHelpers() {
		getBreakHelper().collectBlocks(getDestination(), 2);
		getBreakHelper().collectBlocks(getSource(), 2, 1);
		
		Node destination = getDestination();
		
		getPlaceHelper().collectBlock(destination, -1);
	}
	
	@Override
	public double cost() {
		double cost = Cost.JUMP + getBreakHelper().cost();
		
		cost += getPlaceHelper().cost();
		
		return cost + super.cost();
	}
	
	@Override
	public void onRenderTick() {
		if(getBreakHelper().onRenderTick()) return;
		
		if(!getPlaceHelper().onRenderTick()) {
			
			lookAt(getDestination());
			
			setPressed(InputAction.MOVE_FORWARD, true);
			setPressed(InputAction.SPRINT, true);
			
			boolean jump = getJumpHelper().shouldJump();
			
			setPressed(InputAction.JUMP, jump);
		}
	}
	
	@Override
	public MovementState getState() {
		if(getPlaceHelper().hasTargets()) return MovementState.PROCEEDING;
		
		return super.getState();
	}
	
}
