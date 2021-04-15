package thirtyvirus.template.helpers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import thirtyvirus.multiversion.API;
import thirtyvirus.multiversion.XMaterial;
import thirtyvirus.template.TemplatePlugin;

import java.util.ArrayList;
import java.util.List;

public class MenuUtils {

    public static void tutorialMenu(Player player) {
        ItemStack book = new ItemStack(XMaterial.WRITTEN_BOOK.parseMaterial());
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.setAuthor("ThirtyVirus");
        meta.setTitle("Welcome to TemplatePlugin!");

        List<String> pages = new ArrayList<String>();

        // exmaple main menu
        pages.add(ChatColor.translateAlternateColorCodes('&',
                "      &7&lWelcome to:" + "\n" +
                        "   &c&lTemplate&5&lPlugin&r" + "\n" +
                        "This guide book will show you everything you need to know about template! Happy reading!" + "\n" +
                        "" + "\n" +
                        " - ThirtyVirus" + "\n" +
                        "" + "\n" +
                        "&7&lGo to the next page for info on a second page!"));

        // example secondary page
        pages.add(ChatColor.translateAlternateColorCodes('&',
                "&c&lIn-Inventory&r" + "\n" +
                        "" + "\n" +
                        "Middle Click any slot in any container-block's open inventory, and it will instantly be sorted!" + "\n" +
                        "" + "\n" +
                        "Use &c'/template inv'&r to sort your personal inventory." + "\n" +
                        "" + "\n" +
                        "&7&lNext: External Sorting" + "\n"));


        meta.setPages(pages);
        book.setItemMeta(meta);

        Utilities.playSound(ActionSound.CLICK, player);
        API.openBook(book, player);
    }

}
