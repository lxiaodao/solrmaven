
package com.huaweisymantec.search.solr;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.junit.Test;
/**
 * PDF文档上传后全文索引测试
 * @author AA
 *
 */
public class PdfContentParserIndexStreamTest extends BaseTest {

    @Test
    public void testEmbeddedIndexFile(){
        indexContentStream();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.execute("testEmbeddedIndexFile", "number_ti:[2 TO 3]");       
    }
    @Test
    public void testChineseWordSegmentation(){
    	// word segmentationtext:算法
    	//text:方法
    	 this.execute("testChineseWordSegmentation", "text:方法");
    }
    @Test
    public void testDateRangQuery(){
        //1976-03-06T23:59:59.999Z
    
        this.execute("testDateRangQuery", "PdfTestDate_dt:[2010-01-05T00:00:00Z TO 2010-01-07T00:00:00Z]");
         
    }
    @Test
    public void testNumberRangQuery(){
        
        this.execute("testNumberRangQuery", "testnumber:[4 TO 5] ");
    }
    @Test
    //日期查询，转换成long数字
    public void testLongNumberRangQuery(){
      long time1=DateUtil.parseDate("2010-01-02").getTime();
       long time2=DateUtil.parseDate("2010-01-06").getTime();
        
        this.execute("testLongNumberRangQuery", "timelong_tl:["+time1+" TO "+time2+"]");
    }
  
    private void indexContentStream() {
    	String test_ik_pdf=this.getClass().getResource("/customer/test-ik-chinese.pdf").toString().substring("file:/".length());
        ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
        try {
            up.addFile(new File(test_ik_pdf),"application/octet-stream");
             
            up.setParam("literal.id", UUID.randomUUID().toString());          
           
            up.setParam("literal.PdfTestDate_dt", "2010-01-05T00:00:00Z");
            up.setParam("literal.number_ti", "2");
           
            up.setParam("literal.testnumber", "3.5");
         
            up.setParam("literal.timelong_tl", DateUtil.parseDate("2010-01-02").getTime()+"");
            
            
            up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            SolrServer server= getSolrServer();
            //server.add(doc);
            server.request(up);
            //server.commit();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
