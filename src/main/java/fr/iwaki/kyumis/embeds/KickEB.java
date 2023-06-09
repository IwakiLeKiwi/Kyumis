package fr.iwaki.kyumis.embeds;

import fr.iwaki.kyumis.bot.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;

public class KickEB {

    private final EmbedBuilder builder;

    public KickEB() {
        builder = new EmbedBuilder();
        builder.setColor(BotInfos.BOT_COLOR);
    }

    public EmbedBuilder getBuilder() {
        return builder;
    }
}
