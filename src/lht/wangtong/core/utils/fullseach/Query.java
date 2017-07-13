package lht.wangtong.core.utils.fullseach;

import java.util.*;


public class Query {
    private QueryFilter filter = null;
    private Set<String> codes = new HashSet<String>();
    private Set<UUID> authMemIds = new HashSet<UUID>();
    private boolean hasTitle = true;	//和标题匹配
    private boolean hasContent = true;	//和内容匹配
    private boolean hasRelatedText = true;	//和文本匹配
    private boolean hasRelatedFile = true;	//和文件匹配
    private Set<String> findKeys = new HashSet<String>();	//关键字集合
    private Date startDate = null;	//开始时间
    private Date endDate = null;	//结束时间
    private boolean andOperator = true;	//多个关键字时是否使用and操作

    public QueryFilter getFilter() {
        return this.filter;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Query setDate(Date startDate, Date endDate) {
        this.endDate = endDate;
        this.startDate = startDate;
        return this;
    }

    public Set<String> getModCode() {
        return Collections.unmodifiableSet(this.codes);
    }

    public Query addModCode(String... codes) {
        Collections.addAll(this.codes, codes);
        return this;
    }

    public Query addModCode(Collection<String> codes) {
        this.codes.addAll(codes);
        return this;
    }

    public Set<UUID> getAuthMemId() {
        return Collections.unmodifiableSet(this.authMemIds);
    }

    public Query addAuthMemId(UUID... ids) {
        Collections.addAll(this.authMemIds, ids);
        return this;
    }

    public Query addAuthMemId(Collection<UUID> ids) {
        this.authMemIds.addAll(ids);
        return this;
    }

    public Set<String> getFindKey() {
        return Collections.unmodifiableSet(this.findKeys);
    }

    public Query addFindKey(String... findKey) {
        Collections.addAll(this.findKeys, findKey);
        return this;
    }

    public Query addFindKey(Collection<String> findKeys) {
        this.findKeys.addAll(findKeys);
        return this;
    }

    public Query setFilter(QueryFilter filter) {
        this.filter = filter;
        return this;
    }

    public boolean hasRelatedFile() {
        return this.hasRelatedFile;
    }

    public Query setHasRelatedFile(boolean hasRelatedFile) {
        this.hasRelatedFile = hasRelatedFile;
        return this;
    }

    public boolean hasTitle() {
        return this.hasTitle;
    }

    public Query setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
        return this;
    }

    public boolean hasContent() {
        return this.hasContent;
    }

    public Query setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
        return this;
    }

    public boolean hasRelatedText() {
        return this.hasRelatedText;
    }

    public Query setHasRelatedText(boolean hasRelatedText) {
        this.hasRelatedText = hasRelatedText;
        return this;
    }

    public boolean isAndOperator() {
        return this.andOperator;
    }

    public Query setAndOperator(boolean andOperator) {
        this.andOperator = andOperator;
        return this;
    }
}
