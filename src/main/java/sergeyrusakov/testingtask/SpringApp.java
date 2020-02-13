package sergeyrusakov.testingtask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableIntegration
@IntegrationComponentScan
@EnableAspectJAutoProxy

public class SpringApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class,args);
    }

    @Bean(name="inputChannel")
    public DirectChannel inputChannel(){
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow integrationFlows(){
        return IntegrationFlows.from("inputChannel")
                .handle("activator","startService")
                .get();
    }

    @Bean
    public MainServiceActivator activator(){
        return new MainServiceActivator(mainService());
    }

    @Bean
    public MainService mainService(){
        return new MainService();
    }

    @Bean
    public TaskManager taskManager(){
        TaskManager taskManager = new TaskManager();
        taskManager.setChannel(inputChannel());
        return taskManager;
    }

    @Bean
    public ExecutorService executorService(){
        return Executors.newCachedThreadPool();
    }

    @Bean
    public AccountProcessor accountProcessor(){
        return new AccountProcessor();
    }
}
