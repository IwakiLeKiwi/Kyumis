package fr.iwaki.kyumis.embeds;

import fr.iwaki.kyumis.bot.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

public class HelpEB {

    private final EmbedBuilder builder;
    private final List<Button> actionRow;

    public HelpEB() {
        builder = new EmbedBuilder();
        builder.setTitle("Help");
        builder.setColor(BotInfos.BOT_COLOR);
        this.actionRow = List.of(
                Button.primary("pause", "Pause")
        );
    }

    public EmbedBuilder getBuilder() {
        return builder;
    }

    public List<Button> getActionRow() {
        return actionRow;
    }
}
