package shepherd.exceptions;

public class Unauthorized extends Exception {
    
    public Unauthorized (){}

    @Override
    public Throwable fillInStackTrace() {
	return this;
    }
   
}
