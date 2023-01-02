package q11.confluence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfluencePage extends ConfluenceNode {
    private String content;
    private String title;
    private String status;
    private List<ConfluenceAuthor> contributors = new ArrayList<>();

    public ConfluencePage() {
        this.status = "Editing";
    }

    public String interact(String using, ConfluenceAuthor interactor) {
        switch (status) {
            case "Editing":
                pageEdit(using, interactor);
                return "";
            case "Viewing":
                return pageView();
            case "Publishing":
                pagePublish(using);
                return "";
            case "Published":
                return pagePublished();
            default:
                return null;
        }
    }

    public void pageEdit(String content, ConfluenceAuthor author){
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Document content cannot be empty");
        }
        if (!contributors.contains(author)) {
            contributors.add(author);
        }
        this.content = content;
    }

    public String pageView(){
        return  "==== Draft in Progress === \n" +
                this.content.length() + " characters long \n" +
                "=============================\n" + this.content;
    }

    public void pagePublish(String title){
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        } else if (title.length() > 50) {
            throw new IllegalArgumentException("Title cannot be > 50 characters long");
        }
        this.title = title;
    }

    public String pagePublished(){
        return "==== " + this.title + " === \n" +
                "By " + getContributors() + "\n" +
                "=============================\n" + this.content;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public int getNumPages() { return 1;}
    private String getContributors() {
        return this.contributors
                .stream()
                .map(item -> new String(item.getName()))
                .collect(Collectors.joining(", "));
    }
}