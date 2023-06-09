package fr.iwaki.kyumis.commands.utils;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.database.DBCommands;
import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Token extends ListenerAdapter {

    public static final String COMMAND_NAME = "token";
    public static final String COMMAND_DESCRIPTION = "Donne les information d'un utilisateur.";
    DBCommands dbCommands = new DBCommands();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                int tokens = dbCommands.getTokens(event.getUser());
                event.reply(event.getUser().getAsMention() + " tu as " + tokens + " tokens.").queue();
            }
        } catch (Exception e) {
            Logger.log(LogType.ERROR, "Impossible d'exécuter la commande !", Arrays.toString(e.getStackTrace()));

            EmbedBuilder error = new EmbedBuilder()
                    .setColor(BotInfos.BOT_COLOR)
                    .setDescription(BotInfos.WRONG + "Impossible d'exécuter la commande !");

            event.replyEmbeds(error.build()).setEphemeral(false).queue();
        }
    }
}
