package lets.practice.jda;

import lets.practice.jda.dto.DiscordBotToken;
import lets.practice.jda.listener.DiscordListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageActivity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JdaApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JdaApplication.class, args);

        JDA jda = JDABuilder.createDefault(context.getBean(DiscordBotToken.class).getToken())
                .setActivity(Activity.playing("START UP VALLEY"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new DiscordListener())
                .build();

        jda.updateCommands().addCommands(
                Commands.slash("업무시작", "오늘 업무를 시작해요!"),
                Commands.slash("업무종료", "오늘 업무를 종료해요! 오늘의 업무 내역을 적어주세요!")
                        .addOption(OptionType.STRING, "work_list", "오늘의 업무 내역을 ', ' 을 통해 구분하여 줄글로 작성해주세요!", true),
                Commands.slash("질문하기", "업무 상황에 관련하여 질문해요")
                        .addOption(OptionType.STRING, "question_word", "질문의 내용을 포괄하는 단어를 입력해주세요!", true)
                        .addOption(OptionType.USER, "to", "질문을 받는 상대방을 지정해주세요", true)
                        .addOption(OptionType.STRING, "content", "질문의 자세한 내용을 알려주세요!", true)
        ).queue();

    }

}
