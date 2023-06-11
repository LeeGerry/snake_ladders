package com.me.server;

import com.me.game.Die;
import com.me.game.GameState;
import com.me.game.Player;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class DieStreamingRequest implements StreamObserver<Die> {
    private Player client;
    private Player server;
    private StreamObserver<GameState> gameStateStreamObserver;

    public DieStreamingRequest(Player client, Player server, StreamObserver<GameState> gameStateStreamObserver) {
        this.client = client;
        this.server = server;
        this.gameStateStreamObserver = gameStateStreamObserver;
    }

    @Override
    public void onNext(Die die) {
        client = getNewPlayerPosition(client, die.getValue());
        if (client.getPosition() != 100) {
            server = getNewPlayerPosition(server, ThreadLocalRandom.current().nextInt(1, 7));
        }
        gameStateStreamObserver.onNext(getGameState());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        gameStateStreamObserver.onCompleted();
    }

    private GameState getGameState() {
        return GameState.newBuilder()
                .addPlayer(client)
                .addPlayer(server)
                .build();
    }

    private Player getNewPlayerPosition(Player player, int dieValue) {
        int position = player.getPosition() + dieValue;
        if (position <= 100) {
            player = player.toBuilder()
                    .setPosition(position)
                    .build();
        }
        return player;
    }
}
