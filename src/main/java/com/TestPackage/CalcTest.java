package com.TestPackage;

import Pages.Page1;
import Pages.Page2;
import com.utils.SeleniumHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.w3c.dom.ls.LSException;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class CalcTest {

    Logger log = Logger.getLogger("CalcTest");
    SeleniumHelper seleniumHelper;
    Page1 page1;
    Page2 page2;
    @BeforeTest
    public void setup(){

        seleniumHelper = new SeleniumHelper();
        seleniumHelper.setDriver();

        page1=new Page1(seleniumHelper.driver);
        page2 = new Page2(seleniumHelper.driver);

    }
    //Test: All images (only first 30 provided by the API) will be displayed in cards on multiple rows
    @Test(priority = 0)
    public void validateHeaderWithNoOfImgs() throws InterruptedException {

        String strNoOfImages= page1.getNumberOfImagesOnLanding();
        String strExpectedMsg = "Pictures ("+strNoOfImages+")";
        String strMessageFromHeader = page1.getTheHeader();
        //Validate if only 30 images are present on a page.
        Assert.assertEquals("30",strNoOfImages);
        //Validate if the Header is correct.
        Assert.assertEquals(strExpectedMsg,strMessageFromHeader);
    }

    //Test: The cards will have the same height and will adapt the width to preserve the aspect ratio of the images
    @Test(priority =  1)
    public void validateHeight(){

        List<String> lstHeights= page1.getAllImgHeight();

        Boolean bAllHeightsSame= true;

        String firstHeight = lstHeights.get(0);

        for (String str:
             lstHeights) {

            if (!str.equals(firstHeight)){

                bAllHeightsSame=false;


            }

        }

        Assert.assertTrue(bAllHeightsSame);

    }
    //Test: On click on the image card, the user will open the detail view of the image
    @Test(priority =  2)
    public void verfyIfUserCanOpenAllImages(){
        page1.getDetailViewsforAllImages(page2);
        List<String> imageNameOnLanding = page1.lstPageImages;//to get the images from Landing page
        List<String>lstIfImgOpened = page2.lstImageOpened;//To check if the image is getting opened

        List<Integer> lstErrorIndexes = new ArrayList<>();
        int icount=0;
        for (String err:lstIfImgOpened){

            if (err.equals("Error")){

                lstErrorIndexes.add(icount);
            }

            icount++;
        }

        for (int i:lstErrorIndexes){

            log.info("The Open that was not opened is "+imageNameOnLanding.get(i));

        }


        Assert.assertTrue(lstErrorIndexes.size()==0,"There are some Images that cannot be opened");


    }

    //Test: Verify if Download Button is present
    @Test(priority =  3)
    public void verfyIfDownloadButtonIsPresentForAllCards(){

        List<String> imageNameOnLanding = page1.lstPageImages;//to get the images from Landing page

        List<String> lstDowloads = page1.lstDownloadOption;

        List<Integer> lstErrorIndexes = new ArrayList<>();
        int icount=0;
        for (String err:lstDowloads){

            if (err.equals("Error")){

                lstErrorIndexes.add(icount);
            }

            icount++;
        }

        for (int i:lstErrorIndexes){

            log.info("The Download Button is  Not Present for "+imageNameOnLanding.get(i));

        }

        Assert.assertTrue(!lstDowloads.contains("Error"),"Download Button is Not Present for some Images");

        System.out.println("Download Button "+lstDowloads);
    }

    //Test: The detail view will display the same content card, but with an expanded image (height 500px)
    @Test(priority =  3)
    public void validateIfCorrectImageIsLoadedOnClick(){


        List<String> imageNameOnLanding = page1.lstPageImages;
        List<String>imageNameWhenOpened = page2.lstPageImages;

        System.out.println("Landing*****"+imageNameOnLanding);
        System.out.println("Opening*****"+imageNameWhenOpened);

        Assert.assertTrue(imageNameOnLanding.equals(imageNameWhenOpened));


    }


    //Test: A back button will allow users to return to the gallery view
    @Test(priority =  5)
    public void verfyIfBackButtonWorks(){

        List<String> imageNameOnLanding = page1.lstPageImages;//to get the images from Landing page

        List<String> lstBackButtonWorks = page1.lstIfBackButtonWorks;

        List<Integer> lstErrorIndexes = new ArrayList<>();
        int icount=0;
        for (String err:lstBackButtonWorks){

            if (err.equals("Error")){

                lstErrorIndexes.add(icount);
            }

            icount++;
        }

        for (int i:lstErrorIndexes){

            log.info("The Back Button Does Not work for "+imageNameOnLanding.get(i));

        }

        Assert.assertTrue(!lstBackButtonWorks.contains("Error"),"Back Button does not work");

        System.out.println("Back Button "+lstBackButtonWorks);
    }



}
