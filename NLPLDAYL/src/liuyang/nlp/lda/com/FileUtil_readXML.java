package liuyang.nlp.lda.com;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import liuyang.nlp.lda.conf.PathConfig;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FileUtil_readXML {
	public void addXml(Element e,ArrayList<String> lines){
		List<Element> list=e.elements();
		if(list.size()>0) 
			for(Element son : list)
				addXml(son,lines);
		else if(!e.getText().matches("[0-9]*"))
			lines.add(e.getText());
		
		
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
		List<Element> books=root.elements();
		addXml(root,lines);
		
	}
/*	public static void main(String[] args) {
		FileUtil_readXML r=new FileUtil_readXML();
		String file="G:/rerank data and code/data/userData/userProfile/57.xml";
		ArrayList<String> lines=new ArrayList<String>();
		r.readXMLLines(file, lines);
		for(String s : lines)
			System.out.println(s);
	}
*/
}





/*public static void readXMLLines(String file, ArrayList<String> lines) {
SAXReader reader = new SAXReader();
org.dom4j.Document document = null;
String line = null;
try{
	document = reader.read(file);
	System.out.println("reading successfully!");
}catch (Exception e) {
	e.printStackTrace();
}
Element topic=document.getRootElement();
List<Element> books=topic.elements();
for(Iterator<Element> it=books.iterator();it.hasNext();){
	Element book=it.next();
	Element tags=book.element("tags");
	Element title=book.element("title");
	Element rating=book.element("rating");
	int i=(int)Float.parseFloat(rating.getText());
	if(i>5)
		lines.add(tags.getText()+" "+tags.getText()+" "+title.getText()+" "+title.getText()+" ");
	else
		lines.add(tags.getText()+" "+title.getText()+" ");			
}*/
