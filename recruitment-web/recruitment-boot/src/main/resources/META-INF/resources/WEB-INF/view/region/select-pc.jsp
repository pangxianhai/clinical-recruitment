<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<span class="sui-dropdown dropdown-bordered region-select">
    <input type="hidden" name="provinceId" value="${provinceId}"/>
    <input type="hidden" name="cityId" value="${cityId}"/>
    <input type="hidden" name="districtId" value="${districtId}"/>
        <span class="dropdown-inner">
            <a role="button" data-toggle="dropdown" href="javascript:void(0)"
               class="dropdown-toggle">
                <i class="caret"></i>
                <span item="choiceText">${address}</span>
            </a>
            <ul role="menu" aria-labelledby="drop1" class="sui-dropdown-menu">
                <c:forEach var="regionVO" items="${regionVOList}">
                    <c:if test="${not empty regionVO.children}">
                        <li role="presentation" class="dropdown-submenu">
                            <a role="menuitem" tabindex="-1" href="#">
                                <i class="sui-icon icon-angle-right pull-right"></i>${regionVO.regionName}
                            </a>
                            <ul class="sui-dropdown-menu">
                                <c:forEach var="childRegionVO"
                                           items="${regionVO.children}">
                                    <c:if test="${not empty regionVO.children}">
                                        <li role="presentation"
                                            class="dropdown-submenu">
                                             <a role="menuitem" tabindex="-1" href="#">
                                                 <i class="sui-icon icon-angle-right pull-right"></i>${childRegionVO.regionName}
                                             </a>
                                             <ul class="sui-dropdown-menu">
                                                  <c:forEach var="grandChildRegionVO"
                                                             items="${childRegionVO.children}">
                                                      <li role="presentation">
                                                          <a role="menuitem"
                                                             tabindex="-1"
                                                             href="javascript:void(0)"
                                                             item="regionSelectItem"
                                                             regionName="${regionVO.regionName} ${childRegionVO.regionName} ${grandChildRegionVO.regionName}"
                                                             provinceId="${regionVO.regionId}"
                                                             cityId="${childRegionVO.regionId}"
                                                             districtId="${grandChildRegionVO.regionId}">
                                                                  ${grandChildRegionVO.regionName}
                                                          </a>
                                                      </li>
                                                  </c:forEach>
                                             </ul>
                                        </li>
                                    </c:if>
                                    <c:if test="${empty regionVO.children}">
                                        <li role="presentation">
                                            <a role="menuitem" tabindex="-1"
                                               href="javascript:void(0)">${childRegionVO.regionName}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                             </ul>
                        </li>
                    </c:if>
                    <c:if test="${empty regionVO.children}">
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1"
                               href="javascript:void(0)">${regionVO.regionName}</a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </span>
    </span>
</span>