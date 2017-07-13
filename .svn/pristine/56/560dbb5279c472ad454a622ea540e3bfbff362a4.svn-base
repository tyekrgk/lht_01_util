package lht.wangtong.core.utils.fullseach;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public abstract class Reader {

    /**
     * @param query
     * @param allFlag ：是否查询出主键集合,true->查询,false->不查询
     * @return get(0)->数据集合,get(1)->主键集合
     */
    public abstract List<Object> query(Query query, boolean allFlag);

    int rows = 10;

    public Reader setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getRows() {
        return this.rows;
    }

    int start = 0;

    public Reader setStart(int start) {
        this.start = start;
        return this;
    }

    public int getStart() {
        return this.start;
    }


    protected Set<SortField> sortSet = new LinkedHashSet<SortField>();

    public Reader addSortTitle(boolean asc) {
        SortField sortField = new SortField();
        sortField.sortDest = 2;
        sortField.asc = asc;
        sortSet.add(sortField);
        return this;
    }

    public Reader addSortDataDate(boolean asc) {
        SortField sortField = new SortField();
        sortField.sortDest = 1;
        sortField.asc = asc;
        sortSet.add(sortField);
        return this;
    }

    public Reader addSortField(String field, FieldType type, boolean asc) {
        SortField sortField = new SortField();
        sortField.sortDest = 0;
        sortField.asc = asc;
        sortField.fieldName = field;
        sortField.fieldType = type;
        sortSet.add(sortField);
        return this;
    }

    protected class SortField {

        int sortDest;
        String fieldName;
        FieldType fieldType;
        boolean asc;

        /**
         * 返回排序的目标<br />
         *
         * @return 返回值说明。<br />
         *         0：代表是定制的字段，fieldName fieldType 属性有效 <br />
         *         1：代表是 DataDate 字段，fieldName fieldType 属性无效 <br />
         *         2：代表是 Title 字段，fieldName fieldType 属性无效 <br />
         */
        public int getSortDest() {
            return this.sortDest;
        }

        public String getFieldName() {
            if (this.sortDest == 0) {
                return this.fieldName;
            }
            return null;
        }

        public FieldType getFieldType() {
            if (this.sortDest == 0) {
                return this.fieldType;
            }
            return null;
        }

        public boolean isAsc() {
            return this.asc;
        }
    }
}
