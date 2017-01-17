package com.dc.gateway.exceptions;

public class GetUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1698902890776040637L;

	  public GetUserException(String msg)
	  {
	    super(msg);
	  }
	  
	  public GetUserException(String msg, Throwable cause)
	  {
	    super(msg, cause);
	  }
	  
	  public GetUserException(Throwable cause)
	  {
	    super(cause);
	  }
}
