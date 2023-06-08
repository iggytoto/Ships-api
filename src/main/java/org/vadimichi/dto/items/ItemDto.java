package org.vadimichi.dto.items;

import lombok.*;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.item.Item;
import org.gassangaming.model.item.ItemRarity;
import org.gassangaming.model.item.ItemType;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ItemDto extends DtoBase {

    private Long id;
    private long userId;
    private ItemType itemType;
    private ItemRarity rarity;
    private String name;

    public static ItemDto of(Item i) {
        return builder()
                .id(i.getId())
                .userId(i.getUserId())
                .itemType(i.getItemType())
                .name(i.getName())
                .rarity(i.getRarity())
                .build();
    }

}
