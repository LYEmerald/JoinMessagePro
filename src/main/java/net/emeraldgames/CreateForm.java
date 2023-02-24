package net.emeraldgames;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;

/**
 * 部分源代码来自@smartcmd
 * www.minebbs.com/resources/joinform.1623/
 */

public class CreateForm {
    static int JoinForm = 20230120;
    public static void JoinSimpleForm(Plugin p, Player player) {
        Config config = new Config(p.getDataFolder() + "/config.yml");
        FormWindowSimple form = new FormWindowSimple(config.getString("form.title"),config.getString("form.content"));
        form.addButton(new ElementButton(config.getString("form.button")));
        player.showFormWindow(form,JoinForm);
    }
}
