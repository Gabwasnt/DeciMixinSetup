package net.hcml.community_relaunched.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import deci.k.a;
import net.hcml.community_relaunched.Addon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerListGetter {
    private static final String serverListURL = "https://raw.githubusercontent.com/ProjectArmadeus/Project-Armadeus-Public/main/serverlist.json";

    private static JsonObject listCache;

    private static List<a> buildList(JsonObject object){
        List<a> newlist = new ArrayList<>();
        JsonArray list = object.get("official").getAsJsonArray();
        list.forEach(jsonElement -> {
            JsonObject server = jsonElement.getAsJsonObject();
            String name = server.get("name").getAsString();
            String motd = server.get("motd").getAsString();
            String ip = server.get("ip").getAsString();
            int port = server.get("port").getAsInt();
            String serverType = server.get("servertype").getAsString();
            String mapType = server.get("maptype").getAsString();
            newlist.add((new a(name, motd, ip, port, serverType, mapType)).y(mapType));
        });
        return newlist;
    }

    public static CompletableFuture<List<a>> getServerList(){
        Addon.logger.info("Trying to fetch server list...");
        CompletableFuture<List<a>> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            try (InputStream inputStream = new URL(serverListURL).openStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                JsonObject object = new JsonParser().parse(bufferedReader).getAsJsonObject();
                completableFuture.complete(buildList(object));
                setListCache(object);
            } catch (Exception e) {
                Addon.logger.info("Failed to get server list at:"+serverListURL);
                completableFuture.complete(buildList(listCache));
            }
        }).start();
        return completableFuture;
    }

    public static void setListCache(JsonObject listCache) {
        ServerListGetter.listCache = listCache;
    }
}
