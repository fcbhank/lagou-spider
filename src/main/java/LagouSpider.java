import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Create by root on 2019/5/8
 */
public class LagouSpider {
    public static void main(String[] args) {
        // 设置webdriver驱动文件位置
        System.setProperty("webdriver.chrome.driver",
                LagouSpider.class.getClassLoader().getResource("chromedriver.exe").getPath());

        // 创建webdriver
        WebDriver webDriver = new ChromeDriver();

        // 设置爬取的页面
        webDriver.get("https://www.lagou.com/zhaopin/Java/?labelWords=label");

        clickOption(webDriver, "工作经验", "应届毕业生");
        clickOption(webDriver, "学历要求", "本科");
        clickOption(webDriver, "融资阶段", "不限");
        clickOption(webDriver, "公司规模", "150-500人");
        clickOption(webDriver, "行业领域", "不限");
    }

    private static void clickOption(WebDriver webDriver, String chosenTitle, String optionTitle) {
        // 通过xpath方式，先获取左侧筛选条件元素
        WebElement chosenElement = webDriver.findElement(By.xpath("//li[@class='multi-chosen']//span[contains(text(), '" + chosenTitle + "')]"));
        // 再获取筛选项元素
        WebElement optionElement = chosenElement.findElement(By.xpath("../a[contains(text(), '" + optionTitle + "')]"));
        // 点击选中元素
        optionElement.click();
    }
}
