package konstytucja;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

//import java.util.regex.PatternSyntaxException;

class FileParser {
    private String textLine;

    Constitution parse(String filePath) throws IOException {
        Constitution result = new Constitution();
        List<Chapter> parsedChapters = new ArrayList<>();
        List<Article> parsedArticles = new ArrayList<>();

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        this.textLine = "";
        StringBuilder articleContent = new StringBuilder(this.textLine);
        StringBuilder chapterSubtitle = new StringBuilder();
        StringBuilder chapterTitle = new StringBuilder();


        int artCount = 0;
        int chapterCount = 0;
        boolean isChapterTitle = true;


        do {
            if (this.removeKancelaria()) {
                this.textLine = bufferedReader.readLine();
                continue;
            }

            if (this.findRegExp("Rozdzia")) {
                parsedArticles.add(new Article(artCount, articleContent.toString(), chapterSubtitle.toString()));
                parsedChapters.add(new Chapter(chapterCount, chapterTitle.toString(), parsedArticles));
                chapterCount++;

                chapterSubtitle = new StringBuilder("");
                chapterTitle = new StringBuilder(this.textLine);
                isChapterTitle = false;

                parsedArticles = new ArrayList<>();

                articleContent = new StringBuilder();
                this.textLine = bufferedReader.readLine();
                continue;

            }

            if (this.findRegExp("[^XI ][A-Z][A-Z]")) {
                if (!isChapterTitle) {
                	chapterTitle.append("\n");
                	chapterTitle.append(this.textLine);
                    isChapterTitle = true;
                } else {
                	chapterSubtitle = new StringBuilder(this.textLine);
                }
                this.textLine = bufferedReader.readLine();
                continue;
            }

            if (this.findRegExp("Art. ")) {
                if (!(articleContent.toString().isEmpty()))
                    parsedArticles.add(new Article(artCount, articleContent.toString(), chapterSubtitle.toString()));
                artCount++;
                articleContent = new StringBuilder();
            }

            if (this.textLine.endsWith("-")) {
                this.deleteAndConcat(bufferedReader, articleContent);
                continue;
            }

            this.addAndSet(bufferedReader, articleContent);

        } while (this.textLine != null);

        artCount++;
        parsedArticles.add(new Article(artCount, articleContent.toString(), chapterSubtitle.toString()));
        parsedChapters.add(new Chapter(chapterCount, chapterTitle.toString(), parsedArticles));

        bufferedReader.close();
        result.setChapters(parsedChapters);
        return result;
    }

    private boolean removeKancelaria() {
        return (this.textLine.length() < 2 || this.findRegExp("Kancelaria") || this.findRegExp("2009-"));
    }

    private void addAndSet(BufferedReader bReader, StringBuilder article) throws IOException {
        article.append("\n");
        article.append(this.textLine);
        this.textLine = bReader.readLine();
    }

    private boolean findRegExp(String expression) {
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(this.textLine);
        return matcher.find();
    }

    private void deleteAndConcat(BufferedReader bReader, StringBuilder article) throws IOException {
        StringBuilder that = new StringBuilder(this.textLine);
        that.deleteCharAt(this.textLine.length() - 1);

        String next = bReader.readLine();
        String[] lineBreak = next.split(" ", 2);
        that.append(lineBreak[0]);
        article.append("\n");
        article.append(that);
        if(lineBreak.length == 2)
            this.textLine = lineBreak[1];
        else
            this.textLine = bReader.readLine();
    }
}
