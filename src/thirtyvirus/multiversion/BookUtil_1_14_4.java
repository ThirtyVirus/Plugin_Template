package thirtyvirus.multiversion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.Method;
import java.util.List;

/**
* Create a "Virtual" book gui that doesn't require the user to have a book in their hand.
* Requires ReflectionUtil class.
* Built for Minecraft 1.9
* @author Jed
*
*/
public class BookUtil_1_14_4 {
   private static boolean initialised = false;
   private static Method getHandle;
   private static Method openBook;
   
   static {
      try {
         getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
         openBook = ReflectionUtils.getMethod("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, "openBook", ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack"), ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand"));
         initialised = true;
      } catch (ReflectiveOperationException e) {
         e.printStackTrace();
         Bukkit.getServer().getLogger().warning("Cannot force open book!");
         initialised = false;
      }
   }   
   public static boolean isInitialised(){
      return initialised;
   }
   /**
   * Open a "Virtual" Book ItemStack.
   * @param i Book ItemStack.
   * @param p Player that will open the book.
   * @return
   */
   public static boolean openBook(ItemStack i, Player p) {
      if (!initialised) return false;
      ItemStack held = p.getInventory().getItemInMainHand();
      try {
         p.getInventory().setItemInMainHand(i);
         sendPacket(i, p);
      } catch (ReflectiveOperationException e) {
         e.printStackTrace();
      initialised = false;
      }
      p.getInventory().setItemInMainHand(held);
      return initialised;
   }

   private static void sendPacket(ItemStack i, Player p) throws ReflectiveOperationException {
      Object entityplayer = getHandle.invoke(p);
      Class<?> enumHand = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand");
      Object[] enumArray = enumHand.getEnumConstants();
      openBook.invoke(entityplayer, getItemStack(i), enumArray[0]);
   }

   public static Object getItemStack(ItemStack item) {
      try {
         Method asNMSCopy = ReflectionUtils.getMethod(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"), "asNMSCopy", ItemStack.class);
         return asNMSCopy.invoke(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"), item);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   /**
   * Set the pages of the book in JSON format.
   * @param metadata BookMeta of the Book ItemStack.
   * @param pages Each page to be added to the book.
   */
   @SuppressWarnings("unchecked")
   public static void setPages(BookMeta metadata, List<String> pages) {
      List<Object> p;
      Object page;
      try {
         p = (List<Object>) ReflectionUtils.getField(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"), true, "pages").get(metadata);
         for (String text : pages) {
            page = ReflectionUtils.invokeMethod(ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer").newInstance(), "a", text);
            p.add(page);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}