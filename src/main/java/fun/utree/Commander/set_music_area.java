package fun.utree.Commander;

import fun.utree.InfoObj.ElementFromYaml;
import fun.utree.InfoObj.FileToLocation;
import fun.utree.InfoObj.Info_OBj;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class set_music_area implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if (!commandSender.isOp()) {
            commandSender.sendMessage("没有权限 ~ ");
            return true;
        }
        if (strings[0].equalsIgnoreCase("delete") && !new ElementFromYaml().getAreaNames().contains(strings[1])) {
            commandSender.sendMessage("没有这个音乐域哦 ~ ");
            return true;
        }

        switch (strings[0].toLowerCase()) {
            case "create" -> createArea(commandSender, strings[1], strings[2]);
            case "delete" -> deleteArea(commandSender, strings[1]);
            case "change" -> changeArea(commandSender, strings[1], strings[2]);
        }

        return true;
    }

    private void createArea(CommandSender commandSender, String areaName, String musicAddress) {
        if (new ElementFromYaml().getAreaNames().contains(areaName)) {
            commandSender.sendMessage("这个域的名字已经存在了哦，换一个吧！");
            return;
        }
        Info_OBj.config.set("areas." + areaName + ".music", musicAddress);
        Info_OBj.config.set("areas." + areaName + ".position_left",
                new FileToLocation(Info_OBj.left_Location.get(((Player) commandSender).getUniqueId())).toString());
        Info_OBj.config.set("areas." + areaName + ".position_right",
                new FileToLocation(Info_OBj.right_Location.get(((Player) commandSender).getUniqueId())).toString());
        new ElementFromYaml().getMainObj().saveConfig();
        commandSender.sendMessage("创建 [" + areaName + "] 成功！");
    }

    private void deleteArea(CommandSender commandSender, String areaName) {
        Info_OBj.config.set("areas." + areaName, null);
        new ElementFromYaml().getMainObj().saveConfig();
        commandSender.sendMessage("删除 [" + areaName + "] 成功！");
    }

    private void changeArea(CommandSender commandSender, String areaName, String changedMusicAddress) {
        Info_OBj.config.set("areas." + areaName + ".music", changedMusicAddress);
        new ElementFromYaml().getMainObj().saveConfig();
        commandSender.sendMessage("更新 [" + areaName + "] 成功！");
    }

    /**
     * @return 返回的就是提示的命令
     * @apiNote /set_music_area
     * - create <area_name> <music_name>
     * - delete <area_name>
     * - change <area_name> <music_name>
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> modeList = Arrays.asList("create", "delete", "change");
        switch (strings.length) {
            //第一个参数
            case 1:
                //开头的两次判断
                //第一次
                if (strings[0].isEmpty()) return modeList;
                    //第二次
                else {
                    return getFilteredInput(modeList, strings[0]);
                }
                //第二个参数
            case 2:
                switch (strings[0]) {
                    case "create":
                        if (strings[1].isEmpty()) return List.of("example_area_name");
                    case "delete", "change":
                        if (strings[1].isEmpty()) return new ElementFromYaml().getAreaNames();
                        else {
                            return getFilteredInput(new ElementFromYaml().getAreaNames(), strings[1]);
                        }
                    default:
                        return null;
                }
            case 3:
                switch (strings[0]) {
                    case "create", "change":
                        List<String> collect = null;
                        try {
                            collect = Files
                                    .list(new ElementFromYaml().getMainObj().getDataFolder().toPath().resolve("music"))
                                    .map(x -> x.toString().split(Pattern.quote(File.separator))[3])
                                    .collect(Collectors.toList());
                        } catch (IOException ignored) {
                        }
                        if (strings[2].isEmpty()) {
                            return collect;
                        } else if (collect != null) {
                            return getFilteredInput(collect, strings[2]);
                        }
                }
            default:
                return List.of("不用输入了");
        }
    }

    private List<String> getFilteredInput(List<String> args, String matched) {
        LinkedList<String> list = new LinkedList<>();
        for (var firstArg : args) {
            if (firstArg.toLowerCase().startsWith(matched.toLowerCase())) list.add(firstArg);
        }
        return list;
    }


}
