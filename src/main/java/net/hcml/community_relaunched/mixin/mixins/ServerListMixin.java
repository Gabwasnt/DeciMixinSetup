package net.hcml.community_relaunched.mixin.mixins;


import deci.k.a;
import deci.k.b;
import net.hcml.community_relaunched.client.ServerListGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Mixin(value = {b.class}, remap = false)
public class ServerListMixin {

    /**
     * @author G_ab
     * @reason Set new Server List
     */
    @Overwrite
    private static List<a> bg() {
        CompletableFuture<List<a>> list = ServerListGetter.getServerList();
        List<a> arrayList = new ArrayList<>();
        try {
            arrayList = list.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
