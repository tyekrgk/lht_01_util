package lht.wangtong.core.utils.fullseach;


public interface QueryFilter {

    QueryFilter and(QueryFilter query);

    QueryFilter or(QueryFilter query);

    QueryFilter not();

}