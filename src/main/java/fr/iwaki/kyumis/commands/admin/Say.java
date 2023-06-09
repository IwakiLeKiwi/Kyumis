package fr.iwaki.kyumis.commands.admin;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.*;

import java.util.Arrays;

public class Say extends ListenerAdapter {

    public static final String COMMAND_NAME = "say";
    public static final String COMMAND_DESCRIPTION = "Fait parler le bot.";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                String message = event.getOption("message").getAsString();

                if (message.length() >= 512) {
                    EmbedBuilder messageIsToHight = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription(" Le message ne peut pas dépasser plus de ``512`` caractères");
                    event.replyEmbeds(messageIsToHight.build()).setEphemeral(true).queue();
                } else {

                    EmbedBuilder sayMessage = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("Le message ``" + message + "`` à bien été envoyé !");

                    event.getChannel().sendMessage(message).queue();
                    event.replyEmbeds(sayMessage.build()).setEphemeral(true).queue();
                }
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
