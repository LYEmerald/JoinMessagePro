package net.emeraldgames;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;

import static net.emeraldgames.CreateForm.JoinForm;

import java.util.HashMap;

/**
 * 部分源代码来自@smartcmd
 * www.minebbs.com/resources/joinform.1623/
 */

public class FormEvent implements Listener {
    Plugin p;

    public FormEvent(Plugin p) {
        this.p = p;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Config config = new Config(this.p.getDataFolder() + "/config.yml");
        HashMap m = (HashMap)config.get("form");
        Boolean form = config.getBoolean("form.enable");
        if(form == true){
            Server.getInstance().getScheduler().scheduleDelayedTask(new SendForm(this.p, event.getPlayer()), 10 * (Integer)m.get("TaskTime"));
        }
    }
    @EventHandler
    public void onFormResponse(PlayerFormRespondedEvent event){
        Config config = new Config(this.p.getDataFolder() + "/config.yml");
        Player player = event.getPlayer();
        String command = config.getString("form_command.command");
        int id = event.getFormID(); //这将返回一个form的唯一标识`id`
        if (event.wasClosed()) {
            return;
        }

        //分类别讨论
        if (id == JoinForm) {
            FormResponseSimple response = (FormResponseSimple) event.getResponse(); //这里需要强制类型转换一下
            //获取到被按的按钮的id(如果按"x"则返回-1)
            //重点: 按钮id: 我们将上面的addButton中传入的ElementButton放入一个数组，返回的id就是被按的Button的角标
            //例如：玩家按了"我是按钮1"这个按钮，则会返回0
            int clickedButtonId = response.getClickedButtonId();
            switch (clickedButtonId) {
                case 0:
                    Server.getInstance().getCommandMap().dispatch(new ConsoleCommandSender(), command);
                    break;
            }
        }
    }
}
