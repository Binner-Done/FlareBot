package stream.flarebot.flarebot.commands.administrator;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import stream.flarebot.flarebot.FlareBot;
import stream.flarebot.flarebot.commands.Command;
import stream.flarebot.flarebot.commands.CommandType;
import stream.flarebot.flarebot.util.MessageUtils;

import java.awt.*;
import java.util.EnumSet;

public class SetPrefixCommand implements Command {
    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reset")) {
                FlareBot.getPrefixes().set(channel.getGuild().getId(), '_');
                channel.sendMessage(MessageUtils.getEmbed(sender)
                        .setColor(Color.CYAN)
                        .setDescription("Reset the prefix back to `_`!").build())
                        .queue();
            } else if (args[0].length() == 1) {
                FlareBot.getPrefixes().set(channel.getGuild().getId(), args[0].charAt(0));
                channel.sendMessage(MessageUtils.getEmbed(sender)
                        .setDescription(String.format("Set the prefix to `%s`", args[0])).build())
                        .queue();
            } else {
                channel.sendMessage(MessageUtils.getEmbed(sender)
                        .setDescription("Cannot set the prefix to be more than one character long!")
                        .setColor(Color.RED)
                        .build()).queue();
                return;
            }
        } else {
            channel.sendMessage(MessageUtils.getEmbed(sender)
                    .setDescription(String.format("Current guild prefix is `%s`!", FlareBot
                            .getPrefix(channel.getGuild().getId()))).build()).queue();
        }
    }

    @Override
    public String getCommand() {
        return "prefix";
    }

    @Override
    public String getDescription() {
        return "Sets the prefix in this guild!";
    }

    @Override
    public String getUsage() {
        return "`{%}prefix [reset]/[prefix]` - Reset or set a prefix for FlareBot commands";
    }

    @Override
    public CommandType getType() {
        return CommandType.MODERATION;
    }

    @Override
    public boolean isDefaultPermission() {
        return false;
    }

    @Override
    public EnumSet<Permission> getDiscordPermission() {
        return EnumSet.of(Permission.MANAGE_SERVER);
    }

    @Override
    public String[] getAliases() {
        return new String[]{"setprefix"};
    }
}
