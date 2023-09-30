import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.DataGenerator;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.Keys.DELETE;


public class CardDeliveryTest {
    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysToAddForFirstMeeting = 3;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting, "dd.MM.yyyy");
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(DELETE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(visible);
        $("[data-test-id='date'] input").doubleClick().sendKeys(DELETE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"))
                .shouldBe(visible);
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(visible);

    }
}





