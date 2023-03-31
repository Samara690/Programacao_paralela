package model;

import java.io.ObjectOutputStream;

import controller.Server;

public class Client {
    ObjectOutputStream output;
    Server server;
    String id;

    public Client(String id, ObjectOutputStream output, Server server) {
        this.output = output;
        this.server = server;
        this.id = id;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public String getUserId() {
        return id;
    }

    public void setUserId(String id) {
        this.id = id;
    }
}
