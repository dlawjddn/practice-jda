package lets.practice.jda.listener;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DiscordListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();
        log.info("discord message: {}", message.getContentDisplay());
        log.info("discord user info: id = {}, name = {}", event.getAuthor().getId(), event.getAuthor().getName());
        log.info("discord member info: id = {}, nickname = {}", event.getMember().getId(), event.getMember().getNickname());
        log.info("discord server info: name = {}, image = {}", event.getGuild().getName(), event.getGuild().getIconUrl());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "업무시작":
                LocalDateTime startTime = LocalDateTime.now();
                // TODO 출근 시간에 대한 저장
                event.reply(
                        LocalDate.now().toString() + "의 업무 시작 시간은\n" + startTime.getHour() +"시 " + startTime.getMinute() + "분 입니다!"
                        ).setEphemeral(true).queue();
                break;
            case "업무종료":
                List<String> workList = Arrays.stream(event.getOption("work_list").getAsString().split(", ")).toList();
                workList.forEach(work -> log.info("work = {}", work));
                // TODO work 목록에 대한 저장 로직 필요
                LocalDateTime endTime = LocalDateTime.now();
                event.reply(
                        LocalDate.now().toString() + "의 업무 종료 시간은\n" + endTime.getHour() +"시 " + endTime.getMinute() + "분 입니다!"
                ).setEphemeral(true).queue();
                break;
            case "질문하기":
                // TODO 질문에 대한 저장 로직 필요
                // TODO word에 대한 중복 여부 파악
                User receiver = event.getOption("to").getAsMember().getUser();
                String word = event.getOption("question_word").getAsString();
                log.info("receiver: {}", receiver.toString());
                log.info("word: {}", word);
                log.info("content: {}", event.getOption("content").getAsString());
                event.reply("성공").setEphemeral(true).queue();
                break;
            default:
                System.out.printf("알 수 없는 커멘드입니다! %s used by %#s%n", event.getName(), event.getUser());
        }
    }
}
