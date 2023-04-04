package com.ts.compendium.telegram.bot.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Data
@Document
public class OrderEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String code;

    @Indexed
    private String partnerOrderCode;

    @Indexed
    private String partnerId;

    @Indexed
    private String branchCode;

    private String brandName;

    @Indexed
    private OrderStatus status;

    @Indexed
    private String userPhone;

    @Indexed
    private String okpo;

    private List<Note> notes;

    private List<Item> items;

    @JsonIgnore
    private LocalDateTime lastUpdateDate;

    @Indexed
    private LocalDateTime createDate;

    private LocalDateTime paidDate;

    private Double price;

    private String branchAddress;

    private String city;

    private String district;

    private String branchWorkTime;

    public OrderEntity setItems(List<Item> items) {
        this.items = items;
        this.price = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        return this;
    }

    public String orderCode() {
        return isNotBlank(getPartnerOrderCode()) ? getPartnerOrderCode() : getCode();
    }
}
