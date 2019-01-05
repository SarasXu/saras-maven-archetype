package com.saras.archetype.hepler;


import com.saras.archetype.base.Base;

/**
 * @author shaoweihong
 * @apiNote 登录菜单
 */
public class SessionMenu extends Base {
    private static final long serialVersionUID = -550619591518897187L;
    /**
     * ID
     */
    private String id;
    /**
     * 上级ID
     */
    private String pid;
    /**
     * 名称(en)
     */
    private String name;
    /**
     * 菜单标题(cn)
     */
    private String title;
    /**
     * 图标,设置一级菜单即可
     */
    private String icon;
    /**
     * 跳转路径
     */
    private String jump;
    /**
     * 是否展开,默认展开第一个
     */
    private boolean spread = false;

    public SessionMenu() {

    }

    public SessionMenu(String id, String pid, String name, String title, String icon, String jump, boolean spread) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.title = title;
        this.icon = icon;
        this.jump = jump;
        this.spread = spread;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getJump() {
        return jump;
    }

    public void setJump(String jump) {
        this.jump = jump;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }
}
