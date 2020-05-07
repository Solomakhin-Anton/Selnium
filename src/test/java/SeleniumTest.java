import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }
    
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @org.junit.jupiter.api.Test
    void shouldSubmitRequest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Имя Фамилия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input ")).sendKeys("+79110001212");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @org.junit.jupiter.api.Test
    void shouldSubmitRequestWithoutID() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Имя Фамилия");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79110001212");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @org.junit.jupiter.api.Test
    void shouldNotSubmitRequest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Name Surname");
        driver.findElement(By.cssSelector("input[type='tel']")).sendKeys("+79110001212");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button[type='button']")).click();
        String text = driver.findElement(By.cssSelector("span[data-test-id = \"name\"] span + span +span")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }
}