package net.gabwasnt.deci_addon.proxy;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

public class CommonProxy {
    public void preInit(){

    }

    /**
     * Returns a side-appropriate EntityPlayer for use during message handling
     */
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }

    /**
     * Returns the current thread based on side during message handling,
     * used for ensuring that the message is being handled by the main thread
     */
    public WorldServer getThreadFromContext(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity.getServerForPlayer();
    }
}
