package com.example.demo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.Spec;
import com.example.demo.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {
	Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@Autowired
	ProductRepository productRepository;

	@GetMapping
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping("/customers")
	public Map<String, List<Product>> getAllCustomers() {
		return Collections.singletonMap("customers", productRepository.findAll());
	}

	@PostMapping("/customers")
	public Product addCustomer(@RequestBody ProductDto product) {
		logger.info(product.getName());
		Set<Spec> productSpecs = product.getSpecs();
		
		String specStr = "";
		if (productSpecs != null) specStr = productSpecs.toString();
		logger.info(specStr);
		Product persistentProduct = new Product();
		persistentProduct.setName(product.getName());
		persistentProduct.setSpecs(product.getSpecs());
		return productRepository.save(persistentProduct);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
