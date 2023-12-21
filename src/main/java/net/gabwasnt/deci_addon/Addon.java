package net.gabwasnt.deci_addon;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.gabwasnt.deci_addon.network.PacketDispatcher;
import net.gabwasnt.deci_addon.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Addon.MOD_ID, version = Addon.MOD_VERSION)
public class Addon {
    public static final String MOD_ID = "deci_addon";
    public static final String MOD_NAME = "DeciAddon";
    public static final String MOD_VERSION = "1.0.0";

    public static final Logger logger = LogManager.getLogger(MOD_ID);

    @Mod.Instance(MOD_ID)
    public static Addon instance;

    @SidedProxy(clientSide = "net.gabwasnt.deci_addon.proxy.ClientProxy", serverSide = "net.gabwasnt.decimated.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger.info("Beginning pre-initialization");

        proxy.preInit();

        PacketDispatcher.registerPackets();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        logger.info("Beginning initialization");
        EventHandler eventHandler = new EventHandler();
        FMLCommonHandler.instance().bus().register(eventHandler);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Beginning post-initialization");
    }

    public static String getFormattedName() {
        return MOD_NAME + "v" + MOD_VERSION;
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        logger.info("initalise FMLServerStartingEvent :" + getFormattedName());
    }

    public static File getDirectory() {
        File dir = new File(Minecraft.getMinecraft().mcDataDir, "deci_addon");
        dir.mkdir();
        return dir;
    }
}