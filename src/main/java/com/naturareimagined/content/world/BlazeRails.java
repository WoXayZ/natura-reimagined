package com.naturareimagined.content.world;

import com.naturareimagined.config.NRConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class BlazeRails {

    private BlazeRails() {}

    public static float speed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
        return NRConfig.BLAZE_RAIL_SPEED.get().floatValue();
    }

    public static class Normal extends RailBlock {
        public Normal(Properties properties) {
            super(properties);
        }

        @Override
        public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
            return speed(state, level, pos, cart);
        }
    }

    public static class Powered extends PoweredRailBlock {
        public Powered(Properties properties) {
            super(properties, true);
        }

        @Override
        public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
            return speed(state, level, pos, cart);
        }
    }

    public static class Activator extends PoweredRailBlock {
        public Activator(Properties properties) {
            super(properties, true);
        }

        @Override
        public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
            return speed(state, level, pos, cart);
        }
    }

    public static class Detector extends DetectorRailBlock {
        public Detector(Properties properties) {
            super(properties);
        }

        @Override
        public float getRailMaxSpeed(BlockState state, Level level, BlockPos pos, AbstractMinecart cart) {
            return speed(state, level, pos, cart);
        }
    }
}
