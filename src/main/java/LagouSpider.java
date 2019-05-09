import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

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

        // 设置筛选条件
        clickOption(webDriver, "工作经验", "应届毕业生");
        clickOption(webDriver, "学历要求", "本科");
        clickOption(webDriver, "融资阶段", "不限");
        clickOption(webDriver, "公司规模", "不限");
        clickOption(webDriver, "行业领域", "移动互联网");

        // 解析页面元素
        analysisByPage(webDriver);

    }

    private static void analysisByPage(WebDriver webDriver) {
        List<WebElement> jobElements = webDriver.findElements(By.className("con_list_item "));
        for (WebElement jobElement : jobElements) {
            String company = jobElement.findElement(By.className("company")).findElement(By.className("company_name")).
                    findElement(By.xpath("a")).getText();
            String money = jobElement.findElement(By.className("position")).findElement(By.className("money")).getText();
            System.out.println(company + " : " + money);
        }

        // 获取页面顶部下一页按钮
        WebElement nextPageBtn = webDriver.findElement(By.className("pager_next "));
        // 不是最后一页
        if (!nextPageBtn.getAttribute("class").contains("pager_next_disabled")) {
            nextPageBtn.click();
            System.out.println("\n----解析下一页----");
            // 等待网页更新加载出来，否则找不到对应的element
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            analysisByPage(webDriver);
        }
    }

    private static void clickOption(WebDriver webDriver, String chosenTitle, String optionTitle) {
        // 通过xpath方式，先获取左侧筛选条件元素
        WebElement chosenElement = webDriver.findElement(By.xpath("//li[@class='multi-chosen']//span[contains(text(), '" + chosenTitle + "')]"));
        // 再获取筛选项元素
        WebElement optionElement = chosenElement.findElement(By.xpath("../a[contains(text(), '" + optionTitle + "')]"));
        // 点击选中元素
        optionElement.click();
        // 关闭driver
    }
}
