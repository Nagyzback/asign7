interface SupportHandler {
    void setNextHandler(SupportHandler nextHandler);
    void handleRequest(SupportRequest request);
}

abstract class BaseSupportHandler implements SupportHandler {
    private SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handleRequest(SupportRequest request) {
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

class HardwareSupportHandler extends BaseSupportHandler {
    public void handleRequest(SupportRequest request) {
        if (request.getType() == SupportRequest.Type.HARDWARE) {
            System.out.println("Hardware support team handling request: " + request);
        } else {
            super.handleRequest(request);
        }
    }
}

class SoftwareSupportHandler extends BaseSupportHandler {
    public void handleRequest(SupportRequest request) {
        if (request.getType() == SupportRequest.Type.SOFTWARE) {
            System.out.println("Software support team handling request: " + request);
        } else {
            super.handleRequest(request);
        }
    }
}

class NetworkSupportHandler extends BaseSupportHandler {
    public void handleRequest(SupportRequest request) {
        if (request.getType() == SupportRequest.Type.NETWORK) {
            System.out.println("Network support team handling request: " + request);
        } else {
            super.handleRequest(request);
        }
    }
}

class SupportRequest {
    enum Type {HARDWARE, SOFTWARE, NETWORK}

    private int id;
    private Type type;
    private String description;
    private int priority;

    public SupportRequest(int id, Type type, String description, int priority) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "SupportRequest{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}

public class HelpDeskTicketingSystem {
    public static void main(String[] args) {
        SupportHandler hardwareHandler = new HardwareSupportHandler();
        SupportHandler softwareHandler = new SoftwareSupportHandler();
        SupportHandler networkHandler = new NetworkSupportHandler();

        hardwareHandler.setNextHandler(softwareHandler);
        softwareHandler.setNextHandler(networkHandler);

        SupportRequest request1 = new SupportRequest(1, SupportRequest.Type.HARDWARE, "Hardware issue", 1);
        SupportRequest request2 = new SupportRequest(2, SupportRequest.Type.SOFTWARE, "Software issue", 2);
        SupportRequest request3 = new SupportRequest(3, SupportRequest.Type.NETWORK, "Network issue", 3);

        hardwareHandler.handleRequest(request1);
        hardwareHandler.handleRequest(request2);
        hardwareHandler.handleRequest(request3);
    }
}
