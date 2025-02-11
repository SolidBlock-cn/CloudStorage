package com.github.alexthe668.cloudstorage.block;

import com.github.alexthe668.cloudstorage.CloudStorage;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSPOIRegistry {

    public static final DeferredRegister<PoiType> DEF_REG = DeferredRegister.create(ForgeRegistries.POI_TYPES, CloudStorage.MODID);
    public static final RegistryObject<PoiType> BALLOON_STAND = DEF_REG.register("balloon_stand", () -> new PoiType("balloon_stand", PoiType.getBlockStates(CSBlockRegistry.BALLOON_STAND.get()), 1, 1));
}
