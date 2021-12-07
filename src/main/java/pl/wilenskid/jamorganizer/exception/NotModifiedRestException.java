package pl.wilenskid.jamorganizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class NotModifiedRestException extends HttpStatusCodeException {
	private static final long serialVersionUID = 1495383435331369006L;

	public NotModifiedRestException() {
        super(HttpStatus.NOT_MODIFIED);
    }
}
