package kz.hxncus.mc.minesonapi.gameevent;

public interface IEventListener {
    void setGameState(GameState gameState);
    void onStart();
    void onStop();
    void onTick();
}
