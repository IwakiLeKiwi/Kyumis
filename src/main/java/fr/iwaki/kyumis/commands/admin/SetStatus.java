package fr.iwaki.kyumis.commands.admin;

import fr.iwaki.kyumis.bot.*;
import fr.iwaki.kyumis.utils.logger.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

import static net.dv8tion.jda.api.entities.Activity.STREAMING_URL;

public class SetStatus extends ListenerAdapter {

    public static final String COMMAND_NAME = "setstatus";
    public static final String COMMAND_DESCRIPTION = "Change le status du bot.";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                String status = event.getOption("status").getAsString();
                String activity = event.getOption("activity").getAsString();
                var urlOpt = event.getOption("url");
                String url = urlOpt != null && !urlOpt.getAsString().isEmpty() ? urlOpt.getAsString() : "Aucune URL n'est saisie.";
                Guild guild = event.getGuild();

                Activity.ActivityType streaming = Activity.ActivityType.STREAMING;
                Activity.ActivityType playing = Activity.ActivityType.PLAYING;
                Activity.ActivityType listening = Activity.ActivityType.LISTENING;
                Activity.ActivityType competing = Activity.ActivityType.COMPETING;
                Activity.ActivityType watching = Activity.ActivityType.WATCHING;

                switch (status.toLowerCase()) {
                    case "playing" -> {

                        EmbedBuilder setActivity = new EmbedBuilder()
                                .setColor(BotInfos.BOT_COLOR)
                                .setDescription("Le status de Kyumis à été changé avec succès pour: \n" +
                                        "``Joue à " + activity + "``");

                        event.replyEmbeds(setActivity.build()).setEphemeral(false).queue();

                        guild.getJDA().getPresence().setActivity(Activity.of(playing, activity, null));
                    }
                    case "watching" -> {

                        EmbedBuilder setActivity = new EmbedBuilder()
                                .setColor(BotInfos.BOT_COLOR)
                                .setDescription("Le status de Kyumis à été changé avec succès pour: \n" +
                                        "``Regarde " + activity + "``");

                        event.replyEmbeds(setActivity.build()).setEphemeral(false).queue();

                        guild.getJDA().getPresence().setActivity(Activity.of(watching, activity, null));
                    }
                    case "listening" -> {

                        EmbedBuilder setActivity = new EmbedBuilder()
                                .setColor(BotInfos.BOT_COLOR)
                                .setDescription("Le status de Kyumis à été changé avec succès pour: \n" +
                                        "``Écoute " + activity + "``");

                        event.replyEmbeds(setActivity.build()).setEphemeral(false).queue();

                        guild.getJDA().getPresence().setActivity(Activity.of(listening, activity, null));
                    }
                    case "competing" -> {

                        EmbedBuilder setActivity = new EmbedBuilder()
                                .setColor(BotInfos.BOT_COLOR)
                                .setDescription("Le status de Kyumis à été changé avec succès pour: \n" +
                                        "``Participant à " + activity + "``");

                        event.replyEmbeds(setActivity.build()).setEphemeral(false).queue();

                        guild.getJDA().getPresence().setActivity(Activity.of(competing, activity, null));
                    }
                    case "streaming" -> {

                        if (isValidStreamingUrl(url)) {

                            EmbedBuilder setActivity = new EmbedBuilder()
                                    .setColor(BotInfos.BOT_COLOR)
                                    .setDescription("Le status de Kyumis à été changé avec succès pour: \n" +
                                            "``Stream " + activity + "``");

                            event.replyEmbeds(setActivity.build()).setEphemeral(false).queue();

                            guild.getJDA().getPresence().setActivity(Activity.of(streaming, activity, url));
                        } else {

                            EmbedBuilder errorStream= new EmbedBuilder()
                                    .setColor(BotInfos.BOT_COLOR)
                                    .setDescription("Veuillez indiquez une **URL** valide. "
                                            + "(ex: https://twitch.tv/)");

                            event.replyEmbeds(errorStream.build()).setEphemeral(true).queue();
                        }
                    }
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

    static boolean isValidStreamingUrl(String validUrl)
    {
        return validUrl != null && STREAMING_URL.matcher(validUrl).matches();
    }
}
