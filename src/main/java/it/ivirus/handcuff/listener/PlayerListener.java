package it.ivirus.handcuff.listener;

import de.tr7zw.changeme.nbtapi.NBTItem;
import it.ivirus.handcuff.MainHandcuff;
import it.ivirus.handcuff.data.HandcuffData;
import it.ivirus.handcuff.utils.Strings;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PlayerListener implements Listener {
    private final HandcuffData handcuffData = HandcuffData.getInstance();
    private final MainHandcuff plugin;

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (handcuffData.isHandCuffed(event.getPlayer().getUniqueId())) {
            Location from = event.getFrom();
            Location to = event.getTo();
            double x = Math.floor(from.getX());
            double z = Math.floor(from.getZ());

            if (to == null) {
                return;
            }
            if (Math.floor(to.getX()) != x || Math.floor(to.getZ()) != z) {
                x += .5;
                z += .5;
                event.getPlayer().teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
            }
        }
    }

    @EventHandler
    public void onPlayerDrag(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Player target = handcuffData.isDragging(player);
        if (target != null) {
            if (player.getLocation().distance(target.getLocation()) > plugin.getConfig().getInt("distance-to-teleport")) {
                target.teleport(player.getLocation());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player targetDragged = handcuffData.isDragging(event.getPlayer());
        if (targetDragged != null) {
            handcuffData.getTargetIsDragged().remove(targetDragged);
            return;
        }
        if (handcuffData.isHandCuffed(event.getPlayer().getUniqueId()) && handcuffData.getTargetIsDragged().containsKey(event.getPlayer())) {
            handcuffData.getTargetIsDragged().remove(event.getPlayer());
        }
        handcuffData.getCooldown().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (handcuffData.isHandCuffed(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(Strings.INFO_TARGET_HANDCUFFED.getFormattedString());
        }
    }

    @EventHandler
    public void onPlayerHandcuffPlayer(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("handcuff.use")) return;
        if (!(event.getHand() == EquipmentSlot.OFF_HAND)) return;
        if (!(event.getRightClicked() instanceof Player)) return;
        Player target = (Player) event.getRightClicked();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType() != Material.AIR) {
            NBTItem nbtItem = new NBTItem(itemInHand);
            if (nbtItem.hasNBTData() && nbtItem.hasKey("handcuff")) {
                if (handcuffData.isHandCuffed(target.getUniqueId())) {
                    handcuffData.getPlayerCuff().remove(target.getUniqueId());
                    handcuffData.getTargetIsDragged().remove(target);
                    target.sendMessage(Strings.INFO_HANDCUFF_REMOVED.getFormattedString());
                    player.sendMessage(Strings.INFO_SENDER_HANDCUFF_REMOVED.getFormattedString()
                            .replaceAll("%target_name%", target.getName()));
                    return;
                }
                if (handcuffData.getCooldown().containsKey(player.getUniqueId())) {
                    int countdownTime = plugin.getConfig().getInt("cooldown");
                    long time = handcuffData.getCooldown().get(player.getUniqueId()) / 1000L + countdownTime - System.currentTimeMillis() / 1000L;
                    if (time > 0L) {
                        player.sendMessage(Strings.ERROR_COOLDOWN.getFormattedString().replaceAll("%cooldown%", String.valueOf(time)));
                        return;
                    }
                }
                handcuffData.getPlayerCuff().add(target.getUniqueId());
                target.sendMessage(Strings.INFO_TARGET_HANDCUFFED.getFormattedString());
                player.sendMessage(Strings.INFO_SENDER_HANDCUFFED.getFormattedString()
                        .replaceAll("%target_name%", target.getName()));
                handcuffData.getCooldown().put(player.getUniqueId(), System.currentTimeMillis());
            }
        } else {
            if (handcuffData.isHandCuffed(target.getUniqueId()) && !handcuffData.getTargetIsDragged().containsKey(target)) {
                handcuffData.getTargetIsDragged().put(target, player);
                player.sendMessage(Strings.INFO_SENDER_IS_DRAGGING.getFormattedString()
                        .replaceAll("%target_name%", target.getName()));
            } else if (handcuffData.isHandCuffed(target.getUniqueId()) && handcuffData.getTargetIsDragged().containsKey(target)) {
                handcuffData.getTargetIsDragged().remove(target);
                player.sendMessage(Strings.INFO_SENDER_IS_NOT_DRAGGING.getFormattedString()
                        .replaceAll("%target_name%", target.getName()));
            }
        }
    }

    @EventHandler
    public void onPlayerClickOnGui(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (handcuffData.isHandCuffed(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChangeItemHeld(PlayerItemHeldEvent event) {
        if (handcuffData.isHandCuffed(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerOpenGui(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        if (handcuffData.isHandCuffed(player.getUniqueId())) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTaskLater(plugin, player::closeInventory, 1);
        }
    }

    @EventHandler
    public void onPlayerSwapItem(PlayerSwapHandItemsEvent event) {
        if (handcuffData.isHandCuffed(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
