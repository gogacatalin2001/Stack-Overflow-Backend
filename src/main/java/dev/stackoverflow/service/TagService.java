package dev.stackoverflow.service;

import dev.stackoverflow.model.Tag;
import dev.stackoverflow.repository.TagRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag getTagById(@NonNull Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.orElse(null);
    }

    public Tag getByText(@NonNull String text) {
        return tagRepository.findByText(text);
    }

    public boolean existsByText(@NonNull String text) {
        return tagRepository.existsByText(text);
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Tag saveTag(@NonNull Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> saveTags(@NonNull List<Tag> tags) {
        return tagRepository.saveAll(tags);
    }

    public Tag updateTag(@NonNull Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(@NonNull Long id) {
        tagRepository.deleteById(id);
    }
}