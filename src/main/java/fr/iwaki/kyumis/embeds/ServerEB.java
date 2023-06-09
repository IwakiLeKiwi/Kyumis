package fr.iwaki.kyumis.embeds;

import fr.iwaki.kyumis.bot.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;

public class ServerEB {

    private final EmbedBuilder builder;

    public ServerEB() {
        builder = new EmbedBuilder();
        builder.setColor(BotInfos.BOT_COLOR);
        builder.setDescription(BotInfos.CHECK + "Message envoyé !");
    }

    public EmbedBuilder getBuilder() {
        return builder;
    }
}
