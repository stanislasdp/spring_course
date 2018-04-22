package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
public class CounterAspect {

  private Map<String, Integer> countByEventName = new ConcurrentHashMap<>();

    @Before("execution(* *.EventSer1viceImpl.getByName(*))")
    public void counterEventByNameAdvice(JoinPoint joinPoint) {

        Object argument = joinPoint.getArgs()[0];
        if (argument instanceof String) {
            countByEventName.merge((String) argument, 1, (oldValue, one) ->  oldValue + one);
        }
    }


    public int getCounter(String eventName) {
        return countByEventName.get(eventName);
    }
}
