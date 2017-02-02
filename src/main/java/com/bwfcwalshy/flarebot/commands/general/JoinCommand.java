package com.bwfcwalshy.flarebot.commands.general;

import com.bwfcwalshy.flarebot.commands.Command;
import com.bwfcwalshy.flarebot.commands.CommandType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class JoinCommand implements Command {

    @Override
    public void onCommand(User sender, TextChannel channel, Message message, String[] args, Member member) {
        if(member.getVoiceState().inVoiceChannel()){
            if(channel.getGuild().getAudioManager().isAttemptingToConnect()){
                channel.sendMessage("Currently connecting to a voice channel! Try again soon!");
                return;
            }
            channel.getGuild().getAudioManager().openAudioConnection(member.getVoiceState().getChannel());
        }
    }

    @Override
    public String getCommand() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Tell me to join your voice channel.";
    }

    @Override
    public CommandType getType() {
        return CommandType.GENERAL;
    }
}
