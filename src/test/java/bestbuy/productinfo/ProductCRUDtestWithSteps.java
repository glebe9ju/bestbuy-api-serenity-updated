package bestbuy.productinfo;


import bestbuy.bestbuyapiinfo.ProductSteps;
import bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCRUDtestWithSteps {
    static String name = "Duracell - New Batteries (4-Pack)";
    static String type = "Triple AAAA";
    static double price = 7.99;
    static String upc = "0512333456";
    static int shipping = 0;
    static String description = "Compatible with select electonic devices; AAA size; DURALOCK Power Preserve technology; 4-pack";
    static String manufacturer = "Duracell";
    static String model = "MN" + TestUtils.getRandomValue() + "YZ";
    static String url = "http://www.bestbuy.com/site/duracell-batteries-8-pack/185267.p?id=1051384046551&skuId=185267&cmp=RMXCC";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/185285/185267_sa.jpg";
    static int productsId;

    @Steps
    ProductSteps productsSteps;

    @Title("This will create a new product")
    @Test
    public void test001() {
        ValidatableResponse response = productsSteps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the product was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> value = productsSteps.getProductInfoByName(name, type, manufacturer);
        Assert.assertThat(value, hasValue(name));
        productsId = (int) value.get("id");
        System.out.println(productsId);
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";
        productsSteps.updateProduct(productsId, name, type, price, upc, shipping, description, manufacturer, model, url, image).log().all().statusCode(200);
        HashMap<String, Object> value = productsSteps.getProductInfoByName(name, type, manufacturer);
        Assert.assertThat(value, hasValue(name));
    }
    @Title("Delete the product and verify if the product is deleted!")
    @Test
    public void test004() {
        productsSteps.deleteProduct(productsId).statusCode(200);
        productsSteps.getProductById(productsId).statusCode(404);
    }


}
