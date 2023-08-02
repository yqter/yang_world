package fun.utree.Commander;

import fun.utree.InfoObj.Info_OBj;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class bound_tool implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "You are not player...");
            return true;
        }
        if (!commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You are not OP, sorry you can't do this..");
            return true;
        }

        if (strings.length != 0) {
            commandSender.sendMessage("The format of input is " + ChatColor.RED + command.getUsage());
            return true;
        }

        Material hand_type = ((Player) commandSender).getInventory().getItemInMainHand().getType();
        if (hand_type.equals(Material.AIR)) {
            commandSender.sendMessage(ChatColor.RED + "不能绑定空气！");
            return true;
        }
        commandSender.sendMessage(ChatColor.GREEN + "已经绑定成功: " + hand_type);

        /*
         把绑定的UUID和类型存起来
         */
        Info_OBj.toolsMap.put(((Player) commandSender).getUniqueId(), hand_type);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of("不用输入了！这前面这一个就够了！他会绑定你手上拿的工具！");
    }
}
