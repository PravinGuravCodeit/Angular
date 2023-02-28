package Pages;

import com.utils.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Page2 extends SeleniumHelper {

    WebDriver driver;
    WebDriverWait wait;

    public List<String> lstPageImages = new ArrayList<>();

    public List<String> lstImageOpened = new ArrayList<>();

    By btnBack= By.xpath("//div[@class='display-block back']/a");

    //Image Name

    By imageName = By.xpath("//div[@class='info-container']/span[1]");

    //Download Button

    By btnDownload = By.xpath("//div[@class='info-container']/span[2]");
    public Page2(WebDriver driver){

        this.driver= driver;
        wait =new WebDriverWait(driver, Duration.ofSeconds(5));
    }


    public String getDetailsofOpenedImage(){

        String strImageName="";

        int iwait =0;

        boolean isImageOpened =false;

        try {

            while (!isElementDisplayed(btnBack)){
                System.out.println("I am waiting");
                Thread.sleep(1000);

                iwait=iwait+50;

                if (iwait==500)
                    break;

            }

            if (isElementDisplayed(btnBack)){

                isImageOpened=true;
                lstImageOpened.add("Yes");
                wait.until(ExpectedConditions.presenceOfElementLocated(imageName));

                strImageName = driver.findElement(imageName).getText().split("\\)")[1].trim();

                lstPageImages.add(strImageName);

                //Click on back button
                driver.findElement(btnBack).click();
            }else {

                lstImageOpened.add("Error");
                lstPageImages.add("BAD IMAGE");
            }




        }catch (Exception e){

            e.printStackTrace();
        }

        return strImageName;

    }

    public boolean isElementDisplayed(By ele) {
        try{
            driver.findElement(ele).isDisplayed();
            return true;
        }catch(NoSuchElementException ne){
        }
        return false;
    }

}
