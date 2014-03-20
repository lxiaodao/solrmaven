
package com.huaweisymantec.search.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
 * 简单字符串，数字，日期建立索引
 * 测试查询
 * @author AA
 *
 */
public class SolrSimpleIndexTest extends BaseTest{
  
	@Test
    public void testMutiValue(){
        
        try {
           
            SolrServer server =this.getSolrServer();
            SolrInputDocument doc1 = new SolrInputDocument();
            doc1.addField("id", UUID.randomUUID().toString());
            doc1.addField("ArticleTitle", "double多值");           
           
            
            doc1.addField("nodevalue_s", 12.04);
            doc1.addField("nodevalue_s", "animal");
            doc1.addField("nodevalue_s", new Date());
            doc1.addField("nodevalue_s", true);
            
        
            //
            Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
            docs.add(doc1);
          

            server.add(docs);
            server.commit();
            //[ 2010-01-15 TO 2010-01-24]
            String sql="nodevalue_s:12.04";
            SolrQuery q = new SolrQuery();
            q.setStart(0);
            q.setRows(10);
            
            q.setQuery(sql);
            q.addHighlightField("content");
            q.setHighlight(true).setHighlightSnippets(3);
            q.addSortField("id", SolrQuery.ORDER.desc);
            QueryRequest r = new QueryRequest(q);
            r.setMethod(METHOD.POST);
            QueryResponse response;
            try {
                response = r.process(server);
                SolrDocumentList results = response.getResults();
                
                for (SolrDocument doc : results) {
                    String idResult = (String) doc.getFieldValue("id");
                    // String nameResult = (String) doc.getFieldValue("name");
                    System.out.println("testMutiValue--------------id:" + idResult);

                }
            } catch (SolrServerException e) {
               
                e.printStackTrace();
            }

        } catch (IOException e) {
            
            e.printStackTrace();
        
        } catch (SolrServerException e) {
           
            e.printStackTrace();
        }
    }

	@Test
	public void testString() {
		String sql2 = "nodevalue_s:[12 TO 13]";

		execute("testString", sql2);
	}

	@Test
	public void testBoolean() {
		String sql2 = "nodevalue_s:true";

		execute("testBoolean", sql2);
	}
	@Test
	public void testDateRange() {
		
		Date adate=new Date();
		adate.setMonth(adate.getMonth()+1);
		
		String sql2 = "nodevalue_s:[ 2010-01-05T00:00:00Z TO "+DateUtil.format(adate, DateUtil.TIME_ZONE)+"]";
		execute("testDateRange", sql2);
	}
    /**
     * 需要查看索引数据，进一步确定某个字段的值没有被索引。
     * 暂时没有使用
     */
    public void testSimpleIndex() {
       
        try {
           
            SolrServer server =this.getSolrServer();
            SolrInputDocument doc1 = new SolrInputDocument();
            doc1.addField("id", UUID.randomUUID().toString());
            doc1.addField("ArticleTitle", "北京2008年奥运会");           
            doc1.addField("title_s", "11");
            
            doc1.addField("Author_s", "22");
            doc1.addField("ArticleText2", "石榴，苹果");
            
            //
            SolrInputDocument doc2 = new SolrInputDocument();
            doc2.addField("id", UUID.randomUUID().toString());
            doc2.addField("ArticleTitle", "电影");          
            doc2.addField("ArticleText", "方便面"); //索引不存储
            doc2.addField("content", "This is a good thing,computer is tool for us.");
            doc2.addField("ArticleText2", "核桃"); //存储 不索引
            //
            Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
            docs.add(doc1);
            docs.add(doc2);

            server.add(docs);
            
            server.commit();           
           

        } catch (IOException e) {
           
            e.printStackTrace();
        
        } catch (SolrServerException e) {
            
            e.printStackTrace();
        }

    }
}
