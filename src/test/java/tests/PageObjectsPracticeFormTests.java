package tests;

import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

public class PageObjectsPracticeFormTests extends TestBase {

    RegistrationPage registrationPage = new RegistrationPage();

    @Test
    void fullRegistrationTest() {
        registrationPage.openPage()
                .setFirstName("Aleksandr")
                .setLastName("Exile")
                .setEmail("AleksandrExile@gmail.com")
                .setGender("Male")
                .setUserNumber("9001122999")
                .setDateOfBirth("30", "June", "2008")
                .setSubjectsInput("Arts")
                .setHobbiesWrapperInput("Sports")
                .setUploadPictureInput("9.png")
                .setCurrentAddress("www.Leningrad.spb.ru")
                .setStateWrapper("Rajasthan")
                .setCityWrapper("Jaipur")
                .submit();

        registrationPage.checkResult("Student Name", "Aleksandr Exile")
                .checkResult("Student Email", "AleksandrExile@gmail.com")
                .checkResult("Gender", "Male")
                .checkResult("Mobile", "900112299")
                .checkResult("Date of Birth", "30 June,2008")
                .checkResult("Subjects", "Arts")
                .checkResult("Hobbies", "Sports")
                .checkResult("Picture", "9.png")
                .checkResult("Address", "www.Leningrad.spb.ru")
                .checkResult("State and City", "Rajasthan Jaipur");
    }


    @Test
    void minimalRegistrationTest() {
        registrationPage.openPage()
                .setFirstName("Aleksandr")
                .setLastName("Exile")
                .setGender("Male")
                .setUserNumber("9001122999")
                .submit();

        registrationPage.checkResult("Student Name", "Aleksandr Exile")
                .checkResult("Gender", "Male")
                .checkResult("Mobile", "9001122999");

    }

    @Test
    void negativeRegistrationTest() {
        registrationPage.openPage()
                .setFirstName("Aleksandr")
                .setGender("Male")
                .setUserNumber("9001122999")
                .submit();

        registrationPage.noResult();


    }
}


