/**
 * 
 */
package com.huaweisymantec.search.solr;

import org.junit.Test;


/**
 * @author AA
 *
 */
public class LoadSolrConfigPathTest {
	@Test
    public void testPath(){
    	 ClassLoader cload = Thread.currentThread().getContextClassLoader();    
      
         //-----------------------优先从classpath加载，然后从lib的jar包加载--------------------------//
         System.out.println("-------------getClassLoader加载classpath----------------"+LoadSolrConfigPathTest.class.getResource("/conf/solrconfig.xml"));         
         System.out.println("-------------getContextClassLoader加载classpath----------------"+cload.getResource("conf/solrconfig.xml"));           
         System.out.println("-------------System classpath----------------"+LoadSolrConfigPathTest.class.getClassLoader().getResource("conf/solrconfig.xml"));
         
        
    }
}
