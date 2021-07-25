package de.stylextv.lynx.pathing.movement;

import de.stylextv.lynx.context.PlayerContext;
import de.stylextv.lynx.input.InputAction;
import de.stylextv.lynx.input.controller.InputController;
import de.stylextv.lynx.input.controller.ViewController;
import de.stylextv.lynx.pathing.calc.Node;
import net.minecraft.core.BlockPos;

public abstract class Movement {
	
	private Node node;
	
	public Movement(Node n) {
		this.node = n;
	}
	
	public abstract void onRenderTick();
	
	protected void setPressed(InputAction i, boolean pressed) {
		InputController.setPressed(i, pressed);
	}
	
	protected void lookAt(BlockPos pos) {
		ViewController.lookAt(pos);
	}
	
	protected void lookAt(Node n) {
		ViewController.lookAt(n);
	}
	
	protected void lookAt(Node n, boolean lookDown) {
		ViewController.lookAt(n, lookDown);
	}
	
	public MovementState getState() {
		BlockPos pos = PlayerContext.feetPosition();
		
		return getNode().equals(pos) ? MovementState.REACHED_NODE : MovementState.GOING;
	}
	
	public Node getNode() {
		return node;
	}
	
}
