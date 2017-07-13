package lht.wangtong.core.utils.fullseach;

import java.io.Serializable;
import java.util.*;


public class Document {
    protected String modCode;	//模块编码(枚举类)
    protected String dataId;	//业务数据主键
    protected Set<UUID> authMemId = new HashSet<UUID>();	//权限控制
    protected String title;	//索引标题
    protected String content;	//索引内容
    protected Date dateDate;	//创建索引时间
    protected Map<String, String> param = new HashMap<String, String>();	//关键字
    protected Serializable extendedData;	//可序列化对象

    /**
     * 获取数据发生的时间
     *
     * @return 返回数据发生的时间
     */
    public Date getDateDate() {
        return this.dateDate;
    }

    /**
     * 获取数据所在的模块编码
     *
     * @return 返回数据所在的模块编码
     */
    public String getModCode() {
        return this.modCode;
    }

    /**
     * 获取数据的编号
     *
     * @return 返回数据的编号
     */
    public String getDataId() {
        return this.dataId;
    }


    /**
     * 获取有权限读取数据的成员编号只读集合
     *
     * @return 返回一个存有成员编号的只读集合
     */
    public Set<UUID> getAuthMemId() {
        return Collections.unmodifiableSet(this.authMemId);
    }

    public Document addAuthMemId(UUID... memIds) {
        Collections.addAll(this.authMemId, memIds);
        return this;
    }

    public Document addAuthMemId(Collection<UUID> memIds) {
        this.authMemId.addAll(memIds);
        return this;
    }


    /**
     * 获取标题
     *
     * @return 返回标题
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 获取内容
     *
     * @return 返回内容
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 获取参数名和值的只读集合
     *
     * @return 返回参数名和值的只读集合
     */
    public Map<String, String> getParam() {
        return Collections.unmodifiableMap(this.param);
    }

    /**
     * 添加一个参数
     *
     * @param key   参数名
     * @param value 参数值
     */
    public Document addParam(String key, String value) {
        this.param.put(key, value);
        return this;
    }

    /**
     * 添加参数
     *
     * @param params 参数集合
     */
    public Document addParam(Map<String, String> params) {
        this.param.putAll(params);
        return this;
    }

    /**
     * 设置数据发生的时间
     *
     * @param date 数据发生的时间
     */
    public Document setDataDate(Date date) {
        this.dateDate = date;
        return this;
    }


    /**
     * 设置数据的模块编码
     *
     * @param modCode 数据的模块编码
     */
    public Document setModCode(String modCode) {
        this.modCode = modCode;
        return this;
    }


    /**
     * 设置数据的编号
     *
     * @param dataId 返回数据的编号
     */
    public Document setDataId(String dataId) {
        this.dataId = dataId;
        return this;
    }


    /**
     * 设置标题
     *
     * @param title 标题
     */
    public Document setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public Document setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 获取扩展数据
     *
     * @return 返回扩展数据
     */
    public Serializable getExtendedData() {
        return extendedData;
    }

    /**
     * 设置文档的扩展数据
     *
     * @param extendedData 扩展的数据
     */
    public void setExtendedData(Serializable extendedData) {
        this.extendedData = extendedData;
    }

//    Long version = null;
//
//    public Long getVersion() {
//        return version;
//    }
//
//    public void setVersion(Long version) {
//        this.version = version;
//    }
}