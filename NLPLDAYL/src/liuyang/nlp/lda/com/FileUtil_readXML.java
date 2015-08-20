package liuyang.nlp.lda.com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import liuyang.nlp.lda.conf.PathConfig;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class FileUtil_readXML {
	MaxentTagger tagger = new MaxentTagger("data/LdaParameter/english-left3words-distsim.tagger");
	public void addXml(Element e,ArrayList<String> lines){			   
		List<Element> list=e.elements();
		if(list.size()>0) 
			for(Element son : list)
				addXml(son,lines);
		else if(!e.getText().matches("[0-9]*")){
			 String tagged = tagger.tagString(e.getText());
			 String [] words=tagged.split(" ");
			 String temp="";
			 for(String word:words){
				 if(word.endsWith("NN")||word.endsWith("NNP")||word.endsWith("NNPS")||word.endsWith("NNS"))
				 	temp=temp+" "+word.substring(0, word.lastIndexOf("_"));
			 }
			 lines.add(temp);
		}
		/*if(list.size()<1){
			lines.add("user");
			System.out.println("null user");
		}*/
			
	}
	public  void readXMLLines(String file, ArrayList<String> lines) {
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = null;
		String line = null;
		try{
			document = reader.read(file);
			PathConfig.Count++;
			System.out.println(PathConfig.Count+":reading successfully!");
		}catch (Exception e) {
			e.printStackTrace();
		}
		Element root=document.getRootElement();
		addXml(root,lines);
		
	}
	/*public static void main(String[] args) {
		FileUtil_readXML r=new FileUtil_readXML();
		String file="H:/rerank data and code/data/userData/userProfile/57.xml";
		ArrayList<String> lines=new ArrayList<String>();
		r.readXMLLines(file, lines);
		for(String s : lines)
			System.out.println(s);
	}*/

}