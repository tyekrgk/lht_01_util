package lht.wangtong.core.utils.fullseach;

public interface FactoryBean {
    Writer createWriter();

    Reader createReader();

    QueryFilter createFilter(String field, Object value, FieldType type);

    QueryFilter createFilter(String field, Object startValue, Object endValue, FieldType type);

}
