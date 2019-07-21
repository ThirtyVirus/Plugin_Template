package thirtyvirus.template.events.inventory;

import org.bukkit.Bukkit;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import thirtyvirus.template.TemplatePlugin;
import thirtyvirus.template.helpers.Utilities;

import java.util.Arrays;

import static thirtyvirus.template.helpers.ActionSound.CLICK;

public class InventoryClick implements Listener {

    private TemplatePlugin main = null;
    public InventoryClick(TemplatePlugin main) {
        this.main = main;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Utilities.playSound(CLICK, (Player)event.getWhoClicked());
    }

}