package lht.wangtong.core.utils.fullseach;

import org.apache.commons.lang3.StringUtils;


public class FieldInfo {
    String field;
    Object value;
    FieldType type;

    FieldInfo(String field, Object value, FieldType type) {
        if (StringUtils.isBlank(field) || type == null) {
            throw new IllegalArgumentException("参数 field 和 type 都不能为空值.");
        }
        this.field = field;
        this.value = value;
        this.type = type;
    }

    public String getField() {
        return this.field;
    }

    public Object getValue() {
        return this.value;
    }

    public FieldType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldInfo that = (FieldInfo) o;

        if (this.value != null ? !this.value.equals(that.value) : that.value != null)
            return false;
        if (this.field != null ? !this.field.equals(that.field) : that.field != null)
            return false;
        if (this.type != null ? !this.type.equals(that.type) : that.type != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.field != null ? this.field.hashCode() : 0;
        result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
        result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
        return result;
    }
}
