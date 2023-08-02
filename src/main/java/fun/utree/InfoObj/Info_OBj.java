package fun.utree.InfoObj;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Info_OBj {

    //这个是保存的每个管理员所设置的区块选择工具
    public static Map<UUID, Material> toolsMap = new HashMap<>();

    //接下来的两个是工具Map里面保存的相应的位置
    public static Map<UUID, Location> left_Location = new HashMap<>();
    public static Map<UUID, Location> right_Location = new HashMap<>();

    //这个变量为这个世界的配置文件
    public static FileConfiguration config;

    //音乐文件路径
    public static Path musicFilePath;
}
