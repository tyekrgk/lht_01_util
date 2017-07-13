package lht.wangtong.core.utils.fullseach.lucene;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lht.wangtong.core.utils.fullseach.Reader;
import lht.wangtong.core.utils.fullseach.SerializationUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;


public class LuceneReader extends lht.wangtong.core.utils.fullseach.Reader {
	
	public static IndexReader reader = null;

    LuceneFactoryBean luceneFactoryBean = null;

    LuceneReader(LuceneFactoryBean luceneFactoryBean) {
        this.luceneFactoryBean = luceneFactoryBean;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Object> query(lht.wangtong.core.utils.fullseach.Query query, boolean allFlag) {
        Query mainQuery = Utils.getLuceneQuery(query);	//得到组合查询对象
        try {
        	//IndexReader是一个线程安全的对象，跟索引目录是一一对应，实例化IndexReader很耗资源，通常搜索时同一个索引目录只需要实例化一个IndexReader即可
        	if(reader == null) {
        		//第一次调用
        		reader = DirectoryReader.open(this.luceneFactoryBean.getDirectory());
        	} else {
        		//不是第一次调用
        		try {
        			//判断索引是否有变化,如果有变化更新成最新的
					IndexReader rederTemp = DirectoryReader.openIfChanged((DirectoryReader) reader);
					if(rederTemp != null) {
						reader.close();
						reader = rederTemp;
					}
				} catch (Exception e) {
					//重新打开
					reader = DirectoryReader.open(this.luceneFactoryBean.getDirectory());
				}
        	}
            IndexSearcher searcher = new IndexSearcher(reader);
            //TopScoreDocCollector results = TopScoreDocCollector.create(this.getStart() + this.getRows(), false);
            TopDocs tds;
            //组装排序列表
            Set<org.apache.lucene.search.SortField> sortFieldList = new LinkedHashSet<org.apache.lucene.search.SortField>();
            for (Reader.SortField sortField : this.sortSet) {
            	//同一批次写入的DocumentWriter中setDataDate一定要一样,查询的时候会按照dateDate排序
                org.apache.lucene.search.SortField lusf;
                //0：代表是定制的字段，fieldName fieldType 属性有效 <br />
                //1：代表是 DataDate 字段，fieldName fieldType 属性无效 <br />
                //2：代表是 Title 字段，fieldName fieldType 属性无效 <br />
                switch (sortField.getSortDest()) {
                    case 0:
                        lusf = new org.apache.lucene.search.SortField(
                                Utils.getExpandFieldName(sortField.getFieldName(), sortField.getFieldType()),
                                Utils.getSortFieldType(sortField.getFieldType()),
                                !sortField.isAsc());
                        break;
                    case 1:
                        lusf = new org.apache.lucene.search.SortField(
                                FieldName.DATA_DATE,
                                org.apache.lucene.search.SortField.Type.LONG,
                                !sortField.isAsc());
                        break;
                    case 2:
                        lusf = new org.apache.lucene.search.SortField(
                                FieldName.TITLE,
                                org.apache.lucene.search.SortField.Type.STRING,
                                !sortField.isAsc());
                        break;
                    default:
                        continue;
                }
                sortFieldList.add(lusf);
            }
            //进行排序操作查询数据
            try {
                if (sortFieldList.isEmpty()) {
                    //tds = searcher.search(mainQuery, this.getStart() + this.getRows());
                	if(allFlag) {
                		//查询所有
                		tds = searcher.search(mainQuery, 10000);
                	} else {
                		//不查询所有
                		tds = searcher.search(mainQuery, this.getStart() + this.getRows());
                	}
                } else {
                	if(allFlag) {
                		//查询所有
                		tds = searcher.search(
                                mainQuery,
                                10000,
                                new org.apache.lucene.search.Sort(sortFieldList.toArray(new org.apache.lucene.search.SortField[sortFieldList.size()])));
                	} else {
                		//不查询所有
                		tds = searcher.search(
		                        mainQuery,
		                        this.getStart() + this.getRows(),
		                        new org.apache.lucene.search.Sort(sortFieldList.toArray(new org.apache.lucene.search.SortField[sortFieldList.size()])));
                	}
                }
            } catch (IOException e) {
                throw new UnsupportedOperationException("查询索引数据发生错误.", e);
            }
            
            //以红色字体标记关键词
            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
            //SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("", "");
            //用于高亮查询,query是Lucene的查询对象Query
            QueryScorer scorer = new QueryScorer(mainQuery);
            //创建一个高亮器,控制前台页面高亮显示的文字
            Highlighter highlighter = new Highlighter(formatter, scorer);
            //设置文本摘要大小
            int fragmentSize = 200;
            Fragmenter fragmenter = new SimpleFragmenter(fragmentSize);
            highlighter.setTextFragmenter(fragmenter);

            ScoreDoc[] scoreDocs = tds.scoreDocs;
            //获取分页显示的数据
            int i = this.getStart();
            int j = 0;
            if((i+this.getRows()) > tds.totalHits) {
            	j = tds.totalHits;
            } else {
            	j = i + this.getRows();
            }
            lht.wangtong.core.utils.fullseach.DocumentList docReaderList = new lht.wangtong.core.utils.fullseach.DocumentList(j);
            //docReaderList.setNumFound(results.getTotalHits());
            docReaderList.setNumFound(tds.totalHits);	//匹配到的记录总数
            docReaderList.setStart(this.getStart());	//分页数据,从哪开始的标记
            for (; i < j; i++) {
                org.apache.lucene.document.Document ldoc;
                try {
                    ldoc = reader.document(scoreDocs[i].doc);
                } catch (IOException e) {
                    throw new UnsupportedOperationException("查询索引数据发生错误.", e);
                }
                IndexableField oField;
                lht.wangtong.core.utils.fullseach.Document dr = new lht.wangtong.core.utils.fullseach.Document();
                dr.setDataId(ldoc.get(FieldName.DATA_ID));
                dr.setModCode(ldoc.get(FieldName.MOD_CODE));
                oField = ldoc.getField(FieldName.DATA_DATE);
                if (oField != null) {
                    dr.setDataDate(new Date(oField.numericValue().longValue()));
                }
                dr.setTitle(ldoc.get(FieldName.TITLE));
                //用于将内容(content)高亮显示
                oField = ldoc.getField(FieldName.CONTENT);
                if (oField != null) {
                    String _content = null;
                    try {
                        _content = highlighter.getBestFragment(this.luceneFactoryBean.getAnalyzer(), FieldName.CONTENT, oField.stringValue());
                    } catch(InvalidTokenOffsetsException ignore) {
                    } catch(IOException  ignore) {
                    }
                    if (StringUtils.isBlank(_content)) {
                        _content = StringUtils.left(oField.stringValue(), fragmentSize);
                    }
                    dr.setContent(_content);
                }

                for (IndexableField of : ldoc.getFields(FieldName.AUTH_MEM_ID)) {
                    dr.addAuthMemId(UUID.fromString(of.stringValue()));
                }

                oField = ldoc.getField(FieldName.PARAM);
                if (oField != null) {
                    try {
                        dr.addParam((new ObjectMapper()).readValue(oField.stringValue(), Map.class));
                    } catch(IOException ignored) {
                    }
                }

                oField = ldoc.getField(FieldName.EXTENDED_DATA);
                if (oField != null) {
                    try {
                        dr.setExtendedData(SerializationUtils.convertToSerializable(oField.binaryValue().bytes));
                    } catch(IOException  ignored) {
                    } catch(ClassNotFoundException ignored) {
                    }
                }

                docReaderList.add(dr);
            }
            if(docReaderList.size() > 0) {
                List<Object> rtn = new ArrayList<Object>();
                rtn.add(docReaderList);
                if(allFlag) {
            		//查询所有
                	//返回匹配到的所有索引的主键
                    List<String> dataIds = new ArrayList<String>();
                    for(int k=0; k<scoreDocs.length; k++) {
                    	String id = reader.document(scoreDocs[k].doc).get(FieldName.DATA_ID);
                    	dataIds.add(id);
                    }
                    rtn.add(dataIds);
                } else {
                	//不查询所有
                }
                return rtn;
            } else {
            	return null;
            }
        } catch (IOException e) {
            throw new UnsupportedOperationException("查询索引数据发生错误.", e);
        }
    }
}
