package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.aeonbits.owner.ConfigFactory;
import config.ProjectConfig;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


@Tag("properties_task")
public class PracticeFormRemoteTests {

    @BeforeAll
    static void beforeAll() {
        System.setProperty("environment", System.getProperty("environment", "prod"));

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "normal";
        Configuration.timeout = 5000;
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "100.0");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.remote = System.getProperty("browserRemoteUrl");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @Test
    void fillRegistrationForm() {
        ProjectConfig projConfig = ConfigFactory.create(ProjectConfig.class);

        step("Открываем раздел automation-practice-form", ()-> {
            open("/automation-practice-form");

            SelenideElement bannerRoot = $(".fc-consent-root");
            if (bannerRoot.isDisplayed()) {
                bannerRoot.$(byText("Consent")).click();
            }

            executeJavaScript("$('#fixedban').remove()");
            executeJavaScript("$('footer').remove()");
        });

        step("Заполняем форму", ()-> {
            $("#firstName").setValue(projConfig.firstName());
            $("#lastName").setValue(projConfig.lastName());
                $("#userEmail").setValue("AleksandrExile@gmail.com");
                $("#genterWrapper").$(byText("Male")).click();
                $("#userNumber").setValue("9001122999");
                $("#dateOfBirthInput").click();
                $(".react-datepicker__month-select").selectOptionByValue("6");
                $(".react-datepicker__year-select").selectOption("1988");
                $(".react-datepicker__day--009:not(.react-datepicker__day--outside-month)").click();
                $("#subjectsInput").setValue("Arts").pressEnter();
                $("#hobbiesWrapper").$(byText("Sports")).click();
                $("#uploadPicture").uploadFromClasspath("9.png");
                $("#currentAddress").setValue("www.Leningrad.spb.ru");
                $("#state").click();
                $("#stateCity-wrapper").$(byText("Rajasthan")).click();
                $("#city").click();
                $("#stateCity-wrapper").$(byText("Jaipur")).click();
                $("#submit").click();
            });

            step("Verify results", () -> {
                $(".table-responsive").shouldHave(text(projConfig.firstName()));
                $(".table-responsive").shouldHave(text(projConfig.lastName()));
                $(".table-responsive").shouldHave(text("9001122999"));
                $(".table-responsive").shouldHave(text("9 July,1988"));
                $(".table-responsive").shouldHave(text("Arts"));
                $(".table-responsive").shouldHave(text("Sports"));
                $(".table-responsive").shouldHave(text("9.png"));
                $(".table-responsive").shouldHave(text("www.Leningrad.spb.ru"));
                $(".table-responsive").shouldHave(text("Rajasthan Jaipur"));
            });

        }
    }


