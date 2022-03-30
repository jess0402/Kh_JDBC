package member.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * vo 클래스
 * 	- data를 가진 객체로 사용
 * 	- db table의 한 행과 vo객체 하나 매칭
 * 	- db table의 컬럼과 vo의 컬럼이 매칭
 *
 */
public class Member {
	private String id;
	private String name;
	private String gender;
	private Date birthday;
	private String email;
	private String address;
	private Timestamp regDate;
	
	public Member() {
		super();
	}

	public Member(String id, String name, String gender, Date birthday, String email, String address,
			Timestamp regDate) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday + ", email="
				+ email + ", address=" + address + ", regDate=" + regDate + "]";
	}

	
	
	
	
	
}
