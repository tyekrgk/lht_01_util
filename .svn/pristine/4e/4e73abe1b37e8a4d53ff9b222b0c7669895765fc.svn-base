package lht.wangtong.core.utils.fullseach;

import java.io.File;
import java.util.*;


public class DocumentWriter extends Document {


    private Set<FieldInfo> fieldInfo = new HashSet<FieldInfo>();

    /**
     * 添加一个字段
     *
     * @param field 字段名称
     * @param value 字段内容
     * @param type  字段类型
     */
    public DocumentWriter addField(String field, Object value, FieldType type) {
        this.fieldInfo.add(new FieldInfo(field, value, type));
        return this;
    }

    /**
     * 获取字段的只读集合
     *
     * @return 返回字段的只读集合
     */
    public Set<FieldInfo> getFields() {
        return new HashSet<FieldInfo>(this.fieldInfo);
    }

    Set<String> relatedText = new HashSet<String>();

    /**
     * 添加相关的文本用于检索
     *
     * @param values 文本
     */
    public DocumentWriter addRelatedText(String... values) {
        Collections.addAll(this.relatedText, values);
        return this;
    }

    public DocumentWriter addRelatedText(Collection<String> values) {
        this.relatedText.addAll(values);
        return this;
    }

    /**
     * 获取相关文本的只读集合
     *
     * @return 返回相关文本的只读集合
     */
    public Set<String> getRelatedTexts() {
        return new HashSet<String>(this.relatedText);
    }

    Set<String> relatedFile = new HashSet<String>();

    /**
     * 添加相关的文件用于检索
     *
     * @param paths 文件
     */
    public DocumentWriter addRelatedFile(File... paths) {
        return this.addRelatedFile(Arrays.asList(paths));
    }

    /**
     * 添加相关的文件用于检索
     *
     * @param paths 文件
     */
    public DocumentWriter addRelatedFile(Collection<File> paths) {
        for (File path : paths) {
            if (path.isDirectory()) {
                this.addRelatedFile(Directorys.getFiles(path));
            } else {
                this.addFile(path);
            }
        }
        return this;
    }

    private void addFile(File file) {
        this.relatedFile.add(file.getName() + "\r\n" + Utils.getTextByFile(file));
    }


    /**
     * 获取相关文件的只读集合
     *
     * @return 返回相关文件的只读集合
     */
    public Set<String> getRelatedFileTexts() {
        return new HashSet<String>(this.relatedFile);
    }


}
