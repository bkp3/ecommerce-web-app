package com.ecom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ecom.entity.Product;
import com.ecom.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		HttpMethod[] theUnsupportedActions= {HttpMethod.PUT,HttpMethod.POST, HttpMethod.DELETE};
		
		//disable HTTP methods for product: PUT, POST and DELETE
		config.getExposureConfiguration()
			.forDomainType(Product.class)
			.withItemExposure((metdata,httpMethods)-> httpMethods.disable(theUnsupportedActions))
			.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
		//disable HTTP methods for product category: PUT, POST and DELETE
		config.getExposureConfiguration()
			.forDomainType(ProductCategory.class)
			.withItemExposure((metdata,httpMethods)-> httpMethods.disable(theUnsupportedActions))
			.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
	}
		
	
	

}
