package net.emeraldgames;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

/**
 * 部分源代码来自@smartcmd
 * www.minebbs.com/resources/joinform.1623/
 */

public class SendForm extends PluginTask {
    private Player player;

    public SendForm(Plugin owner, Player player) {
        super(owner);
        this.player = player;
    }

    public void onRun(int i) {
        CreateForm.JoinSimpleForm(this.owner, this.player);
    }
}
