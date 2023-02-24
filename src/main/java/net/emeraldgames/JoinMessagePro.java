package net.emeraldgames;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.network.protocol.ToastRequestPacket;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.util.HashMap;

/**
 * @author LYEmerald Copyright (c) 2023.1
 * @version 1.0.0
 */

public class JoinMessagePro extends PluginBase implements Listener {

    @Override
    public void onLoad() {
        this.getLogger().info("JoinMessage Pro 开始加载");
        this.getLogger().info("作者: LYEmerald");
        //插件加载
    }
    @Override
    public void onEnable() {
        this.getLogger().info("JoinMessage Pro 加载完毕");
        this.getLogger().info("当前版本: v1.0.0");
        this.getServer().getPluginManager().registerEvents(this, this);//注册监听器
        this.getServer().getPluginManager().registerEvents(new FormEvent(this),this);
        this.getDataFolder().mkdirs(); //创建插件文件夹
        this.saveDefaultConfig(); //保存默认配置文件
        Config config = this.getConfig(); //读取默认配置文件
        //插件启用
    }
    @Override
    public void onDisable() {
        this.getLogger().info("JoinMessage Pro 正在关闭");
        this.getLogger().info("感谢您的使用");
        //插件卸载
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config config = this.getConfig();
        boolean toast = config.getBoolean("Toast.enable");
        boolean welcome = config.getBoolean("welcome.enable");
        boolean join = config.getBoolean("join.enable");
        boolean join_broadcast = config.getBoolean("join_broadcast.enable");
        if(welcome == true){
            event.getPlayer().sendTip(config.getString("welcome.tip").replace("%player%",event.getPlayer().getName()));
            event.getPlayer().sendTitle(config.getString("welcome.title"),
                    config.getString("welcome.subtitle").replace("%player%",event.getPlayer().getName()),
                    config.getInt("fadeIn",20),
                    config.getInt("stay",40),
                    config.getInt("fadeOut",20));
            event.getPlayer().sendMessage(config.getString("welcome.message").replace("%player%",event.getPlayer().getName()));
            //欢迎消息
        }
        if(join == true){
            event.setJoinMessage(config.getString("join.message").replace("%player%",event.getPlayer().getName()));
            //玩家加入消息
        }
        if(toast == true){
            ToastRequestPacket packet = new ToastRequestPacket();
            packet.title = config.getString("Toast.title").replace("%player%",event.getPlayer().getName());
            packet.content = config.getString("Toast.content").replace("%player%",event.getPlayer().getName());
            player.dataPacket(packet);
            //顶部弹窗
        }
        if(join_broadcast == true) {
            for(Player player1 : Server.getInstance().getOnlinePlayers().values()){
                player1.sendTip(config.getString("join_broadcast.message").replace("%player%",event.getPlayer().getName()));
            }
        }

    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Config config = this.getConfig();
        boolean quit = config.getBoolean("quit.enable");
        boolean quit_broadcast = config.getBoolean("quit_broadcast.enable");
        if(quit_broadcast == true){
            for(Player player1 : Server.getInstance().getOnlinePlayers().values()){
                player1.sendTip(config.getString("quit_broadcast.message").replace("%player%",event.getPlayer().getName()));
            }
        }
        if(quit == true){
            event.setQuitMessage(config.getString("quit.message").replace("%player%",event.getPlayer().getName()));
        }
        //玩家退出消息
    }
}
