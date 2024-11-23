package com.gdsc.game.behavior;

import com.gdsc.game.character.Character;

import java.util.Random;

public class Skill implements Behavior{
    // Generate random number
    private final Random randomNum = new Random();

    private String skillName;
    private int minDamage;
    private int maxDamage;
    private int manaCost;
    private int cooldown;

    public Skill(String skillName, int minDamage, int maxDamage, int manaCost, int cooldown) {
        this.skillName = skillName;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
    }

    @Override
    public void execute(Character myChar, Character opponentChar) {
        if (myChar.getMp() >= manaCost && myChar.getTurnsUntilSkillReady() == 0) {
            // 마나 소모
            myChar.setMp(myChar.getMp() - manaCost);

            // 랜덤 데미지 계산
            int damage = randomNum.nextInt(maxDamage - minDamage + 1) + minDamage;
            opponentChar.setHp(opponentChar.getHp() - damage);

            // 쿨타임 설정
            myChar.setTurnsUntilSkillReady(cooldown);

            System.out.printf("%s가 %s를 사용하여 %d 데미지를 입혔습니다! (마나: %d 소모, %d턴 후 재사용)%n",
                    myChar.getName(), skillName, damage, manaCost, cooldown);
        } else if (myChar.getMp() < manaCost) {
            System.out.println("마나가 부족하여 " + skillName + "을 사용할 수 없습니다!");
        } else {
            System.out.println(skillName + "은(는) 쿨타임 중입니다!");
        }
    }
}