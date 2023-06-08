package org.vadimichi.dto.unit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.gassangaming.dto.DtoBase;
import org.vadimichi.dto.items.ItemDto;
import org.vadimichi.dto.skills.UnitSkillsDto;
import org.gassangaming.model.unit.Activity;
import org.gassangaming.model.unit.BattleBehavior;
import org.gassangaming.model.unit.Unit;
import org.gassangaming.model.unit.UnitType;
import org.gassangaming.service.tavern.UnitForSale;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UnitDto extends DtoBase {
    private Long id;
    private Long ownerId;
    private String name;
    private int hitPoints;
    private int maxHitPoints;
    private int mana;
    private int maxMana;
    private int armor;
    private int magicResistance;
    private int damage;
    private long goldAmount;
    private float attackSpeed;
    private float attackRange;
    private float movementSpeed;
    private Activity activity;
    private BattleBehavior battleBehavior;
    private UnitType unitType;

    private UnitSkillsDto skills;

    private List<ItemDto> items;


    public static UnitDto of(UnitForSale u) {
        final var result = of(u.getUnit());
        result.setGoldAmount(u.getGoldAmount());
        return result;
    }

    public static UnitDto of(Unit u) {
        return UnitDto
                .builder()
                .id(u.getId())
                .name(u.getName())
                .hitPoints(u.getHitPoints())
                .maxHitPoints(u.getMaxHitPoints())
                .mana(u.getMana())
                .maxMana(u.getMaxMana())
                .armor(u.getArmor())
                .magicResistance(u.getMagicResistance())
                .damage(u.getDamage())
                .attackSpeed(u.getAttackSpeed())
                .attackRange(u.getAttackRange())
                .movementSpeed(u.getMovementSpeed())
                .activity(u.getActivity())
                .battleBehavior(u.getBattleBehavior())
                .unitType(u.getUnitType())
                .ownerId(u.getOwnerId())
                .items(u.getItems().stream().map(ItemDto::of).toList())
                .skills(UnitSkillsDto.of(u.getSkills()))
                .build();
    }

    @JsonIgnore
    public Unit ToDomain() {
        final var result = new Unit();
        result.setId(id);
        result.setName(name);
        result.setHitPoints(hitPoints);
        result.setMana(mana);
        result.setMaxMana(maxMana);
        result.setMaxHitPoints(maxHitPoints);
        result.setArmor(armor);
        result.setMagicResistance(magicResistance);
        result.setDamage(damage);
        result.setAttackSpeed(attackSpeed);
        result.setMovementSpeed(movementSpeed);
        result.setActivity(activity);
        result.setBattleBehavior(battleBehavior);
        result.setUnitType(unitType);
        result.setOwnerId(ownerId);
        result.setAttackRange(attackRange);
        return result;
    }
}
