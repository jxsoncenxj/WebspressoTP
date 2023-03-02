package com.group15.Webspresso;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.group15.Webspresso.controller.ProductController;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.service.ProductService;

@SpringBootTest
public class WebspressoApplicationTests {

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }
	
	
	@Test
	void testCreateProductForm() {
		// when
		String viewName = productController.createProductForm(mock(Model.class));
	
		// define products list
		List<Product> products = Arrays.asList(new Product(1, "Product 1", 10.0, "Product 1 description", 10),
				new Product(2, "Product 2", 20.0, "Product 2 description", 20));
	
		// then
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("products");
		assertEquals("products", modelAndView.getViewName());
		assertEquals(products, modelAndView.getModel().get("products"));
	}
	
		

    @Test
    void testSaveProduct() {
        // given
        Product product = new Product(1, "Product 1", 10.0, "Product 1 description", 10);

        // when
        String viewName = productController.saveProduct(product);

        // then
        assertEquals("redirect:/products", viewName);
    }

    // similar tests for other methods
}




	


