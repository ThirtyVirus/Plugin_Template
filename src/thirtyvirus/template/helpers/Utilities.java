package thirtyvirus.template.helpers;

import com.google.common.io.ByteStreams;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import thirtyvirus.multiversion.Sound;
import thirtyvirus.multiversion.Version;
import thirtyvirus.multiversion.XMaterial;
import thirtyvirus.template.TemplatePlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public final class Utilities {

    // list of transparent blocks to be ignored when a player looks at a block
    private static final Set<Material> TRANSPARENT = EnumSet.of(XMaterial.AIR.parseMaterial(), XMaterial.BLACK_CARPET.parseMaterial(), XMaterial.BLUE_CARPET.parseMaterial(),
            XMaterial.BROWN_CARPET.parseMaterial(), XMaterial.CYAN_CARPET.parseMaterial(), XMaterial.GRAY_CARPET.parseMaterial(), XMaterial.GREEN_CARPET.parseMaterial(), XMaterial.LIGHT_BLUE_CARPET.parseMaterial(),
            XMaterial.LIME_CARPET.parseMaterial(), XMaterial.MAGENTA_CARPET.parseMaterial(), XMaterial.ORANGE_CARPET.parseMaterial(), XMaterial.PINK_CARPET.parseMaterial(), XMaterial.PURPLE_CARPET.parseMaterial(),
            XMaterial.RED_CARPET.parseMaterial(), XMaterial.WHITE_CARPET.parseMaterial(), XMaterial.YELLOW_CARPET.parseMaterial());

    // list of all supported inventory blocks in the plugin
    public static final List<Material> INVENTORY_BLOCKS = Arrays.asList(XMaterial.CHEST.parseMaterial(),XMaterial.TRAPPED_CHEST.parseMaterial(), XMaterial.ENDER_CHEST.parseMaterial(), XMaterial.SHULKER_BOX.parseMaterial(), XMaterial.BLACK_SHULKER_BOX.parseMaterial(),
            XMaterial.BLUE_SHULKER_BOX.parseMaterial(), XMaterial.BROWN_SHULKER_BOX.parseMaterial(), XMaterial.CYAN_SHULKER_BOX.parseMaterial(), XMaterial.GRAY_SHULKER_BOX.parseMaterial(),
            XMaterial.GREEN_SHULKER_BOX.parseMaterial(), XMaterial.LIGHT_BLUE_SHULKER_BOX.parseMaterial(), XMaterial.LIGHT_GRAY_SHULKER_BOX.parseMaterial(), XMaterial.LIME_SHULKER_BOX.parseMaterial(),
            XMaterial.MAGENTA_SHULKER_BOX.parseMaterial(), XMaterial.ORANGE_SHULKER_BOX.parseMaterial(), XMaterial.PINK_SHULKER_BOX.parseMaterial(), XMaterial.PURPLE_SHULKER_BOX.parseMaterial(),
            XMaterial.RED_SHULKER_BOX.parseMaterial(), XMaterial.WHITE_SHULKER_BOX.parseMaterial(), XMaterial.YELLOW_SHULKER_BOX.parseMaterial());

    private static Map<Player, Long> mostRecentSelect = new HashMap<>();

    // load file from JAR with comments
    public static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResource(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }

    // convert a location to formatted string (world,x,y,z)
    public static String toLocString(Location location) {
        if (location.equals(null)) return "";
        return location.getWorld().getName() + "," + (int)location.getX() + "," + (int)location.getY() + "," + (int)location.getZ();
    }

    // renames item
    public static ItemStack nameItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // creates item that is renamed given material and name
    public static ItemStack nameItem(Material item, String name) {
        return nameItem(new ItemStack(item), name);
    }

    // set the lore of an item
    public static ItemStack loreItem(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    // makes visible string invisible to player
    public static String convertToInvisibleString(String s) {
        if (Version.getVersion().isBiggerThan(Version.v1_15)) return s; // HOTFIX to prevent invisible text being garbled by 1.16 changes
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR + "" + c;
        return hidden;
    }

    // make invisible string visible to player
    public static String convertToVisibleString(String s) {
        if (Version.getVersion().isBiggerThan(Version.v1_15)) return s; // HOTFIX to prevent invisible text being garbled by 1.16 changes
        String c = "";
        c = c + ChatColor.COLOR_CHAR;
        return s.replaceAll(c, "");
    }

    // warns player of something in plugin
    public static void warnPlayer(CommandSender sender, List<String> messages) {
        if (sender instanceof Player) { Player player = (Player) sender; playSound(ActionSound.ERROR, player); }
        for (String message : messages) sender.sendMessage(TemplatePlugin.prefix + ChatColor.RESET + ChatColor.RED + message);
    }
    public static void warnPlayer(CommandSender sender, String message) {
        warnPlayer(sender, Collections.singletonList(message));
    }

    // informs player of something in plugin
    public static void informPlayer(CommandSender sender, List<String> messages) {
        for (String message : messages) sender.sendMessage(TemplatePlugin.prefix + ChatColor.RESET + ChatColor.GRAY + message);
    }
    public static void informPlayer(CommandSender sender, String message) {
        informPlayer(sender, Collections.singletonList(message));
    }

    // return the block the player is looking at, ignoring transparent blocks
    public static Block getBlockLookingAt(Player player) {
        return player.getTargetBlock(TRANSPARENT, 120);
    }

    // play sound at player (version independent)
    public static void playSound(ActionSound sound, Player player) {

        switch (sound) {
            case OPEN:
                Sound.CHEST_OPEN.playSound(player);
                break;
            case MODIFY:
                Sound.ANVIL_USE.playSound(player);
                break;
            case SELECT:
                Sound.LEVEL_UP.playSound(player);
                break;
            case CLICK:
                Sound.CLICK.playSound(player);
                break;
            case POP:
                Sound.CHICKEN_EGG_POP.playSound(player);
                break;
            case BREAK:
                Sound.ANVIL_LAND.playSound(player);
                break;
            case ERROR:
                Sound.BAT_DEATH.playSound(player);
                break;
        }

    }

}
