package lets.practice.jda;

import lets.practice.jda.dto.DiscordBotToken;
import lets.practice.jda.listener.DiscordListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageActivity;
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
                Commands.slash("업무시작", "오늘 업무를 시작해요!")
        ).queue();

    }

}
