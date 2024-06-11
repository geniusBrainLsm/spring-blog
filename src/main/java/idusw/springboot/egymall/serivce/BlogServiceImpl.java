package idusw.springboot.egymall.serivce;

import idusw.springboot.egymall.entity.BlogEntity;
import idusw.springboot.egymall.entity.MemberEntity;
import idusw.springboot.egymall.model.BlogDto;
import idusw.springboot.egymall.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    @Override
    public int create(BlogDto dto) {
        BlogEntity entity = dtoToEntity(dto);

        blogRepository.save(entity);
        return 1;

        //서비스에서 이거써보ㅓ기ㅏ
    }

    @Override
    public BlogDto read(BlogDto dto) {
        Optional<BlogEntity> blogEntityOptional = blogRepository.findById(dto.getIdx());
        MemberEntity memberEntity= MemberEntity.builder()
                .idx(dto.getWriterIdx())
                .build();
        return blogEntityOptional.map(blogEntity -> entityToDto(blogEntity, memberEntity)).orElse(null);
    }

    @Override
    public List<BlogDto> readList() {
        List<BlogEntity> blogEntityList =  blogRepository.findAll();
        List<BlogDto> blogDtoList = blogEntityList.stream()
                .map(blogEntity -> entityToDto(blogEntity, blogEntity.getBlogger()))
                .collect(Collectors.toList());
        //change to using builder pattern
        return blogDtoList;
    }

    @Override
    public int update(BlogDto dto) {
        blogRepository.save(dtoToEntity(dto));
        return 1;
    }

    @Override
    public int delete(BlogDto dto) {
        blogRepository.delete(dtoToEntity(dto));


        return 1;
    }
}
