package isma.games.pacman.core.rules;

import static isma.games.pacman.core.actors.Fruit.FruitEnum;
import static isma.games.pacman.core.actors.Fruit.FruitEnum.APPLE;
import static isma.games.pacman.core.actors.Fruit.FruitEnum.BELL;
import static isma.games.pacman.core.actors.Fruit.FruitEnum.CHERRY;
import static isma.games.pacman.core.actors.Fruit.FruitEnum.GALAXIAN;
import static isma.games.pacman.core.actors.Fruit.FruitEnum.KEY;
import static isma.games.pacman.core.actors.Fruit.FruitEnum.MELON;
import static isma.games.pacman.core.actors.Fruit.FruitEnum.ORANGE;
import static isma.games.pacman.core.actors.Fruit.FruitEnum.STRAWBERRY;

public class FruitRules {
    public static FruitEnum getFruit(int level) {
        switch (level) {
            case 1:
                return CHERRY;
            case 2:
                return STRAWBERRY;
            case 3:
                return ORANGE;
            case 4:
                return ORANGE;
            case 5:
                return APPLE;
            case 6:
                return APPLE;
            case 7:
                return MELON;
            case 8:
                return MELON;
            case 9:
                return GALAXIAN;
            case 10:
                return GALAXIAN;
            case 11:
                return BELL;
            case 12:
                return BELL;
        }
        return KEY;
    }


}
