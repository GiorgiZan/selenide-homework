import com.codeborne.selenide.*;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;


import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTests {

    public SelenideTests() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        browserCapabilities = options;
        browserSize = null;
        timeout=20000;
        holdBrowserOpen=false;
        screenshots=false;
        reopenBrowserOnFail = true;
        fastSetValue=true;
    }


    @Test
    public void testCheckboxes() {
        open("http://the-internet.herokuapp.com/checkboxes");


        ElementsCollection checkboxCollection = $("#checkboxes").$$(by("type", "checkbox"));

        // Set first checkbox selected
        checkboxCollection.first().click();

        // Validate that both checkboxes has type 'checkbox'
        checkboxCollection.forEach(checkbox -> checkbox.shouldHave(attribute("type", "checkbox")));

    }

    @Test
    public void testDropdown() {
        // Open the Chrome browser
        Selenide.open("http://the-internet.herokuapp.com/dropdown");

        SelenideElement dropdown = $("#dropdown");

        // Validate that 'Please select an option' is selected
        dropdown.getSelectedOption().shouldHave(text("Please select an option"));

        // Select 'Option 2'
        dropdown.selectOptionContainingText("Option 2");

        // Validate that 'Option 2' was selected
        dropdown.getSelectedOption().shouldHave(text("Option 2"));
    }


    @Test
    public void testTextBox() {
        open("https://demoqa.com/text-box");

        // Name
        $(by("placeholder", "Full Name")).setValue("Giorgi zandarashvili");

        // Email
        $(byXpath("//input[@type='email']")).setValue("giorgizandarashvili7@gmail.com");

        // Current Address
        $("#currentAddress").setValue("საქართველოს შეერთებული შტატები");


        // Permanent Address
        $$(byClassName("form-control")).last().setValue("საქართველოს ფედერაცია");
        //ალტერნატივა
//        $(byId("permanentAddress")).setValue("საქართველოს ფედერაცია");


        // Click the "Submit" button
        $(("#submit")).click();

        $$(".mb-1").shouldHave(exactTexts(
                "Name:Giorgi zandarashvili",
                "Email:giorgizandarashvili7@gmail.com",
                "Current Address :საქართველოს შეერთებული შტატები",
                "Permananet Address :საქართველოს ფედერაცია"
        ));

    }
}
