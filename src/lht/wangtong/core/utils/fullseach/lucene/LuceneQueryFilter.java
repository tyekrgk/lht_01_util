package lht.wangtong.core.utils.fullseach.lucene;


import java.util.Date;

import lht.wangtong.core.utils.fullseach.FieldType;
import lht.wangtong.core.utils.fullseach.QueryFilter;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;


public class LuceneQueryFilter implements lht.wangtong.core.utils.fullseach.QueryFilter {
    Query query = null;

    public LuceneQueryFilter(String field, Object value, FieldType type) {
        if (type.equals(FieldType.DATE)) {
            this.query = new TermQuery(new Term(Utils.getExpandFieldName(field, type), String.valueOf(((Date) value).getTime())));
        } else {
            this.query = new TermQuery(new Term(Utils.getExpandFieldName(field, type), String.valueOf(value)));
        }
    }

    public LuceneQueryFilter(String field, Object startValue, Object endValue, FieldType type) {
        switch (type) {
            case DATE:
                this.query = NumericRangeQuery.newLongRange(Utils.getExpandFieldName(field, type),
                        ((Date) startValue).getTime(),
                        ((Date) endValue).getTime(),
                        true,
                        true);
                break;
            case LONG:
                this.query = NumericRangeQuery.newLongRange(Utils.getExpandFieldName(field, type),
                        (Long) startValue,
                        (Long) endValue,
                        true,
                        true);
                break;
            case INT:
                this.query = NumericRangeQuery.newIntRange(Utils.getExpandFieldName(field, type),
                        (Integer) startValue,
                        (Integer) endValue,
                        true,
                        true);
            case FLOAT:
                this.query = NumericRangeQuery.newFloatRange(Utils.getExpandFieldName(field, type),
                        (Float) startValue,
                        (Float) endValue,
                        true,
                        true);
            case DOUBLE:
                this.query = NumericRangeQuery.newDoubleRange(Utils.getExpandFieldName(field, type),
                        (Double) startValue,
                        (Double) endValue,
                        true,
                        true);
                break;
            case STRING:
            case UUID:
            case TEXT:
            case BOOLEAN:
            default:
                this.query = TermRangeQuery.newStringRange(Utils.getExpandFieldName(field, type),
                        String.valueOf(startValue),
                        String.valueOf(endValue),
                        true,
                        true);
                break;
        }
    }

    private QueryFilter add(QueryFilter query, BooleanClause.Occur occur) {
        Query addQuery = ((LuceneQueryFilter) query).query;
        if (this.query instanceof BooleanQuery) {
            ((BooleanQuery) this.query).add(addQuery, occur);
        } else if (addQuery instanceof BooleanQuery) {
            ((BooleanQuery) addQuery).add(this.query, occur);
            this.query = addQuery;
        } else {
            BooleanQuery booleanQuery = new BooleanQuery();
            booleanQuery.add(this.query, occur);
            booleanQuery.add(addQuery, occur);
            this.query = booleanQuery;
        }
        return this;
    }

    @Override
    public QueryFilter and(QueryFilter query) {
        return this.add(query, BooleanClause.Occur.MUST);
    }

    @Override
    public QueryFilter or(QueryFilter query) {
        return this.add(query, BooleanClause.Occur.SHOULD);
    }

    @Override
    public QueryFilter not() {
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(this.query, BooleanClause.Occur.MUST_NOT);
        this.query = booleanQuery;
        return this;
    }
}
