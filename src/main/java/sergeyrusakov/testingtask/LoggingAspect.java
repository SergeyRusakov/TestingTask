package sergeyrusakov.testingtask;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger("LOGGER");

    @Pointcut("execution(* sergeyrusakov.testingtask.AccountProcessor.processAccount(..)) && args(account,..))")
    public void pointcutOnProcessAccount(BankAccount account){

    }

    @Before(value = "pointcutOnProcessAccount(account)", argNames = "account")
    public void beforeProcessAccount(BankAccount account){
        logger.info("Account id: "+account.getId()+". Balance: "+account.getBalance()+" is processing");
    }

    @Pointcut("execution(* sergeyrusakov.testingtask.MainService.processAccounts(..)) && args(collection,..))")
    public void pointcutOnProcessAccounts(Collection<BankAccount> collection){
    }

    @Before(value = "pointcutOnProcessAccounts(collection)", argNames = "collection")
    public void beforeProcessAccounts(Collection<BankAccount> collection){
        logger.info("Account list sent to processing. Size: "+collection.size());
    }

    @AfterReturning(value = "pointcutOnProcessAccounts(collection)", argNames = "collection")
    public void afterProcessingAndSaving(Collection<BankAccount> collection){
        logger.info("Accounts processed and saved to database");
    }

    @AfterThrowing(pointcut = "execution(* sergeyrusakov.testingtask.AccountProcessor.getAccount(..))", throwing = "exp")
    public void afterThrowingAtAccountGet(Exception exp){
        logger.error(exp.getMessage());
    }

    @Pointcut("execution(* sergeyrusakov.testingtask.AccountProcessor.saveAccount(..)) && args(account,..)")
    public void savingAccountPointcut(BankAccount account){

    }

    @Before(value = "savingAccountPointcut(account)",argNames = "account")
    public void beforeSaving(BankAccount account){
        logger.info("Saving state of account id:"+account.getId());
    }

    @After(value = "savingAccountPointcut(account)",argNames = "account")
    public void afterSaving(BankAccount account){
        logger.info("Account id:"+account.getId()+ " updated and saved to database."+" Balance: "+account.getBalance());
    }

    @AfterThrowing(pointcut = "execution(* sergeyrusakov.testingtask.AccountProcessor.saveAccount(..))",throwing = "exp")
    public void afterThrowingOnAccounts(Exception exp){
        logger.error(exp.getMessage()+" saving account failed.");
    }

}
