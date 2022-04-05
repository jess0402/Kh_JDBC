package product.model.dao;

import static common.JdbcTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import product.model.exception.ProductException;
import product.model.vo.Product;
import product.model.vo.ProductIO;

public class ProductDao {

	Properties prop = new Properties();
	
	public ProductDao() {
		// product-query.properties의 내용 불러오기
		try {
			prop.load(new FileReader("resources/product-query.properties"));
			System.out.println("> product-query.properties 로드 완료!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Product> selectAll(Connection conn) {

		String sql1 = prop.getProperty("selectAll_detail");
		List<Product> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql1);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Product product = new Product();
				product.setId(rset.getString("id"));
				product.setBrand(rset.getString("brand"));
				product.setName(rset.getString("name"));
				product.setPrice(rset.getInt("price"));
				product.setMonitor_size(rset.getInt("monitor_size"));
				product.setOs(rset.getString("os"));
				product.setStorage(rset.getInt("storage"));
				product.setStock(rset.getInt("stock"));
				
				list.add(product);
			}
		} catch (SQLException e) {
			throw new ProductException("selectAll 조회오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int deleteProduct(Connection conn, String id) {
		
		String sql = prop.getProperty("deleteProduct");
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
//			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ProductException("deleteProduct 예외", e);
		} finally {
			close(pstmt);
		}
	
		return result;
	}

	public List<Product> selectOne(Connection conn, String col, String searchInfo) {
		
		String sql = prop.getProperty("selectOne");
		List<Product> list = new ArrayList<>();
		Product product = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		sql = prop.getProperty("selectOne");
		sql = sql.replace("#", col);	
		
		try {			
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, "%" + searchInfo + "%");

			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				product = new Product();
				product.setId(rset.getString("id"));
				product.setBrand(rset.getString("brand"));
				product.setName(rset.getString("name"));
				product.setPrice(rset.getInt("price"));
				product.setMonitor_size(rset.getInt("monitor_size"));
				product.setOs(rset.getString("os"));
				product.setStorage(rset.getInt("storage"));
				product.setStock(rset.getInt("stock"));
				
				list.add(product);
			}
		} catch (SQLException e) {
			throw new ProductException("selectOne 조회오류", e);
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int insertProduct(Connection conn, Product product) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getId());
			pstmt.setString(2, product.getBrand());
			pstmt.setString(3, product.getName());
			pstmt.setInt(4, product.getPrice());
			pstmt.setInt(5, product.getMonitor_size());
			pstmt.setString(6, product.getOs());
			pstmt.setInt(7, product.getStorage());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ProductException("insertProduct 오류", e);
		} 
		
		// 재고테이블에 추가
		String sql_stock = prop.getProperty("insertStock");
		
		try {
			pstmt = conn.prepareStatement(sql_stock);
			pstmt.setString(1, product.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ProductException("insertProduct_stock 오류", e);
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateProduct(Connection conn, Product product) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getName());
			pstmt.setInt(2, product.getPrice());
			pstmt.setInt(3, product.getMonitor_size());
			pstmt.setString(4, product.getOs());
			pstmt.setInt(5, product.getStorage());
			pstmt.setString(6, product.getId());
			
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("updateProduct 오류", e);
		}
		
		return result;
	}

	public List<ProductIO> selectProductIOAll(Connection conn, String id) {
		List<ProductIO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectProductIOAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				ProductIO p = new ProductIO();
				p.setNo(rset.getInt("no"));
				p.setProduct_id(rset.getString("product_id"));
				p.setCount(rset.getInt("count"));
				p.setStatus(rset.getString("status"));
				p.setIo_datetime(rset.getTimestamp("io_datetime"));
				
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("selectProductIOAll 오류", e);
		}
	
		return list;
	}

	public int inputProduct(Connection conn, String id, int inputNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("inputProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, inputNum);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ProductException("inputProduct 오류", e);
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int outputProduct(Connection conn, String id, int outputNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("outputProduct");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, outputNum);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ProductException("outputProduct 오류", e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
