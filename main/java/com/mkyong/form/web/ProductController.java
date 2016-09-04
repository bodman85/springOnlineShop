package com.mkyong.form.web;

import com.mkyong.form.model.Product;
import com.mkyong.form.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

//    @Autowired
//    ProductFormValidator productFormValidator;
//
//    //Set a form validator
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.setValidator(productFormValidator);
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        logger.debug("index()");
        return "redirect:/products";
    }

    // list page
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String showAllProducts(Model model) {

        logger.debug("showAllProducts()");
        model.addAttribute("products", productService.findAll());
        return "products/list";

    }

    // save or update product
    // 1. @ModelAttribute bind form value
    // 2. @Validated form validator
    // 3. RedirectAttributes for flash value
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String saveOrUpdateProduct(@ModelAttribute("productForm") @Validated Product product,
                                   BindingResult result,
                                   final RedirectAttributes redirectAttributes) {

        logger.debug("saveOrUpdateProduct() : {}", product);

        if (result.hasErrors()) {
            return "products/productform";
        } else {

            // Add message to flash scope
            redirectAttributes.addFlashAttribute("css", "success");
            if(product.isNew()){
                redirectAttributes.addFlashAttribute("msg", "Product added successfully!");
            }else{
                redirectAttributes.addFlashAttribute("msg", "Product updated successfully!");
            }

            productService.saveOrUpdate(product);

            // POST/REDIRECT/GET
            return "redirect:/products/" + product.getId();

            // POST/FORWARD/GET
            // return "product/list";

        }

    }

    // show add product form
    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
    public String showAddProductForm(Model model) {

        logger.debug("showAddProductForm()");

        Product product = new Product();

        // set default value
//        product.setName("Product Name");
//        product.setCategory("Product Category");
//        product.setDescription("Product Description");
//        product.setPrice(0.00);

        model.addAttribute("productForm", product);

        return "products/productform";

    }

    // show update form
    @RequestMapping(value = "/products/{id}/update", method = RequestMethod.GET)
    public String showUpdateProductForm(@PathVariable("id") int id, Model model) {

        logger.debug("showUpdateProductForm() : {}", id);

        Product product = productService.findById(id);
        model.addAttribute("productForm", product);

        return "products/productform";

    }

    // delete product
    @RequestMapping(value = "/products/{id}/delete", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable("id") int id,
                                final RedirectAttributes redirectAttributes) {

        logger.debug("deleteProduct() : {}", id);

        productService.delete(id);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Product is deleted!");

        return "redirect:/products";

    }

    // show product
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public String showProduct(@PathVariable("id") int id, Model model) {

        logger.debug("showProduct() id: {}", id);

        Product product = productService.findById(id);
        if (product == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "Product not found");
        }
        model.addAttribute("product", product);

        return "products/show";

    }

}