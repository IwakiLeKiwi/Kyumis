package fr.iwaki.kyumis.database;

import fr.iwaki.kyumis.utils.logger.LogType;
import fr.iwaki.kyumis.utils.logger.Logger;
import net.dv8tion.jda.api.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static fr.iwaki.kyumis.Kyumis.mySQL;

public class DBCommands {

    public int getTokens(User user) {

        try {

            final String query = "SELECT * FROM tokens WHERE userId = ?";
            final PreparedStatement ps = mySQL.getConnection().prepareStatement(query);
            ps.setString(1, user.getId());
            final ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("tokens");
            }

            final String queryCU = "INSERT INTO tokens (userId, tokens) values (?, ?)";
            final PreparedStatement ps2 = mySQL.getConnection().prepareStatement(queryCU);

            int defaultTokens = 50;

            ps2.setString(1, user.getId());
            ps2.setInt(2, defaultTokens);
            ps2.execute();

            return defaultTokens;

        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Nop", Arrays.toString(e.getStackTrace()));
        }

        return 0;
    }
}
