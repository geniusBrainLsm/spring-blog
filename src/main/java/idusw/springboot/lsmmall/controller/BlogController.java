package idusw.springboot.lsmmall.controller;

import idusw.springboot.lsmmall.model.BlogDto;
import idusw.springboot.lsmmall.serivce.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blogs")
    public String getBlogs(Model model,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) Integer page,
                           @RequestParam(required = false) Integer size) {

        // 무한리디렉션뜸
        if (page == null || size == null) {
            page = (page == null) ? 0 : page;
            size = (size == null) ? 10 : size;
            return "redirect:/blogs?page=" + page + "&size=" + size + "&title=" + (title != null ? title : "");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<BlogDto> blogDtoList = blogService.readList(title, pageable);
        model.addAttribute("blogs", blogDtoList);
        model.addAttribute("title", title != null ? title : "");

        return "blogs/list";
    }

    @GetMapping("/blogs/{idx}")
    public String getBlogById(@PathVariable("idx") Long idx, Model model) {
        BlogDto blogDto = blogService.read(idx);
        model.addAttribute("blogDto", blogDto);
        return "./blogs/detail";
    }
}