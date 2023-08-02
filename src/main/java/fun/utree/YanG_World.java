package fun.utree;

import fun.utree.Commander.bound_tool;
import fun.utree.Commander.set_music_area;
import fun.utree.InfoObj.Info_OBj;
import fun.utree.ListenerColletion.PlayerClickForSelect;
import fun.utree.ListenerColletion.PlayerMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public final class YanG_World extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.printf("""
                ========================================
                        【 utree.fun beta版本 】
                        
                    正在启动！
                        
                    原神，启动！
                        
                      --%s
                ========================================
                %n""", new Date());
        Info_OBj.config = getConfig();
        Bukkit.getPluginCommand("bound_tool").setExecutor(new bound_tool());
        Bukkit.getPluginCommand("set_music_area").setExecutor(new set_music_area());
        Bukkit.getPluginManager().registerEvents(new PlayerClickForSelect(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);

        try {
            Info_OBj.musicFilePath = Files.createDirectories(
                    getDataFolder()
                    .toPath()
                    .resolve("music"));
            File[] files = Info_OBj.musicFilePath.toFile().listFiles(((dir, name) -> name.endsWith(".nbs")));
            if (files == null || files.length == 0) {
                getLogger().warning("目前music文件夹内没有音乐文件，如果不及时添加可能会引发未知错误");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        saveDefaultConfig();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        String showMessage = """
                ============================
                ==              _ _       
                ==       原神  '(-_-)'     
                ==             - | -      
                ==              / \\       
                ==             再起不能     
                ==                        
                ============================
                """;
        System.out.println(showMessage);
    }
}
