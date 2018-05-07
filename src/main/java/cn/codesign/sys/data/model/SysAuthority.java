package cn.codesign.sys.data.model;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/4/30
 * Time: 16:31
 * Description:
 */
public class SysAuthority {

    private String id;
    private int authorityLevel;
    private String authorityParentId;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(int authorityLevel) {
        this.authorityLevel = authorityLevel;
    }

    public String getAuthorityParentId() {
        return authorityParentId;
    }

    public void setAuthorityParentId(String authorityParentId) {
        this.authorityParentId = authorityParentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
