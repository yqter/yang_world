package fun.utree.InfoObj;

import fun.utree.YanG_World;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ElementFromYaml {

    /**
     * 获取插件主类来创建项
     *
     * @return 返回主类
     */
    public YanG_World getMainObj() {
        return (YanG_World) Bukkit.getPluginManager().getPlugin("YanG_World");
    }

    /**
     * @return 区域的名称。储存于List之中
     * @apiNote 返回各个区域的名称
     */
    public List<String> getAreaNames() {
        ConfigurationSection section = getMainObj()
                .getConfig()
                .getConfigurationSection("areas");
        if (section == null) {
            return List.of("NULL");
        }
        return new LinkedList<>(Objects
                .requireNonNull(section)
                .getKeys(false));
    }

    public String getMusicName(String area_name) {

        return Objects
                .requireNonNull(getMainObj()
                        .getConfig()
                        .getConfigurationSection("areas." + area_name))
                .getString("music");
    }

    public Area_Locations getLocation(String area_name) {
        String position_left = null;
        String position_right = null;
        try {
            position_left = Objects
                    .requireNonNull(getMainObj()
                            .getConfig()
                            .getConfigurationSection("areas." + area_name))
                    .getString("position_left");
            position_right = Objects.requireNonNull(getMainObj()
                            .getConfig()
                            .getConfigurationSection("areas." + area_name))
                    .getString("position_right");
        } catch (Exception ignored) {

        }

        if (position_left != null && position_right != null) {
            return new Area_Locations(
                    area_name,
                    new FileToLocation(position_left).getLocation(),
                    new FileToLocation(position_right).getLocation());
        } else return null;
    }

}
