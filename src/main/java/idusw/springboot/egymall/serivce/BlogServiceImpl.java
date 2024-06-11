package idusw.springboot.egymall.serivce;

import idusw.springboot.egymall.entity.BlogEntity;
import idusw.springboot.egymall.model.BlogDto;
import idusw.springboot.egymall.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    @Override
    public int create(BlogDto dto) {
        return 0;
    }

    @Override
    public BlogDto read(BlogDto dto) {
        return null;
    }

    @Override
    public List<BlogDto> readList() {
        List<BlogEntity> blogEntityList =  blogRepository.findAll();
        List<BlogDto> blogDtoList = blogEntityList.stream()
                .map(blogEntity -> entityToDto(blogEntity, blogEntity.getBlogger()))
                .collect(Collectors.toList());
        return blogDtoList;
    }

    @Override
    public int update(BlogDto dto) {
        return 0;
    }

    @Override
    public int delete(BlogDto dto) {
        return 0;
    }
}
