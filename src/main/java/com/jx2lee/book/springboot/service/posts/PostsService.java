package com.jx2lee.book.springboot.service.posts;

import com.jx2lee.book.springboot.domain.posts.Posts;
import com.jx2lee.book.springboot.domain.posts.PostsRepository;
import com.jx2lee.book.springboot.web.dto.PostsListResponseDto;
import com.jx2lee.book.springboot.web.dto.PostsResponseDto;
import com.jx2lee.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jx2lee.book.springboot.web.dto.PostsSaveRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("There is no such post. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("There is no such post. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("There is no such post. id=" + id));
        postsRepository.delete(posts);
    }
}
