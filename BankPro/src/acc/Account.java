package acc;

import java.io.Serializable;

import exc.BankException;
import exc.ERR_CODE;

public class Account implements Serializable {
	String id;
	String name;
	int balance;
	
	public String getId() {
		return id;
	}

	
	public String getName() {
		return name;
	}


	public int getBalance() {
		return balance;
	}

	public Account() {}
	
	public Account(String id, String name, int money) {
		this.id=id;
		this.name=name;
		this.balance=money;
	}
	
	public String info() {
		return String.format("계좌번호:%s,이름:%s,잔액:%d", id, name, balance);
	}
	
	public void transfer(int money) {
		balance += money;
	}
	
	public void deposit(int money) throws BankException {
		if(money<=0) throw new BankException("입금오류",ERR_CODE.DEPOSIT);
		balance += money;
	}
	
	public void withdraw(int money) throws BankException {
		if(balance<money) throw new BankException("출금오류",ERR_CODE.WITHDRAW);
		balance -= money;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Account)) return false;
		Account acc = (Account)obj;
		return id.equals(acc.getId());
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}