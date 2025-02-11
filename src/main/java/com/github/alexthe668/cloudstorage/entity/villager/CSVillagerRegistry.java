package com.github.alexthe668.cloudstorage.entity.villager;


import com.github.alexthe666.citadel.server.generation.VillageHouseManager;
import com.github.alexthe668.cloudstorage.CloudStorage;
import com.github.alexthe668.cloudstorage.block.CSBlockRegistry;
import com.github.alexthe668.cloudstorage.block.CSPOIRegistry;
import com.github.alexthe668.cloudstorage.entity.BalloonEntity;
import com.github.alexthe668.cloudstorage.entity.CSEntityRegistry;
import com.github.alexthe668.cloudstorage.item.BalloonItem;
import com.github.alexthe668.cloudstorage.item.CSItemRegistry;
import com.github.alexthe668.cloudstorage.world.BalloonStandPoolElement;
import com.github.alexthe668.cloudstorage.misc.CSSoundRegistry;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.MoveToSkySeeingSpot;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = CloudStorage.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CSVillagerRegistry {

    public static final VillagerProfession BALLOON_SALESMAN = new BalloonSalesman().setRegistryName(CloudStorage.MODID, "balloon_salesman");
    public static final StructurePoolElementType<BalloonStandPoolElement> BALLOON_STAND_TYPE = Registry.register(Registry.STRUCTURE_POOL_ELEMENT, new ResourceLocation(CloudStorage.MODID, "balloon_stand"), () -> BalloonStandPoolElement.CODEC);
    public static boolean registeredHouses = false;

    @SubscribeEvent
    public static void registerVillagers(final RegistryEvent.Register<VillagerProfession> event) {
        if (CloudStorage.CONFIG.balloonSalesmanVillager.get()) {
            event.getRegistry().register(BALLOON_SALESMAN);
        }
    }

    public static void initTrades(List<VillagerTrades.ItemListing> level1, List<VillagerTrades.ItemListing> level2, List<VillagerTrades.ItemListing> level3, List<VillagerTrades.ItemListing> level4, List<VillagerTrades.ItemListing> level5) {
        level1.add(new BuyingItemTrade(Items.STRING, 10, 3, 11, 2));
        level1.add(new SellingItemTrade(CSItemRegistry.BALLOON_BIT.get(), 2, 3, 13, 2));
        level1.add(new SellingItemTrade(CSItemRegistry.COTTON_CANDY.get(), 2, 7, 13, 3));
        level1.add(new BuyingItemTrade(Items.GLASS_BOTTLE, 8, 2, 11, 2));
        level2.add(new BuyingItemTrade(CSItemRegistry.GUIDE_BOOK.get(), 8, 2, 11, 2));
        level2.add(new SellingItemTrade(CSItemRegistry.BALLOON.get(), 2, 5, 13, 4));
        level2.add(new SellingRandomlyDyedItem(CSItemRegistry.BALLOON.get(), 1, 2, 13, 4));
        level2.add(new SellingRandomlyDyedItem(CSItemRegistry.BALLOON.get(), 4, 2, 13, 4));
        level3.add(new SellingItemTrade(CSBlockRegistry.CLOUD.get(), 2, 4, 11, 8));
        level3.add(new BuyingItemTrade(CSItemRegistry.ANGRY_CLOUD_IN_A_BOTTLE.get(), 8, 4, 4, 9));
        level3.add(new SellingItemTrade(Items.FIREWORK_STAR, 3, 9, 4, 9));
        level3.add(new SellingItemTrade(Items.CYAN_BANNER, 3, 4, 4, 9));
        level3.add(new SellingItemTrade(Items.SPYGLASS, 5, 1, 2, 9));
        level3.add(new SellingMap(10, true, "item.cloudstorage.big_balloon_map", MapDecoration.Type.TARGET_POINT, 1, 13));
        level3.add(new SellingMap(10, false, "item.cloudstorage.sky_temple_map", MapDecoration.Type.TARGET_X, 1, 13));
        level4.add(new SellingItemTrade(CSItemRegistry.PROPELLER_HAT.get(), 11, 1, 1, 15));
        level4.add(new SellingItemTrade(CSBlockRegistry.STATIC_CLOUD.get(), 3, 5, 6, 13));
        level4.add(new BuyingItemTrade(CSItemRegistry.HAPPY_CLOUD_IN_A_BOTTLE.get(), 8, 6, 7, 12));
        level5.add(new SellingItemTrade(CSItemRegistry.BALLOON_BUDDY.get(), 15, 1, 10, 15));
        level5.add(new SellingRandomlyDyedItem(CSItemRegistry.BALLOON_BUDDY.get(), 13, 1, 3, 15));
        level5.add(new SellingRandomlyDyedItem(CSItemRegistry.BALLOON.get(), 6, 2, 13, 5));
    }

    public static void registerHouses() {
        registeredHouses = true;
        int weight = CloudStorage.CONFIG.balloonStandSpawnWeight.get();
        StructurePoolElement plains = new BalloonStandPoolElement(new ResourceLocation(CloudStorage.MODID, "balloon_stand_plains"), ProcessorLists.EMPTY);
        VillageHouseManager.register("minecraft:village/plains/houses", (pool) -> VillageHouseManager.addToPool(pool, plains, weight));
        StructurePoolElement desert = new BalloonStandPoolElement(new ResourceLocation(CloudStorage.MODID, "balloon_stand_desert"), ProcessorLists.EMPTY);
        VillageHouseManager.register("minecraft:village/desert/houses", (pool) -> VillageHouseManager.addToPool(pool, desert, weight));
        StructurePoolElement savanna = new BalloonStandPoolElement(new ResourceLocation(CloudStorage.MODID, "balloon_stand_savanna"), ProcessorLists.EMPTY);
        VillageHouseManager.register("minecraft:village/savanna/houses", (pool) -> VillageHouseManager.addToPool(pool, savanna, weight));
        StructurePoolElement snowy = new BalloonStandPoolElement(new ResourceLocation(CloudStorage.MODID, "balloon_stand_snowy"), ProcessorLists.EMPTY);
        VillageHouseManager.register("minecraft:village/snowy/houses", (pool) -> VillageHouseManager.addToPool(pool, snowy, weight));
        StructurePoolElement taiga = new BalloonStandPoolElement(new ResourceLocation(CloudStorage.MODID, "balloon_stand_taiga"), ProcessorLists.EMPTY);
        VillageHouseManager.register("minecraft:village/taiga/houses", (pool) -> VillageHouseManager.addToPool(pool, taiga, weight));
    }

    public static void onBalloonCelebrate(ServerLevel level, Villager villager) {
        Random random = villager.getRandom();
        if (random.nextInt(75) == 0) {
            villager.playCelebrateSound();
            if(MoveToSkySeeingSpot.hasNoBlocksAbove(level, villager, villager.blockPosition())) {
                int[] colors = getBalloonColorsForVillager(villager.getVillagerData().getType());
                for (int i = 0; i < random.nextInt(2) + 1; i++) {
                    int color = colors[colors.length <= 1 ? 0 : random.nextInt(colors.length)];
                    BalloonEntity balloon = CSEntityRegistry.BALLOON.get().create(villager.level);
                    balloon.copyPosition(villager);
                    balloon.setStringLength(BalloonEntity.DEFAULT_STRING_LENGTH + random.nextInt(1));
                    balloon.setBalloonColor(color);
                    villager.level.addFreshEntity(balloon);
                }
            }
        }
    }

    public static int[] getBalloonColorsForVillager(VillagerType type) {
        if (type == VillagerType.PLAINS) {
            return new int[]{BalloonItem.DEFAULT_COLOR, 0XFED83D};
        } else if (type == VillagerType.DESERT) {
            return new int[]{0X80C71F, 0X3AB3DA, 0XFED83D};
        } else if (type == VillagerType.SAVANNA) {
            return new int[]{0XFED83D, 0X835432, 0XF9801D};
        } else if (type == VillagerType.SNOW) {
            return new int[]{0X3AB3DA, 0X3C44AA};
        } else if (type == VillagerType.TAIGA) {
            return new int[]{BalloonItem.DEFAULT_COLOR, 0X3AB3DA};
        } else {
            return new int[]{BalloonItem.DEFAULT_COLOR};
        }
    }
   /*  public static void registerHouses(MinecraftServer server) {
       RegistryAccess.Frozen manager = server.registryAccess();
        Registry<StructureTemplatePool> registry = manager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
        registerJigsawPiece(registry, new ResourceLocation("minecraft:village/plains/houses"), new ResourceLocation(DomesticationMod.MODID, "plains_petshop"), 20);
        registerJigsawPiece(registry, new ResourceLocation("minecraft:village/desert/houses"), new ResourceLocation(DomesticationMod.MODID, "desert_petshop"), 20);
        registerJigsawPiece(registry, new ResourceLocation("minecraft:village/savanna/houses"), new ResourceLocation(DomesticationMod.MODID, "savanna_petshop"), 20);
        registerJigsawPiece(registry, new ResourceLocation("minecraft:village/snowy/houses"), new ResourceLocation(DomesticationMod.MODID, "snowy_petshop"), 20);
        registerJigsawPiece(registry, new ResourceLocation("minecraft:village/taiga/houses"), new ResourceLocation(DomesticationMod.MODID, "taiga_petshop"), 20);

    }

    private static void registerJigsawPiece(Registry<StructureTemplatePool> registry, ResourceLocation poolLocation, ResourceLocation nbtLocation, int weight) {
        StructureTemplatePool pool = registry.get(poolLocation);
        StructurePoolElement element = new PetshopStructurePoolElement(nbtLocation, ProcessorLists.EMPTY);
        if (pool != null) {
            List<StructurePoolElement> templates = new ArrayList<>(pool.templates);
            for (int i = 0; i < weight; i++) {
                templates.add(element);
            }
            List<Pair<StructurePoolElement, Integer>> rawTemplates = new ArrayList(pool.rawTemplates);
            rawTemplates.add(new Pair<>(element, weight));
            pool.templates = templates;
            pool.rawTemplates = rawTemplates;
        }
    }*/

    private static class BalloonSalesman extends VillagerProfession {

        public BalloonSalesman() {
            super("balloon_salesman", null, ImmutableSet.of(), ImmutableSet.of(), CSSoundRegistry.BALLOON_HURT);
        }

        public PoiType getJobPoiType() {
            return CSPOIRegistry.BALLOON_STAND.get();
        }
    }
}
