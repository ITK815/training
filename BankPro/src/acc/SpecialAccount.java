package acc;
import java.io.Serializable;

import exc.BankException;

public class SpecialAccount extends Acount implements Serializable {
	String grade;

	public SpecialAccount(String id, String name, int money, String grade) {
		super(id, name, money);
		this.grade = grade;
	}

	public String getGrade() {
		return grade;
	}

	@Override
	public void deposit(int money) throws BankException {
		double rate = 1;
		switch (grade.toUpperCase()) {
		case "VIP":
			rate = 0.04;
			break;
		case "GOLD":
			rate = 0.03;
			break;
		case "SILVER":
			rate = 0.02;
			break;
		case "NORMAL":
			rate = 0.01;
			break;
		}
		super.deposit(money + (int) (money * rate));
	}

	public String info() { // 그래서 이놈도 void가 아닌 문자열인 string으로 만들고 return 값을 준다.
		return super.info() + ", 등급:" + grade;
	}
}
