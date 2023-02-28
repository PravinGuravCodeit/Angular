package Pages;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.ByAngularModel;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.utils.SeleniumHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.Log;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Page1 extends SeleniumHelper {

    Logger log = Logger.getLogger("Page1");
    WebDriverWait wait;

    public  List<String> lstPageImages= new ArrayList<>();
    public  List<String> lstIfBackButtonWorks= new ArrayList<>();

    public  List<String> lstDownloadOption= new ArrayList<>();

    public  List<String> lstNumberOnImage= new ArrayList<>();


    WebDriver driver;

    //Xpath For all Images
    By imgsAll= By.xpath("//div[@class='image-container']/img");
    By header= By.xpath("//app-pictures-list-view/h1");

    By btnBack= By.xpath("//div[@class='display-block back']/a");


     public Page1(WebDriver driver){

         this.driver= driver;
         wait =new WebDriverWait(driver, Duration.ofSeconds(5));
     }
    public String getNumberOfImagesOnLanding() throws InterruptedException {
        log.info("Getting The Number of Tiles/Image present on Landing");


        int iNo= driver.findElements(imgsAll).size();

        log.info("The Number of Tiles/Image present on Landing are: "+iNo);

        return String.valueOf(iNo);


    }

    public List<String> getAllImgHeight(){

         List<String> lstImgHeights= new ArrayList<String>() {};

         try {

             for (WebElement e:
                     driver.findElements(imgsAll)) {

                 lstImgHeights.add(getHeightOfImage(e));

             }

          log.info("All heights are: "+lstImgHeights);

         }catch (Exception e){
             e.printStackTrace();
         }

         return lstImgHeights;


    }

    public String getHeightOfImage(WebElement eleImg){
         log.info("Getting the Height of the Image: ");
        String strHeight="";
         try {

             strHeight= eleImg.getAttribute("style").split(";")[1].split(":")[1].trim();

             log.info("The Height of Image "+eleImg.findElement(By.xpath("../../div[2]/div/span")).getText()+" is: "+strHeight);



         }catch (Exception e){

             e.printStackTrace();
         }

        return strHeight;

    }

    public String getTheHeader(){
         log.info("Getting the info from header");

         String strHeader=driver.findElement(header).getText();

         log.info("The header on the Landing is: "+strHeader);

         return strHeader;


    }

    public void getDetailViewsforAllImages(Page2 page2){


        try {

            String strHeader =getTheHeader();
            By headerCorrect = By.xpath("//app-pictures-list-view/h1[text()='"+strHeader+"']");
            int icount=0;
            for (WebElement e:
                    driver.findElements(imgsAll)) {
                WebElement ele = driver.findElements(imgsAll).get(icount);
                lstPageImages.add(ele.findElement(By.xpath("../../div[2]/div/span")).getText().split("\\)")[1].trim());
                lstNumberOnImage.add(ele.findElement(By.xpath("../../div[2]/div/span")).getText().split("\\)")[0].trim());

                if (ele.findElement(By.xpath("../../div[2]/div/span[2]")).isDisplayed()){

                    lstDownloadOption.add(ele.findElement(By.xpath("../../div[2]/div/span[2]")).getText());

                }else {
                    lstDownloadOption.add("Error");

                }

                clickOnImage(ele);

                page2.getDetailsofOpenedImage();

                wait.until(ExpectedConditions.presenceOfElementLocated(headerCorrect));

                if (page2.isElementDisplayed(headerCorrect)){

                    lstIfBackButtonWorks.add("Yes");
                }else {

                    lstIfBackButtonWorks.add("Error");
                }

                icount++;

            }



    }catch (Exception e){

            e.printStackTrace();
        }}



    //This method is used to click on the Image

    public void clickOnImage(WebElement eleImg){
         log.info("Clicking on Image "+eleImg.findElement(By.xpath("../../div[2]/div/span")).getText());
        wait.until(ExpectedConditions.elementToBeClickable(eleImg));
        eleImg.click();
        //wait.until(ExpectedConditions.presenceOfElementLocated(btnBack));


    }

}
