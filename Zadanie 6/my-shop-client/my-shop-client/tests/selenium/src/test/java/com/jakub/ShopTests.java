
package com.example.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShopTests {

    private ChromeDriverService chromeDriverService;
    private WebDriver driver;
    private final static String WEB_DRIVER_PATH = "/usr/bin/chromedriver";
    private final static String APP_URL = "http://localhost:5173/";

    @BeforeEach
    public void setUp() {
        try {
            chromeDriverService = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(WEB_DRIVER_PATH))
                    .usingAnyFreePort()
                    .build();
            chromeDriverService.start();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Nie udało się uruchomić ChromeDriverService");
        }
        driver = new ChromeDriver(chromeDriverService);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get(APP_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterAll
    public void stopService() {
        if (chromeDriverService != null && chromeDriverService.isRunning()) {
            chromeDriverService.stop();
        }
    }

    @Test
    public void test01_navigationLinksAreVisible() {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        assertTrue(links.stream().anyMatch(e -> e.getText().equals("Produkty")), "Link 'Produkty' powinien istnieć");
        assertTrue(links.stream().anyMatch(e -> e.getText().equals("Koszyk")), "Link 'Koszyk' powinien istnieć");
        assertTrue(links.stream().anyMatch(e -> e.getText().equals("Płatności")), "Link 'Płatności' powinien istnieć");
        assertEquals(3, links.size(), "Powinny być 3 główne linki nawigacyjne");

    }
    @Test
    public void test02_navigateToProduktyPageHeader() {
        driver.findElement(By.linkText("Produkty")).click();
        WebElement header = driver.findElement(By.xpath("//h2"));
        assertTrue(header.isDisplayed());
        assertEquals("Produkty", header.getText());
    }

    @Test
    public void test03_productsListIsNotEmpty() {
        driver.findElement(By.linkText("Produkty")).click();
        List<WebElement> listItems = driver.findElements(By.cssSelector("li"));

        assertFalse(listItems.isEmpty(), "Lista produktów nie powinna być pusta");
        assertTrue(listItems.stream().anyMatch(el -> el.getText().contains("PLN")), "Produkty powinny zawierać ceny");
        assertTrue(listItems.stream().anyMatch(el -> el.getText().contains("Dodaj")), "Co najmniej jeden produkt powinien mieć przycisk 'Dodaj'");
    }


    @Test
    public void test04_addProductToCart() {
        driver.findElement(By.linkText("Produkty")).click();
        List<WebElement> addButtons = driver.findElements(By.xpath("//button[contains(text(),'Dodaj')]"));
        assertFalse(addButtons.isEmpty(), "Powinien być przynajmniej jeden przycisk 'Dodaj'");

        addButtons.get(0).click();

        driver.findElement(By.linkText("Koszyk")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement removeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(),'Usuń')]")
        ));

        assertNotNull(removeButton, "Produkt powinien mieć opcję 'Usuń'");
        assertTrue(removeButton.isDisplayed(), "Przycisk 'Usuń' powinien być widoczny");
    }


    @Test
    public void test05_removeProductFromCart() {
        driver.findElement(By.linkText("Produkty")).click();
        driver.findElements(By.tagName("button")).get(0).click();
        driver.findElement(By.linkText("Koszyk")).click();
        WebElement removeBtn = driver.findElement(By.xpath("//button[contains(text(), 'Usuń')]"));
        assertNotNull(removeBtn);
        removeBtn.click();
        WebElement emptyMsg = driver.findElement(By.tagName("p"));
        assertEquals("Koszyk jest pusty.", emptyMsg.getText());
        assertTrue(emptyMsg.isDisplayed());

    }

    @Test
    public void test06_totalAmountCorrectInCart() {
        driver.findElement(By.linkText("Produkty")).click();
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        buttons.get(0).click();
        buttons.get(1).click();
        driver.findElement(By.linkText("Koszyk")).click();
        WebElement total = driver.findElement(By.tagName("h3"));
        assertTrue(total.isDisplayed(), "Łączna kwota powinna być widoczna");
        assertTrue(total.getText().contains("Łączna kwota:"));
        assertTrue(total.getText().contains("PLN"));


        String kwota = total.getText().replaceAll("[^0-9]", "");
        assertTrue(Integer.parseInt(kwota) > 0, "Kwota powinna być większa niż 0");
    }


    @Test
    public void test07_paymentButtonVisibleWithItems() {
        driver.findElement(By.linkText("Produkty")).click();
        driver.findElements(By.tagName("button")).get(0).click();
        driver.findElement(By.linkText("Płatności")).click();

        WebElement payBtn = driver.findElement(By.tagName("button"));
        assertEquals("Zapłać", payBtn.getText());
        assertTrue(payBtn.isDisplayed());
        assertTrue(payBtn.getAttribute("outerHTML").contains("button"), "Element powinien być przyciskiem");
    }


    @Test
    public void test08_emptyCartMessageInPayment() {
        driver.findElement(By.linkText("Płatności")).click();
        WebElement msg = driver.findElement(By.tagName("p"));
        assertEquals("Koszyk jest pusty.", msg.getText());
        assertTrue(msg.isDisplayed());
    }

    @Test
    public void test09_paymentSuccessClearsCart() {
        driver.findElement(By.linkText("Produkty")).click();
        driver.findElements(By.tagName("button")).get(0).click();
        driver.findElement(By.linkText("Płatności")).click();
        driver.findElement(By.tagName("button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Płatność zakończona sukcesem!')]")));

        assertEquals("Płatność zakończona sukcesem!", message.getText());
        assertTrue(message.isDisplayed());

        driver.findElement(By.linkText("Koszyk")).click();
        WebElement emptyMsg = driver.findElement(By.tagName("p"));
        assertEquals("Koszyk jest pusty.", emptyMsg.getText());
        assertTrue(driver.findElement(By.tagName("p")).isDisplayed(), "Powinna być wyświetlona informacja po płatności");

    }

    @Test
    public void test10_cartShowsCorrectProductNames() {
        driver.findElement(By.linkText("Produkty")).click();
        List<WebElement> products = driver.findElements(By.cssSelector("li"));
        String productName = products.get(0).getText().split(" - ")[0];
        driver.findElements(By.xpath("//button[contains(text(),'Dodaj')]")).get(0).click();
        driver.findElement(By.linkText("Koszyk")).click();
        WebElement cartItem = driver.findElement(By.tagName("li"));
        assertTrue(cartItem.getText().contains(productName), "Koszyk powinien zawierać nazwę dodanego produktu");
        assertTrue(driver.findElement(By.tagName("h2")).getText().contains("Koszyk"), "Nagłówek powinien wskazywać, że jesteś na stronie koszyka");
    }

    @Test
    public void test11_emptyCartMessageVisible() {
        driver.findElement(By.linkText("Koszyk")).click();
        WebElement message = driver.findElement(By.tagName("p"));
        assertEquals("Koszyk jest pusty.", message.getText());
        assertTrue(message.isDisplayed());
    }

    @Test
    public void test12_emptyPaymentMessageVisible() {
        driver.findElement(By.linkText("Płatności")).click();
        WebElement message = driver.findElement(By.tagName("p"));
        assertEquals("Koszyk jest pusty.", message.getText());
        assertTrue(message.isDisplayed());
    }

    @Test
    public void test13_cartTotalAmount() {
        driver.findElement(By.linkText("Produkty")).click();
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        buttons.get(0).click();
        driver.findElement(By.linkText("Koszyk")).click();
        WebElement total = driver.findElement(By.tagName("h3"));
        assertTrue(total.getText().contains("Łączna kwota:"));
        assertTrue(total.getText().contains("PLN"));
    }

    @Test
    public void test14_productDetailsInCart() {
        driver.findElement(By.linkText("Produkty")).click();
        String productName = driver.findElements(By.cssSelector("li")).get(0).getText().split(" - ")[0];
        driver.findElements(By.xpath("//button[contains(text(),'Dodaj')]")).get(0).click();
        driver.findElement(By.linkText("Koszyk")).click();
        WebElement cartItem = driver.findElement(By.tagName("li"));
        assertTrue(cartItem.isDisplayed(), "Element z produktem w koszyku powinien być widoczny");
        assertTrue(cartItem.getText().contains(productName), "Koszyk powinien zawierać szczegóły produktu");
    }

    @Test
    public void test15_addMultipleProducts() {
        driver.findElement(By.linkText("Produkty")).click();
        List<WebElement> buttons = driver.findElements(By.xpath("//button[contains(text(),'Dodaj')]"));
        int count = Math.min(buttons.size(), 3);
        for (int i = 0; i < count; i++) buttons.get(i).click();
        driver.findElement(By.linkText("Koszyk")).click();
        List<WebElement> items = driver.findElements(By.tagName("li"));
        assertTrue(items.size() >= count, "Koszyk powinien zawierać " + count + " produkty");
        assertTrue(items.stream().anyMatch(e -> e.getText().contains("Usuń")), "Każdy produkt w koszyku powinien mieć przycisk 'Usuń'");

    }

    @Test
    public void test16_removeProductFromCart() {
        driver.findElement(By.linkText("Produkty")).click();
        driver.findElements(By.tagName("button")).get(0).click();
        driver.findElement(By.linkText("Koszyk")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Usuń')]")).click();
        WebElement info = driver.findElement(By.tagName("p"));
        assertEquals("Koszyk jest pusty.", info.getText());
        assertTrue(info.isDisplayed());
    }

    @Test
    public void test17_paymentButtonNotVisibleWhenEmpty() {
        driver.findElement(By.linkText("Płatności")).click();
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        boolean paymentVisible = buttons.stream().anyMatch(b -> b.getText().equals("Zapłać") && b.isDisplayed());
        assertFalse(paymentVisible);
        assertTrue(driver.findElement(By.tagName("p")).getText().contains("Koszyk"));
    }

    @Test
    public void test18_paymentAmountText() {
        driver.findElement(By.linkText("Produkty")).click();
        driver.findElements(By.tagName("button")).get(0).click();
        driver.findElement(By.linkText("Płatności")).click();
        WebElement total = driver.findElement(By.tagName("h3"));
        assertTrue(total.isDisplayed(), "Suma do zapłaty powinna być widoczna");
        assertTrue(total.getText().contains("Do zapłaty:"));
        assertTrue(total.getText().contains("PLN"));
    }

    @Test
    public void test19_navigationFlow() {
        driver.findElement(By.linkText("Koszyk")).click();
        driver.findElement(By.linkText("Płatności")).click();
        driver.findElement(By.linkText("Produkty")).click();
        WebElement header = driver.findElement(By.xpath("//h2"));
        assertEquals("Produkty", header.getText());
        assertTrue(header.isDisplayed());
    }

    @Test
    public void test20_cartStateAfterRefresh() {
        driver.findElement(By.linkText("Produkty")).click();
        driver.findElements(By.tagName("button")).get(0).click();
        driver.navigate().refresh();
        driver.findElement(By.linkText("Koszyk")).click();
        WebElement info = driver.findElement(By.tagName("p"));
        assertEquals("Koszyk jest pusty.", info.getText());
        assertTrue(info.isDisplayed());
    }
}
