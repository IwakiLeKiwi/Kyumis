package fr.iwaki.kyumis.commands.admin;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import fr.iwaki.kyumis.embeds.MuteEB;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Mute extends ListenerAdapter {

    public static final String COMMAND_NAME = "mute";
    public static final String COMMAND_DESCRIPTION = "Réduis au silence un membre du serveur. " +
            "(Utilisable uniquement sur un serveur)";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                var reasonOpt = event.getOption("reason");
                String reason = reasonOpt != null && !reasonOpt.getAsString().isEmpty() ? reasonOpt.getAsString() : "Aucune raison n'est fournie.";
                String time = event.getOption("time").getAsString();
                Member targetMember = event.getOption("user").getAsMember();
                Member author = event.getMember();
                Guild guild = event.getGuild();

                switch (time.toLowerCase()) {
                    case "1min" -> {

                        long amount = 1;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else  {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " minutes``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.MINUTES).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                    case "5min" -> {

                        long amount = 5;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else  {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " minutes``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.MINUTES).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                    case "15min" -> {

                        long amount = 15;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else  {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " minutes``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.MINUTES).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                    case "30min" -> {

                        long amount = 30;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else  {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " minutes``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.MINUTES).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                    case "1h" -> {

                        long amount = 1;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else  {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " heure``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.HOURS).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                    case "2h" -> {

                        long amount = 2;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else  {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " heures``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.HOURS).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                    case "1day" -> {

                        long amount = 1;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else  {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " jour``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.DAYS).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                    case "2days" -> {

                        long amount = 2;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " jours``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.DAYS).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                    case "1week" -> {

                        long amount = 7;

                        if (reason.length() >= 512) {

                            MuteEB reasonHight = new MuteEB();
                            reasonHight.getBuilder().setColor(BotInfos.BOT_COLOR);
                            reasonHight.getBuilder().setDescription("La raison ne peux pas dépasser plus de ``512`` caractères !");

                            event.replyEmbeds(reasonHight.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isOwner()) {

                            MuteEB targetIsOwner = new MuteEB();
                            targetIsOwner.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsOwner.getBuilder().setDescription(targetMember.getAsMention() + " est le propriétaire de ce serveur, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetIsOwner.getBuilder().build()).setEphemeral(true).queue();
                        } else if (author.getId().equals(targetMember.getId())) {

                            MuteEB targetIsAuthor = new MuteEB();
                            targetIsAuthor.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsAuthor.getBuilder().setDescription("N'essaie pas de te mute toi même !");
                            event.replyEmbeds(targetIsAuthor.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.hasPermission(Permission.MODERATE_MEMBERS)) {

                            MuteEB targetAsBanPerm = new MuteEB();
                            targetAsBanPerm.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetAsBanPerm.getBuilder().setDescription("Le membre " + targetMember.getAsMention() + " à aussi les permissions de mute des membres, tu ne peux donc pas le mute !");
                            event.replyEmbeds(targetAsBanPerm.getBuilder().build()).setEphemeral(true).queue();
                        } else if (targetMember.isTimedOut()) {

                            MuteEB targetIsMute = new MuteEB();
                            targetIsMute.getBuilder().setColor(BotInfos.BOT_COLOR);
                            targetIsMute.getBuilder().setDescription(targetMember.getAsMention() + " est déjà mute !");
                            event.replyEmbeds(targetIsMute.getBuilder().build()).setEphemeral(true).queue();
                        } else {

                            MuteEB eb = new MuteEB();
                            eb.getBuilder().setColor(BotInfos.BOT_COLOR);
                            eb.getBuilder().setTitle("Sanction - Mute");
                            eb.getBuilder().setDescription(author.getAsMention() + " a réduis au silence " + targetMember.getAsMention() + " !");
                            eb.getBuilder().addField("Raison du mute:", "``" + reason + "``", false);
                            eb.getBuilder().addField("Durée du mute:", "``" + amount + " jours``", false);
                            guild.timeoutFor(targetMember, amount, TimeUnit.DAYS).reason(reason)
                                    .flatMap(success -> event.replyEmbeds(eb.getBuilder().build())).queue();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.log(LogType.ERROR, "Impossible d'exécuter la commande !", Arrays.toString(e.getStackTrace()));

            MuteEB error = new MuteEB();
            error.getBuilder().setColor(BotInfos.BOT_COLOR);
            error.getBuilder().setDescription(BotInfos.WRONG + "Impossible d'exécuter la commande !");

            event.replyEmbeds(error.getBuilder().build()).setEphemeral(false).queue();
        }
    }
}
