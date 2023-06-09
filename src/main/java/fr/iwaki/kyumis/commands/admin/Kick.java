package fr.iwaki.kyumis.commands.admin;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.embeds.KickEB;
import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.Date;

public class Kick extends ListenerAdapter {

    public static final String COMMAND_NAME = "kick";
    public static final String COMMAND_DESCRIPTION = "Kick le membre sélectionné du serveur. " +
            "(Utilisable uniquement sur un serveur)";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                var reasonOpt = event.getOption("reason");
                String reason = reasonOpt != null && !reasonOpt.getAsString().isEmpty() ? reasonOpt.getAsString() : "Aucune raison n'est fournie.";
                Member targetMember = event.getOption("user").getAsMember();
                User userTarget = event.getUser();
                Member author = event.getMember();
                Guild guild = event.getGuild();

                if (reason.length() >= 512) {

                    EmbedBuilder reasonHight = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");
                    event.replyEmbeds(reasonHight.build()).setEphemeral(true).queue();
                } else if (targetMember.isOwner()) {

                    EmbedBuilder targetIsOwner = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur tu ne peux donc pas le kick !");
                    event.replyEmbeds(targetIsOwner.build()).setEphemeral(true).queue();
                } else if (author.getId().equals(targetMember.getId())) {

                    EmbedBuilder targetIsAuthor = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("N'essaie pas de te kick toi même !");
                    event.replyEmbeds(targetIsAuthor.build()).setEphemeral(true).queue();
                } else if (targetMember.hasPermission(Permission.KICK_MEMBERS)) {

                    EmbedBuilder targetAsKickPerm = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de kick des membres, tu ne peux donc pas le kick !");
                    event.replyEmbeds(targetAsKickPerm.build()).setEphemeral(true).queue();
                } else {

                    KickEB kickEB = new KickEB();
                    kickEB.getBuilder().setTitle("Sanctions - Kick");
                    kickEB.getBuilder().setDescription(targetMember.getAsMention() + " a été kick !");
                    kickEB.getBuilder().addField("Par: ", author.getAsMention(), true);
                    kickEB.getBuilder().addField("Raison: ", "``" + reason + "``", true);
                    kickEB.getBuilder().addField("Id du membre: ", "``" +targetMember.getId() + "``", false);
                    kickEB.getBuilder().setColor(BotInfos.BOT_COLOR);
                    kickEB.getBuilder().setTimestamp(new Date().toInstant());

                    guild.kick(targetMember).reason(reason)
                            .flatMap(success -> event.replyEmbeds(kickEB.getBuilder().build()))
                            .queue();

                    KickEB kickToMemberEB = new KickEB();
                    kickToMemberEB.getBuilder().setTitle("Sanctions - Kick");
                    kickToMemberEB.getBuilder().setDescription("Vous avez été kick du serveur **" + guild.getName() + "**");
                    kickToMemberEB.getBuilder().addField("Par: ", author.getAsMention(), true);
                    kickToMemberEB.getBuilder().addField("Raison: ", "``" + reason + "``", true);
                    kickToMemberEB.getBuilder().setColor(BotInfos.BOT_COLOR);
                    kickToMemberEB.getBuilder().setTimestamp(new Date().toInstant());

                    userTarget.openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(kickToMemberEB.getBuilder().build()).queue());
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
