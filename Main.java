package pack1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
interface DocumentStorage {
    void uploadDocument(String document, String user);
    String downloadDocument(String document, String user);
    void editDocument(String document, String content, String user);
    List<String> searchDocuments(String query, String user);
}
class RealDocumentStorage implements DocumentStorage {
    private Map<String, String> documents = new HashMap<>();

    @Override
    public void uploadDocument(String document, String user) {
        documents.put(document, user);
    }

    @Override
    public String downloadDocument(String document, String user) {
        if (documents.containsKey(document)) {
            return documents.get(document);
        }
        return null;
    }
    @Override
    public void editDocument(String document, String content, String user) {
        if (documents.containsKey(document)) {
            documents.put(document, content);
        }
    }
    @Override
    public List<String> searchDocuments(String query, String user) {
        List<String> results = new ArrayList<>();
        for (Map.Entry<String, String> entry : documents.entrySet()) {
            if (entry.getKey().contains(query)) {
                results.add(entry.getKey());
            }
        }
        return results;
    }
}
class ProxyDocumentStorage implements DocumentStorage {
    private RealDocumentStorage realDocumentStorage = new RealDocumentStorage();
    private Map<String, String> sessions = new HashMap<>();
    @Override
    public void uploadDocument(String document, String user) {
        if (isValidSession(user)) {
            realDocumentStorage.uploadDocument(document, user);
        }
    }
    @Override
    public String downloadDocument(String document, String user) {
        if (isValidSession(user)) {
            return realDocumentStorage.downloadDocument(document, user);
        }
        return null;
    }
    @Override
    public void editDocument(String document, String content, String user) {
        if (isValidSession(user)) {
            realDocumentStorage.editDocument(document, content, user);
        }
    }
    @Override
    public List<String> searchDocuments(String query, String user) {
        if (isValidSession(user)) {
            return realDocumentStorage.searchDocuments(query, user);
        }
        return new ArrayList<>();
    }
    private boolean isValidSession(String user) {
        return sessions.containsKey(user);
    }
    public void login(String user) {
        sessions.put(user, "active");
    }
    public void logout(String user) {
        sessions.remove(user);
    }
}
class UserAuthentication {
    public boolean authenticate(String user, String password) {
        return user.equals("admin") && password.equals("password");
    }
}
class Logger {
    public void log(String message) {
        System.out.println(message);
    }
}

public class Main {
    public static void main(String[] args) {
        ProxyDocumentStorage proxy = new ProxyDocumentStorage();
        UserAuthentication authentication = new UserAuthentication();
        String user = "admin";
        String password = "password";
        if (authentication.authenticate(user, password)) {
            proxy.login(user);
            proxy.uploadDocument("Document1", user);
            proxy.editDocument("Document1", "Updated content", user);
            System.out.println(proxy.downloadDocument("Document1", user));
            System.out.println(proxy.searchDocuments("Document", user));
            proxy.logout(user);
        } else {
            System.out.println("Authentication failed");
        }
    }
}
