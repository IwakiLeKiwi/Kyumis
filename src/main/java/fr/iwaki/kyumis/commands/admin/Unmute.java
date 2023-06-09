package fr.iwaki.kyumis.commands.admin;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.Date;

public class Unmute extends ListenerAdapter {

    public static final String COMMAND_NAME = "unmute";
    public static final String COMMAND_DESCRIPTION = "Fait revenir la voie à un membre du serveur. " +
            "(Utilisable uniquement sur un serveur)";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                var reasonOpt = event.getOption("reason");
                String reason = reasonOpt != null && !reasonOpt.getAsString().isEmpty() ? reasonOpt.getAsString() : "Aucune raison n'est fournie.";
                Member targetMember = event.getOption("user").getAsMember();
                Member author = event.getMember();
                Guild guild = event.getGuild();

                if (reason.length() >= 512) {

                    EmbedBuilder reasonHight = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                    event.replyEmbeds(reasonHight.build()).setEphemeral(true).queue();
                } else if (author.getId().equals(targetMember.getId())) {

                    EmbedBuilder targetIsAuthor = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("N'essaie pas de te unmute toi même !");
                    event.replyEmbeds(targetIsAuthor.build()).setEphemeral(true).queue();
                } else if (!targetMember.isTimedOut()) {

                    EmbedBuilder targetIsMute = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("Ce membre n'est pas mute !");
                    event.replyEmbeds(targetIsMute.build()).setEphemeral(true).queue();
                } else {
                    EmbedBuilder unmuteMessage = new EmbedBuilder()
                            .setTitle("Sanctions - Unmute")
                            .setDescription(targetMember.getAsMention() + " a retrouvé la voie grâce à " + author.getAsMention() + " pour la raison: \n ``" + reason + "``")
                            .setColor(BotInfos.BOT_COLOR)
                            .setTimestamp(new Date().toInstant());

                    guild.removeTimeout(targetMember).reason(reason)
                            .flatMap(success -> event.replyEmbeds(unmuteMessage.build()))
                            .queue();
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
