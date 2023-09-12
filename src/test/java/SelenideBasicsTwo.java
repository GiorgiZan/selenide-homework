import com.codeborne.selenide.*;

import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.*;
public class SelenideBasicsTwo {
    public SelenideBasicsTwo() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        browserCapabilities = options;
        reportsFolder = "src/main/resources/Reports";
        browserSize = null;
        timeout=20000;
        holdBrowserOpen=false;
        screenshots=true;
        reopenBrowserOnFail = true;

        fastSetValue=true;
        savePageSource=false;
    }

    @Test
    public void searchBooks() {
        SoftAssert softAssert = new SoftAssert();


        open("https://demoqa.com/books");


        // Using find() and findAll() methods Find all books with publisher 'O'Reilly Media' containing title 'Javascript'
        ElementsCollection specifiedBooks = $(".rt-table").find(".rt-tbody").findAll(".rt-tr-group").filterBy(text("O'Reilly Media")).filterBy(text("Javascript"));

        // Check with Test NG soft assertions that size of returned list equals to 10(failed case)
        softAssert.assertEquals(specifiedBooks.size(), 10, "Size does not equal to 10.");


        // Check that first match row's title equals to 'Learning JavaScript Design Patterns'(success case)
        String firstBookTitle = specifiedBooks.first().$(".mr-2").getText();
        softAssert.assertEquals(firstBookTitle, "Learning JavaScript Design Patterns");


        //Using stream() click on all matching row's title
        specifiedBooks.asDynamicIterable().stream().forEach(book -> {
            book.$(".mr-2 a").scrollTo().click();
            SelenideElement newElement = $("#addNewRecordButton");
            newElement.scrollTo().click();
        });

        softAssert.assertAll();
    }

    @Test
    public void searchBookImages() {
        open("https://demoqa.com/books");

        // Using inner elements locators chain Find all books with publisher 'O'Reilly Media' containing title 'Javascript'
        ElementsCollection specifiedBooks = $(".rt-table").$(".rt-tbody").$$(".rt-tr-group").filterBy(text("O'Reilly Media")).filterBy(text("Javascript"));


        //Using inner elements locators chain Find that each books images are not empty(success case)
        specifiedBooks.forEach(book -> {
            // აქ იმიტომ მიწერია ასე რომ უნდა ქონდეს src ატრიბუტი და ასევე არ უნდა იყოს ცარიელი
            book.$(byClassName("rt-td")).$("img").shouldHave(attribute("src"));
            book.$(byClassName("rt-td")).$("img").shouldNotHave(attribute("src", ""));

        });
    }
}
