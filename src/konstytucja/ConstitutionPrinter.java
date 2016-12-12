package konstytucja;

import java.util.List;
import java.io.IOException;

class ConstitutionPrinter {
    void print(String[] args) throws IOException {
        OptionParser parser = new OptionParser().getUserOptions(args);
        Constitution constitution = new FileParser().parse(parser.getPath());

        if (parser.getPrintType() == 'r') //przelacznik rozdz/art
            this.printChapter(parser.getContentToPrint().get(0), constitution);
        else if (parser.getContentToPrint().size() == 1)
            this.printSingleArticle(parser.getContentToPrint().get(0), constitution);
        else
            this.printArticles(parser.getContentToPrint(), constitution);
    }

    private void printChapter(Integer num, Constitution constitution) {
        Chapter chapter = constitution.getChapter(num);
        List<Article> articles = chapter.getArticles();
        String prevTitle = articles.get(0).getChapterSubtitle();

        System.out.print(chapter.getChapterTitle());

        if (!(prevTitle.equals("")))
            System.out.print("\n" + prevTitle);

        for (Article art : articles) {
            System.out.print(art.getArticleContent());

            if (!(prevTitle.equals(art.getChapterSubtitle()) && !(prevTitle.equals(" ")))) {
                prevTitle = art.getChapterSubtitle();
                System.out.print("\n" + prevTitle);
            }
        }
    }

    private void printSingleArticle(Integer num, Constitution constitution) {
        if (num == 0) {
            printChapter(num, constitution);
            return;
        }

        int chaptNumber;

        for (Chapter chapter : constitution.getChaptersList()) {
            if (chapter.getFirstArticleNumber() > num) {
                chaptNumber = chapter.getChapterNumber() - 1;

                for (Article artykul : constitution.getChapter(chaptNumber).getArticles()) {
                    if (artykul.getArticleNumber() == num) {
                        System.out.println(artykul.getArticleContent());
                        break;
                    }
                }
                break;
            }
        }
    }

    private void printArticles(List<Integer> content, Constitution constitution) {
        if(content.get(0) < content.get(1)) {
            for (int i = content.get(0); i <= content.get(1); i++)
                printSingleArticle(i, constitution);
        }
        else {
            for (int i = content.get(1); i <= content.get(0); i++)
                printSingleArticle(i, constitution);
        }
    }
}