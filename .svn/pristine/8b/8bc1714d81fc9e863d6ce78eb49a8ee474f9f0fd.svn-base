package lht.wangtong.core.utils.fullseach;

import java.io.File;

import lht.wangtong.core.utils.fullseach.lucene.LuceneFactoryBean;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


/**
 * 适合被spring容器管理的工厂类
 * indexPath需要传入
 * init-method="init"
 * @author zsc
 */
public class DefaultFactoryBean {

    private FactoryBean factoryBean = null;
    private String indexPath = "C:/fileStorage/lucene";
    private Version version = Version.LUCENE_41;
    private Analyzer analyzer = null;
    
    public DefaultFactoryBean(String indexPath, Version version, Analyzer analyzer) {
		super();
		if(indexPath != null && !"".equals(indexPath.trim())) {
			this.indexPath = indexPath;
		}
		if(version != null) {
			this.version = version;
		}
		if(analyzer == null) {
			analyzer = new StandardAnalyzer(this.version);	//lucene提供的标准分词
		} else {
			this.analyzer = analyzer;
		}
	}

	public void init() {
        if(factoryBean == null) {
        	try {
        		LuceneFactoryBean luceneFactoryBean = new LuceneFactoryBean();
				luceneFactoryBean.setAnalyzer(analyzer);
				luceneFactoryBean.setVersion(version);
				luceneFactoryBean.setDirectory(FSDirectory.open(new File(indexPath)));
				factoryBean = luceneFactoryBean;
			} catch (Exception e) {
				
			}
        }
    }

    public Writer createWriter() {
        return getFactoryBean().createWriter();
    }

    public Reader createReader() {
        return getFactoryBean().createReader();
    }

    public QueryFilter createFilter(String field, Object value, FieldType type) {
        return getFactoryBean().createFilter(field, value, type);
    }

    public QueryFilter createFilter(String field, Object startValue, Object endValue, FieldType type) {
        return getFactoryBean().createFilter(field, startValue, endValue, type);
    }

	public FactoryBean getFactoryBean() {
		return factoryBean;
	}

	public void setFactoryBean(FactoryBean factoryBean) {
		this.factoryBean = factoryBean;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

}