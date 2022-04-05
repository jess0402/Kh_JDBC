package product.controller;

import java.util.List;

import product.model.service.ProductService;
import product.model.vo.Product;
import product.model.vo.ProductIO;

public class ProductController {
	
	ProductService productService = new ProductService();
	
	public List<Product> selectAll() {
		List<Product> list = null;
		
		try {
			list = productService.selectAll();
		} catch (Exception e) {
			System.err.println("[" + e.getMessage() + "] 불편을 드려 죄송합니다. 관리자에게 문의하세요.");
		}
		
		return list;
	}

	public int deleteProduct(String id) {
		int result = 0;
		
		try {
			result = productService.deleteProduct(id);			
		} catch(Exception e) {
			System.err.println("[" + e.getMessage() + "] 불편을 드려 죄송합니다. 관리자에게 문의하세요.");
		}
		
		return result;
	}

	public List<Product> selectOne(String col, String searchInfo) {
		List<Product> list = null;
		
		try {			
			list = productService.selectOne(col, searchInfo);
		} catch(Exception e) {
			System.err.println("[" + e.getMessage() + "] 불편을 드려 죄송합니다. 관리자에게 문의하세요.");
		}
		
		return list;
	}

	public int insertProduct(Product product) {
		int result = 0;
		
		try {
			result = productService.insertProduct(product);			
		} catch(Exception e) {
			System.err.println("[" + e.getMessage() + "] 불편을 드려 죄송합니다. 관리자에게 문의하세요.");
		}
		
		return result;
	}

	public int updateProduct(Product product) {
		int result = 0;
		
		try {
			result = productService.updateProduct(product);
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("[" + e.getMessage() + "] 불편을 드려 죄송합니다. 관리자에게 문의하세요.");
		}
		
		return result;
	}

	public List<ProductIO> selectProductIOAll(String id) {
		List<ProductIO> list = null;
		
		try {
			list = productService.selectProductIOAll(id);
		} catch(Exception e) {
			System.err.println("[" + e.getMessage() + "] 불편을 드려 죄송합니다. 관리자에게 문의하세요.");
		}
		
		return list;
	}

	public int inputProduct(String id, int inputNum) {
		int result = 0;
		
		try {
		 	result = productService.inputProduct(id, inputNum);			
		} catch(Exception e) {
			System.err.println("[" + e.getMessage() + "] 불편을 드려 죄송합니다. 관리자에게 문의하세요.");
		}
		return result;
	}

	public int outputProduct(String id, int outputNum) {
		int result = 0;
		
		try {
			result = productService.outputProduct(id, outputNum);
		} catch(Exception e) {
			System.err.println("[" + e.getMessage() + "] 불편을 드려 죄송합니다. 관리자에게 문의하세요.");
		}
		
		return result;
	}

}
