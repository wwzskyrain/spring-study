package erik.study.spring.mvc.first.controller;

import erik.study.spring.mvc.first.domain.Product;
import erik.study.spring.mvc.first.form.ProductForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;


@Controller
public class ProductController {

    private static final Log logger = LogFactory.getLog(ProductController.class);

    @RequestMapping(value = "/product_input")
    public String inputProduct() {
        logger.info("inputProduct called");
        return "ProductForm";
    }

    @RequestMapping(value = "/product_save")
    public String saveProduct(ProductForm productForm, Model model) { //ProductForm是和前端form约定好的
        logger.info("saveProduct called");
        // no need to create and instantiate a ProductForm
        // create Product
        Product product = new Product();
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        try {
            product.setPrice(Float.parseFloat(
                    productForm.getPrice()));
        } catch (NumberFormatException e) {
        }

        // add product
        model.addAttribute("product", product);
        return "ProductDetails";
    }

    @RequestMapping(value = "/json_data_get")
    @ResponseBody
    public Object getJsonDate() {

        Product product = new Product();
        product.setDescription("description");
        product.setId(123456L);
        product.setName("erik.wang");
        product.setPrice(1.2F);
        return product;
    }

    @RequestMapping("/redirect")
    public ModelAndView testRedSSS() throws Exception {
        logger.info("request for redirect.");
        ModelAndView modelAndView = new ModelAndView("redirect:http://www.qq.com");
        return modelAndView;
    }

}
