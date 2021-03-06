package com.dailyhome.back.item.service;

import com.dailyhome.back.exception.item.ItemNotFoundException;
import com.dailyhome.back.item.domain.Item;
import com.dailyhome.back.item.domain.ItemRepository;
import com.dailyhome.back.item.presentation.dto.response.ItemDetailResponse;
import com.dailyhome.back.item.presentation.dto.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponse> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }

    public ItemDetailResponse findById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return ItemDetailResponse.of(item);
    }

    public List<ItemResponse> findItemPagesBy(Long from, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);

        return itemRepository.findByIdGreaterThanEqualOrderById(from, pageRequest)
                .stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }

}
