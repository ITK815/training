package acc;

import java.io.Serializable;

import exc.BankException;
import exc.ERR_CODE;

public class Acount implements Serializable{
	public String id;
	public String name;
	public int balance;
	String grade;
	
	public String getName() {
		return name;
	}
	public int getBalance() {
		return balance;
	}
	public String getGrade() {
		return grade;
	}
	public String getId() {
		return id;
	}
	public Acount(){}
	public Acount(String id, String name, int balance){
		this.id=id;
		this.name = name;
		this.balance = balance;
	}
	public void SpecialAccount(){}
	public void SpecialAccount(String id, String name, int balance, String grade){
		this.id=id;
		this.name = name;
		this.balance = balance;
		this.grade = grade;
	}
	
	public String info() {		//그래서 이놈도 void가 아닌 문자열인 string으로 만들고 return 값을 준다.
		return "계좌번호:"+id+", 이름:"+name+", 잔액:"+balance;
	}
	public String info2() {		//그래서 이놈도 void가 아닌 문자열인 string으로 만들고 return 값을 준다.
		return "계좌번호:"+id+", 이름:"+name+", 잔액:"+balance+", 등급:"+grade;
	}
	public void transfer(int money) {
		balance += money;
	}
	public void deposit(int money) throws BankException {
		if(money<=0) throw new BankException("입금오류", ERR_CODE.DEPOSIT);
		balance += money;
	}
	
	public void withdraw(int money) throws BankException {
		if(money> balance) throw new BankException("출금오류", ERR_CODE.WITHDRAW);
		balance -= money;		
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Acount)) return false;
		Acount acc = (Acount)obj;
		return id.equals(acc.getId());
	}
	@Override
	public String toString() {
		return "[계좌번호="+id+", 이름="+name+", 잔액="+balance+", 등급="+grade+"]";
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
