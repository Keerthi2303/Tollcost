package highway;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TollCost {

	public static void main(String[] args) throws org.json.simple.parser.ParseException 
	{

		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("src/main/resources/interchanges.json"))
		{

			Object obj = jsonParser.parse(reader);
			JSONObject locationList = (JSONObject) obj;
			String dest1=args[0];
			String dest2=args[1];
			HashMap<String,Double> result=costOfTrip((JSONObject) locationList,dest1,dest2 );
			System.out.println("costOfTrip('"+dest1+"','"+dest2+"')");
			System.out.println("distance: "+result.get("distance"));
			System.out.println("cost: $"+result.get("tollcost"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private static HashMap<String, Double> costOfTrip(JSONObject employee,String dest1,String dest2) 
	{

		JSONObject locationObject = (JSONObject) employee.get("locations");	
		HashMap<String,Integer> inputcheck=new HashMap<String,Integer>();
		for(int i=1;i<=locationObject.size();i++) {
			JSONObject location=(JSONObject) locationObject.get(Integer.toString(i));
			String name=(String)location.get("name");
			if(name.equalsIgnoreCase(dest1)) {
				inputcheck.put(name, i);
			}
			if(name.equalsIgnoreCase(dest2)) {
				inputcheck.put(name, i);
			}
		}
		JSONObject destination1 =  (JSONObject) locationObject.get(inputcheck.get(dest1).toString());    
		JSONArray routes1=(JSONArray) destination1.get("routes");
		Double tollcost;	
		Double distance=null;
		for(int j=0;j<routes1.size();j++) {
			JSONObject routesObj=(JSONObject)routes1.get(j);
			Long toIdCheck=(Long)routesObj.get("toId");
			if(toIdCheck==inputcheck.get(dest2).intValue()) {
				distance=(Double)routesObj.get("distance");
			}
		}
		HashMap<String,Double> tollCharge=new HashMap<String,Double>();
		tollcost=distance*0.25;
		tollCharge.put("distance", distance);
		tollCharge.put("tollcost", tollcost);

		return tollCharge;

	}
}
