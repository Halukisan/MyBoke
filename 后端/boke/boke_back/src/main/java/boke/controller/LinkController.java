package boke.controller;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AdLinkDto;
import com.Myboke.domain.dto.AdMenuDto;
import com.Myboke.domain.entity.Link;
import com.Myboke.domain.entity.LinkChange;
import com.Myboke.domain.vo.LinkVo;
import com.Myboke.service.LinkService;
import com.Myboke.utils.BeanCopyUtils;
import com.Myboke.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    public ResponseResult list(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                               @RequestParam(value = "pageSize",required = false) Integer pageSize,
                               @RequestParam(value = "menuName",required = false) String linkName,
                               @RequestParam(value = "status",required = false) String status){
        if (StringUtils.hasText(linkName) || StringUtils.hasText(status)){
            //两个条件有一个即可查询
            return linkService.getLinkListByDetail(linkName,status);
        }
        return linkService.ContentgetAllLink(pageNum,pageSize);
    }
    @PostMapping
    public ResponseResult add(@RequestBody AdLinkDto adLinkDto){
        return linkService.add(adLinkDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteByMenuId(@PathVariable Long id){
        return linkService.deleteByLinkId(id);
    }
    @GetMapping("/{id}")
    public ResponseResult getById_for_Modify(@PathVariable Long id){
        return linkService.getById_for_Modify(id);
    }
    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody LinkChange linkChange){
        String id = linkChange.getId();
        String status = linkChange.getStatus();
        return linkService.changeLinkStatus(id,status);
    }
    @PutMapping
    public ResponseResult updateById(@RequestBody String json){
        return linkService.myUpdateById(json);
    }
}
