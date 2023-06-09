package fr.iwaki.kyumis.utils;

import fr.iwaki.iwalib.files.ResourceLoader;
import fr.iwaki.kyumis.utils.logger.*;

import java.io.*;
import java.util.Properties;

public class Config {

    private final Properties properties;
    private final ResourceLoader resourceLoader = new ResourceLoader();

    private String token, dbHost, dbName, dbUser, dbPassword;
    private int dbPort;

    public Config() throws IOException, InterruptedException {
        this.properties = new Properties();
        InputStream inputStream = resourceLoader.getResourceAsStream("config.ini");
        if (inputStream == null) {
            throw new IOException("Can't load the config file.");
        }
        properties.load(inputStream);
        loadConfig();
    }

    public void loadConfig() {
        token = properties.getProperty("TOKEN");
        dbHost = properties.getProperty("DB_HOST");
        dbPort = Integer.parseInt(properties.getProperty("DB_PORT"));
        dbName = properties.getProperty("DB_DATABASE");
        dbUser = properties.getProperty("DB_USER");
        dbPassword = properties.getProperty("DB_PASSWORD");
        Logger.log(LogType.OK, "Config loaded.");
    }

    public String getToken() {
        return token;
    }

    public String getDB_Host() {
        return dbHost;
    }

    public int getDB_Port() {
        return dbPort;
    }

    public String getDB_Database() {
        return dbName;
    }

    public String getDB_User() {
        return dbUser;
    }

    public String getDB_Password() {
        return dbPassword;
    }
}