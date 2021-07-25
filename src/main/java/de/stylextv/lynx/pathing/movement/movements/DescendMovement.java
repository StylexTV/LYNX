package de.stylextv.lynx.pathing.movement.movements;

import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.pathing.calc.Node;
import de.stylextv.lynx.pathing.movement.Movement;

public class DescendMovement extends Movement {
	
	public DescendMovement(Node n) {
		super(n);
	}
	
	@Override
	public void onRenderTick() {
		lookAt(getNode());
		
		setPressed(InputAction.MOVE_FORWARD, true);
		setPressed(InputAction.SPRINT, true);
	}
	
}
