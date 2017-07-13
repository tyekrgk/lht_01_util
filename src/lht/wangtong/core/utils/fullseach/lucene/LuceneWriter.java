package lht.wangtong.core.utils.fullseach.lucene;

import java.io.IOException;
import java.util.Collection;

import lht.wangtong.core.utils.fullseach.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;


public class LuceneWriter implements lht.wangtong.core.utils.fullseach.Writer {
    LuceneFactoryBean luceneFactoryBean = null;

    LuceneWriter(LuceneFactoryBean luceneFactoryBean) {
        this.luceneFactoryBean = luceneFactoryBean;
    }

    @Override
    public void add(lht.wangtong.core.utils.fullseach.DocumentWriter document) {
        IndexWriterConfig iwc = new IndexWriterConfig(this.luceneFactoryBean.getVersion(), this.luceneFactoryBean.getAnalyzer());
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = null;
        try {
        	indexWriter = new IndexWriter(this.luceneFactoryBean.getDirectory(), iwc);
            indexWriter.addDocument(Utils.getDocument(document));
            indexWriter.commit();
        } catch (IOException e) {
        	try {
        		if(indexWriter != null) {
        			indexWriter.rollback();
        		}
			} catch (IOException e1) {
			}
            throw new UnsupportedOperationException("添加索引数据发生错误.", e);
        } finally {
        	try {
        		if(indexWriter != null) {
        			indexWriter.close();
        		}
			} catch (IOException e) {
				throw new UnsupportedOperationException("添加索引数据发生错误.", e);
			}
        }
    }

    @Override
    public void addAll(Collection<lht.wangtong.core.utils.fullseach.DocumentWriter> documents) {
        IndexWriterConfig iwc = new IndexWriterConfig(this.luceneFactoryBean.getVersion(), this.luceneFactoryBean.getAnalyzer());
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = null;
        try {
        	indexWriter = new IndexWriter(this.luceneFactoryBean.getDirectory(), iwc);
            indexWriter.addDocuments(Utils.getDocumentAll(documents));
            indexWriter.commit();
        } catch (IOException e) {
        	try {
        		if(indexWriter != null) {
        			indexWriter.rollback();
        		}
			} catch (IOException e1) {
			}
            throw new UnsupportedOperationException("添加索引数据发生错误.", e);
        } finally {
        	try {
        		if(indexWriter != null) {
        			indexWriter.close();
        		}
			} catch (IOException e) {
				throw new UnsupportedOperationException("添加索引数据发生错误.", e);
			}
        }
    }

    @Override
    public void update(lht.wangtong.core.utils.fullseach.DocumentWriter document) {

        if (StringUtils.isBlank(document.getModCode())) {
            throw new IllegalArgumentException("参数 ModCode 不能为空值.");
        }
        if (StringUtils.isBlank(document.getDataId())) {
            throw new IllegalArgumentException("参数 DataId 不能为空值.");
        }

        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(new TermQuery(new Term(FieldName.MOD_CODE, document.getModCode())), BooleanClause.Occur.MUST);
        booleanQuery.add(new TermQuery(new Term(FieldName.DATA_ID, document.getDataId())), BooleanClause.Occur.MUST);

        try {
        	IndexReader reader = DirectoryReader.open(this.luceneFactoryBean.getDirectory());
            IndexSearcher searcher = new IndexSearcher(reader);
            if (searcher.search(booleanQuery, 1).totalHits == 0) {
                throw new UnsupportedOperationException("没有找到要修改的索引数据.");
            }
        } catch (IOException e) {
            throw new UnsupportedOperationException("查询索引数据发生错误.", e);
        }

        IndexWriterConfig iwc = new IndexWriterConfig(this.luceneFactoryBean.getVersion(), this.luceneFactoryBean.getAnalyzer());
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = null;
        try {
        	indexWriter = new IndexWriter(this.luceneFactoryBean.getDirectory(), iwc);
            indexWriter.deleteDocuments(booleanQuery);	//先删除再新增
            indexWriter.addDocument(Utils.getDocument(document));
            indexWriter.commit();
        } catch (IOException e) {
        	try {
        		if(indexWriter != null) {
        			indexWriter.rollback();
        		}
			} catch (IOException e1) {
			}
            throw new UnsupportedOperationException("添加索引数据发生错误.", e);
        } finally {
        	try {
        		if(indexWriter != null) {
        			indexWriter.close();
        		}
			} catch (IOException e) {
				throw new UnsupportedOperationException("添加索引数据发生错误.", e);
			}
        }

    }

    @Override
    public void delete(String modCode, String dataId) {
        if (StringUtils.isBlank(modCode)) {
            throw new IllegalArgumentException("参数 ModCode 不能为空值.");
        }
        if (StringUtils.isBlank(dataId)) {
            throw new IllegalArgumentException("参数 DataId 不能为空值.");
        }
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(new TermQuery(new Term(FieldName.MOD_CODE, modCode)), BooleanClause.Occur.SHOULD);
        booleanQuery.add(new TermQuery(new Term(FieldName.DATA_ID, dataId)), BooleanClause.Occur.SHOULD);

        IndexWriterConfig iwc = new IndexWriterConfig(this.luceneFactoryBean.getVersion(), this.luceneFactoryBean.getAnalyzer());
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = null;
        try {
        	indexWriter = new IndexWriter(this.luceneFactoryBean.getDirectory(), iwc);
            indexWriter.deleteDocuments(booleanQuery);	//删除
            indexWriter.commit();
        } catch (IOException e) {
        	try {
        		if(indexWriter != null) {
        			indexWriter.rollback();
        		}
			} catch (IOException e1) {
			}
            throw new UnsupportedOperationException("删除索引数据发生错误.", e);
        } finally {
        	try {
        		if(indexWriter != null) {
        			indexWriter.close();
        		}
			} catch (IOException e) {
				throw new UnsupportedOperationException("删除索引数据发生错误.", e);
			}
        }
    }

    @Override
    public void delete(Query query) {
        IndexWriterConfig iwc = new IndexWriterConfig(this.luceneFactoryBean.getVersion(), this.luceneFactoryBean.getAnalyzer());
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = null;
        try {
        	indexWriter = new IndexWriter(this.luceneFactoryBean.getDirectory(), iwc);
            indexWriter.deleteDocuments(Utils.getLuceneQuery(query));	//删除
            indexWriter.commit();
        } catch (IOException e) {
        	try {
        		if(indexWriter != null) {
        			indexWriter.rollback();
        		}
			} catch (IOException e1) {
			}
            throw new UnsupportedOperationException("删除索引数据发生错误.", e);
        } finally {
        	try {
        		if(indexWriter != null) {
        			indexWriter.close();
        		}
			} catch (IOException e) {
				throw new UnsupportedOperationException("删除索引数据发生错误.", e);
			}
        }

    }

    @Override
    public void optimize(int efforts) {
        if (efforts < 1) return;
        IndexWriterConfig iwc = new IndexWriterConfig(this.luceneFactoryBean.getVersion(), this.luceneFactoryBean.getAnalyzer());
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter indexWriter = null;
        try  {
        	indexWriter = new IndexWriter(this.luceneFactoryBean.getDirectory(), iwc);
            indexWriter.forceMerge(efforts);	//索引段合并
            indexWriter.commit();
        } catch (IOException e) {
        	try {
        		if(indexWriter != null) {
        			indexWriter.rollback();
        		}
			} catch (IOException e1) {
			}
        	throw new UnsupportedOperationException("优化索引数据发生错误.", e);
        } finally {
        	try {
        		if(indexWriter != null) {
        			indexWriter.close();
        		}
			} catch (IOException e) {
				throw new UnsupportedOperationException("优化索引数据发生错误.", e);
			}
        }
    }
}