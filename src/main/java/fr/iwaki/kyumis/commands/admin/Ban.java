package fr.iwaki.kyumis.commands.admin;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.embeds.BanEB;
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
import java.util.concurrent.TimeUnit;

public class Ban extends ListenerAdapter {

    public static final String COMMAND_NAME = "ban";
    public static final String COMMAND_DESCRIPTION = "Bannir le membre sélectionné du serveur. " +
            "(Utilisable uniquement sur un serveur)";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                var reasonOpt = event.getOption("reason");
                String reason = reasonOpt != null && !reasonOpt.getAsString().isEmpty() ? reasonOpt.getAsString() : "Aucune raison n'est fournie.";
                Member targetMember = event.getOption("user").getAsMember();
                User userTarget = targetMember.getUser();
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
                            .setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur tu ne peux donc pas le bannir !");
                    event.replyEmbeds(targetIsOwner.build()).setEphemeral(true).queue();
                } else if (author.getId().equals(targetMember.getId())) {

                    EmbedBuilder targetIsAuthor = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("N'essaie pas de te bannir toi même !");
                    event.replyEmbeds(targetIsAuthor.build()).setEphemeral(true).queue();
                } else if (targetMember.hasPermission(Permission.BAN_MEMBERS)) {

                    EmbedBuilder targetAsBanPerm = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de bannir des membres, tu ne peux donc pas le bannir !");
                    event.replyEmbeds(targetAsBanPerm.build()).setEphemeral(true).queue();
                } else {
                    
                    BanEB banEB = new BanEB();
                    banEB.getBuilder().setTitle("Sanctions - Bannissements");
                    banEB.getBuilder().setDescription(targetMember.getAsMention() + " a été banni !");
                    banEB.getBuilder().addField("Par: ", author.getAsMention(), true);
                    banEB.getBuilder().addField("Raison: ", "``" + reason + "``", true);
                    banEB.getBuilder().addField("Id du membre: ", "``" + targetMember.getId() + "``", false);
                    banEB.getBuilder().setColor(BotInfos.BOT_COLOR);
                    banEB.getBuilder().setTimestamp(new Date().toInstant());

                    guild.ban(targetMember, 0, TimeUnit.SECONDS).reason(reason)
                            .flatMap(success -> event.replyEmbeds(banEB.getBuilder().build()))
                            .queue();

                    BanEB banToMemberEB = new BanEB();
                    banToMemberEB.getBuilder().setTitle("Sanctions - Bannissements");
                    banToMemberEB.getBuilder().setDescription("Vous avez été banni du serveur **" + guild.getName() + "**");
                    banToMemberEB.getBuilder().addField("Par: ", author.getAsMention(), true);
                    banToMemberEB.getBuilder().addField("Raison: ", "``" + reason + "``", true);
                    banToMemberEB.getBuilder().setColor(BotInfos.BOT_COLOR);
                    banToMemberEB.getBuilder().setTimestamp(new Date().toInstant());

                    userTarget.openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(banToMemberEB.getBuilder().build()).queue());
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
