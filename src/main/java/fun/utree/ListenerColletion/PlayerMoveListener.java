package fun.utree.ListenerColletion;

import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import fun.utree.InfoObj.Area_Locations;
import fun.utree.InfoObj.ElementFromYaml;
import fun.utree.InfoObj.Info_OBj;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;

public class PlayerMoveListener implements Listener {
    private RadioSongPlayer radioSongPlayer;
    private String currentAreaName;

    public PlayerMoveListener() {
        // 初始化创建一个空的RadioSongPlayer对象
        this.radioSongPlayer = null;
        this.currentAreaName = "";
    }

    @EventHandler
    public void onPlayerMoved(PlayerMoveEvent moveEvent) {
        Player player = moveEvent.getPlayer();
        ElementFromYaml elementFromYaml = new ElementFromYaml();

        boolean isInAnyRegion = false;

        for (var areaName : elementFromYaml.getAreaNames()) {
            if (isInRegion(player, elementFromYaml.getLocation(areaName))) {
                // 玩家进入了新的区域
                if (!areaName.equals(currentAreaName)) {
                    playMusic(player, areaName, true);
                    currentAreaName = areaName;
                }
                isInAnyRegion = true;
                break;
            }
        }

        // 玩家不在任何区域内
        if (!isInAnyRegion && !currentAreaName.isEmpty()) {
            playMusic(player, "", false);
            currentAreaName = "";
        }
    }

    /**
     * 来判断是否是在区块内的工具方法
     *
     * @return 是否是在已设定的区块内！
     */
    public boolean isInRegion(Player player, Area_Locations area_location) {
        Location location_left = null;
        Location location_right = null;
        try {
            location_left = area_location.getLocation_left();
            location_right = area_location.getLocation_right();
        } catch (NullPointerException ignored) {
            return false;
        }
        Location playerLoc = player.getLocation();
        double playerX = playerLoc.getX();
        double playerY = playerLoc.getY();
        double playerZ = playerLoc.getZ();

        double minX = Math.min(location_left.getX(), location_right.getX());
        double minY = Math.min(location_left.getY(), location_right.getY());
        double minZ = Math.min(location_left.getZ(), location_right.getZ());

        double maxX = Math.max(location_left.getX(), location_right.getX());
        double maxY = Math.max(location_left.getY(), location_right.getY());
        double maxZ = Math.max(location_left.getZ(), location_right.getZ());

        // 判断玩家位置是否在区域内
        // 玩家不在区域内

        return playerX >= minX && playerX <= maxX &&
                playerY >= minY && playerY <= maxY &&
                playerZ >= minZ && playerZ <= maxZ; // 玩家在区域内
    }

    public void playMusic(Player player, String area_name, boolean play_or_stop) {
        String musicName = new ElementFromYaml().getMusicName(area_name);

        // 停止之前正在播放的音乐
        if (this.radioSongPlayer != null) {
            this.radioSongPlayer.setPlaying(false);
            this.radioSongPlayer.removePlayer(player);
        }

        if (play_or_stop && !musicName.isEmpty()) {
            player.stopSound(SoundCategory.MUSIC);
            // 初始化新的RadioSongPlayer对象并播放音乐
            this.radioSongPlayer = new RadioSongPlayer(NBSDecoder
                    .parse(new File(Info_OBj.musicFilePath + File.separator + musicName)));
            this.radioSongPlayer.addPlayer(player);
            this.radioSongPlayer.setPlaying(true);
        }
    }
}
