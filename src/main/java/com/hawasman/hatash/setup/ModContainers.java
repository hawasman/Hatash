package com.hawasman.hatash.setup;

import com.hawasman.hatash.containers.CarbonGeneratorContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ModContainers {
    public static final RegistryObject<ContainerType<CarbonGeneratorContainer>> CARBON_GENERATOR_CONTAINER = Registration.CONTAINERS.register("carbon_generator", ()-> IForgeContainerType.create(CarbonGeneratorContainer::new));

    static void register(){}
}
