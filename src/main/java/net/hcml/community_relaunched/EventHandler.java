package net.hcml.community_relaunched;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import deci.i.i;
import net.hcml.community_relaunched.client.ServerListGetter;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.lwjgl.Sys;

public class EventHandler {
    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent event){
        //if(event.gui instanceof i) if(Addon.serverListGetter != null) Addon.serverListGetter.fetchServerList();
    }
}
