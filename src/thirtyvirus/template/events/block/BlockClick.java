package thirtyvirus.template.events.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import thirtyvirus.template.TemplatePlugin;
import thirtyvirus.template.helpers.ActionSound;
import thirtyvirus.template.helpers.Utilities;

public class BlockClick implements Listener {

    private TemplatePlugin main = null;
    public BlockClick(TemplatePlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        Utilities.playSound(ActionSound.ERROR, event.getPlayer());

    }

}