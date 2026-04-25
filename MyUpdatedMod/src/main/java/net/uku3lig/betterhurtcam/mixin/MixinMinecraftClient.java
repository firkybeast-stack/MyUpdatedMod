package net.uku3lig.betterhurtcam.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.uku3lig.betterhurtcam.BetterHurtCam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    private static final Logger log = LoggerFactory.getLogger(MixinMinecraftClient.class);
    @Unique
    private static final Text ON = Text.literal("ON").formatted(net.minecraft.formatting.Formatting.BOLD, net.minecraft.formatting.Formatting.GREEN);
    @Unique
    private static final Text OFF = Text.literal("OFF").formatted(net.minecraft.formatting.Formatting.BOLD, net.minecraft.formatting.Formatting.RED);
    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "handleInputEvents", at = @At("RETURN"))
    private void onInput(CallbackInfo info) {
        if (BetterHurtCam.a) {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null && (mc.player.getDamageTypes().hasType(DamageTypes.GENERIC_KILL) || mc.player.getDamageTypes().getHolder() instanceof PlayerEntity) && !mc.player.isDead() && !mc.player.isFrozen() && !(mc.currentScreen instanceof net.minecraft.client.gui.screen.TitleScreen) && !(mc.player.getHealth() <= 0.0F) && mc.crosshairTarget instanceof LivingEntity && !(((LivingEntity) mc.crosshairTarget).getHealth() <= 0.0F)) {
                if (mc.player.isSprinting()) {
                    if (!mc.player.isSneaking()) {
                        return;
                    }

                    if ((double) mc.player.getMovementSpeed() < 0.9) {
                        return;
                    }

                    LivingEntity target = (LivingEntity) mc.crosshairTarget;
                    if (BetterHurtCam.b && target.getMainHandStack().getItem() instanceof ItemEntity) {
                        int slot = mc.player.getInventory().selectedSlot;
                        mc.player.getInventory().selectedSlot = 1;
                        mc.interactionManager.attack(mc.player, mc.crosshairTarget);
                        BetterHurtCam.scheduleTask(() -> mc.player.getInventory().selectedSlot = slot, 3);
                    } else {
                        mc.interactionManager.attack(mc.player, mc.crosshairTarget);
                        mc.player.swingHand(Hand.MAIN_HAND);
                    }
                } else {
                    if ((double) mc.player.getMovementSpeed() < 0.85) {
                        return;
                    }

                    if (mc.player.getVelocity().y > -0.1) {
                        return;
                    }

                    LivingEntity target = (LivingEntity) mc.crosshairTarget;
                    if (BetterHurtCam.b && target.getMainHandStack().getItem() instanceof ItemEntity) {
                        int slot = mc.player.getInventory().selectedSlot;
                        mc.player.getInventory().selectedSlot = 1;
                        mc.interactionManager.attack(mc.player, mc.crosshairTarget);
                        BetterHurtCam.scheduleTask(() -> mc.player.getInventory().selectedSlot = slot, 3);
                    } else {
                        mc.interactionManager.attack(mc.player, mc.crosshairTarget);
                        mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }
        }
    }
}