package dev.stackoverflow.service;

import dev.stackoverflow.model.Tag;
import dev.stackoverflow.repository.TagRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    @Autowired
    private final TagRepository tagRepository;

    public Tag getById(@NonNull Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.orElse(null);
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Tag save(@NonNull Tag tag) {
        if (tagRepository.existsByText(tag.getText())) {
            return tagRepository.findByText(tag.getText());
        }
        return tagRepository.save(tag);
    }

    public List<Tag> saveTags(@NonNull List<Tag> tags) {
        return tagRepository.saveAll(tags);
    }

    public Tag update(@NonNull Tag tag) {
        return tagRepository.save(tag);
    }

    public void delete(@NonNull Long id) {
        tagRepository.deleteById(id);
    }
}
