package thirtyvirus.template.helpers;

import org.bukkit.inventory.ItemStack;
import thirtyvirus.multiversion.Version;
import thirtyvirus.template.TemplatePlugin;

import java.util.Comparator;
import java.util.Random;

public class ItemComparator implements Comparator<ItemStack> {

    public int compare(ItemStack a, ItemStack b) {
        //return oldMethod(a, b);

        int result = 0;
        switch (TemplatePlugin.sortingMode) {

            // alphabetical
            case 1:
                result = alphabetical(a, b);
                break;
            // smart
            // TODO code
            case 2:
                result = smart(a, b);
                break;
            // numeric ID
            case 3:
                // stop crashing by defaulting to alphabetical sort in 1.13+
                if (Version.getVersion().isBiggerThan(Version.v1_12)) result = alphabetical(a, b);
                else result = oldMethod(a, b);
                break;

            // scramble aka random
            case 4:
                Random rand = new Random();
                result = rand.nextInt(2) - 1;
                break;
        }

        if (TemplatePlugin.reverseSort) result *= -1;

        return result;
    }

    private int oldMethod(ItemStack a, ItemStack b) {

        // compare numeric IDs
        if (a.getType().getId() < b.getType().getId()) {
            return -1;
        }
        else if (a.getType().getId() > b.getType().getId()){
            return 1;
        }
        else{
            // compare block data
            if (a.getData().getData() < b.getData().getData()){
                return -1;
            }
            else if (a.getData().getData() > b.getData().getData()){
                return 1;
            }
            else{
                return 0;
            }

        }
    }

    private int alphabetical(ItemStack a, ItemStack b) {
       return a.getType().name().compareTo(b.getType().name());
    }

    private int smart(ItemStack a, ItemStack b) {


        // building blocks
        // decoration
        // redstone
        // transportation
        // misc
        // food
        // tools
        // combat
        // brewing

        // put food first (test)
        if (a.getType().isEdible() && !b.getType().isEdible()) {
            return -1;
        }
        if (!a.getType().isEdible() && b.getType().isEdible()) {
            return 1;
        }

        // sort items by type (block, food, etc...)

        // sort equipment by max durability
        if (a.getType().getMaxDurability() > b.getType().getMaxDurability()) {
            return -1;
        }
        if (a.getType().getMaxDurability() < b.getType().getMaxDurability()) {
            return 1;
        }

        // sort equipment by durability
        if (a.isSimilar(b)) {
            if (a.getDurability() < b.getDurability()) {
                return -1;
            }
            if (a.getDurability() < b.getDurability()) {
                return 1;
            }
        }

        // final comparison, if no other factors to sort by
        return a.getType().name().compareTo(b.getType().name());
    }
}