package bestbuy.storeinfo;

import bestbuy.bestbuyapiinfo.StoreSteps;
import bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDtestWithSteps {
    static String name = "Barnet Road Newcastle";
    static String type = "NewsAgent";
    static String address = TestUtils.getRandomValue() + ", Barn Road";
    static String address2 = "Watford";
    static String city = "Barmigham";
    static String state = "Barmigham";
    static String zip = "JU7 4HW";
    static int storeId;

    @Steps
    StoreSteps storesSteps;

    @Title("This will create a new Store")
    @Test
    public void test01() {
        storesSteps.createStore(name, type, address, address2, city, state, zip).statusCode(201).log().all();
    }

    @Title("Verify if the store was added to the application")
    @Test
    public void test02() {
        HashMap<String, Object> storesMap = storesSteps.getStoreInfoByName(name);
        Assert.assertThat(storesMap, hasValue(name));
        storeId = (int) storesMap.get("id");
        System.out.println(storeId);
    }

    @Title("Update the store information and verify the updated information")
    @Test
    public void test03() {
        name = "Robbin";
        type = "Store";
        address = TestUtils.getRandomValue() + ", Robbin road";
        address2 = "Wesfield";
        city = "Leistar";
        state = "Leicester";
        zip = "HA7 6YH";
        storesSteps.updateStore(storeId, name, type, address, address2, city, state, zip).log().all().statusCode(200);
        HashMap<String, Object> storesMap = storesSteps.getStoreInfoByName(name);
        Assert.assertThat(storesMap, hasValue(name));
    }

    @Title("Delete the stores and verify if the stores is deleted!")
    @Test
    public void test04() {
        storesSteps.deleteStore(storeId).statusCode(200).log().status();
        storesSteps.getStoreById(storeId).statusCode(404).log().status();
    }
}
