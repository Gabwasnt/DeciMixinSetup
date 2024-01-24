package net.hcml.community_relaunched;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.hcml.community_relaunched.client.ServerListGetter;
import net.hcml.community_relaunched.network.PacketDispatcher;
import net.hcml.community_relaunched.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Addon.MOD_ID, version = Addon.MOD_VERSION,dependencies = "required-after:deci@[1.21.10,);")
public class Addon {
    public static final String MOD_ID = "community_relaunched";
    public static final String MOD_NAME = "Relaunched";
    public static final String MOD_VERSION = "1.0.0";

    public static final Logger logger = LogManager.getLogger(MOD_ID);

    public static ServerListGetter serverListGetter;

    @Mod.Instance(MOD_ID)
    public static Addon instance;

    @SidedProxy(clientSide = "net.hcml.community_relaunched.proxy.ClientProxy", serverSide = "net.hcml.community_relaunched.proxy.CommonProxy")
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
        MinecraftForge.EVENT_BUS.register(new EventHandler());

        JsonObject mainServer = new JsonObject();

        mainServer.add("name",new JsonPrimitive("EU-BUKOVKA"));
        mainServer.add("motd",new JsonPrimitive("Bukovka - §9EU"));
        mainServer.add("ip",new JsonPrimitive("eu.mcdecimation.net"));
        mainServer.add("port",new JsonPrimitive("25566"));
        mainServer.add("servertype",new JsonPrimitive("§aOfficial"));
        mainServer.add("maptype",new JsonPrimitive("bukovka"));

        JsonArray list = new JsonArray();
        list.add(mainServer);
        JsonObject preCache = new JsonObject();
        preCache.add("official",list);
        ServerListGetter.setListCache(preCache);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Beginning post-initialization");
    }

    public static String getFormattedName() {
        return MOD_NAME + " v" + MOD_VERSION;
    }
    public static String getLogPrefix(){
        return "[" + Addon.getFormattedName() + "]";
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        logger.info("initalise FMLServerStartingEvent :" + getFormattedName());
    }

    public static File getDirectory() {
        File dir = new File(Minecraft.getMinecraft().mcDataDir, "community_relaunched");
        dir.mkdir();
        return dir;
    }
}