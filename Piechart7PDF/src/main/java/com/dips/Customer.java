package com.dips;

public class Customer {

	private Long x;
	private Long y;
	private String Firstname;
	private String Lastname;
	public Long getX() {
		return x;
	}
	public void setX(Long x) {
		this.x = x;
	}
	public Long getY() {
		return y;
	}
	public void setY(Long y) {
		this.y = y;
	}
	public String getFirstname() {
		return Firstname;
	}
	public void setFirstname(String firstname) {
		Firstname = firstname;
	}
	public String getLastname() {
		return Lastname;
	}
	public void setLastname(String lastname) {
		Lastname = lastname;
	}
	public Customer(Long x, Long y, String firstname, String lastname) {
		super();
		this.x = x;
		this.y = y;
		Firstname = firstname;
		Lastname = lastname;
	}
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Customer [x=" + x + ", y=" + y + ", Firstname=" + Firstname + ", Lastname=" + Lastname + "]";
	}
	
	

}
