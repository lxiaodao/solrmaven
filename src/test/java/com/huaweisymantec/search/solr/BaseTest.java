
/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 * 
 * 
 */

package com.huaweisymantec.search.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Assert;


/**
 * Document BaseTest
 * <p />
 * 测试基类，根据需要提供SolrServer。
 * 一种是代码紧密相关的，通常测试的时候使用。
 * 另一种是http协议的，搜索引擎需要独立时使用。
 * @author l90003709
 */
public class BaseTest {
   //默认嵌入式
   protected  SolrServer getSolrServer(){
	
	   return SolrServerHelper.buildSolrServer(SolrServerHelper.SOLR_EMBDDED);
   }
  
    
    public void execute(String name,String sql) {
        SolrServer server = this.getSolrServer();
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
            long after = System.currentTimeMillis();
            List<String> highightSnippets = new ArrayList<String>();
            int number=0;
            for (SolrDocument doc : results) {
                String idResult = (String) doc.getFieldValue("id");
                // String nameResult = (String) doc.getFieldValue("name");
                System.out.println(name+"--------------id:" + idResult);
                number++;
            }
            Assert.assertTrue(number>0);
        } catch (SolrServerException e) {
        	Assert.fail(name);
            e.printStackTrace();
        }

    }
}
