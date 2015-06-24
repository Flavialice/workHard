package CRUDproj;

import java.util.HashMap;
import java.util.Map;

public enum ListaSenzor {
	instance;
	
	private Map<String, Senzor> continut=new HashMap<String, Senzor>();

	private ListaSenzor() {
		Senzor s1=new Senzor("1", "temperatura", 26f, null, "OK");
		continut.put("1", s1);
	}
	
	public Map<String, Senzor> getModel(){
		return continut;
	}
}
