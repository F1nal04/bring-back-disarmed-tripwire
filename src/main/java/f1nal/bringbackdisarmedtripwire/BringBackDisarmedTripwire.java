package f1nal.bringbackdisarmedtripwire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TripwireBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;

public class BringBackDisarmedTripwire implements ModInitializer {

    public static final String MOD_ID = "bring-back-disarmed-tripwire";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Register event to restore pre-1.21.1 behavior: shearing tripwire disarms instead of breaking
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (!(state.getBlock() instanceof TripwireBlock)) {
                return true; // not a tripwire, allow normal breaking
            }

            ItemStack mainHand = player.getMainHandStack();
            if (!(mainHand.getItem() instanceof ShearsItem)) {
                return true; // not using shears, allow normal breaking
            }

            // Replace first break with disarm
            if (state.contains(TripwireBlock.DISARMED) && !state.get(TripwireBlock.DISARMED)) {
                BlockState disarmed = state.with(TripwireBlock.DISARMED, true);
                world.setBlockState(pos, disarmed, Block.NOTIFY_ALL);
                // Damage shears by 1 durability to mirror vanilla interaction cost
                mainHand.damage(1, player, EquipmentSlot.MAINHAND);
                return false; // cancel the original break action (we disarmed instead)
            }

            // Already disarmed; allow normal breaking
            return true;
        });

        LOGGER.info("Bring Back Disarmed Tripwire initialized");
    }
}
