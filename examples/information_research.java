import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
//import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Dictionary;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;

public class information_research extends HttpServlet {
public static String[] suggest=new String[5];
public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	         request.setCharacterEncoding("UTF-8");
	         String query = request.getParameter("name");
			 String id=request.getParameter("page");
			 String pathname = "C:\\Tomcat 6.0\\webapps\\examples\\WEB-INF\\classes\\index\\buffer.txt"; // ����·��������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ����·��
            File filename = new File(pathname); // Ҫ��ȡ����·����input��txt�ļ�
            if (query==null)
			{InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // ����һ������������reader
            BufferedReader br = new BufferedReader(reader); // ����һ�������������ļ�����ת�ɼ������ܶ���������
            query=br.readLine(); }
			else{
				 BufferedWriter out = new BufferedWriter(new FileWriter(pathname));
            out.write(query); // \r\n��Ϊ����
            out.flush(); // �ѻ���������ѹ���ļ�
            out.close(); // �����ǵùر��ļ�
			}
			 if (id==null)
				 id="1";
	         String htmlStr = "";
	         htmlStr+="<html>";
	         htmlStr+="<head><style type=\"text/css\">body{ font-size:24px;}</style>";
			 	 	 htmlStr+="<link href=\"../../search_result.css\" rel=\"stylesheet\" type=\"text/css\" />";
	         htmlStr+="<link href=\"../../bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\" />";
					 htmlStr+="<script src=\"{% static '../js/jquery-2.1.3.min.js' %}\"></script>";
					 htmlStr+="<script src=\"{% static '../js/bootstrap.min.js' %}\"></script>";
	         htmlStr+="</head>";
					 htmlStr+="<div class=\"container\"><div id=\"wrap\"><header id=\"site-header\"><div class=\"row\"><div class=\"col-md-4 col-sm-5 col-xs-8\">";
					 htmlStr+="<div class=\"logo\"><h1><a><b>NEWS</a></h1></div></div><!-- col-md-4 --><div class=\"col-md-8 col-sm-7 col-xs-4\"> <nav class=\"main-nav\" role=\"navigation\">";
					 htmlStr+="<div class=\"navbar-header\"><button type=\"button\" id=\"trigger-overlay\" class=\"navbar-toggle\"><span class=\"ion-navicon\"></span>";
					 htmlStr+="</button></div><div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\"><ul class=\"nav navbar-nav navbar-right\">";
					 htmlStr+="<li class=\"cl-effect-11\"><a data-hover=\"Home\" href = \"/index\">relation graph</a></li></ul></div><!-- /.navbar-collapse --></nav>";
					 htmlStr+="<div id=\"searchBox\"><form action=\"information_research\" method=\"post\"><input  placeholder =\"search for news\" name = \"name\" id=\"keyword\" type=\"text\" />";
					 htmlStr+="<input id=\"search_button\" type=\"submit\" value = \"\" /></form></div></div><!-- col-md-8 --></div></header>";
	        // index();
						htmlStr+="<p>you may need:   ";
			 		String [][]ins=searcher(query);
			 		for(int i=0;i<suggest.length;i++){
				 		if(suggest[i]==null)
							break;
						htmlStr+=suggest[i]+"   ";
				 }
				 htmlStr+="</p>";
				 int sta=(Integer.parseInt(id)-1)*10;
			// htmlStr+=id;
			 //htmlStr+=query;

	        for(int i=sta; i<(10+sta<ins.length?10+sta:ins.length);i++){

	        		 htmlStr+="<a href="+ins[i][1]+">"+ins[i][0]+"</a>";
					 	 	 htmlStr+="<p><font color=\"green\">"+ins[i][3]+"</font></p>";
	        		 htmlStr+="<p>"+ins[i][2]+"</p>";
		   }

			htmlStr+="<div class=\"div-inline\" style=\"text-align:center;\">";
			htmlStr+="<form  name=\"hh\" action=\"information_research\" method=\"post\">";
			htmlStr+="<input class=\"button\" name=\"page\" type=\"submit\" value=\"1\">";
			htmlStr+="<input class=\"button\" name=\"page\" type=\"submit\" value=\"2\">";
			htmlStr+="<input class=\"button\" name=\"page\" type=\"submit\" value=\"3\">";
			htmlStr+="<input class=\"button\" name=\"page\" type=\"submit\" value=\"4\">";
			htmlStr+="<input class=\"button\" name=\"page\" type=\"submit\" value=\"5\">";
			htmlStr+="</form>";
			htmlStr+="</div></div></div>";
			 response.setCharacterEncoding("UTF-8");//���÷���������UTF-8�����������ݵ��ͻ���
	         response.setContentType("text/html;charset=UTF-8");//���ÿͻ�����������UTF-8������������
	         response.getWriter().write(htmlStr);//����htmlStr���������ݵ��ͻ�����������ʾ
	     }

	     public void doPost(HttpServletRequest request, HttpServletResponse response)
	             throws ServletException, IOException {
	         doGet(request, response);
	     }

    static int maxid=640;
		private static IndexWriterConfig getConfig() {
		return new IndexWriterConfig(Version.LUCENE_36, new IKAnalyzer(true));
}
private static String[] getSuggestions(SpellChecker spellchecker, String word, int numSug) throws IOException {
		return spellchecker.suggestSimilar(word, numSug);
}

public static String[] search(String word, int numSug) throws IOException {
		Directory directory1 = FSDirectory.open(new File("C:\\Tomcat 6.0\\webapps\\examples\\WEB-INF\\classes\\newindex"));

		try {
			//Directory directory=FSDirectory.open(new File("C:\\Tomcat 6.0\\webapps\\examples\\WEB-INF\\classes\\index"));
				//2.创建IndexReader
				SpellChecker spellchecker = new SpellChecker(directory1);
			//	String filepath = "C:\\Tomcat 6.0\\webapps\\examples\\WEB-INF\\classes\\index\\dictionary.txt";
			//	spellchecker.indexDictionary(new PlainTextDictionary(new File(filepath)), getConfig(), true);
				return getSuggestions(spellchecker, word, numSug);

		} catch (IOException e) {
				e.printStackTrace();
				return null;
		}
}
public static void dict() throws IOException{
		 Analyzer analyzer = new IKAnalyzer(true);
		 String filepath = "C:\\Tomcat 6.0\\webapps\\examples\\WEB-INF\\classes\\index\\dictionary.txt";
		 InputStreamReader read = new InputStreamReader(new FileInputStream("d:\\homework\\IR\\News1.txt"),"utf-8");
			 BufferedReader bf=new BufferedReader(read);
			 OutputStreamWriter writer=new OutputStreamWriter(new FileOutputStream(filepath));
			 BufferedWriter bw=new BufferedWriter(writer);
			 TokenStream ts=analyzer.tokenStream("",bf);
			 CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
			 HashMap<CharTermAttribute,Integer> hm=new HashMap<CharTermAttribute,Integer>();
			 //遍历分词数据
			 while(ts.incrementToken()){
				 if(hm.containsKey(term))
					 continue;
				 else{
					 bw.write(term+"\n");
					 hm.put(term, 1);
				 }
			 }
			 read.close();
	}
public static void index(){
        IndexWriter writer=null;
        try{
            //1������Directory
            //Directory directory=new RAMDirectory();�������������ڴ���
            Directory directory=FSDirectory.open(new File("C:\\Tomcat 6.0\\webapps\\examples\\WEB-INF\\classes\\index"));//������������Ӳ����
            //2������IndexWriter writer
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36,new StandardAnalyzer(Version.LUCENE_36));

            writer = new IndexWriter(directory,iwc);
            //3������Document����
            Document doc = null;
            //4��ΪDocument����Field
            InputStreamReader read = new InputStreamReader(new FileInputStream("C:\\Tomcat 6.0\\webapps\\examples\\WEB-INF\\classes\\index\\News.txt"),"utf-8");
            BufferedReader bf=new BufferedReader(read);
            String str=null;
            String []ids=new String[maxid];
            String []title=new String [maxid];
            String []content=new String[maxid];
            String []url=new String[maxid];
            int k=0;
            while((str=bf.readLine())!=null){
            	//System.out.println(str);
            	int l=0;
            	int pre=0;
            	for(int i=0;i<str.length();i++){
            		if (str.charAt(i)=='\t'){
            			if (l==0)
            				ids[k]=str.substring(pre, i);
            			else if (l==1)
            				title[k]=str.substring(pre,i);
            			else if (l==2)
            				content[k]=str.substring(pre,i);
            			else if (l==3)
            				url[k]=str.substring(pre,i);
            			pre=i+1;
            			l+=1;
            		}
            	}
            	k+=1;
            }
            for(int i=0;i<maxid;i++){
                doc = new Document();
                doc.add(new Field("id",ids[i],Field.Store.YES,Field.Index.NOT_ANALYZED));
                doc.add(new Field("title",title[i],Field.Store.YES,Field.Index.ANALYZED));
                doc.add(new Field("content",content[i],Field.Store.YES,Field.Index.ANALYZED));
                doc.add(new Field("url",url[i],Field.Store.YES,Field.Index.NOT_ANALYZED));
                //5��ͨ��IndexWriter�����ĵ���������
                writer.addDocument(doc);
            }

        }catch(CorruptIndexException e){
            e.printStackTrace();
        }catch(LockObtainFailedException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(writer!=null) writer.close();
            }catch(CorruptIndexException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
public static String[][] searcher(String sb){
        String [][]b=new String[100][4];
		int k=0;
        try{
        //1.����Directory
        Directory directory=FSDirectory.open(new File("C:\\Tomcat 6.0\\webapps\\examples\\WEB-INF\\classes\\index"));
        //2.����IndexReader
        IndexReader reader = IndexReader.open(directory);
        //3.����IndexReader����IndexSearcher
        IndexSearcher searcher = new IndexSearcher(reader);
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color=\"red\">","</font>");
        //4.����������Query
        // //����parser��ȷ������������
        String[] fields = {  "content", "title" };
       Analyzer analyzer = new IKAnalyzer(true);
       QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_36,fields,analyzer);

        //5.����search�������ҷ���TopDoc
				//Term term = new Term("content", sb );

				//Query query = new WildcardQuery(term);
				suggest=search(sb,5);

					Query query = parser.parse(sb);
          QueryScorer scorer=new QueryScorer(query);
          TopDocs tds = searcher.search(query, 100);
          ScoreDoc[] sds=tds.scoreDocs;
          Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
          Highlighter highlight=new Highlighter(formatter,scorer);
          highlight.setTextFragmenter(fragmenter);
          //6.����TopDocs��ȡScoreDoc����

          for(ScoreDoc sd:sds){
          //7.����search��ScordDoc������ȡ������Document����
              Document d=searcher.doc(sd.doc);
              String value =d.get("content");

              TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(value));
          //8.����Document������ȡ��Ҫ��ֵ
              String str1 = highlight.getBestFragment(tokenStream, d.get("content"));
  			if (str1 == null)
  				continue;
             // System.out.println(d.get("id")+d.get("title")+d.get("content")+d.get("url"));
              b[k][0]=d.get("title");
              b[k][1]=d.get("url");
              b[k][2]=str1;
  			b[k][3]=d.get("keywords");
              k+=1;
          }//�ر�reader

        reader.close();
        } catch(CorruptIndexException e){
            e.printStackTrace();
        } catch(IOException e){
             e.printStackTrace();
         } catch (ParseException e) {
             // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(InvalidTokenOffsetsException e){
			e.printStackTrace();
		}
		String [][]ans=new String[k][3];
	System.arraycopy(b,0,ans,0,k);
	    return ans;
    }

}
