package fr.iwaki.kyumis;

import fr.iwaki.kyumis.commands.admin.*;
import fr.iwaki.kyumis.commands.mod.*;
import fr.iwaki.kyumis.commands.utils.*;
import fr.iwaki.kyumis.database.*;
import fr.iwaki.kyumis.event.*;
import fr.iwaki.kyumis.utils.*;
import fr.iwaki.kyumis.utils.logger.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.*;
import net.dv8tion.jda.api.utils.cache.*;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Kyumis {

    public static final GatewayIntent[] INTENTS = { GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS,
    GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS };

    public static final CacheFlag[] CACHE_FLAGS = { CacheFlag.VOICE_STATE, CacheFlag.MEMBER_OVERRIDES };

    public static final Object[] COMMANDS = { new Say(), new Mute(), new Unmute(), new Infos(), new Token(), new Help(), new Clear(), new Ban(),
    new Unban(), new Kick(), new Server(), new SetStatus() };
    public static final Object[] EVENT_LISTENERS = { new InteractionEventListener() };

    private static final Long millisStart = System.currentTimeMillis();
    public static MySQL mySQL;
    private static final Config config;
    public static JDABuilder jdaBuilder;
    public static JDA jda;

    static {
        try {
            config = new Config();
        } catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public static void start() {

        jdaBuilder = JDABuilder.createDefault(config.getToken());
        jda = jdaBuilder
                .enableIntents(Arrays.asList(INTENTS))
                .enableCache(Arrays.asList(CACHE_FLAGS))
                .addEventListeners(EVENT_LISTENERS)
                .addEventListeners(COMMANDS)
                .setActivity(Activity.playing("Kanawa"))
                .setStatus(OnlineStatus.ONLINE)
                .build();

        Logger.log(LogType.OK, "Loading services. [" + ConsoleColors.GREEN_BOLD + (System.currentTimeMillis() - millisStart) + "ms" + ConsoleColors.GREEN + "]");
    }

    public static void main(String[] args) {

        try {
            start();

            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        connectMySQL();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } catch (Exception e) {
            Logger.log(LogType.ERROR, "Launch ERROR " + Arrays.toString(e.getStackTrace()));
        }
    }

    private static void connectMySQL() throws SQLException {

        mySQL = new MySQL(config.getDB_Host(), config.getDB_Port(), config.getDB_Database(), config.getDB_User(), config.getDB_Password());
        try {
            mySQL.connect();
            Logger.log(LogType.OK, "Database connectée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.log(LogType.ERROR, "Error while trying to connect to database.");
            return;
        }

        if(mySQL.checkConnection(0)) {
            DatabaseMetaData dbm = mySQL.getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, "guilds", null);
            if(tables.next()) {
                return;
            }
            String table = "CREATE TABLE guilds ( guild_id varchar(255) primary key, music_channel varchar(255) )";
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(table);
            preparedStatement.execute();
        }
    }
}