package com.src.exception.mapUtility;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class MappingUtilityException extends Exception{
	private String message = "";

	/**     * Constructor for MappingUtilityException.     */   
	public MappingUtilityException(){
		super(); 
	}
	/**     * Constructor for MappingUtilityException.     *     * @param message     */ 
	public MappingUtilityException (String message){
		System.out.println(message);
	} 
	/**    * Constructor for MappingUtilityException.   * @param Exception */
	public MappingUtilityException(Exception e){ 
		super(e);
		try{
			if(e.toString().contains("java.io.FileNotFoundException")){
				System.out.println("Cannot find a file at the specified path");
				
			}
			else if(e.toString().contains("java.lang.ArrayIndexOutOfBoundsException")){
				System.out.println("Exception caused while passing input arguments");
			}
			else if(StringUtils.isNotEmpty(e.toString())){
				System.out.println("Exception Message : "+e.toString());
			}
		}catch (Exception exp) {
			exp.printStackTrace();
		}
	}
	/**    * Constructor for MappingUtilityException.   * @param message,IOException  */
	public MappingUtilityException(String message, IOException e){ 
		System.out.println(message+":"+e.toString());
	}
	/**     * @see java.lang.Throwable#getMessage()     */  
	public String getMessage (){ 
		return this.message;    
	}    
	/**     * @see java.lang.Object#toString()     */ 
	public String toString (){ 
		return this.message; 
	}
}

