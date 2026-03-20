package mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions;

public class SemiFullFunction extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String messageError;
	
	private Object data;
	
	public SemiFullFunction(String message, Object data) {
        this.messageError = message;
        this.data = data;
    }
	
	
	public String getMessageError() {
		return this.messageError;
	}
	
	public Object getData() {
		return this.data;
	}

}
