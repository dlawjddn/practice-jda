package lets.practice.jda.listener;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class DiscordListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();
        log.info("discord message: {}", message.getContentDisplay());
    }

    /**
     * Slash Command 이벤트 처리
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "업무시작":
                LocalDateTime now = LocalDateTime.now();
                // 출근시간을 저장하는 로직이 필요함
                event.reply(
                        LocalDate.now().toString() + "의 업무 시작 시간은\n" + now.getHour() +"시 " + now.getMinute() + "분 입니다!"
                        ).setEphemeral(true).queue();
                break;
            default:
                System.out.printf("알 수 없는 커멘드입니다! %s used by %#s%n", event.getName(), event.getUser());
        }
    }
}
