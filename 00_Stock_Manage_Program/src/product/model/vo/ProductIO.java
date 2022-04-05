package product.model.vo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ProductIO extends Product implements Serializable {
	
	private int no;
	private String product_id;
	private int count;
	private String status;
	private Timestamp io_datetime;
	
	public ProductIO() {
		super();
	}
	
	public ProductIO(String id, String brand, String name, int price, int monitor_size, String os, int storage,
			Date reg_date, int stock) {
		super(id, brand, name, price, monitor_size, os, storage, reg_date, stock);
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getIo_datetime() {
		return io_datetime;
	}

	public void setIo_datetime(Timestamp io_datetime) {
		this.io_datetime = io_datetime;
	}

	@Override
	public String toString() {
		return "ProductIO [no=" + no + ", product_id=" + product_id + ", count=" + count + ", status=" + status
				+ ", io_datetime=" + io_datetime + "]";
	}
	
	

}
