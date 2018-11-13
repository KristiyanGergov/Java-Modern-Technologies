package bg.sofia.uni.fmi.mjt.git;

public class Result {
    private String message;
    private boolean successful;

    public Result() {
        this.successful = false;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
