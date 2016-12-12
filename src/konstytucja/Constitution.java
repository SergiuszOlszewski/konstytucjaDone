package konstytucja;

import java.util.ArrayList;
import java.util.List;

class Constitution {
    private List<Chapter> chapters = new ArrayList<>();

    List<Chapter> getChaptersList() {
        return this.chapters;
    }

    Chapter getChapter(int i) {
        return this.chapters.get(i);
    }

    void setChapters(List<Chapter> chapterList) {
        this.chapters = chapterList;
    }

}

