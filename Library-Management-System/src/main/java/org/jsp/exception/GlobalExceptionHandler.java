package org.jsp.exception;

import java.util.List;

import org.jsp.response.ResponseStructure;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException exception) {
		ResponseStructure<String> res = new ResponseStructure<String>();

		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("Failure");

		return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoRecordsAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNRAE(NoRecordsAvailableException exception) {
		ResponseStructure<String> res = new ResponseStructure<String>();

		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("Failure");

		return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<FieldError> errors = exception.getBindingResult().getFieldErrors();
		StringBuilder message = new StringBuilder();

		for (int i = 0; i < errors.size(); i++) {

			FieldError error = errors.get(i);

			message.append(error.getField()).append(": ").append(error.getDefaultMessage());

			if (i < errors.size() - 1) {
				message.append(", ");
			}
		}
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage("Validation failed");
		res.setData(message.toString());

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseStructure<String>> handleConstraintViolation(ConstraintViolationException exception) {

		StringBuilder message = new StringBuilder();

		int i = 0;

		for (var error : exception.getConstraintViolations()) {

			message.append(error.getPropertyPath()).append(": ").append(error.getMessage());

			if (i < exception.getConstraintViolations().size() - 1) {
				message.append(", ");
			}

			i++;
		}
		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage("Validation failed");
		res.setData(message.toString());

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseStructure<String>> handleIAE(IllegalArgumentException exception) {

		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage("Invalid request");
		res.setData(exception.getMessage());

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}
}
