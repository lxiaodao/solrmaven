/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 * 
 * 
 */

package com.huaweisymantec.search.solr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;

/**
 * 
 * Document SolrServerHelper 搜索引擎服务器
 * <p />
 * 
 * @author l90003768
 */
public final class SolrServerHelper {
    
	//todo: add this in a property file
    public final static String SOLR_CONFIG_PATH = "E:\\business\\eclipse\\workspace\\solrmaven\\src\\main\\resources";
   

    /**
     * 搜索引擎服务器,嵌入式
     */
    private static SolrServer solrServer = null;

    /**
     * solr核心容器
     */
    private static CoreContainer coreContainer = null;

    /**
     * 锁
     */
    private static final Lock lock = new ReentrantLock();

  
    private SolrServerHelper() {

    }

    /**
     * 关闭服务
     */
    private static void close() {
        if (null != coreContainer) {
            coreContainer.shutdown();
        }
    }
    public static final int SOLR_EMBDDED = 1;
    public static final int SOLR_HTTPED = 2;
    
    public static SolrServer buildSolrServer(int type) {    
        return (type == SOLR_EMBDDED) ? SolrServerHelper.initEmbdSolrServer(): SolrServerHelper.initHttpSolrServer("");
    }
    public static SolrServer buildSolrServer(String url) {    
        return initHttpSolrServer(url);
    }
  
    /**
     * 请注意，每次返回新的实例
     * @param url 为空的时候，返回默认的相同的HttpSolrServer实例。
     * @return
     * @throws MalformedURLException 
     */
    private static SolrServer initHttpSolrServer(String url) {
        boolean isBlank=url==null||url.equals("");
        String default_url = "http://localhost:8000/solr";
	    return new HttpSolrServer(isBlank?default_url:url);
	
    }


    /**
     * 得到SolrServer实例
     * 
     * @return
     */
    private static SolrServer initEmbdSolrServer() {
        if (null == solrServer) {
            lock.lock();
            {
                if (null == solrServer) {
                    System.setProperty("solr.solr.home", SOLR_CONFIG_PATH);
                    CoreContainer.Initializer initializer = new CoreContainer.Initializer();
                    //String solrconfig=SolrServerHelper.class.getResource("/conf/solrconfig.xml").toString().substring("file:/".length());
                   // initializer.setSolrConfigFilename(solrconfig);
                    try {
                        coreContainer = initializer.initialize();
                        coreContainer.setPersistent(true);
                        solrServer = new EmbeddedSolrServer(coreContainer, "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }

                }
            }
            lock.unlock();
        }
        return solrServer;
    }
}
