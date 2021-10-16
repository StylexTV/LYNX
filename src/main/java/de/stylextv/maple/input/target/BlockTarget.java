package de.stylextv.maple.input.target;

import de.stylextv.maple.input.controller.AwarenessController;
import de.stylextv.maple.input.controller.PlaceController;
import de.stylextv.maple.input.controller.ViewController;
import de.stylextv.maple.util.world.Offset;
import de.stylextv.maple.world.BlockInterface;
import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockTarget {
	
	private BlockPos pos;
	
	public BlockTarget(int x, int y, int z) {
		this(new BlockPos(x, y, z));
	}
	
	public BlockTarget(BlockPos pos) {
		this.pos = pos;
	}
	
	public boolean lookAt(boolean atNeighbour) {
		Offset o = atNeighbour ? visibleNeighbour() : visiblePoint();
		
		if(o == null) return false;
		
		ViewController.lookAt(o);
		
		return true;
	}
	
	public Offset visibleNeighbour() {
		BlockPos pos = getPos();
		
		for(Offset o : Offset.DIRECT_BLOCK_NEIGHBOURS) {
			
			int x = pos.getX() + o.getBlockX();
			int y = pos.getY() + o.getBlockY();
			int z = pos.getZ() + o.getBlockZ();
			
			if(PlaceController.canPlaceAgainst(x, y, z)) {
				
				double rx = pos.getX() + 0.5;
				double ry = pos.getY() + 0.5;
				double rz = pos.getZ() + 0.5;
				
				Offset o2 = o.divide(2);
				
				o2 = o2.add(rx, ry, rz);
				
				rx = o2.getX();
				ry = o2.getY();
				rz = o2.getZ();
				
				if(ViewController.canSee(o2)) return o2;
			}
		}
		
		return null;
	}
	
	public Offset visiblePoint() {
		Offset sum = new Offset();
		
		int n = 0;
		
		BlockPos pos = getPos();
		
		for(Offset o : Offset.TRIPLED_BLOCK_CORNERS) {
			
			double x = pos.getX() + o.getX();
			double y = pos.getY() + o.getY();
			double z = pos.getZ() + o.getZ();
			
			if(ViewController.canSee(x, y, z)) {
				
				sum = sum.add(x, y, z);
				
				n++;
			}
		}
		
		if(n == 0) return null;
		
		return sum.divide(n);
	}
	
	public boolean isInReach() {
		return AwarenessController.canReach(pos);
	}
	
	public boolean isSelected(boolean ignoreNeighbours) {
		BlockHitResult result = AwarenessController.getBlockTarget();
		
		if(result == null) return false;
		
		BlockPos p = result.getBlockPos();
		
		BlockPos pos = getPos();
		
		if(p.equals(pos)) return true;
		
		if(ignoreNeighbours) return false;
		
		Direction dir = result.getSide();
		
		return p.offset(dir).equals(pos);
	}
	
	public BlockState state() {
		return BlockInterface.getState(pos);
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
}
