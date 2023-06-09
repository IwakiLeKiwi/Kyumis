package fr.iwaki.kyumis.commands.mod;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Clear extends ListenerAdapter {

    public static final String COMMAND_NAME = "clear";
    public static final String COMMAND_DESCRIPTION = "Efface la conversation.";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                int amount = Integer.parseInt(event.getOption("amount").getAsString());

                if (amount == 1) {

                    EmbedBuilder clearOneEmbed = new EmbedBuilder()
                            .setTitle("Utilitaire - Clear")
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("``" + amount + "`` message a bien été suprimmé.")
                            .setTimestamp(new Date().toInstant())
                            .setFooter("Clear");

                    List<Message> messageHistory = event.getChannel().getHistory().retrievePast(amount).complete();
                    for (Message message : messageHistory) {
                        if (!message.isPinned()) message.delete().queue();
                    }

                    event.replyEmbeds(clearOneEmbed.build()).setEphemeral(true).queue();
                } else if (amount >= 101 || amount <= 0) {

                    EmbedBuilder errorEmbed = new EmbedBuilder()
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription(BotInfos.WRONG + "Je ne peux que supprimer un nombre de messages compris entre ``1`` et ``100``.");

                    event.replyEmbeds(errorEmbed.build()).setEphemeral(true).queue();
                } else {

                    EmbedBuilder clearManyEmbed = new EmbedBuilder()
                            .setTitle("Utilitaire - Clear")
                            .setColor(BotInfos.BOT_COLOR)
                            .setDescription("``" + amount + "`` messages ont bien été suprimmés.")
                            .setTimestamp(new Date().toInstant())
                            .setFooter("Clear");

                    event.getChannel().getHistory().retrievePast(amount).queue(messageHistory -> {

                        for (Message message : messageHistory) {
                            if (!message.isPinned()) message.delete().queue();
                        }

                    });

                    event.replyEmbeds(clearManyEmbed.build()).setEphemeral(true).queue();
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
