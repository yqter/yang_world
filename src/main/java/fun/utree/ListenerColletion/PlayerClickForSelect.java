package fun.utree.ListenerColletion;

import fun.utree.InfoObj.Info_OBj;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class PlayerClickForSelect implements Listener {

    @EventHandler
    public void onPlayerClicked(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        //不是op不能
        if (!player.isOp()) return;
        //如果手上拿的不是之前自己设置过的，那也不能成立
        if (!player.getInventory().getItemInMainHand().getType()
                .equals(Info_OBj.toolsMap.get(player.getUniqueId())))
            return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Info_OBj.left_Location.put(player.getUniqueId(), Objects.requireNonNull(event.getClickedBlock()).getLocation());
            player.sendMessage("保存了第一个点: "
                    + getLocationAndSave(Objects.requireNonNull(event.getClickedBlock()).getLocation()));
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            /*
            这里是为了判断是否是主手，副手就不启动了
             */
            if (event.getHand() == EquipmentSlot.HAND) {
                Info_OBj.right_Location.put(player.getUniqueId(), Objects.requireNonNull(event.getClickedBlock()).getLocation());
                player.sendMessage("保存了第二个点: "
                        + getLocationAndSave(Objects.requireNonNull(event.getClickedBlock()).getLocation()));
            }
        }
    }

    private String getLocationAndSave(Location location) {
        return " X=%.2f, Y=%.2f, Z=%.2f ".formatted(location.getX(), location.getY(), location.getZ());
    }
}
