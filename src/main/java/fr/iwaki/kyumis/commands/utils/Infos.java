package fr.iwaki.kyumis.commands.utils;

import fr.iwaki.kyumis.bot.BotInfos;
import fr.iwaki.kyumis.embeds.InfosEB;
import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Arrays;

public class Infos extends ListenerAdapter {

    public static final String COMMAND_NAME = "infos";
    public static final String COMMAND_DESCRIPTION = "Donne les information d'un utilisateur.";

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        String command = event.getName();

        try {

            if (command.equals(COMMAND_NAME)) {

                Member member = event.getOption("user").getAsMember();
                String roleList = "";
                for (Role role : member.getRoles()) {
                    roleList += role.getAsMention() + " ";
                }

                InfosEB infosEB = new InfosEB();
                infosEB.getBuilder().setDescription("*Informations sur* " + member.getUser().getAsMention());
                infosEB.getBuilder().addField("Pseudo:", "``" + member.getUser().getName() + "``", true);
                infosEB.getBuilder().addField("Tag:", "``" + member.getUser().getAsTag() + "``", true);
                infosEB.getBuilder().addField("Identifiant:", "``" + member.getUser().getId() + "``", false);
                infosEB.getBuilder().addField("Rôles:", roleList, false);

                event.replyEmbeds(infosEB.getBuilder().build()).queue();
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