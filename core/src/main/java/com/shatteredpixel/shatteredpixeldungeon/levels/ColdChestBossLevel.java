package com.shatteredpixel.shatteredpixeldungeon.levels;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;
import static com.shatteredpixel.shatteredpixeldungeon.levels.ColdChestBossLevel.State.GO_START;
import static com.shatteredpixel.shatteredpixeldungeon.levels.ColdChestBossLevel.State.MAZE_START;
import static com.shatteredpixel.shatteredpixeldungeon.levels.ColdChestBossLevel.State.START;
import static com.shatteredpixel.shatteredpixeldungeon.levels.ColdChestBossLevel.State.VSBOSS_START;
import static com.shatteredpixel.shatteredpixeldungeon.levels.ColdChestBossLevel.State.VSLINK_START;
import static com.shatteredpixel.shatteredpixeldungeon.levels.ColdChestBossLevel.State.VSYOU_START;
import static com.shatteredpixel.shatteredpixeldungeon.levels.ColdChestBossLevel.State.WIN;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.bosses.DiamondKnight;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundle;

import java.util.HashMap;

//宝藏迷宫 10层
public class ColdChestBossLevel extends Level {

    private State pro;

    public State pro(){
        return pro;
    }

    private static final String PRO	= "pro";

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        pro = bundle.getEnum(PRO, State.class);
    }

    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put(PRO, pro);
    }

    //地图状态
    public enum State {
        GO_START,
        START,
        MAZE_START,
        VSBOSS_START,
        VSLINK_START,
        VSYOU_START,
        WIN
    }

    private static final int WIDTH = 35;
    private final int HEIGHT = 35;

    @Override
    protected boolean build() {
        setSize(WIDTH,HEIGHT);
        this.entrance = 0;
        this.exit = 0;
        //首次构建地图
        pro = GO_START;
        setMapStart();

        return true;
    }
    private static final short W = Terrain.WALL;
    private static final short E = Terrain.CHASM;

    private static final short X = Terrain.EXIT;
    private static final short J = Terrain.ENTRANCE;
    private static final short O = Terrain.WATER;
    private static final short P = Terrain.EMPTY_SP;
    private static final short B = Terrain.EMPTY_SP;
    private static final short K = Terrain.EMPTY;
    private static final short L = Terrain.EMPTY;
    private static final short D = Terrain.DOOR;
    private static final short T = Terrain.PEDESTAL;
    private static final short S = Terrain.STATUE;

    private static final int[] WorldRoomShort = {
            W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,
            W,E,E,E,W,W,W,W,W,E,E,E,E,E,E,E,W,J,W,E,E,E,E,E,E,E,W,W,W,W,W,E,E,E,W,
            W,E,E,E,O,O,O,O,O,E,E,E,E,E,E,E,W,P,W,E,E,E,E,E,E,E,O,O,O,O,O,E,E,E,W,
            W,E,E,E,P,P,P,P,P,E,E,E,E,E,E,E,W,P,W,E,E,E,E,E,E,E,P,P,P,P,P,E,E,E,W,
            W,E,E,E,P,E,E,E,P,E,E,E,E,E,E,E,W,K,W,E,E,E,E,E,E,E,P,E,E,E,P,E,E,E,W,
            W,E,P,E,P,E,P,E,P,E,P,E,P,E,P,E,W,P,W,E,P,E,P,E,P,E,P,E,P,E,P,E,P,E,W,
            W,E,E,E,P,E,E,E,P,E,E,E,E,E,E,E,W,P,W,E,E,E,E,E,E,E,P,E,E,E,P,E,E,E,W,
            W,E,E,E,P,P,P,P,P,E,E,E,E,E,E,E,W,P,W,E,E,E,E,E,E,E,P,P,P,P,P,E,E,E,W,
            W,E,E,E,E,E,P,E,E,E,E,E,E,E,E,E,W,K,W,E,E,E,E,E,E,E,E,E,P,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,P,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,P,E,W,W,W,W,E,E,E,E,W,P,W,E,E,E,E,W,W,W,W,E,P,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,W,K,K,W,W,W,W,W,W,D,W,W,W,W,W,W,K,K,W,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,P,E,W,K,K,K,K,K,K,K,W,K,W,K,K,K,K,K,K,K,W,E,P,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,W,K,K,K,K,K,K,K,W,O,W,K,K,K,K,K,K,K,W,E,E,E,E,E,E,E,W,
            W,W,W,W,W,W,W,W,W,K,K,K,K,K,K,K,O,O,O,K,K,K,K,K,K,K,W,W,W,W,W,W,W,W,W,
            W,B,B,B,B,B,B,B,W,K,K,K,K,K,K,O,O,O,O,O,K,K,K,K,K,K,W,B,B,B,B,B,B,B,W,
            W,B,K,K,K,K,K,B,W,K,K,K,K,K,O,O,O,K,O,O,O,K,K,K,K,K,W,B,K,K,K,K,K,B,W,
            W,B,K,B,B,B,K,B,W,K,K,K,K,K,O,O,K,K,K,O,O,K,K,K,K,K,W,B,K,B,B,B,K,B,W,
            W,B,B,B,T,B,B,B,W,K,K,K,K,O,O,K,O,O,O,K,O,O,K,K,K,K,W,B,B,B,T,B,B,B,W,
            W,B,K,B,B,B,K,B,D,P,P,K,K,O,K,K,O,K,O,K,K,O,K,K,P,P,D,B,K,B,B,B,K,B,W,
            W,B,K,K,K,K,K,B,W,K,K,K,K,O,O,K,O,O,O,K,O,O,K,K,K,K,W,B,K,K,K,K,K,B,W,
            W,B,K,K,K,K,K,B,W,K,K,K,K,K,O,O,K,K,K,O,O,K,K,K,K,K,W,B,K,K,K,K,K,B,W,
            W,B,B,B,B,B,B,B,W,K,K,K,K,K,O,O,O,K,O,O,O,K,K,K,K,K,W,B,B,B,B,B,B,B,W,
            W,W,W,W,W,W,W,W,W,K,K,K,K,K,K,O,O,O,O,O,K,K,K,K,K,K,W,W,W,W,W,W,W,W,W,
            W,E,E,W,T,W,E,E,W,K,K,K,K,K,K,K,O,O,O,K,K,K,K,K,K,K,W,E,W,T,W,E,E,E,W,
            W,E,E,W,K,W,E,E,W,K,K,W,W,W,K,K,K,O,K,K,K,W,W,W,K,K,W,E,W,K,W,E,E,E,W,
            W,E,E,W,K,W,E,E,W,K,K,W,E,W,K,K,K,K,K,K,K,W,E,W,K,K,W,E,W,K,W,E,E,E,W,
            W,E,E,W,K,W,E,E,W,W,W,W,E,W,K,K,K,P,K,K,K,W,E,W,W,W,W,E,W,K,W,E,E,E,W,
            W,E,E,W,K,W,E,E,E,E,E,E,E,W,K,K,K,K,K,K,K,W,E,E,E,E,E,E,W,K,W,E,E,E,W,
            W,E,E,W,K,W,E,E,E,E,E,E,E,W,K,K,K,P,K,K,K,W,E,E,E,E,E,E,W,K,W,E,E,E,W,
            W,E,E,W,K,W,W,W,W,W,W,W,W,W,K,K,P,P,P,K,K,W,W,W,W,W,W,W,W,K,W,E,E,E,W,
            W,E,E,W,K,K,K,K,K,K,K,K,K,D,K,P,P,X,P,P,K,D,K,K,K,K,K,K,K,K,W,E,E,E,W,
            W,E,E,W,W,W,W,W,W,W,W,W,W,W,K,K,P,P,P,K,K,W,W,W,W,W,W,W,W,W,W,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,W,K,K,K,P,K,K,K,W,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,

    };

    private static final int[] MazeRoom = {
            W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,
            W,J,L,L,L,L,W,L,W,L,W,W,W,W,W,L,L,L,W,W,W,W,W,W,L,L,L,L,W,L,W,W,W,L,W,
            W,W,W,W,W,L,W,L,W,L,W,W,W,W,W,L,W,W,W,W,W,W,W,W,L,W,L,L,W,L,W,W,W,L,W,
            W,W,W,B,L,L,W,L,L,L,W,L,L,L,W,L,L,L,W,L,L,L,L,L,L,W,L,L,W,L,W,W,W,L,W,
            W,W,W,L,W,W,W,L,W,W,W,L,W,L,L,L,L,L,W,L,L,L,L,W,W,W,L,L,L,L,L,L,W,L,W,
            W,L,L,L,W,L,L,L,L,L,L,L,W,L,W,L,W,L,W,W,W,L,L,W,L,L,L,L,L,L,W,L,W,L,W,
            W,T,W,L,W,L,W,W,W,W,W,W,W,W,W,L,W,L,W,L,L,L,L,W,L,L,W,W,W,W,W,T,W,L,W,
            W,W,W,L,L,L,L,L,L,W,L,W,L,L,W,L,W,W,W,L,L,W,W,W,L,L,L,L,L,L,W,W,W,L,W,
            W,W,W,L,W,W,W,W,W,W,L,W,L,L,W,L,W,L,L,L,L,W,L,W,L,L,W,W,W,L,W,L,L,L,W,
            W,W,W,L,L,W,L,W,L,L,L,L,L,L,W,L,W,W,W,W,W,W,L,W,L,L,L,L,W,L,W,L,L,L,W,
            W,W,W,L,L,W,L,W,L,W,W,W,W,W,W,L,W,L,W,L,L,W,L,W,L,L,W,L,W,L,L,L,W,L,W,
            W,W,W,L,L,L,L,W,L,L,W,L,L,L,W,L,L,L,W,L,L,W,L,W,L,L,W,L,W,L,W,W,W,L,W,
            W,W,W,W,W,W,L,L,L,L,W,L,L,L,W,L,W,W,W,L,L,L,L,L,L,L,W,L,L,L,L,L,W,L,W,
            W,W,W,L,L,L,L,L,L,L,W,L,L,L,L,L,W,L,D,B,L,W,L,W,L,L,W,W,W,W,L,W,W,L,W,
            W,W,W,W,W,L,L,W,L,L,L,L,L,L,L,L,W,W,W,L,L,W,L,W,L,L,L,L,L,L,L,L,W,L,W,
            W,W,W,L,L,L,W,W,B,W,W,W,L,W,L,L,L,L,W,L,W,W,W,W,W,L,L,L,L,L,L,L,W,L,W,
            W,W,W,L,L,L,L,W,L,D,L,W,L,W,L,L,L,L,W,L,L,W,L,L,L,L,L,W,W,W,W,W,W,L,W,
            W,W,W,L,L,L,L,W,L,W,W,W,L,W,L,L,W,L,L,L,L,W,L,W,L,L,L,L,L,L,L,L,L,B,W,
            W,W,W,L,W,W,W,W,L,L,L,L,L,L,L,L,W,L,L,L,L,W,L,W,W,W,L,L,W,L,L,L,L,W,W,
            W,W,W,L,L,L,L,W,L,L,L,L,L,L,L,L,W,W,W,W,L,L,L,W,L,W,W,L,W,W,W,W,W,W,W,
            W,W,W,L,L,L,L,W,L,L,L,L,W,W,W,L,W,L,L,L,L,L,L,W,L,L,L,L,L,L,L,L,L,W,W,
            W,L,L,L,L,L,L,L,L,L,L,L,W,L,L,L,W,L,W,W,W,W,L,W,L,W,W,W,W,W,W,W,L,W,W,
            W,L,W,L,L,W,W,W,W,L,W,W,W,W,W,W,W,L,L,L,L,W,L,L,L,L,L,L,L,L,L,W,L,L,W,
            W,L,W,L,L,L,L,L,W,L,L,L,L,L,W,L,W,W,W,W,L,W,L,L,L,L,L,L,W,L,L,W,L,L,W,
            W,T,W,W,W,W,L,L,W,L,L,W,W,W,W,L,L,W,L,W,L,W,L,L,W,W,W,L,W,L,L,L,L,L,W,
            W,W,W,L,L,L,L,L,W,L,L,L,L,L,W,L,L,W,L,L,L,W,L,L,L,L,W,L,W,W,W,W,W,W,W,
            W,W,W,L,W,L,W,W,W,L,L,L,W,L,W,L,L,W,W,W,L,W,W,W,W,W,W,L,W,L,L,L,L,L,W,
            W,W,W,L,W,L,L,L,W,L,L,L,W,L,W,L,L,W,L,L,L,L,L,L,L,L,W,L,L,L,W,W,W,L,W,
            W,W,W,L,W,L,L,W,W,L,W,L,W,L,L,L,L,W,L,W,W,W,L,W,L,L,W,L,W,L,W,L,W,L,W,
            W,W,W,W,W,L,L,W,W,L,W,L,W,L,W,W,W,W,L,L,W,L,L,W,L,L,L,L,W,L,W,W,W,L,W,
            W,W,W,L,L,L,W,W,W,L,W,L,W,L,L,W,L,L,L,L,L,L,L,W,L,L,L,L,W,L,L,L,L,L,W,
            W,W,W,L,W,L,L,L,W,L,W,L,W,L,L,W,L,W,W,W,W,W,L,W,W,W,W,W,W,W,W,W,W,W,W,
            W,W,W,L,W,L,W,L,W,W,W,W,W,L,W,W,T,L,L,L,W,L,L,L,W,L,L,L,W,L,L,L,W,L,W,
            W,B,L,L,W,L,W,L,L,L,L,L,L,L,L,W,W,W,W,W,W,L,W,L,L,L,W,L,L,L,W,L,T,X,W,
            W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,
    };

    private static final int[] EndMap = {
            W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,B,B,B,B,B,B,B,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,B,K,K,K,K,K,B,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,B,K,B,B,B,K,B,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,B,B,B,T,B,B,B,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,B,K,B,B,B,K,B,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,B,K,K,K,K,K,B,E,E,W,W,W,K,O,K,W,W,W,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,B,B,B,B,B,B,B,E,E,W,S,K,K,K,K,K,S,W,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,B,E,E,E,E,E,W,K,O,K,O,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,B,E,W,W,W,W,W,K,O,K,O,K,O,K,W,W,W,W,W,E,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,B,E,W,S,K,K,K,K,O,K,O,K,O,K,K,K,K,S,W,E,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,B,E,W,K,O,O,O,O,O,K,O,K,O,O,O,O,O,K,W,E,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,B,E,W,K,O,K,S,K,K,K,T,K,K,K,S,K,O,K,W,E,E,E,E,E,E,E,E,W,
            W,W,W,W,W,W,W,D,W,W,K,K,K,K,B,B,B,B,B,B,B,K,K,K,K,W,W,W,W,W,W,W,W,W,W,
            W,K,K,K,K,K,K,K,K,S,K,K,K,K,B,K,K,K,K,K,B,K,K,K,K,S,K,K,K,K,K,K,K,K,W,
            W,O,O,O,K,K,O,K,O,K,O,K,O,K,B,K,B,B,B,K,B,K,O,O,O,K,K,K,K,K,K,O,O,O,W,
            W,O,K,O,O,O,O,O,O,K,K,K,O,T,B,B,B,K,B,B,B,T,O,K,K,K,O,O,O,O,O,O,K,O,W,
            W,O,O,O,K,K,K,K,K,K,O,O,O,K,B,K,B,B,B,K,B,K,O,K,O,K,O,K,O,K,K,O,O,O,W,
            W,K,K,K,K,K,K,K,K,S,K,K,K,K,B,K,K,K,K,K,B,K,K,K,K,S,K,K,K,K,K,K,K,K,W,
            W,W,W,W,W,W,W,W,W,W,K,O,K,K,B,B,B,B,B,B,B,K,K,K,K,W,W,D,W,W,W,W,W,W,W,
            W,E,E,E,E,E,E,E,E,W,K,O,K,S,K,K,K,T,K,K,K,S,K,O,K,W,E,B,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,W,K,O,O,O,O,O,K,O,K,O,O,O,O,O,K,W,E,B,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,W,S,K,K,K,K,O,K,O,K,O,K,K,K,K,S,W,E,B,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,W,W,W,W,W,K,O,K,O,K,O,K,W,W,W,W,W,E,B,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,W,K,O,K,O,K,O,K,W,E,E,E,E,E,B,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,W,S,K,K,K,K,K,S,W,E,E,E,W,W,D,W,W,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,W,W,W,K,O,K,W,W,W,E,E,E,W,K,K,K,W,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,K,O,K,W,E,E,E,E,E,W,K,T,K,W,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,K,O,K,W,E,E,E,E,E,W,K,K,K,W,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,K,O,K,W,E,E,E,E,E,W,W,W,W,W,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,K,O,K,W,E,E,E,E,E,E,E,E,E,E,E,E,E,E,W,
            W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,W,
    };


    private void setMapStart() {
        entrance = HOME;
        map = WorldRoomShort.clone();
    }

    private static final HashMap<Integer, Integer> MAIN_PORTAL = new HashMap<>(5);
    {
        MAIN_PORTAL.put(32 + WIDTH * 33, WIDTH*3 + 3);
        MAIN_PORTAL.put(211, 628);
        MAIN_PORTAL.put(1136, 533);
        MAIN_PORTAL.put(841, 1156);
        MAIN_PORTAL.put(241, 474);
    }

    private static final HashMap<Integer, Integer> IF_MAIN_PORTAL = new HashMap<>(5);
    {
        IF_MAIN_PORTAL.put(32 + WIDTH * 33, WIDTH*3 + 3);
        IF_MAIN_PORTAL.put(211, 628);
        IF_MAIN_PORTAL.put(1136, 533);
        IF_MAIN_PORTAL.put(841, 1156);
        IF_MAIN_PORTAL.put(241, 474);
    }

    @Override
    public void occupyCell(Char ch) {
        super.occupyCell(ch);
        if (map[getBossDoor] == Terrain.DOOR && ch.pos == LDBossDoor && ch == hero) {
           progress();
        }

        if(ch == hero){
            //指定区域
            if(MAIN_PORTAL.containsKey(ch.pos) && pro == MAZE_START) {
                ScrollOfTeleportation.appear(ch, IF_MAIN_PORTAL.get(ch.pos));
                //传送目标区域
                hero.interrupt();
                Dungeon.observe();
                GameScene.updateFog();
            }
        }
        //GLog.n(String.valueOf(hero.pos));
    }

    public void progress(){
        switch (pro) {
            case GO_START:
                //如果有生物来到BossDoor的下一个坐标，且生物是玩家，那么触发seal().
                seal();
                DiamondKnight bossx = new DiamondKnight();
                bossx.state = bossx.WANDERING;
                bossx.pos = WIDTH*19+17;
                GameScene.add( bossx );
                set( getBossDoor, Terrain.LOCKED_DOOR );
                GameScene.updateMap( getBossDoor );
                set( HOME, Terrain.EMPTY );
                GameScene.updateMap( HOME );
                set( 1102, Terrain.EMPTY );
                GameScene.updateMap( 1102 );
                Dungeon.observe();
                pro = START;
                break;
            case START:
                //血量低于360后且在START枚举中
                for (Mob boss : Dungeon.level.mobs.toArray(new Mob[0])) {
                    if(boss instanceof DiamondKnight) {
                        //如果楼层为开始且boss血量小于360 1阶段
                        if (pro == START && boss.HP <= 360) {
                            //动态修改整个房间
                            changeMap(MazeRoom);
                            //宝箱王移动到看戏位
                            ScrollOfTeleportation.appear(boss, MDX);
                            //玩家移动到初始位
                            ScrollOfTeleportation.appear(hero, STARTPOS);
                            boss.HP = 360;
                            pro = MAZE_START;
                        }
                    }
                }
                break;
            case MAZE_START:
                //血量低于300后且在MAZE_START枚举中
                for (Mob boss : Dungeon.level.mobs.toArray(new Mob[0])) {
                    if(boss instanceof DiamondKnight) {
                        //如果楼层为开始且boss血量小于300 2阶段
                        if (pro == MAZE_START && boss.HP <= 300) {
                            //动态修改整个房间 宝藏迷宫
                            changeMap(EndMap);
                            ScrollOfTeleportation.appear(boss,647);
                            //玩家移动到初始位
                            ScrollOfTeleportation.appear(hero, 962);
                            boss.HP = 300;
                            pro = VSBOSS_START;
                        }
                    }
                }
                break;
            case VSBOSS_START:
                for (Mob boss : Dungeon.level.mobs.toArray(new Mob[0])) {
                    if(boss instanceof DiamondKnight) {
                        //如果楼层为开始且boss血量小于240 3阶段
                        if (pro == VSBOSS_START && boss.HP <= 240) {
                            boss.HP = 240;
                            pro = VSLINK_START;
                        }
                    }
                }
                break;
            case VSLINK_START:
                for (Mob boss : Dungeon.level.mobs.toArray(new Mob[0])) {
                    if(boss instanceof DiamondKnight) {
                        //如果楼层为开始且boss血量小于200 4阶段
                        if (pro == VSLINK_START && boss.HP <= 200) {
                            pro = VSYOU_START;
                        }
                    }
                }
                break;
            case VSYOU_START:
                //血量低于300后且在MAZE_START枚举中
                for (Mob boss : Dungeon.level.mobs.toArray(new Mob[0])) {
                    if(boss instanceof DiamondKnight) {
                        //如果楼层为开始且boss血量小于100 判定WIN
                        if (pro == VSYOU_START && boss.HP <= 100) {
                            pro = WIN;
                        }
                    }
                }
                break;
            case WIN:
                //
                break;
        }
    }

    @Override
    protected void createMobs() {

    }
    //keep track of removed items as the level is changed. Dump them back into the level at the end.



    private static final int getBossDoor = WIDTH*11+17;
    private static final int LDBossDoor = WIDTH*12+17;

    public static final int MDX = WIDTH*28+31;
    public static final int STARTPOS = WIDTH+4;
    private static final int HOME = WIDTH*2+17;

    @Override
    protected void createItems() {

    }


    public String tilesTex() {
        return Assets.Environment.TILES_COLDCHEST;
    }

    public String waterTex() {
        return Assets.Environment.WATER_PRISON;
    }
}
