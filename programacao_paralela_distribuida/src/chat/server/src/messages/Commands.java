package messages;

public enum Commands {
    WHISPER(":whisper", ":whisper USER_ID MESSAGE"),
    HELP(":help", ":help"),
    EXIT(":bye", ":bye");

    private String command;
    private String sample;

    private Commands(String command, String sample){
        this.command = command;
        this.sample = sample;
    }

    public String getCommand(){
        return this.command;
    }

    public String getSample(){
        return this.sample;
    }
}
