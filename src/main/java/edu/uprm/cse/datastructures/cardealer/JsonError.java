package edu.uprm.cse.datastructures.cardealer;

public class JsonError {

	private String type;
	private String message;

	public JsonError(String type, String message){
		this.type = type;
		this.message = message;                
	} 

	public String getType(){
		return this.type;
	}

	public String getMessage(){
		return this.message;
	}                                
}
