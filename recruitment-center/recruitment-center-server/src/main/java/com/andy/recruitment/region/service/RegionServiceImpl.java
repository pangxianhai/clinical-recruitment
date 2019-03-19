package com.andy.recruitment.region.service;

import com.andy.recruitment.region.mapper.RegionMapper;
import com.andy.recruitment.region.model.Region;
import com.andy.recruitment.region.model.RegionDO;
import com.andy.recruitment.region.util.RegionUtil;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.StringUtil;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地区服务实现
 *
 * @author 庞先海 2018-12-26
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final static ConcurrentMap<Long, Future<Region>> REGION_CACHE_ID = new ConcurrentHashMap<>();

    private final static ConcurrentMap<Long, Future<List<Region>>> PARENT_CACHE = new ConcurrentHashMap<>();

    private final RegionMapper regionMapper;


    @Autowired
    public RegionServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public Region getRegionById(Long regionId) {
        if (null == regionId) {
            return null;
        }
        while (true) {
            Future<Region> cacheRegionFuture = REGION_CACHE_ID.get(regionId);
            if (null == cacheRegionFuture) {
                FutureTask<Region> ft = new FutureTask<>(() -> {
                    RegionDO regionDO = new RegionDO();
                    regionDO.setId(regionId);
                    List<RegionDO> regionDOList = this.regionMapper.select(regionDO);
                    return CollectionUtil.parseOne(regionDOList, RegionUtil::transformRegion);
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
            } catch (ExecutionException | InterruptedException e) {
                return null;
            }
        }
    }

    @Override
    public List<Region> getRegionByParentId(Long parentId) {

        while (true) {
            Future<List<Region>> cacheParentFuture = PARENT_CACHE.get(parentId);
            if (null == cacheParentFuture) {
                FutureTask<List<Region>> ft = new FutureTask<>(() -> {
                    RegionDO regionDO = new RegionDO();
                    regionDO.setParentId(parentId);
                    List<RegionDO> regionDOList = this.regionMapper.select(regionDO);
                    if (CollectionUtil.isEmpty(regionDOList)) {
                        return null;
                    }
                    return RegionUtil.transformRegion(regionDOList);

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
            } catch (ExecutionException | InterruptedException e) {
                return null;
            }
        }
    }

    @Override
    public Region getRegionByParent(Long parentId, String regionName) {
        if (null == parentId || StringUtil.isEmpty(regionName)) {
            return null;
        }
        regionName = regionName.replace("省", "").replace("市", "");
        List<Region> regionList = this.getRegionByParentId(parentId);
        if (CollectionUtil.isEmpty(regionList)) {
            return null;
        }
        for (Region region : regionList) {
            if (regionName.equals(region.getRegionName())) {
                return region;
            }
        }
        return null;
    }

}
