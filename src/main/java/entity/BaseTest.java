package entity;

import clients.WeatherClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    ThreadLocal<WeatherClient> thread = new ThreadLocal<>();

    @BeforeClass(alwaysRun = true)
    public void initalSetup(){


        //Set Browser to ThreadLocalMap
        thread.set(new WeatherClient());

    }
    @BeforeMethod
    public void setup () {

    }

    public WeatherClient getWeatherThread(){
        return thread.get();
    }

    @AfterClass
    public void tearDown(){
        thread.remove();
    }
}
