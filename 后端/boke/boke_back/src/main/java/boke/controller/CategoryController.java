package boke.controller;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AdCategoryDto;
import com.Myboke.domain.entity.Category;
import com.Myboke.domain.vo.CategoryVo;
import com.Myboke.domain.vo.ExcelCategoryVo;
import com.Myboke.enums.AppHttpCodeEnum;
import com.Myboke.service.CategoryService;
import com.Myboke.utils.BeanCopyUtils;
import com.Myboke.utils.WebUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询分类列表
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @PostMapping
    public ResponseResult addCategory(@RequestBody AdCategoryDto adCategoryDto){

        return categoryService.addCategory(adCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delCategory(@PathVariable  List<Long> id){
        return categoryService.deleteCategory(id);
    }
    @GetMapping("/list")
    public ResponseResult list(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize){
        return categoryService.categoryList(pageNum,pageSize);
    }

    /**
     * 导出分类
     * @param response
     */
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

}
