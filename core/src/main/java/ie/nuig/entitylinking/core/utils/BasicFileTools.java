package ie.nuig.entitylinking.core.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BasicFileTools {

	public static int numberOfLineRead = 0;

	public static BufferedReader getBufferedReader(File file) {
		FileInputStream inputFile = null;
		InputStreamReader streamRead = null;
		BufferedReader read = null;				
		try {		
			inputFile = new FileInputStream(file.getAbsolutePath());
			streamRead = new InputStreamReader(inputFile, "UTF8");
			read = new BufferedReader(streamRead);
		} catch(Exception e) {
			System.out.println("File Not there probably or yet not created: " + file.getAbsolutePath());
			numberOfLineRead = 0;
		}		
		return read;
	}

	public static BufferedReader getBufferedReaderFile(String filePath) {
		return getBufferedReader(getFile(filePath));
	}

	public static String extractText(File file) throws IOException {
		BufferedReader bufferedReader = getBufferedReader(file);
		StringBuffer fileText = new StringBuffer();
		try {
			String line;
			int j = 0;
			while((line = bufferedReader.readLine())!= null) {
				j++;
				fileText.append(line + "\n");
			}
			numberOfLineRead = j;			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		bufferedReader.close();
		return fileText.toString().trim();
	}	

	private static File getFile(String filePath){
		File file = null;
		try {
			file = new File(filePath);
		} catch(Exception E) {
			System.out.println("Not a File Path or File Not Found");
		}
		return file;
	}

	public static String extractText(String filePath){
		try {
			return extractText(getFile(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	

	public static boolean writeFile(String filePath, String text) {
		File file = new File(filePath);
		Writer output = null;
		boolean written = false;
		try {
			output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file.getAbsolutePath()), "UTF-8"));
			output.write(text);
			output.close();
			written = true;			
		} catch (IOException e) {
			e.printStackTrace();
		}				
		return written;
	}

	public static void deleteDirOrFile(String dirOrFilePath) {	
		File directory = new File(dirOrFilePath);
		if(!directory.exists()){
			System.out.println("Directory does not exist.");
			System.exit(0);
		}else{
			try{
				deleteDirOrFile(directory);
			}catch(IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	private static void deleteDirOrFile(File fileOrFolder)
			throws IOException{
		if(fileOrFolder.isDirectory()){
			//directory is empty, then delete it
			if(fileOrFolder.list().length==0){
				fileOrFolder.delete();
				//				System.out.println("Directory is deleted : " 
				//						+ fileOrFolder.getAbsolutePath());
			}else{
				//list all the directory contents
				String files[] = fileOrFolder.list();
				for (String temp : files) {
					//construct the file structure
					File fileDelete = new File(fileOrFolder, temp);
					//recursive delete
					deleteDirOrFile(fileDelete);
				}
				//check the directory again, if empty then delete it
				if(fileOrFolder.list().length==0){
					fileOrFolder.delete();
					//					System.out.println("Directory is deleted : " 
					//							+ fileOrFolder.getAbsolutePath());
				}
			}
		}else{
			//if file, then delete it
			fileOrFolder.delete();
			//	System.out.println("File is deleted : " + fileOrFolder.getAbsolutePath());
		}
	}

	public static boolean createNewDirectory(String dirPath) {
		boolean success = (
				new File(dirPath)).mkdir();
		return success;
	}

	public static List<String> listSubDir(String dirPath){
		File dir = new File(dirPath);
		if(!dir.isDirectory()){
			System.err.println(dirPath + " is not a directory");
			return null;
		}
		String[] files = dir.list();
		ArrayList<String> filePathList = new ArrayList<String>();

		for(String file: files){
			if(!file.startsWith("."))
				filePathList.add(dirPath+ File.separatorChar +file);
		}

		return filePathList;

	}

	public static Map<String, List<String>> tsv2Map(String filePath, int keyIndex, int valIndex){
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();

		BufferedReader reader = BasicFileTools.getBufferedReader(new File(filePath));

		String line = null;
		try {
			while((line=reader.readLine()) != null){
				List<String> pairs = Arrays.asList(line.split("\t"));
				if(pairs.size()<valIndex+1)
					continue;
				if(map.containsKey(pairs.get(keyIndex))){
					List<String> tempList = map.get(pairs.get(keyIndex));
					tempList.add(pairs.get(valIndex));
				}
				else{
					ArrayList<String> tempList = new ArrayList<String>();
					tempList.add(pairs.get(valIndex));
					map.put(pairs.get(keyIndex), tempList);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, List<String>> tsv2Map(String filePath){
		return tsv2Map(filePath,0,1);
	}
	
	public static TextStreamWriter getTextStreamWriter(String outputFile){
		return new TextStreamWriter(outputFile);
	}
	
	public static class TextStreamWriter{
		private PrintWriter outFile;
		
		public TextStreamWriter(String outputFilePath){
			initiate(outputFilePath);
		}
		
		public void append(String text){
			 outFile.println(text);
		}
		
		private void initiate(String outputFile){
			   File file = new File(outputFile);
               try {
                       Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"));
                       outFile = new PrintWriter(out, true);
               } catch (IOException e) {
                       e.printStackTrace();
               }              
		}
	}

	public static void main(String[] args) throws IOException {

//		String filePath = "/Users/nitagg/deri/eclipse/data/DBpedia_data/persondata_en.nt";
//		String filePath = "/Users/nitagg/deri/eclipse/data/DBpedia_data/disambiguations_en.nt";
		String filePath = "/Users/nitagg/deri/eclipse/data/DBpedia_data/lexicalizations.pairThresh5.nq";
		BufferedReader reader = BasicFileTools.getBufferedReaderFile(filePath);
		String line = null;
		int count = 0;

		while((line = reader.readLine())!= null){
			System.out.println(line);
			count++;
			if(count> 100){
				break;				
			}
				
		}
		
//		TextStreamWriter streamWriter = BasicFileTools.getTextStreamWriter(filePath);
//		for(int count =1; count<100; count++)
//			streamWriter.append("line is: " + count);
	}

}
