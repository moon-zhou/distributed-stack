package org.moonzhou.transaction.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public abstract class BaseService<M extends BaseMapper<T>, T> {
    @Autowired
    protected M mapper;

    protected int saveEntity(T entity) {
        return mapper.insert(entity);
    }

    protected int updateEntityById(T entity) {
        return mapper.updateById(entity);
    }

    protected int saveOrUpdateEntity(T entity) {
        Class<?> cls = entity.getClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!", new Object[0]);
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!", new Object[0]);
        Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
        if (!StringUtils.checkValNull(idVal) && !Objects.isNull(this.getEntityById((Serializable)idVal))) {
            return this.updateEntityById(entity);
        } else {
            return this.saveEntity(entity);
        }
    }

    protected int deleteEntity(Serializable id) {
        return mapper.deleteById(id);
    }

    public boolean deleteEntitiesByIds(Collection<? extends Serializable> idList) {
        return SqlHelper.retBool(mapper.deleteBatchIds(idList));
    }

    protected Optional<T> getEntityById(Serializable id) {
        T obj = mapper.selectById(id);
        if (obj == null) {
            return Optional.empty();
        }
        return Optional.of(obj);
    }

    protected LambdaQueryChainWrapper<T> lambdaQuery() {
        return new LambdaQueryChainWrapper<>(this.mapper);
    }

    protected LambdaUpdateChainWrapper<T> lambdaUpdate() {
        return new LambdaUpdateChainWrapper<>(this.mapper);
    }

    protected QueryChainWrapper<T> query() {
        return new QueryChainWrapper<>(this.mapper);
    }
}