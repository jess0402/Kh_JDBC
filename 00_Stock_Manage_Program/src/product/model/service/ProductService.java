package product.model.service;

import static common.JdbcTemplate.*;

import java.sql.Connection;
import java.util.List;

import product.model.dao.ProductDao;
import product.model.vo.Product;
import product.model.vo.ProductIO;

public class ProductService {

	ProductDao productDao = new ProductDao();
	
	public List<Product> selectAll() {
		Connection conn = null;
		List<Product> list = null;
		
		conn = getConnection();
		
		list = productDao.selectAll(conn);
		
		close(conn);
		
		return list;
	}

	public int deleteProduct(String id) {
		int result = 0;
		Connection conn = getConnection();
		
		try {
			result = productDao.deleteProduct(conn, id);
			commit(conn);
			
		} catch(Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		
		
		return result;
	}

	public List<Product> selectOne(String col, String searchInfo) {

		Connection conn = getConnection();
		List<Product> list = null;
		
		list = productDao.selectOne(conn, col, searchInfo);
		
		close(conn);
		
		return list;
	}

	public int insertProduct(Product product) {
		
		int result = 0;
		Connection conn = getConnection();
		
		try {
			result = productDao.insertProduct(conn, product);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);			
		}
		
		return result;
	}

	public int updateProduct(Product product) {
		
		int result = 0;
		Connection conn = getConnection();
		
		try {
			result = productDao.updateProduct(conn, product);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			e.printStackTrace();
			throw e;
		} finally {
			close(conn);			
		}
		
		return result;
	}

	public List<ProductIO> selectProductIOAll(String id) {
		
		Connection conn = getConnection();
		
		List<ProductIO> list = productDao.selectProductIOAll(conn, id);
		
		close(conn);
	
		return list;
	}

	public int inputProduct(String id, int inputNum) {
		int result = 0;
		Connection conn = getConnection();
		
		try {			
			result = productDao.inputProduct(conn, id, inputNum);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		
		return result;
	}

	public int outputProduct(String id, int outputNum) {
		int result = 0;
		Connection conn = getConnection();
		
		try {
			result = productDao.outputProduct(conn, id, outputNum);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		
		return result;
	}
	
}
