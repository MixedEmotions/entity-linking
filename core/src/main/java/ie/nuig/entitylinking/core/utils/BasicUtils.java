package ie.nuig.entitylinking.core.utils;

import ie.nuig.entitylinking.core.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class BasicUtils {




	public static Properties loadConfig(String configFilePath) throws FileNotFoundException, IOException {
		Properties config = null;
		config =  new Properties();
		config.load(new FileInputStream(configFilePath));
		return config;
	}

	public static String collection2String(Collection<String> list, String delimiter){
		String concatanatedString = "";
		for(String item: list)
			concatanatedString = concatanatedString + delimiter + item;

		concatanatedString = concatanatedString.trim();
		if(concatanatedString.startsWith(delimiter))
			concatanatedString = concatanatedString.substring(delimiter.length());

		return concatanatedString;
	}

	public static double getMin(Collection<Double> values){
		ArrayList<Double> valuesToSort = new ArrayList<Double>(values);
		Collections.sort(valuesToSort);		
		return valuesToSort.get(0);		
	}

	public static List<String> map2SortedKeyList(Map<String, Double> map){
		ArrayList<String> sortedList = new ArrayList<String>();
		ArrayList<Double> values = new ArrayList<Double>(map.values());
		Collections.sort(values);

		int count = values.size()-1;
		while(count >-1){
			Double val = values.get(count);
			for(String key: map.keySet()){
				if(val == map.get(key)){
					sortedList.add(key);
					break;
				}
			}
			count--;
		}
		return sortedList;
	}

	public static List<String> map2SortedkeyValueList(Map<String, Double> map, String delimiter){
		//		ArrayList<String> sortedList = new ArrayList<String>(map.size());
		String[] sortedList = new String[map.size()];

		ArrayList<Double> values = new ArrayList<Double>(map.values());
		Collections.sort(values);

		for(String key: map.keySet()){
			Double keyVal = map.get(key);
			int index = (values.size() - (values.indexOf(keyVal)+1));
			if(sortedList[index] == null)
				sortedList[index] = key+delimiter+keyVal;
			else {
				while(sortedList[index] != null)
					index = index - 1;
				sortedList[index] = key+delimiter+keyVal;
			}
			//				sortedList.add(index, key+delimiter+keyVal);
		}
		return Arrays.asList(sortedList);
	}

	public static List<Pair<String, Double>> map2SortedkeyValuePair(Map<String, Double> map){

		List<Pair<String, Double>> sortedPairs = new ArrayList<Pair<String, Double>>(map.size());
		//		String[] sortedList = new String[map.size()];

		ArrayList<Double> values = new ArrayList<Double>(map.values());
		Collections.sort(values);

		for(String key: map.keySet()){
			Double keyVal = map.get(key);
			int index = (values.size() - (values.indexOf(keyVal)+1));
			if(sortedPairs.get(index) == null)
				sortedPairs.add(index, new Pair<String, Double>(key, keyVal));
			else {
				while(sortedPairs.get(index) != null)
					index = index - 1;
				sortedPairs.add(index, new Pair<String, Double>(key, keyVal));
			}
			//				sortedList.add(index, key+delimiter+keyVal);
		}
		return sortedPairs;
	}


	public static Map<String, Double> map2sortedMap(Map<String, Double> map, int k){
		HashMap<String, Double> sortedMap = new HashMap<String, Double>();
		String delimeter = "DELIMETER";
		int count = 1;
		List<String> sortedkeyValueList = map2SortedkeyValueList(map, delimeter);
		for(String keyVal: sortedkeyValueList){
			if(count > k)
				break;
			count++;
			String[] keyValPair = keyVal.split(delimeter);
			sortedMap.put(keyValPair[0], Double.parseDouble(keyValPair[1]));			
		}
		return sortedMap;
	}

	public static void main(String[] args) {
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("s11", 0.013);
		map.put("s2", 0.013);
		map.put("s3", 0.011);
		map.put("s4", 0.002);
		map.put("s6", 0.03);
		map.put("s5", 0.004);
		//		TextStreamWriter writer = BasicFileTools.getTextStreamWriter("/Users/Nitish/Work/test.txt");

		Map<String, Double> sortedMap = BasicUtils.map2sortedMap(map, 4);
		for(String key: sortedMap.keySet())
			System.out.println(key+": "+ sortedMap.get(key));

		//		List<String> list = map2SortedkeyValueList(map, "#");
		//		for(int i = 0; i<3;i++)
		//			System.out.println("a1"+"\t"+list);

	}
}
