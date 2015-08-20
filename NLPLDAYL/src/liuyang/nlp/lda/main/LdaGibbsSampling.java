package liuyang.nlp.lda.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import liuyang.nlp.lda.com.FileUtil;
import liuyang.nlp.lda.conf.ConstantConfig;
import liuyang.nlp.lda.conf.PathConfig;

/**Liu Yang's implementation of Gibbs Sampling of LDA
 * @author yangliu
 * @blog http://blog.csdn.net/yangliuy
 * @mail yangliuyx@gmail.com
 */

public class LdaGibbsSampling {
	
	public static class modelparameters {
		float alpha = 0.5f; //usual value is 50 / K
		float beta = 0.1f;//usual value is 0.1
		int topicNum = 50;
		int iteration = 500;
		int saveStep = 100;
		int beginSaveIters = 400;
	}
	
	/**Get parameters from configuring file. If the 
	 * configuring file has value in it, use the value.
	 * Else the default value in program will be used
	 * @param ldaparameters
	 * @param parameterFile
	 * @return void
	 */
	private static void getParametersFromFile(modelparameters ldaparameters,
			String parameterFile) {
		// TODO Auto-generated method stub
		ArrayList<String> paramLines = new ArrayList<String>();
		FileUtil.readLines(parameterFile, paramLines);
		for(String line : paramLines){
			String[] lineParts = line.split("\t");
			switch(parameters.valueOf(lineParts[0])){
			case alpha:
				ldaparameters.alpha = Float.valueOf(lineParts[1]);
				break;
			case beta:
				ldaparameters.beta = Float.valueOf(lineParts[1]);
				break;
			case topicNum:
				ldaparameters.topicNum = Integer.valueOf(lineParts[1]);
				break;
			case iteration:
				ldaparameters.iteration = Integer.valueOf(lineParts[1]);
				break;
			case saveStep:
				ldaparameters.saveStep = Integer.valueOf(lineParts[1]);
				break;
			case beginSaveIters:
				ldaparameters.beginSaveIters = Integer.valueOf(lineParts[1]);
				break;
			}
		}
	}
	
	public enum parameters{
		alpha, beta, topicNum, iteration, saveStep, beginSaveIters;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public  void main(List<String> list) throws IOException {
		// TODO Auto-generated method stub
		
		//String originalDocsPath = PathConfig.ldaDocsPath;
		String resultPath = PathConfig.LdaResultsPath;
		String parameterFile= ConstantConfig.LDAPARAMETERFILE;
		Boolean isXMLFile=true;
		System.out.println(list.size());
		modelparameters ldaparameters = new modelparameters();
		getParametersFromFile(ldaparameters, parameterFile);
		Documents docSet = new Documents();
		docSet.readDocs_my(list,isXMLFile);
		//docSet.readDocs(UserPath, isXMLFile);
		System.out.println("wordMap size " + docSet.termToIndexMap.size());
		FileUtil.mkdir(new File(resultPath));
		LdaModel model = new LdaModel(ldaparameters);
		System.out.println("1 Initialize the model ...");
		model.initializeModel(docSet);
		System.out.println("2 Learning and Saving the model ...");
		model.inferenceModel(docSet);
		System.out.println("3 Output the final model ...");
		model.saveIteratedModel(ldaparameters.iteration, docSet);
		System.out.println("Done!");
	}
	/*public static void main(String args[]) throws IOException{
		FirstRankList fr=new FirstRankList();
		List<String[]> list=fr.getList(Variable_2.FirstRank_n,Variable_2.CORPUS);		
		int i=0;
		while(i<list.size()){
			List<String> li=new ArrayList<String>();
			String userPath=Variable_2.Usercorpus+list.get(i)[0]+".xml";
			if((new File(userPath)).exists())
				li.add(userPath);
			else li.add("user");
			for(int j=0;j<100;j++)
				li.add(list.get(i+j)[1]);
			
			LdaGibbsSampling gibbs=new LdaGibbsSampling();
			System.out.println("dd");
			gibbs.main(li);
			i=i+100;
		}
	}*/
}
