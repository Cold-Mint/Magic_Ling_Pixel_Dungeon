package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreenSltingSprite;
import com.watabou.utils.Random;

public class GreenSlting extends Mob {

    {
        spriteClass = GreenSltingSprite.class;
        flying = true;
        HP = HT = 24;
        defenseSkill = 2;
        maxLvl = 7;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 8, 12 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(4, 5);
    }
}

