package boke.controller;

import com.Myboke.domain.ResponseResult;
import com.Myboke.domain.dto.AddArticleDto;
import com.Myboke.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/content/article")
public class ArticleController {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){

        return articleService.add(article);
    }

    @ApiOperation(value = "删除文章")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable  List<Integer> id){
        LOG.error("articleId is "+id);
        System.out.println(id);
        return articleService.deleteArticle(id);
    }


}
