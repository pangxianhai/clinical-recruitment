package com.andy.recruitment.dao.region.dao;

import com.andy.recruitment.dao.region.entity.RegionDO;
import com.andy.recruitment.dao.region.mapper.RegionMapper;
import com.andy.spring.util.CollectionUtil;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地区服务实现
 *
 * @author 庞先海 2018-12-26
 */
@Service
@Slf4j
public class RegionDAOImpl implements RegionDAO {

    private final static ConcurrentMap<Long, Future<RegionDO>> REGION_CACHE_ID = new ConcurrentHashMap<>();

    private final static ConcurrentMap<Long, Future<List<RegionDO>>> PARENT_CACHE = new ConcurrentHashMap<>();

    private final RegionMapper regionMapper;

    @Autowired
    public RegionDAOImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public RegionDO getRegionById(Long regionId) {
        if (null == regionId) {
            return null;
        }
        while (true) {
            Future<RegionDO> cacheRegionFuture = REGION_CACHE_ID.get(regionId);
            if (null == cacheRegionFuture) {
                FutureTask<RegionDO> ft = new FutureTask<>(() -> {
                    RegionDO regionDO = new RegionDO();
                    regionDO.setId(regionId);
                    List<RegionDO> regionDOList = this.regionMapper.select(regionDO);
                    return CollectionUtil.parseOne(regionDOList, t -> t);
                });
                cacheRegionFuture = REGION_CACHE_ID.putIfAbsent(regionId, ft);
                if (null == cacheRegionFuture) {
                    cacheRegionFuture = ft;
                    ft.run();
                }
            }
            try {
                return cacheRegionFuture.get();
            } catch (CancellationException e) {
                REGION_CACHE_ID.remove(regionId, cacheRegionFuture);
                log.error("getRegionById error", e);
            } catch (ExecutionException | InterruptedException e) {
                log.error("getRegionById error", e);
                return null;
            }
        }
    }

    @Override
    public List<RegionDO> getRegionByParentId(Long parentId) {
        while (true) {
            Future<List<RegionDO>> cacheParentFuture = PARENT_CACHE.get(parentId);
            if (null == cacheParentFuture) {
                FutureTask<List<RegionDO>> ft = new FutureTask<>(() -> {
                    RegionDO regionDO = new RegionDO();
                    regionDO.setParentId(parentId);
                    return this.regionMapper.select(regionDO);
                });
                cacheParentFuture = PARENT_CACHE.putIfAbsent(parentId, ft);
                if (null == cacheParentFuture) {
                    cacheParentFuture = ft;
                    ft.run();
                }
            }
            try {
                return cacheParentFuture.get();
            } catch (CancellationException e) {
                PARENT_CACHE.remove(parentId, cacheParentFuture);
                log.error("getRegionByParentId error", e);
            } catch (ExecutionException | InterruptedException e) {
                log.error("getRegionByParentId error", e);
                return null;
            }
        }
    }

    @Override
    public RegionDO getRegionByParent(Long parentId, String regionName) {
        if (null == parentId || StringUtils.isEmpty(regionName)) {
            return null;
        }
        regionName = regionName.replace("省", "").replace("市", "");
        List<RegionDO> regionList = this.getRegionByParentId(parentId);
        if (CollectionUtils.isEmpty(regionList)) {
            return null;
        }
        for (RegionDO region : regionList) {
            if (regionName.equals(region.getRegionName())) {
                return region;
            }
        }
        return null;
    }

}
