package idusw.springboot.egymall.controller;

import idusw.springboot.egymall.model.BlogDto;
import idusw.springboot.egymall.serivce.BlogService;
import idusw.springboot.egymall.serivce.BlogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("blogs/")
@RequiredArgsConstructor
public class BlogController {
    private final BlogServiceImpl blogService;
    @GetMapping
    public String getBlogs(){
        List<BlogDto> blogDtoList = blogService.readList();
        return "./blogs/list";
    }
}
