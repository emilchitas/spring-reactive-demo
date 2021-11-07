package at.chitas.springreactivedemo;

import at.chitas.springreactivedemo.domain.Person;
import at.chitas.springreactivedemo.domain.PersonCommand;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by: emil
 * Date: 11/6/21
 */
class ReactiveDemoTest {
    Logger logger = LoggerFactory.getLogger(ReactiveDemoTest.class);
    Person darth = new Person("Darth", "Vader");
    Person anakin = new Person("Anakin", "Skywalker");
    Person leia = new Person("Leia", "Organa");
    Person luke = new Person("Luke", "Skywalker");
    Person r2d2 = new Person("R2-D2", "Not really a person");

    @Test
    void monoJustTest() {
        Mono<Person> personMono = Mono.just(r2d2);
        Person person = personMono.block();
        assertEquals("R2-D2", person.getFirstName());
        logger.info("We got {}", person.sayName());
    }

    @Test
    void monoTransformTest() {
        Mono<Person> personMono = Mono.just(leia);
        PersonCommand personCommand = personMono.map(person -> new PersonCommand(person)).block();
        assertEquals("Leia", personCommand.getFirstName());
        logger.info("We got {}", personCommand.sayName());

    }

    @Test
    void monoFilterTest() {
        Mono<Person> personMono = Mono.just(luke);
        Person nobody = personMono
                .filter(person -> person.getFirstName().equalsIgnoreCase("nobody"))
                .block();
        assertThrows(NullPointerException.class, () -> nobody.sayName());
    }

    @Test
    void fluxJustTest() {
        Flux<Person> personFlux = Flux.just(darth, anakin, leia, luke);
        personFlux.subscribe(person -> logger.info("All hand up for {}", person.sayName()));

    }

    @Test
    void fluxFilterTest() {
        Flux<Person> personFlux = Flux.just(darth, anakin, leia, luke);
        personFlux.filter(person -> person.getFirstName().contains("L"))
                .subscribe(person -> logger.info("We got {}", person.sayName()));

    }

    // Test goes through without waiting when using reactive
    @Test
    void fluxDelayNoOutputTest() {
        Flux<Person> personFlux = Flux.just(darth, anakin, leia, luke);
        personFlux.delayElements(Duration.ofSeconds(10))
                .subscribe(person -> logger.info(person.sayName()));
    }


    //Adding a countdownloatch waits for the execution
    @Test
    void fluxDelayTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<Person> personFlux = Flux.just(darth, anakin, leia, luke);
        personFlux.delayElements(Duration.ofSeconds(1))
                .doOnComplete(countDownLatch::countDown)
                .subscribe(person -> logger.info(person.sayName()));
        countDownLatch.await();
    }

    @Test
    void fluxFilterDelayTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<Person> personFlux = Flux.just(darth, anakin, leia, luke);
        personFlux.delayElements(Duration.ofSeconds(1))
                .filter(person -> person.getFirstName().contains("L"))
                .doOnComplete(countDownLatch::countDown)
                .subscribe(person -> logger.info(person.sayName()));
        countDownLatch.await();

    }

}
