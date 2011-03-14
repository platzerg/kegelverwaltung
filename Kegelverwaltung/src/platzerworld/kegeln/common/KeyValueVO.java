package platzerworld.kegeln.common;

import java.io.Serializable;

public class KeyValueVO implements Serializable{
	private static final long serialVersionUID = 7652075763595799928L;

	public long key;
	
	public String value;
	
	public KeyValueVO(){
		
	}
	
	public KeyValueVO(long key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}

}
