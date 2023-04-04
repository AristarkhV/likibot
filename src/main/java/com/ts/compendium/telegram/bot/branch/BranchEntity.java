package com.ts.compendium.telegram.bot.branch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Data
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchEntity {

    @Id
    private String id;

    @Indexed
    private String partnerId;

    @Indexed
    private String code;

    private String name;

    private String workTime;

    private String latitude;

    private String longitude;

    private List<String> phones;

    private String district;

    @Indexed
    private String city;

    private String street;

    private String house;

    @Indexed
    private Boolean enabled;

    private String brand;

    @Indexed
    private String okpo;

    @JsonIgnore
    private LocalDateTime updateTime;

    public String address() {
        List<String> address = new ArrayList<>();
        if (isNotBlank(street)) {
            address.add(street);
        }
        if (isNotBlank(house)) {
            address.add(house);
        }
        return String.join(", ", address);
    }
}
