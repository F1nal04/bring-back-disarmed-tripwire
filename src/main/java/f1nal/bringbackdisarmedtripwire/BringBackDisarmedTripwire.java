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

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (!(state.getBlock() instanceof TripwireBlock)) {
                return true;
            }

            ItemStack mainHand = player.getMainHandStack();
            if (!(mainHand.getItem() instanceof ShearsItem)) {
                return true;
            }

            if (state.contains(TripwireBlock.DISARMED) && !state.get(TripwireBlock.DISARMED)) {
                BlockState disarmed = state.with(TripwireBlock.DISARMED, true);
                world.setBlockState(pos, disarmed, Block.NOTIFY_ALL);
                mainHand.damage(1, player, EquipmentSlot.MAINHAND);
                return false;
            }

            return true;
        });

        LOGGER.info("Bring Back Disarmed Tripwire initialized");
    }
}
