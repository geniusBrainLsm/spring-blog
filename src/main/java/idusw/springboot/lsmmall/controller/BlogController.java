package idusw.springboot.lsmmall.controller;

import idusw.springboot.lsmmall.model.BlogDto;
import idusw.springboot.lsmmall.model.MemberDto;
import idusw.springboot.lsmmall.serivce.BlogService;
import idusw.springboot.lsmmall.serivce.MemberServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final MemberServiceImpl memberService;


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
        MemberDto writer = memberService.readByIdx(blogDto.getWriterIdx());
        if (writer != null) {
            blogDto.setWriterName(writer.getName());
        }
        model.addAttribute("blogDto", blogDto);
        return "./blogs/detail";
    }

    @GetMapping("/blogs/edit/{idx}")
    public String getEditBlogs(@PathVariable("idx") Long idx, Model model){
        BlogDto blogDto = blogService.read(idx);
        model.addAttribute("blogDto", blogDto);
        return "./blogs/edit";
    }

    @PostMapping("/blogs/edit/{idx}")
    public String postEditBlogs(@PathVariable("idx") Long idx, @ModelAttribute BlogDto blogDto) {
        int result = blogService.update(idx, blogDto);
        if (result == 1) {
            return "redirect:/blogs/" + idx;
        } else {
            //dㅔ러
            return "redirect:/blogs";
        }
    }

    @PostMapping("/blogs/delete/{idx}")
    public String deleteBlog(@PathVariable("idx") Long idx) {
        blogService.delete(idx);
        return "redirect:/blogs";
    }

    @GetMapping("/blogs/create")
    public String getCreateBlog(){
        return "./blogs/create";
    }

    @PostMapping("/blogs/create")
    public String postCreateBlog(@ModelAttribute BlogDto blogDto, HttpSession session) {
        Long memberIdx = (Long) session.getAttribute("idx");
        if (memberIdx == null) {
            return "redirect:/members/login";
        }

        MemberDto memberDto = memberService.readByIdx(memberIdx);
        if (memberDto == null) {
            return "./errors/error-message";
        }

        blogService.create(memberDto, blogDto);
        return "redirect:/blogs";
    }
}