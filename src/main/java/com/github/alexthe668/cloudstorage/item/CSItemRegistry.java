package com.github.alexthe668.cloudstorage.item;

import com.github.alexthe668.cloudstorage.CloudStorage;
import com.github.alexthe668.cloudstorage.block.CSBlockRegistry;
import com.github.alexthe668.cloudstorage.entity.CSEntityRegistry;
import com.github.alexthe668.cloudstorage.misc.CSSoundRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = CloudStorage.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CSItemRegistry {
    public static final DeferredRegister<Item> DEF_REG = DeferredRegister.create(ForgeRegistries.ITEMS, CloudStorage.MODID);
    public static final RegistryObject<Item> BALLOON_BIT = DEF_REG.register("balloon_bit", () -> new Item(new Item.Properties().tab(CloudStorage.TAB)));
    public static final RegistryObject<Item> GUIDE_BOOK = DEF_REG.register("guide_book", () -> new GuideBookItem(new Item.Properties().tab(CloudStorage.TAB).stacksTo(1)));
    public static final RegistryObject<Item> BALLOON_INVENTORY = DEF_REG.register("balloon_inventory", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BALLOON = DEF_REG.register("balloon", () -> new BalloonItem());
    public static final RegistryObject<Item> ANGRY_CLOUD_IN_A_BOTTLE = DEF_REG.register("angry_cloud_in_a_bottle", () -> new CloudBottleItem(false));
    public static final RegistryObject<Item> HAPPY_CLOUD_IN_A_BOTTLE = DEF_REG.register("happy_cloud_in_a_bottle", () -> new CloudBottleItem(true));
    public static final RegistryObject<Item> BALLOON_BUDDY_INVENTORY = DEF_REG.register("balloon_buddy_inventory", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BALLOON_BUDDY = DEF_REG.register("balloon_buddy", () -> new BalloonBuddyItem());
    public static final RegistryObject<Item> BALLOON_ARROW = DEF_REG.register("balloon_arrow", () -> new BalloonArrowItem());
    public static final RegistryObject<Item> PROPELLER_HAT = DEF_REG.register("propeller_hat", () -> new PropellerHatItem());
    public static final RegistryObject<Item> COTTON_CANDY = DEF_REG.register("cotton_candy", () -> new Item(new Item.Properties().tab(CloudStorage.TAB).food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.15F).fast().build())));
    public static final RegistryObject<Item> MUSIC_DISC_DRIFT = DEF_REG.register("music_disc_drift", () -> new RecordItem(14, CSSoundRegistry.MUSIC_DISC_DRIFT, new Item.Properties().tab(CloudStorage.TAB).stacksTo(1).rarity(Rarity.RARE)));

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ForgeSpawnEggItem(CSEntityRegistry.BADLOON, 0XE72929, 0XEEE7E1, new Item.Properties().tab(CloudStorage.TAB)).setRegistryName("cloudstorage:spawn_egg_badloon"));
        event.getRegistry().register(new ForgeSpawnEggItem(CSEntityRegistry.BLOVIATOR, 0XDFF3F7, 0X24AFFF, new Item.Properties().tab(CloudStorage.TAB)).setRegistryName("cloudstorage:spawn_egg_bloviator"));
        CSBlockRegistry.DEF_REG.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(block -> event.getRegistry().register(registerItemBlock(block)));
        DispenserBlock.registerBehavior(CSItemRegistry.BALLOON.get(), (blockSource, itemStack) -> {
            Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
            if(((BalloonItem)CSItemRegistry.BALLOON.get()).placeBalloon(blockSource.getLevel(), itemStack, blockSource.getPos(), direction, null, true)){
                itemStack.shrink(1);
            }
            return itemStack;
        });
    }

    private static Item registerItemBlock(Block block) {
        Item.Properties props = new Item.Properties();
        props.tab(CloudStorage.TAB);
        final BlockItem blockItem = block == CSBlockRegistry.CLOUD_CHEST.get() || block == CSBlockRegistry.STATIC_CLOUD_CHEST.get() ? new BlockItemSpecialRender(block, props) : new BlockItem((Block) block, props);
        blockItem.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return blockItem;
    }
}
