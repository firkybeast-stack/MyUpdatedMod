package net.uku3lig.betterhurtcam.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.uku3lig.betterhurtcam.BetterHurtCam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.Keyboard.class)
public class MixinKeyboardHandler {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    @Unique
    private static final Text ON = Text.literal("ON").formatted(net.minecraft.formatting.Formatting.BOLD, net.minecraft.formatting.Formatting.GREEN);
    @Unique
    private static final Text OFF = Text.literal("OFF").formatted(net.minecraft.formatting.Formatting.BOLD, net.minecraft.formatting.Formatting.RED);

    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKeyPressed(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (this.mc.currentScreen == null) {
            if (action == InputUtil.GLFW_KEY_PRESSED && key == 66) { // B key
                BetterHurtCam.a = !BetterHurtCam.a;
            }

            if (action == InputUtil.GLFW_KEY_PRESSED && key == 74) { // J key
                BetterHurtCam.b = !BetterHurtCam.b;
            }
        }
    }
}