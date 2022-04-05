package member.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class DelMember extends Member{
	
	private Date delDate;

	public DelMember() {
		super();
	}

	public DelMember(String id, String name, String gender, Date birthday, String email, String address,
			Timestamp regDate) {
		super(id, name, gender, birthday, email, address, regDate);
	}

	public DelMember(Date delDate) {
		super();
		this.delDate = delDate;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	
	
	
}
