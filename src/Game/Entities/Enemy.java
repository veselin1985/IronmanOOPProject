package Game.Entities;

import Game.Items.*;
import Game.Stat;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static Game.Items.Names.enemiesNames;

public class Enemy extends Entity {
    private static final int HP_DEFAULT = 100;
    private int maxHP;
    private Item item;
    private Item usable;
    private boolean isBashed = false;


    public Enemy(double coefficient) {

        this.name = getRandomName();
        maxHP = (int) (HP_DEFAULT + 100 * coefficient + ThreadLocalRandom.current().nextInt(1, 30));
        currentHP = maxHP;
        this.damage = (int) (getRandomDamage() * coefficient) + ThreadLocalRandom.current().nextInt(1, 15);
        this.gold = (int) ((damage * 10 + maxHP * 5) * coefficient);
        this.item = getRandomItem();
        usable = generateRandomUsable();
        this.xp = (int) ((damage * 15 + maxHP * 5) * coefficient);
        Stat stat;

    }

    public void showStats() {
        System.out.printf("HP: %d \nDamage: %d \nGold reward: %d \nXP reward: %d \nItem reward:???\n\n", currentHP, damage, gold, xp);
    }

    public void dropReward(Hero hero) {
        System.out.printf("%s dropped:\n%s\n", getName(), getItem().toString());
        System.out.printf("You alse received %s, %d gold and gained %d XP.\n\n", usable.getName(), gold, xp);
        hero.addToInventory(item);
        hero.addToInventory(usable);
        hero.setXp(xp);
        hero.setGold(gold);
    }


    public int getMaxHP() {
        return maxHP;
    }

    public Item getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public boolean isBashed() {
        return isBashed;
    }

    public void setBashed(boolean bashed) {
        isBashed = bashed;
    }


    @Override
    public int attack() {

        return damage + ThreadLocalRandom.current().nextInt(0, 5);
    }

    private String getRandomName() {
        int index = ThreadLocalRandom.current().nextInt(0, enemiesNames.size());
        name = enemiesNames.get(index);
        return name;
    }

    private Item getRandomItem() {
        int index = ThreadLocalRandom.current().nextInt(0, 3);
        Item item;
        switch (index) {
            case 0:
                item = new Weapon();
                break;
            case 1:
                item = new Vest();
                break;
            case 2:
                item = new Helmet();
                break;
            default:
                item = null;
                break;
        }
        return item;
    }

    private Item generateRandomUsable() {
        int index = ThreadLocalRandom.current().nextInt(0, 2);
        Item usable;
        switch (index) {
            case 0:
                usable = new HPPotion();
                break;
            case 1:
                usable = new Tome();
                break;
            default:
                usable = null;
                break;

        }
        return usable;

    }

    private int getRandomDamage() {
        return ThreadLocalRandom.current().nextInt(30, 50);
    }

    public void takeDamage(int damage) {
        currentHP -= damage;
    }
}

