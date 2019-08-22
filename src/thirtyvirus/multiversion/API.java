package thirtyvirus.multiversion;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class API {

    // get an entity given the UUID
    public static Entity getEntity(UUID id) {

        // 1.11 and above method
        //if (Version.getVersion().isBiggerThan(Version.v1_10)) return Bukkit.getEntity(id);

        // 1.10 and below method
        for(World w : Bukkit.getWorlds()) {
            for(Entity entity : w.getEntities()) {
                if(entity.getUniqueId() == id) return entity;
            }
        }

        return null;
    }

    // determine whether or not a block is a slab
    public static boolean isSlab(Material material) {
        return ((material.name().toLowerCase().contains("slab") || material.name().toLowerCase().contains("step")) && !material.name().toLowerCase().contains("double"));
    }
    // determine whether or not a slab is the bottom variety
//    public static boolean isBottomSlab(Block block) {
//        if(block == null || !isSlab(block.getType())) return false;
//
//        //location.getBlock().getBlockData().getAsString().contains("type=bottom")
//        if(Version.getVersion().isBiggerThan(Version.v1_12)) {
//            return block.getBlockData().getAsString().contains("type=bottom");
//        } else {
//            return block.getData() < 8; // works in 1.12
//        }
//    }
//
//    // determine whether or not a slab is the top variety
//    public static boolean isTopSlab(Block block) {
//        if(block == null || !isSlab(block.getType())) return false;
//
//        //location.getBlock().getBlockData().getAsString().contains("type=top")
//        if(Version.getVersion().isBiggerThan(Version.v1_12)) {
//            return block.getBlockData().getAsString().contains("type=top");
//        } else {
//            return block.getData() > 7;
//        }
//    }

    // force open a book
    public static void openBook(ItemStack book, Player player) {

        // test if version greater than 1.14.3 then use 1.8 book
        if (Version.getVersion().isBiggerThan(Version.v1_14)) BookUtil_1_14_4.openBook(book, player);
        else if (Version.getVersion().isBiggerThan(Version.v1_8)) BookUtil_1_9.openBook(book, player);
        else BookUtil_1_8.openBook(book, player);
    }

    // get the item in the player's main hand
    public ItemStack getItemInHand(Player player) {
        if (Version.getVersion().isBiggerThan(Version.v1_8)) {
            return player.getInventory().getItemInMainHand();
        }
        else {
            return player.getInventory().getItemInHand();
        }
    }

}
