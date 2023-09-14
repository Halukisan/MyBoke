package boke.controller;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AdMenuDto;
import com.Myboke.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult list(@RequestParam(value = "menuName",required = false) String menuName,@RequestParam(value = "status",required = false) String status){
        if (StringUtils.hasText(menuName) || StringUtils.hasText(status)){
            //两个条件有一个即可查询
            return menuService.getMenuListByDetail(menuName,status);
        }
        return menuService.getMenuList();
    }
    @PostMapping
    public ResponseResult add(@RequestBody AdMenuDto adMenuDto){
        return menuService.add(adMenuDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteByMenuId(@PathVariable Long id){
       return menuService.deleteByMenuId(id);
    }

}
