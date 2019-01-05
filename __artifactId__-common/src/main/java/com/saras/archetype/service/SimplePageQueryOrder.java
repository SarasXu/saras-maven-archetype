package com.saras.archetype.service;

/**
 * description:
 * saras_xu@163.com 2017-12-19 08:38 创建
 */
public class SimplePageQueryOrder<Entity> extends BasePageOrder {
    private static final long serialVersionUID = -591846766783549230L;
    /**
     * 查询实体类
     */
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
