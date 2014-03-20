package com.huaweisymantec.search.solr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
/**
 * 文本文件逐行内容索引
 * 测试中文分词
 * 测试高亮关键词
 * 目前需要人工观察索引最准确
 * @author AA
 *
 */
public class SimpleTextFileIndexTest extends BaseTest {
    
    private static Map<File, List<String>> fileMap = new HashMap<File, List<String>>();
    
    @Test
	public void testMutiTextFileIndex() throws IOException, SolrServerException{
		String text_path=this.getClass().getResource("/index/chinese_index.txt").toString().substring("file:/".length());
		this.readBook(this.getSolrServer(), new File(text_path));
		this.query(this.getSolrServer(), "text:董事长");
		this.query(this.getSolrServer(), "text:编码");
	}
	
	private void query(SolrServer server, String query) throws SolrServerException, IOException {
	    long before = System.currentTimeMillis();
        SolrQuery q = new SolrQuery();
        q.setStart(0);
        q.setRows(10);
        q.setQuery(query);
        q.addHighlightField("content");
        q.setHighlight(true).setHighlightSnippets(3);
        q.addSortField("id", SolrQuery.ORDER.desc);     
        QueryRequest r = new QueryRequest(q);
        r.setMethod(METHOD.POST);
        QueryResponse response = r.process(server);
        SolrDocumentList results = response.getResults();
        long after = System.currentTimeMillis();
        System.out.println("--------time--------:"+(after - before)+"  ---------");
        System.out.println("--------result num----"+ results.getNumFound()+"  ---------");
        
        List<String> highightSnippets = new ArrayList<String>();
        for (SolrDocument doc : results) {   
            String idResult = (String) doc.getFieldValue("id");   
            String nameResult = (String) doc.getFieldValue("name");
            System.out.println("id:" + idResult + ",name:" + nameResult);
            Map<String, List<String>> map = response.getHighlighting().get(idResult);
            if (map != null) {
                for (String key : map.keySet()) {
                    highightSnippets = map.get(key);
                    for (String line : highightSnippets) {
                        System.out.println(line);
                    }
                }
            }
            /*if (response.getHighlighting().get(idResult) != null) {
                highightSnippets = response.getHighlighting().get(idResult).get("ʮ�˴�");
                for (String line : highightSnippets) {
                    System.out.println(line);
                }
            }*/
            //System.out.println("--------"+nameResult +"  ��� "+ idResult + "  ---------");   
        }
	}
	
	private void readBook(SolrServer server, File file) throws IOException, SolrServerException {
	    List<SolrInputDocument> list = new ArrayList<SolrInputDocument>();
	    List<String> fileContent = getFileContent(file);
	  
	    for (String line : fileContent) {
	    	SolrInputDocument doc = new SolrInputDocument();
	        doc.setField("id", UUID.randomUUID());
            doc.setField("name", file.getName());
            doc.setField("content", line);
            list.add(doc);
           
	    }
	    
	    server.add(list);
	   
        server.commit();
	}
	
	private List<String> getFileContent(File file) throws IOException {
	    List<String> list = fileMap.get(file);
	    if (list != null) {
	        return list;
	    }
	    list = new ArrayList<String>();
	    BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while((line = reader.readLine()) != null) {
            if (line.trim().equals("")) {
                continue;
            }
            list.add(line);
        }
        fileMap.put(file, list);
        return list;
	}
	
}
