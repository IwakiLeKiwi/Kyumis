package fr.iwaki.kyumis.event;

import fr.iwaki.kyumis.commands.admin.*;
import fr.iwaki.kyumis.commands.mod.*;
import fr.iwaki.kyumis.commands.utils.*;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import java.util.ArrayList;
import java.util.List;

public class InteractionEventListener extends ListenerAdapter  {

    @Override
    public void onGuildReady(GuildReadyEvent event) {

        List<CommandData> commandData = new ArrayList<>();

        // Options Data Commands

        // Say
        OptionData sayMessageOption = new OptionData(OptionType.STRING, "message", "Message du bot", true);

        // Mute
        OptionData muteUserOption = new OptionData(OptionType.USER, "user", "Le membre à mute.", true);
        OptionData muteTimeOption = new OptionData(OptionType.STRING, "time", "Le temps du mute.", true)
                .addChoice("1 minute", "1min")
                .addChoice("5 minutes", "5min")
                .addChoice("15 minutes", "15min")
                .addChoice("30 minutes", "30min")
                .addChoice("1 heure", "1h")
                .addChoice("2 heures", "2h")
                .addChoice("1 jour", "1day")
                .addChoice("2 jours", "2days")
                .addChoice("1 semaine", "1week");
        OptionData muteReasonOption = new OptionData(OptionType.STRING, "reason", "La raison du mute.", false);

        // Unmute
        OptionData unmuteUserOption = new OptionData(OptionType.USER, "user", "Le membre à unmute.", true);
        OptionData unmuteReasonOption = new OptionData(OptionType.STRING, "reason", "La raison de l'unmute.", false);

        // Clear
        OptionData clearAmountOption = new OptionData(OptionType.INTEGER, "amount", "nombre de message à supprimer", true);

        // Ban
        OptionData banMemberOption = new OptionData(OptionType.USER, "user", "Le membre à bannir", true);
        OptionData banReasonOption = new OptionData(OptionType.STRING, "reason", "La raison du bannisement", false);

        // Unban
        OptionData unbanMemberOption = new OptionData(OptionType.USER, "id", "Le membre à bannir", true);
        OptionData unbanReasonOption = new OptionData(OptionType.STRING, "reason", "Le membre à débannir", false);

        // Kick
        OptionData kickMemberOption = new OptionData(OptionType.USER, "user", "Le membre à kick", true);
        OptionData kickReasonOption = new OptionData(OptionType.STRING, "reason", "Le raison du kick", false);

        // Infos
        OptionData infosUserOption = new OptionData(OptionType.USER, "user", "Les infos du membre", true);

        // SetStatus
        OptionData setstatusStatusOption = new OptionData(OptionType.STRING, "status", "Le status de Kyumis.", true)
                .addChoice("Joue à...", "playing")
                .addChoice("Regarde...", "watching")
                .addChoice("Stream...", "streaming")
                .addChoice("Participe à..." , "competing")
                .addChoice("Écoute...", "listening");
        OptionData setstatusActivityOption = new OptionData(OptionType.STRING, "activity", "L'activité de Kyumis.", true);
        OptionData setstatusUrlOption = new OptionData(OptionType.STRING, "url", "L'URL du stream.", false);

        // Register All Commands

        // Say
        commandData.add(Commands.slash(Say.COMMAND_NAME, Say.COMMAND_DESCRIPTION).addOptions(sayMessageOption).setDefaultPermissions(
                DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)
        ));

        // Mute
        commandData.add(Commands.slash(Mute.COMMAND_NAME, Mute.COMMAND_DESCRIPTION).addOptions(muteUserOption, muteTimeOption, muteReasonOption)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(
                                Permission.MODERATE_MEMBERS
                        )
                ));

        // Unmute
        commandData.add(Commands.slash(Unmute.COMMAND_NAME, Unmute.COMMAND_DESCRIPTION).addOptions(unmuteUserOption, unmuteReasonOption).setDefaultPermissions(
                DefaultMemberPermissions.enabledFor(
                        Permission.MODERATE_MEMBERS
                )
        ));

        // Clear
        commandData.add(Commands.slash(Clear.COMMAND_NAME, Clear.COMMAND_DESCRIPTION).addOptions(clearAmountOption).setDefaultPermissions(
                DefaultMemberPermissions.enabledFor(
                        Permission.MESSAGE_MANAGE
                )
        ));

        // Ban
        commandData.add(Commands.slash(Ban.COMMAND_NAME, Ban.COMMAND_DESCRIPTION).addOptions(banMemberOption, banReasonOption).setDefaultPermissions(
                DefaultMemberPermissions.enabledFor(
                        Permission.BAN_MEMBERS
                )
        ));

        // Unban
        commandData.add(Commands.slash(Unban.COMMAND_NAME, Unban.COMMAND_DESCRIPTION).addOptions(unbanMemberOption, unbanReasonOption).setDefaultPermissions(
                DefaultMemberPermissions.enabledFor(
                        Permission.BAN_MEMBERS
                )
        ));

        // Kick
        commandData.add(Commands.slash(Kick.COMMAND_NAME, Kick.COMMAND_DESCRIPTION).addOptions(kickMemberOption, kickReasonOption).setDefaultPermissions(
                DefaultMemberPermissions.enabledFor(
                        Permission.KICK_MEMBERS
                )
        ));

        // Infos
        commandData.add(Commands.slash(Infos.COMMAND_NAME, Infos.COMMAND_DESCRIPTION).addOptions(infosUserOption));

        // SetStatus
        commandData.add(Commands.slash(SetStatus.COMMAND_NAME, SetStatus.COMMAND_DESCRIPTION).addOptions(setstatusStatusOption,
                setstatusActivityOption, setstatusUrlOption).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)
        ));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onReady(ReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        // Commands options

        // Register all commands

        // Tokens
        commandData.add(Commands.slash(Token.COMMAND_NAME, Token.COMMAND_DESCRIPTION));

        // Help
        commandData.add(Commands.slash(Help.COMMAND_NAME, Help.COMMAND_DESCRIPTION));

        // Server
        commandData.add(Commands.slash(Server.COMMAND_NAME, Server.COMMAND_DESCRIPTION));

        event.getJDA().updateCommands().addCommands(commandData).queue();
    }
}
