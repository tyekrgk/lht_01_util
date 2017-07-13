package lht.wangtong.core.utils.fullseach;

import java.io.File;


/**
 * 传统的单列模式,目前仅用于测试使用
 * @author Administrator
 *
 */
public class DefaultFactory {

    static FactoryBean factoryBean = null;
    static String DEFAULT_INDEX_PATH = "C:/fileStorage/lucene";

    public static FactoryBean getFactory() {
        if (factoryBean == null) {
        	try {
        		lht.wangtong.core.utils.fullseach.lucene.LuceneFactoryBean luceneFactoryBean = new lht.wangtong.core.utils.fullseach.lucene.LuceneFactoryBean();
				luceneFactoryBean.setAnalyzer(new org.wltea.analyzer.lucene.IKAnalyzer());	//IK Analyzer 是一个开源的，基于java语言开发的轻量级的中文分词工具包
				luceneFactoryBean.setVersion(org.apache.lucene.util.Version.LUCENE_41);
				luceneFactoryBean.setDirectory(org.apache.lucene.store.FSDirectory.open(new File(DEFAULT_INDEX_PATH)));
				factoryBean = luceneFactoryBean;
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
        return factoryBean;
    }

    public static Writer createWriter() {
        return getFactory().createWriter();
    }

    public static Reader createReader() {
        return getFactory().createReader();
    }

    public static QueryFilter createFilter(String field, Object value, FieldType type) {
        return getFactory().createFilter(field, value, type);
    }

    public static QueryFilter createFilter(String field, Object startValue, Object endValue, FieldType type) {
        return getFactory().createFilter(field, startValue, endValue, type);
    }

}
