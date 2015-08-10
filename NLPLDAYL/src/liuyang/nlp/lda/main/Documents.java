package liuyang.nlp.lda.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import liuyang.nlp.lda.com.FileUtil_readXML;
import liuyang.nlp.lda.com.FileUtil;
import liuyang.nlp.lda.com.Stopwords;

/**Class for corpus which consists of M documents
 * @author yangliu
 * @blog http://blog.csdn.net/yangliuy
 * @mail yangliuyx@gmail.com
 */

public class Documents {
	
	ArrayList<Document> docs; 
	Map<String, Integer> termToIndexMap;
	ArrayList<String> indexToTermMap;
	Map<String,Integer> termCountMap;
	
	public Documents(){
		docs = new ArrayList<Document>();
		termToIndexMap = new HashMap<String, Integer>();
		indexToTermMap = new ArrayList<String>();
		termCountMap = new HashMap<String, Integer>();
	}
	
	public void readDocs(String docsPath,Boolean isXMLFile){
		for(File docFile : new File(docsPath).listFiles()){
			Document doc = new Document(docFile.getAbsolutePath(), termToIndexMap, indexToTermMap, termCountMap,isXMLFile);
			docs.add(doc);
		}
	}
	public void readDocs_my(List<String> listbook,Boolean isXMLFile){
		for(int i=0;i<listbook.size();i++){
			File docFile=new File(listbook.get(i));
			if(docFile.exists()){
			Document doc = new Document(docFile.getAbsolutePath(), termToIndexMap, indexToTermMap, termCountMap,isXMLFile);
			docs.add(doc);
			}
			
		}
	}
	
	public static class Document {	
		String docName;
		int[] docWords;
		
		public Document(String docName, Map<String, Integer> termToIndexMap, ArrayList<String> indexToTermMap, Map<String, Integer> termCountMap,Boolean isXMLFile){
			this.docName = docName;
			//Read file and initialize word index array
			ArrayList<String> docLines = new ArrayList<String>();
			ArrayList<String> words = new ArrayList<String>();
			if(isXMLFile)
				(new FileUtil_readXML()).readXMLLines(docName, docLines);
			else
				FileUtil.readLines(docName, docLines);
			for(String line : docLines){
				FileUtil.tokenizeAndLowerCase(line, words);
			}
			//Remove stop words and noise words
			for(int i = 0; i < words.size(); i++){
				if(Stopwords.isStopword(words.get(i)) || isNoiseWord(words.get(i))){
					words.remove(i);
					i--;
				}
			}
			//Transfer word to index
			this.docWords = new int[words.size()];
			for(int i = 0; i < words.size(); i++){
				String word = words.get(i);
				if(!termToIndexMap.containsKey(word)){
					int newIndex = termToIndexMap.size();
					termToIndexMap.put(word, newIndex);
					indexToTermMap.add(word);
					termCountMap.put(word, new Integer(1));
					docWords[i] = newIndex;
				} else {
					docWords[i] = termToIndexMap.get(word);
					termCountMap.put(word, termCountMap.get(word) + 1);
				}
			}
			words.clear();
		}
		
		public boolean isNoiseWord(String string) {
			// TODO Auto-generated method stub
			string = string.toLowerCase().trim();
			Pattern MY_PATTERN = Pattern.compile(".*[a-zA-Z]+.*");
			Matcher m = MY_PATTERN.matcher(string);
			// filter @xxx and URL
			if(string.matches(".*www\\..*") || string.matches(".*\\.com.*") || 
					string.matches(".*http:.*") )
				return true;
			if (!m.matches()) {
				return true;
			} else
				return false;
		}
		
	}
}
