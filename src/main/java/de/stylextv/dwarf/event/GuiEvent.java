package de.stylextv.dwarf.event;

import de.stylextv.dwarf.gui.hook.GuiHook;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GuiEvent {
	
	@SubscribeEvent
	public void onOpen(GuiOpenEvent event) {
		Screen screen = event.getGui();
		
		if(GuiHook.isMenuGui(screen)) {
			GuiHook.installAccessor(screen);
		}
	}
	
}
