package com.dailyhome.back.item.service;

import com.dailyhome.back.item.domain.Category;
import com.dailyhome.back.item.domain.CategoryRepository;
import com.dailyhome.back.item.domain.ItemRepository;
import com.dailyhome.back.item.presentation.dto.response.CategoryResponse;
import com.dailyhome.back.item.presentation.dto.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public CategoryResponse createCategoryTree() {
        Map<Long, List<CategoryResponse>> groupingByParent = categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(groupingBy(CategoryResponse::getParentId));

        Category category = new Category(0L, "ROOT", null);
        CategoryResponse rootCategoryResponse = CategoryResponse.of(category);

        addSubCategories(rootCategoryResponse, groupingByParent);

        return rootCategoryResponse;
    }

    private void addSubCategories(CategoryResponse parent, Map<Long, List<CategoryResponse>> groupingByParent) {
        List<CategoryResponse> subCategories = groupingByParent.get(parent.getId());

        if (subCategories == null) {
            return ;
        }

        parent.setSubCategories(subCategories);

        subCategories.forEach(s -> addSubCategories(s, groupingByParent));
    }

    @Transactional
    public List<ItemResponse> findAllByCategoryId(Long id, Long from, int size) {
        return itemRepository.findAllByCategoryId(id)
                .stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }
}
