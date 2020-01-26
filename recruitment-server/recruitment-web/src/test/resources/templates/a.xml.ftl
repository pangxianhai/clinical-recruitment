<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">


    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
        <#list table.fields as field>
            <#if field.keyFlag><#--生成主键排在第一位-->
                <id column="${field.name}" property="${field.propertyName}" jdbcType="BIGINT"/>
            </#if>
        </#list>
        <#list table.commonFields as field><#--生成公共字段 -->
            <result column="${field.name}" property="${field.propertyName}" jdbcType="VARCHAR"/>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag><#--生成普通字段 -->
                <result column="${field.name}" property="${field.propertyName}" jdbcType="VARCHAR"/>
            </#if>
        </#list>
    </resultMap>


    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        <#list table.commonFields as field>
            ${field.name},
        </#list>
        ${table.fieldNames}
    </sql>

    <select id="select" resultMap="BaseResultMap" parameterType="${package.Entity}.${entity}">
        select
        <include refid="Base_Column_List"/>
        from ${table.name}
        <where>
        </where>
    </select>

    <insert id="insert" useGeneratedKeys="true" keyProperty="id"
            parameterType="">
        insert into ${table.name} (
        <include refid="Base_Column_List"/>
        )
        values(
        <#list table.fields as field>
            <#noparse>#{</#noparse>${field.propertyName}, jdbcType=<#noparse>}</#noparse>
        </#list>
        )
    </insert>

    <update id="update" parameterType="${package.Entity}.${entity}">
        update ${table.name}
        <set>
            <#list table.fields as field>
                <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                    ${field.name}=<#noparse>#{</#noparse>${field.propertyName}, jdbcType=<#noparse>}</#noparse>
                </if>
            </#list>
        </set>
        where id =<#noparse>#{id, jdbcType=BIGINT}</#noparse>
    </update>

</mapper>
