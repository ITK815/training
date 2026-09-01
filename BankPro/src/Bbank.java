import java.util.List;
import java.util.Scanner;

import acc.Account;
import acc.Acount;
import acc.SpecialAccount;
import dao.AccountDao;
import exc.BankException;
import exc.ERR_CODE;

public class Bbank {
	Scanner sc = new Scanner(System.in);
	AccountDao dao = new AccountDao();
	
	int menu() throws BankException {
		System.out.println("[코스타 은행]");
		System.out.println("1.계좌개설");
		System.out.println("2.입금");
		System.out.println("3.출금");
		System.out.println("4.계좌송금");
		System.out.println("5.계좌조회");
		System.out.println("6.전체계좌조회");
		System.out.println("0.종료");
		System.out.print("선택>>");
		int sel = Integer.parseInt(sc.nextLine());
		if(!(sel>=0 && sel<=6)) throw new BankException("메뉴오류", ERR_CODE.MENU);
		return sel;
	}
	
	int selMakeAccount() throws BankException {
		System.out.println("[계좌개설]");
		System.out.println("1.일반계좌");
		System.out.println("2.특수계좌");
		System.out.print("선택>>");
		int sel = Integer.parseInt(sc.nextLine());
		switch(sel) {
		case 1: makeAccount(); break;
		case 2: makeSpecialAccount(); break;
		default: throw new BankException("메뉴오류", ERR_CODE.MENU);
		}
		return sel;
	}
	
	void makeAccount() throws BankException {
		System.out.println("[일반계좌개설]");
		System.out.print("계좌번호:");
		String id = sc.nextLine();
		
		//1. id를 계좌 조회:selectAccount
		Acount acc = dao.selectAccount(id);
		if(acc!=null) {
			throw new BankException("계좌오류", ERR_CODE.DOUBLEID);
		}
		System.out.print("이름:");
		String name = sc.nextLine();
		System.out.print("입금액:");
		int money = Integer.parseInt(sc.nextLine());
		dao.insertAccount(new Acount(id,name,money));
	}
	
	void makeSpecialAccount() throws BankException {
		System.out.println("[특수계좌개설]");
		System.out.print("계좌번호:");
		String id = sc.nextLine();
		Acount acc = dao.selectAccount(id);
		if(acc!=null) {
			throw new BankException("계좌오류", ERR_CODE.DOUBLEID);
		}
		System.out.print("이름:");
		String name = sc.nextLine();
		System.out.print("입금액:");
		int money = Integer.parseInt(sc.nextLine());
		System.out.print("등급(VIP,Gold,Silver,Normal):");
		String grade = sc.nextLine();
		dao.insertAccount(new SpecialAccount(id, name, money, grade));
	}
	
	void deposit() throws BankException {
		System.out.println("[입금]");
		System.out.print("계좌번호:");
		String id = sc.nextLine();
		Acount acc = dao.selectAccount(id);
		if(acc==null) {
			throw new BankException("계좌오류", ERR_CODE.DOUBLEID);
		}
		System.out.print("입금액:");
		int money = Integer.parseInt(sc.nextLine());
		acc.deposit(money);
		dao.updateBalance(acc);
	}
	
	void withdraw() throws BankException {
		System.out.println("[출금]");
		System.out.print("계좌번호:");
		String id = sc.nextLine();
		Acount acc = dao.selectAccount(id);
		if(acc==null) {
			throw new BankException("계좌오류", ERR_CODE.DOUBLEID);
		}
		System.out.print("출금액:");
		int money = Integer.parseInt(sc.nextLine());
		
		acc.withdraw(money);
		dao.updateBalance(acc);
	}
	
	void transfer() throws BankException  {
		System.out.println("[계좌송금]");
		System.out.print("보내는 계좌번호:");
		String sid = sc.nextLine();
		Acount sacc = dao.selectAccount(sid);
		if(sacc==null) {
			throw new BankException("계좌오류", ERR_CODE.SENDACCID);
		}
		
		System.out.print("받는 계좌번호:");
		String rid = sc.nextLine();
		Acount racc = dao.selectAccount(rid);
		if(racc==null) {
			throw new BankException("계좌오류", ERR_CODE.RECVACCID);
		}
		
		System.out.print("송금액:");
		int money = Integer.parseInt(sc.nextLine());
		
		sacc.withdraw(money);
		racc.transfer(money);
		
		dao.updateBalance(sacc);
		dao.updateBalance(racc);
	}
	
	void accountInfo() throws BankException {
		System.out.println("[계좌조회]");
		System.out.print("계좌번호:");
		String id = sc.nextLine();
		Acount acc = dao.selectAccount(id);
		if(acc==null) {
			throw new BankException("계좌오류", ERR_CODE.ACCID);
		}
		
		System.out.println(acc.info());
	}
	
	void allAccountInfo() {
		System.out.println("[전체계좌조회]");
		List<Acount> list = dao.selectAccountList();
		for(Acount acc : list) {
			System.out.println(acc.info());
		}
	}
		
	public static void main(String[] args) {
		Bbank bank = new Bbank();
		Loop1 :  while(true) {
			try {
				int sel=bank.menu();
				switch(sel) {
				case 1: bank.selMakeAccount(); break;
				case 2: bank.deposit(); break;
				case 3: bank.withdraw(); break;
				case 4: bank.transfer(); break;
				case 5: bank.accountInfo(); break;
				case 6: bank.allAccountInfo(); break;
				case 0: break Loop1;
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			} catch (BankException e) {
				System.out.println(e);
			}
		}
	}
}
