package exc;

public class BankException extends Exception {
	ERR_CODE errCode;
	public BankException(String message, ERR_CODE errCode) {
		super(message);
		this.errCode = errCode;
	}
	public BankException(ERR_CODE errCode) {
		this.errCode=errCode;
	}
	
	@Override
	public String toString() {
		String message = "";
		if(getMessage() != null && !getMessage().isEmpty()) {
			message += "["+getMessage()+"]";
		}
		switch(errCode) {
		case ACCID: message+="계좌번호가 틀립니다."; break;
		case SENDACCID: message+="보내는 계좌번호가 틀립니다."; break;
		case RECVACCID: message+="받는 계좌번호가 틀립니다."; break;
		case DOUBLEID: message+="계좌번호가 중복됩니다.";break;
		case DEPOSIT: message+="입금액을 확인하세요.";break;
		case WITHDRAW: message+="잔액이 부족합니다.";break;
		case MENU: message+="메뉴를 잘못 선택했습니다.";break;
		}
		return message;
	}
}
