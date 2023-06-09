package fr.iwaki.kyumis.commands.utils;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.embeds.InfosEB;
import fr.iwaki.kyumis.embeds.ServerEB;
import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

public class Server extends ListenerAdapter {

    public static final String COMMAND_NAME = "server";
    public static final String COMMAND_DESCRIPTION = "Donne les instructions du serveur.";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                EmbedBuilder socials = new EmbedBuilder();
                socials.setColor(BotInfos.BOT_PRIMARY_COLOR);
                socials.setImage("https://cdn.discordapp.com/attachments/1044730326498414612/1066825119172935790/Banderolreseaux.png");

                EmbedBuilder socials2 = new EmbedBuilder();
                socials2.setColor(BotInfos.BOT_PRIMARY_COLOR);
                socials2.setDescription("### Les réseaux\n");
                socials2.appendDescription("- Twitter https://twitter.com/wyntale\n");
                socials2.appendDescription("- Discord https://discord.com/invite/wyntale\n");
                socials2.appendDescription("- TikTok https://www.tiktok.com/@wyntalemc\n");
                socials2.appendDescription("- Youtube https://www.youtube.com/@wyntale9563\n");

                EmbedBuilder level = new EmbedBuilder();
                level.setColor(BotInfos.BOT_PRIMARY_COLOR);
                level.setImage("https://cdn.discordapp.com/attachments/1044730326498414612/1066825117310664775/Banderolniveau.png");

                event.replyEmbeds(new ServerEB().getBuilder().build()).setEphemeral(true).queue();
                event.getChannel().sendMessageEmbeds(socials.build(), socials2.build(), level.build()).queue();
            }
        } catch (Exception e) {
            Logger.log(LogType.ERROR, "Impossible d'exécuter la commande !", Arrays.toString(e.getStackTrace()));

            EmbedBuilder error = new EmbedBuilder()
                    .setColor(BotInfos.BOT_COLOR)
                    .setDescription(BotInfos.WRONG + "Impossible d'exécuter la commande !");

            event.replyEmbeds(error.build()).setEphemeral(true).queue();
        }
    }
}
