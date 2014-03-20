(1)Lucene索引分析和查看工具
   可执行文件lukeall-4.0.0-ALPHA.jar
(2)核心配置文件
   src/main/resource/collection1/schema.xml和solrconfig.xml
(3)中文分词器配置IKAnalyzer
   以后如果需要可以进一步优化
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
(4)工程索引数据存放位置
   src/main/resource/collection1/data
(5)自定义POM节点
    <dependency>
    	<groupId>org.wltea.analyzer</groupId>
    	<artifactId>IKAnalyzer</artifactId>
    	<version>2012FF_u1</version>
    	
     </dependency>
(6)部署apache-solr-4.0.0.war时，solr核心配置文件存放位置

拷贝源代码目录放到到E:\business\apache-tomcat-7.0.34\bin下面
单实例solr，拷贝E:\business\apache-solr-4.0.0\example\solr整个目录到上面tomcat classpath
多实例solr，拷贝E:\business\apache-solr-4.0.0\example\multicore整个目录到上面tomcat classpath

   
