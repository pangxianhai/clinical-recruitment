package com.andy.recruitment.web.controller.category.controller;

import com.andy.recruitment.api.category.request.CategoryAddReq;
import com.andy.recruitment.api.category.request.CategoryQueryReq;
import com.andy.recruitment.api.category.response.CategoryDetailRes;
import com.andy.recruitment.api.category.response.CategoryRes;
import com.andy.recruitment.biz.category.service.CategoryService;
import com.andy.recruitment.dao.category.dao.CategoryDAO;
import com.andy.recruitment.dao.category.entity.CategoryDO;
import com.andy.recruitment.dao.category.entity.CategoryQuery;
import com.andy.recruitment.web.controller.category.util.CategoryUtil;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.auth.RoleType;
import com.andy.spring.context.ServletContext;
import com.andy.spring.converter.MyParameter;
import com.andy.spring.page.PageResult;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类目 controller 接口
 *
 * @author 庞先海 2020-04-12
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @GetMapping
    public PageResult<CategoryRes> listCategoryRes(@MyParameter CategoryQueryReq queryReq) {
        CategoryQuery query = CategoryUtil.transformCategoryQuery(queryReq);
        PageResult<CategoryDO> pageResult = this.categoryService.getCategory(query, queryReq.getPaginator());
        List<CategoryRes> categoryResList = CategoryUtil.transformCategoryRes(pageResult.getData());
        return new PageResult<>(categoryResList, pageResult.getPaginator());
    }

    @GetMapping("/{parentId:\\d+}/children")
    public List<CategoryDetailRes> listCategoryResByParent(@PathVariable Long parentId) {
        if (parentId == null || parentId <= 0) {
            parentId = CategoryDAO.ROOT_CATEGORY_ID;
        }
        return this.categoryService.getCategoryByParentId(parentId);
    }

    @RequiresUser
    @GetMapping("/getByName")
    public CategoryRes getCategoryByName(String name) {
        CategoryDO categoryDo = this.categoryService.getCategoryByName(name);
        return CategoryUtil.transformCategoryRes(categoryDo);
    }

    @RequiresUser
    @GetMapping("/{categoryId:\\d+}")
    public CategoryRes getCategoryById(@PathVariable Long categoryId) {
        CategoryDO categoryDo = this.categoryService.getCategoryById(categoryId);
        return CategoryUtil.transformCategoryRes(categoryDo);
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PostMapping
    public boolean addCategory(@RequestBody CategoryAddReq addReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        CategoryDO categoryDo = CategoryUtil.transformCategoryDo(addReq);
        this.categoryService.addCategory(categoryDo, loginInfo.getRealName());
        return true;
    }

    @RequiresUser
    @RequiresRoles(RoleType.MANAGER_CODE + "")
    @PutMapping("/{categoryId:\\d+}")
    public boolean updateCategory(@PathVariable Long categoryId, @RequestBody CategoryAddReq addReq) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        CategoryDO categoryDo = CategoryUtil.transformCategoryDo(addReq);
        categoryDo.setCategoryId(categoryId);
        this.categoryService.updateCategory(categoryDo, loginInfo.getRealName());
        return true;
    }
}
