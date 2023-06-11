package com.me.server;

import com.me.game.Die;
import com.me.game.GameServiceGrpc;
import com.me.game.GameState;
import com.me.game.Player;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {
    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
        Player client = Player.newBuilder().setName("client").setPosition(0).build();
        Player server = Player.newBuilder().setName("server").setPosition(0).build();
        return new DieStreamingRequest(client, server, responseObserver);
    }
}
