package com.ts.compendium.telegram.bot.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderRepository {

    private final OrderMongoRepository orderMongoRepository;

    public List<OrderEntity> findByUserPhone(String userPhone) {
        return orderMongoRepository.findByUserPhone(userPhone);
    }
}
