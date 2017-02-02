package com.bwfcwalshy.flarebot.commands.music;

import com.bwfcwalshy.flarebot.MessageUtils;
import com.bwfcwalshy.flarebot.commands.Command;
import com.bwfcwalshy.flarebot.commands.CommandType;
import com.bwfcwalshy.flarebot.util.SQLController;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <br>
 * Created by Arsen on 29.10.16..
 */
public class DeleteCommand implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member) {
        if (args.length == 0) {
            MessageUtils.sendMessage("Usage: _delete [NAME]", channel);
            return;
        }
        String name = "";
        for (String arg : args) name += arg + ' ';
        name = name.trim();
        channel.sendTyping().queue();
        try {
            String finalName = name;
            SQLController.runSqlTask(connection -> {
                connection.createStatement().execute("CREATE TABLE IF NOT EXISTS playlist (\n" +
                        "  name VARCHAR(10),\n" +
                        "  guild VARCHAR(10),\n" +
                        "  list VARCHAR(80),\n" +
                        "  scope  VARCHAR(7) DEFAULT 'local',\n" +
                        "  PRIMARY KEY(name, guild)\n" +
                        ")");
                PreparedStatement update = connection.prepareStatement("DELETE FROM playlist WHERE name = ? AND guild = ?");
                update.setString(1, finalName);
                update.setString(2, channel.getGuild().getId());
                if (update.executeUpdate() > 0) {
                    MessageUtils.sendMessage(MessageUtils.getEmbed(sender).setDescription(String.format("*Removed the playlist %s*", finalName)), channel);
                } else MessageUtils.sendMessage(MessageUtils.getEmbed(sender)
                        .setDescription(String.format("*The playlist %s never existed!", finalName)), channel);
            });
        } catch (SQLException e) {
            MessageUtils.sendException("**Database error!**", e, channel);
        }
    }

    @Override
    public String getCommand() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Deletes a playlist. Usage `delete NAME`";
    }

    @Override
    public CommandType getType() {
        return CommandType.MUSIC;
    }

    @Override
    public String getPermission() {
        return "flarebot.playlist.delete";
    }
}
