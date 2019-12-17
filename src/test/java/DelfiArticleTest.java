import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DelfiArticleTest {
    private final By HOME_PAGE_TITLE = By.xpath(".//h1[contains(@class, 'text-size-22')]");
    private final By HOME_PAGE_COMMENTS = By.xpath(".//a[@class = 'comment-count text-red-ribbon']");
    private final By ARTICLE_PAGE_TITLE = By.xpath(".//h1[contains(@class, 'text-size-md-30')]");
    private final By ARTICLE_PAGE_COMMENTS = By.xpath(".//a[contains(@class, 'text-size-md-28')]");
    private final By COMMENT_PAGE_TITLE = By.xpath(".//h1 [@class = 'article-title']/a");
    private final By ARTICLE = By.xpath(".//span [@class = 'text-size-22 d-block']");
    private final By COMMENTS_COUNT_LIST = By.xpath(".//span [@class = 'type-cnt']");


    @Test
    public void titleAndCommentsTest() {

        //Open browser
        System.setProperty("webdriver.chrome.driver", "c:/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);


        //Open Delfi home page
        driver.get("http://rus.delfi.lv");

        //Find first article
        WebElement article = driver.findElements(ARTICLE).get(0);

        //Find first article title
        WebElement homePageTitle = article.findElement(HOME_PAGE_TITLE);

        //Save to String
        String titleToCompare = homePageTitle.getText(); //sohranili kak tekst

        //Find comments count
        Integer commentsToCompare = 0;

        if (!article.findElements(HOME_PAGE_COMMENTS).isEmpty()) {
            WebElement homePageComments = article.findElement(HOME_PAGE_COMMENTS);

            //Save to integer
            String comentsToParse = homePageComments.getText(); // получим (1)
            comentsToParse = comentsToParse.substring(1, comentsToParse.length() - 1);
            commentsToCompare = Integer.valueOf(comentsToParse); //peredelali rezuljta v cifru i zapisali

        }

        //Open article page
        homePageTitle.click();

        //Find Title
        String articalPageTitle = driver.findElement(ARTICLE_PAGE_TITLE).getText();

        //Check title
        /////Assertions.assertEquals(titleToCompare, articalPageTitle, "Wrong title on Article Page!");

        //Find comments count
        Integer articalPageComments = Integer.valueOf(driver.findElement(ARTICLE_PAGE_COMMENTS).getText().substring(1, driver.findElement(ARTICLE_PAGE_COMMENTS).getText().length() - 1));

        //Check comments count
        Assertions.assertEquals(commentsToCompare, articalPageComments, "Comments count is not same as on Home Page");

        //Open comments page
        driver.findElement(ARTICLE_PAGE_COMMENTS).click();

        //Find title
        String comentPageTitle = driver.findElement(COMMENT_PAGE_TITLE).getText();

        //Check title
        Assertions.assertEquals(titleToCompare, comentPageTitle, "Titles are not equal");
        //Assertions.assertTrue(titleToCompare.startsWith(comentPageTitle), "Titles are not equal");

        //Get comment count
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(COMMENTS_COUNT_LIST));

        Integer anonimusCommentsToSum = 0;

        if (!article.findElements(COMMENTS_COUNT_LIST).isEmpty()) {
            List<WebElement> anonimCommentsPageCount = article.findElements(COMMENTS_COUNT_LIST);

            //Save to integer
            String anonimComentsToParse = anonimCommentsPageCount.get(0).getText(); // получим (1)
            anonimComentsToParse = anonimComentsToParse.substring(1, anonimComentsToParse.length() - 1);
            anonimusCommentsToSum = Integer.valueOf(anonimComentsToParse); //peredelali rezuljta v cifru i zapisali
        }

        Integer registeredCommentsToSum = 0;

        if (!article.findElements(COMMENTS_COUNT_LIST).isEmpty()) {
            List<WebElement> registerCommentsPageCount = article.findElements(COMMENTS_COUNT_LIST);

            //Save to integer
            String registerComentsToParse = registerCommentsPageCount.get(1).getText(); // получим (1)
            registerComentsToParse = registerComentsToParse.substring(1, registerComentsToParse.length() - 1);
            registeredCommentsToSum = Integer.valueOf(registerComentsToParse); //peredelali rezuljta v cifru i zapisali

        }
        Integer sumOfComments = anonimusCommentsToSum + registeredCommentsToSum;

        //Check comment count
        Assertions.assertEquals(commentsToCompare, sumOfComments, "Comment count is not equal");

        //Close browser
        driver.close();
    }
}