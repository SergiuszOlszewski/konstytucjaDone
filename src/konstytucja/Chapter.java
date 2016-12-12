package konstytucja;

import java.util.ArrayList;
import java.util.List;

class Chapter {
    private int chapterNumber;
    private String chapterTitle;
    private List<Article> articles = new ArrayList<>();

    Chapter(int n, String title, List<Article> articleList) {
        this.chapterNumber = n;
        this.chapterTitle = title;
        this.articles = articleList;
    }

    List<Article> getArticles() {
        return this.articles;
    }

    int getChapterNumber() {
        return this.chapterNumber;
    }

    int getFirstArticleNumber() {
        return this.articles.get(0).getArticleNumber();
    }

    String getChapterTitle() {
        return this.chapterTitle;
    }
}
