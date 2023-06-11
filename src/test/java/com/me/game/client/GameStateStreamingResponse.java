package com.me.game.client;

import com.me.game.Die;
import com.me.game.GameState;
import com.me.game.Player;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class GameStateStreamingResponse implements StreamObserver<GameState> {
    private final CountDownLatch latch;
    private StreamObserver<Die> dieStreamObserver;

    public GameStateStreamingResponse(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(GameState gameState) {
        List<Player> playerList = gameState.getPlayerList();
        playerList.forEach(p -> System.out.println(p.getName() + ": " + p.getPosition()));
        boolean isGameOver = playerList.stream()
                .anyMatch(p -> p.getPosition() == 100);
        if (isGameOver) {
            System.out.println("Game Over!");
            dieStreamObserver.onCompleted();
        } else {
            this.roll();
        }
        System.out.println("======================");
    }

    public void setDieStreamObserver(StreamObserver<Die> streamObserver) {
        dieStreamObserver = streamObserver;
    }

    public void roll()  {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int dieValue = ThreadLocalRandom.current().nextInt(1, 7);
        Die die = Die.newBuilder().setValue(dieValue).buildPartial();
        dieStreamObserver.onNext(die);
    }

    @Override
    public void onError(Throwable throwable) {
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        latch.countDown();
    }
}
