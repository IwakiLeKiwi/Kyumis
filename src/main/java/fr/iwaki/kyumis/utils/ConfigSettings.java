package fr.iwaki.kyumis.utils;

public enum ConfigSettings {

    TOKEN("TOKEN="),
    DB_HOST("DB_HOST="),
    DB_PORT("DB_PORT="),
    DB_DATABASE("DB_DATABASE="),
    DB_USER("DB_USER="),
    DB_PASSWORD("DB_PASSWORD=")
    ;

    public final String label;

    ConfigSettings(String label) {
        this.label = label;
    }
}
