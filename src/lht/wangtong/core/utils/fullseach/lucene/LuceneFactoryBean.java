package lht.wangtong.core.utils.fullseach.lucene;


import lht.wangtong.core.utils.fullseach.FieldType;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;


public class LuceneFactoryBean implements lht.wangtong.core.utils.fullseach.FactoryBean {

    Analyzer analyzer;
    Version version;
    Directory directory;

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    private int rebuildMaxCacheNum = 200000;

    public int getRebuildMaxCacheNum() {
        return rebuildMaxCacheNum;
    }

    public void setRebuildMaxCacheNum(int rebuildMaxCacheNum) {
        if (rebuildMaxCacheNum < 100) return;
        this.rebuildMaxCacheNum = rebuildMaxCacheNum;
    }

    @Override
    public lht.wangtong.core.utils.fullseach.Writer createWriter() {
        return new LuceneWriter(this);
    }

    @Override
    public lht.wangtong.core.utils.fullseach.Reader createReader() {
        return new LuceneReader(this);
    }

    @Override
    public LuceneQueryFilter createFilter(String field, Object value, FieldType type) {
        return new LuceneQueryFilter(field, value, type);
    }

    @Override
    public LuceneQueryFilter createFilter(String field, Object startValue, Object endValue, FieldType type) {
        return new LuceneQueryFilter(field, startValue, endValue, type);
    }    
}
