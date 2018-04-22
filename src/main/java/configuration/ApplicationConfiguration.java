package configuration;

import aspects.CounterAspect;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import ua.epam.spring.hometask.dao.EventRepository;
import ua.epam.spring.hometask.dao.UserRepository;
import ua.epam.spring.hometask.discount.BirthDayDiscountStrategy;
import ua.epam.spring.hometask.discount.DiscountStrategy;
import ua.epam.spring.hometask.discount.EveryTenTicketDiscountStrategy;
import ua.epam.spring.hometask.service.*;
import ua.epam.spring.hometask.service.implementation.*;

@Configuration
@ImportResource("application-context.xml")
@EnableAspectJAutoProxy
public class ApplicationConfiguration {


    @Bean
    public EventRepository eventRepository() {
        return new EventRepository();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public DiscountStrategy birthDayDiscountStrategy(){
        return new BirthDayDiscountStrategy();
    }

    @Bean
    public DiscountStrategy everyTenDiscountStrategy() {
        return new  EveryTenTicketDiscountStrategy(userRepository());
    }


    @Bean
    AuditoriumService auditoriumService() {
        return new AuditoriumServiceImpl();
    }

    @Bean
    public EventService eventService() {
        return new EventServiceImpl(eventRepository());
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }

    @Bean
    public DiscountService discountService() {
        DiscountServiceImpl discountService = new DiscountServiceImpl();
        discountService.setDiscountStrategies(ImmutableList.of(everyTenDiscountStrategy(), birthDayDiscountStrategy()));
        return discountService;
    }

    @Bean
    public BookingService bookingService() {
        BookingServiceImpl bookingService = new BookingServiceImpl();
        bookingService.setEventRepository(eventRepository());
        bookingService.setEventService(eventService());
        bookingService.setUserRepository(userRepository());
        bookingService.setUserService(userService());
        bookingService.setDiscountService(discountService());
        return bookingService;
    }

    @Bean
    public CounterAspect counterAspect() {
        return new CounterAspect();
    }




}
