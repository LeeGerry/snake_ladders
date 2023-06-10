package com.me.server;

import com.me.game.Die;
import com.me.game.GameServiceGrpc;
import com.me.game.GameState;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {
    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
        return super.roll(responseObserver);
    }
}
