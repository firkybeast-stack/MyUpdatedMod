package net.uku3lig.betterhurtcam;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BetterHurtCam implements ModInitializer {
    public static boolean a = true;
    public static boolean b = false;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void scheduleTask(Runnable task, int ticksDelay) {
        long delayMs = (long) ticksDelay * 50L;
        scheduler.schedule(() -> MinecraftClient.getInstance().execute(task), delayMs, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onInitialize() {
    }
}