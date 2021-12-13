package bogdan.kuzenko.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlPageController {

    @RequestMapping("/categories")
    public String categories1() {
        return "categories.html";
    }

    @RequestMapping("/subcategories")
    public String subcategories() {
        return "subcategories.html";
    }

    @RequestMapping("/catalog")
    public String catalog() {
        return "cataloge.html";
    }

    @RequestMapping("/makers")
    public String makers() {
        return "makers.html";
    }

    @RequestMapping("/createProduct")
    public String createProduct() {
        return "createProduct.html";
    }

    @RequestMapping("/fullProduct")
    public String product() {
        return "fullProduct.html";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "cart.html";
    }


}
