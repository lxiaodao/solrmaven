solrmaven
=========

(1)Lucene���������Ͳ鿴����
   ��ִ���ļ�lukeall-4.0.0-ALPHA.jar
(2)���������ļ�
   src/main/resource/collection1/schema.xml��solrconfig.xml
(3)���ķִ�������IKAnalyzer
   �Ժ������Ҫ���Խ�һ���Ż�
    <!-- 2013-01-07 cmmit by liuyang
    <fieldType name="text_general" class="solr.TextField" positionIncrementGap="100">
      <analyzer type="index">
	    
        <tokenizer class="solr.StandardTokenizerFactory"/>

        
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" enablePositionIncrements="true" />
       
        <filter class="solr.SynonymFilterFactory" synonyms="index_synonyms.txt" ignoreCase="true" expand="false"/>
       
        <filter class="solr.LowerCaseFilterFactory"/>
      </analyzer>
      <analyzer type="query">
	   
        <tokenizer class="solr.StandardTokenizerFactory"/>
		
		
        <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" enablePositionIncrements="true" />
        <filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>
        <filter class="solr.LowerCaseFilterFactory"/>
      </analyzer>
    </fieldType>
	-->


	<fieldType name="text_general" class="solr.TextField"> 
	   <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/> 
	</fieldType>
(4)�����������ݴ��λ��
   src/main/resource/collection1/data
(5)�Զ���POM�ڵ�
    <dependency>
    	<groupId>org.wltea.analyzer</groupId>
    	<artifactId>IKAnalyzer</artifactId>
    	<version>2012FF_u1</version>
    	
     </dependency>
(6)����apache-solr-4.0.0.warʱ��solr���������ļ����λ��

����Դ����Ŀ¼�ŵ���E:\business\apache-tomcat-7.0.34\bin����
��ʵ��solr������E:\business\apache-solr-4.0.0\example\solr����Ŀ¼������tomcat classpath
��ʵ��solr������E:\business\apache-solr-4.0.0\example\multicore����Ŀ¼������tomcat classpath

   

