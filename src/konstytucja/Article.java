package konstytucja;


class Article {
    private int articleNumber;
    private String chapterSubtitle;
    private String articleContent;

    Article(int n, String s, String t) {
        this.articleNumber = n;
        this.articleContent = s;
        this.chapterSubtitle = t;
    }

    int getArticleNumber() {
        return this.articleNumber;
    }

    String getArticleContent() {
        return this.articleContent;
    }

    String getChapterSubtitle() {
        return this.chapterSubtitle;
    }
}
