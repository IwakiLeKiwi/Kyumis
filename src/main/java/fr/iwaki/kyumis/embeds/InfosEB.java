package fr.iwaki.kyumis.embeds;

import fr.iwaki.kyumis.bot.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;

public class InfosEB {

    private final EmbedBuilder builder;

    public InfosEB() {
        builder = new EmbedBuilder();
        builder.setColor(BotInfos.BOT_COLOR);
    }

    public EmbedBuilder getBuilder() {
        return builder;
    }
}
