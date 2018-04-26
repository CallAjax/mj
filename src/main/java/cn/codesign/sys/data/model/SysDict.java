package cn.codesign.sys.data.model;

import java.io.Serializable;

public class SysDict implements Serializable {
    private String id;

    private String parentId;

    private Integer dictLevel;

    private String dictName;

    private String dictValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getDictLevel() {
        return dictLevel;
    }

    public void setDictLevel(Integer dictLevel) {
        this.dictLevel = dictLevel;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }
}