package pl.wilenskid.jamorganizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class NotFoundRestException extends HttpStatusCodeException {
	private static final long serialVersionUID = -933341381829241169L;

	public NotFoundRestException() {
        super(HttpStatus.NO_CONTENT);
    }
}
