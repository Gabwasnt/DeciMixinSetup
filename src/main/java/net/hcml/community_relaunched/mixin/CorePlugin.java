package net.hcml.community_relaunched.mixin;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.hcml.community_relaunched.Addon;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class CorePlugin implements IFMLLoadingPlugin {
    private static final Path MODS_DIR = (new File(Launch.minecraftHome, "mods/")).toPath();

    public static File findFileOfName(String name) throws IOException{
        return Files.walk(MODS_DIR, new java.nio.file.FileVisitOption[0])
                .filter(path -> {
                    String file = path.getFileName().toString().toLowerCase();
                    if (file.contains("voice")) return false;
                    if (!path.toString().endsWith(".jar")) return false;
                    return file.contains(name);
                })
                .map(Path::toFile).findFirst().orElse(null);
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        if (!(Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
            Addon.logger.info(Addon.getLogPrefix()+ " searching in " + MODS_DIR + " for Decimation");
            try {
                File decimationJar = findFileOfName("decimation");
                if (decimationJar.exists()) try {
                    LaunchClassLoader loader = Launch.classLoader;
                    loader.addURL(decimationJar.toURI().toURL());
                    loader.getSources().remove(loader.getSources().size() - 1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins."+ Addon.MOD_ID+".json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
